package cc.before30.lease;

import cc.before30.lease.domain.Lease;
import cc.before30.lease.domain.RequestedSecret;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by before30 on 10/05/2017.
 */
public class SecretLeaseContainer extends SecretLeaseEventPublisher implements
        InitializingBean, DisposableBean {

    private static final AtomicIntegerFieldUpdater<SecretLeaseContainer> UPDATER = AtomicIntegerFieldUpdater.newUpdater(SecretLeaseContainer.class, "status");

    private static final AtomicInteger poolId = new AtomicInteger();

    private static final int STATUS_INITIAL = 0;
    private static final int STATUS_STARTED = 1;
    private static final int STATUS_DESTROYED = 2;

    private final List<RequestedSecret> requestedSecrets = new CopyOnWriteArrayList<>();

    private final Map<RequestedSecret, LeaseRen>


    @Override
    public void destroy() throws Exception {
        int status = this.status;

        if (status == STATUS_INITIAL || status == STATUS_STARTED) {

            if (UPDATER.compareAndSet(this, status, STATUS_DESTROYED)) {

                for (Entry<RequestedSecret, LeaseRenewalScheduler> entry : renewals
                        .entrySet()) {

                    Lease lease = entry.getValue().getLease();
                    entry.getValue().disableScheduleRenewal();

                    if (lease != null) {
                        doRevokeLease(entry.getKey(), lease);
                    }
                }

                if (manageTaskScheduler) {

                    if (this.taskScheduler instanceof DisposableBean) {
                        ((DisposableBean) this.taskScheduler).destroy();
                        this.taskScheduler = null;
                    }
                }
            }
        }
    }

    void potentiallyScheduleLeaseRenewal(final RequestedSecret requestedSecret,
                                         final Lease lease, final LeaseRenewalScheduler leaseRenewal) {

        if (leaseRenewal.isLeaseRenewable(lease)) {

            if (log.isDebugEnabled()) {
                log.debug(String.format("Lease %s qualified for renewal",
                        lease.getLeaseId()));
            }

            leaseRenewal.scheduleRenewal(new RenewLease() {

                @Override
                public Lease renewLease(Lease lease) {

                    Lease newLease = doRenewLease(requestedSecret, lease);

                    if (newLease == null) {
                        return null;
                    }

                    potentiallyScheduleLeaseRenewal(requestedSecret, newLease,
                            leaseRenewal);

                    onAfterLeaseRenewed(requestedSecret, newLease);

                    return newLease;
                }
            }, lease, getMinRenewalSeconds(), getExpiryThresholdSeconds());
        }
    }


    protected VaultResponseSupport<Map<String, Object>> doGetSecrets(
            RequestedSecret requestedSecret) {

        try {
            return this.operations.read(requestedSecret.getPath());
        }
        catch (RuntimeException e) {

            onError(requestedSecret, Lease.none(), e);
            return null;
        }
    }

    protected Lease doRenewLease(RequestedSecret requestedSecret, final Lease lease) {

        try {
            ResponseEntity<Map<String, Object>> entity = operations
                    .doWithSession(new RestOperationsCallback<ResponseEntity<Map<String, Object>>>() {

                        @Override
                        @SuppressWarnings("unchecked")
                        public ResponseEntity<Map<String, Object>> doWithRestOperations(
                                RestOperations restOperations) {
                            return (ResponseEntity) restOperations.exchange(
                                    "/sys/renew/{leaseId}", HttpMethod.PUT, null,
                                    Map.class, lease.getLeaseId());
                        }
                    });

            Map<String, Object> body = entity.getBody();
            String leaseId = (String) body.get("lease_id");
            Number leaseDuration = (Number) body.get("lease_duration");
            boolean renewable = (Boolean) body.get("renewable");

            if (leaseDuration == null || leaseDuration.intValue() < minRenewalSeconds) {
                onLeaseExpired(requestedSecret, lease);
                return null;
            }

            return Lease.of(leaseId, leaseDuration.longValue(), renewable);
        }
        catch (HttpStatusCodeException e) {

            if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                onLeaseExpired(requestedSecret, lease);
            }

            onError(requestedSecret,
                    lease,
                    new VaultException(String.format("Cannot renew lease: %s",
                            VaultResponses.getError(e.getResponseBodyAsString()))));
        }
        catch (RuntimeException e) {
            onError(requestedSecret, lease, e);
        }

        return null;
    }

    protected void onLeaseExpired(RequestedSecret requestedSecret, Lease lease) {

        super.onLeaseExpired(requestedSecret, lease);

        if (requestedSecret.getMode() == Mode.ROTATE) {
            start(requestedSecret, renewals.get(requestedSecret));
        }
    }

    protected void doRevokeLease(RequestedSecret requestedSecret, final Lease lease) {
        try {

            onBeforeLeaseRevocation(requestedSecret, lease);

            operations
                    .doWithSession(new RestOperationsCallback<ResponseEntity<Map<String, Object>>>() {

                        @Override
                        @SuppressWarnings("unchecked")
                        public ResponseEntity<Map<String, Object>> doWithRestOperations(
                                RestOperations restOperations) {
                            return (ResponseEntity) restOperations.exchange(
                                    "/sys/revoke/{leaseId}", HttpMethod.PUT, null,
                                    Map.class, lease.getLeaseId());
                        }
                    });

            onAfterLeaseRevocation(requestedSecret, lease);
        }
        catch (HttpStatusCodeException e) {
            onError(requestedSecret,
                    lease,
                    new VaultException(String.format("Cannot revoke lease: %s",
                            VaultResponses.getError(e.getResponseBodyAsString()))));
        }
        catch (RuntimeException e) {
            onError(requestedSecret, lease, e);
        }
    }

    @CommonsLog
    static class LeaseRenewalScheduler {
        private final TaskScheduler taskScheduler;

        final AtomicReference<Lease> currentLeaseRef = new AtomicReference<>();

        final Map<Lease, ScheduledFuture<?>> schedules = new ConcurrentHashMap<Lease, ScheduledFuture<?>>();

        LeaseRenewalScheduler(TaskScheduler taskScheduler) {
            this.taskScheduler = taskScheduler;
        }

        void scheduleRenewal(final RenewLease renewLease, final Lease lease,
                             final int minRenewalSeconds, final int expiryThresholdSeconds) {
            log.debug(
                    String.format("Scheduling renewal for lease %s, lease duration %d",
                    lease.getLeaseId(), lease.getLeaseDuration())
            );

            Lease currentLease = this.currentLeaseRef.get();
            this.currentLeaseRef.set(lease);

            if (currentLease != null) {
                cancelSchedule(currentLease);
            }

            ScheduledFuture<?> scheduledFuture = taskScheduler.schedule(
                    () -> {
                        try {
                            schedules.remove(lease);
                            if (currentLeaseRef.get() != lease) {
                                log.debug("Current lease has changed. Skipping renewal");
                                return;
                            }

                            log.debug(String.format("Renewing lease %s", lease.getLeaseId()));

                            currentLeaseRef.compareAndSet(lease,
                                    renewLease.renewLease(lease));
                        } catch (Exception ex) {
                            log.error(
                                    String.format("Cannot renew lease %s",
                                            lease.getLeaseId()), ex
                            );
                        }
                    },
                    new OneShotTrigger(getRenewalSeconds(lease, minRenewalSeconds, expiryThresholdSeconds))
            );

            schedules.put(lease, scheduledFuture);
        }

        private void cancelSchedule(Lease lease) {
            ScheduledFuture<?> scheduledFuture = schedules.get(lease);
            if (scheduledFuture != null) {
                log.debug(
                        String.format(
                                "Canceling previously registered schedule for lease %s",
                                lease.getLeaseId())
                );
                scheduledFuture.cancel(false);
            }
        }

        void disableScheduleRenewal() {
            currentLeaseRef.set(null);
            Set<Lease> leases = new HashSet<Lease>(schedules.keySet());
            for (Lease lease : leases) {
                cancelSchedule(lease);
                schedules.remove(lease);
            }
        }
        private long getRenewalSeconds(Lease lease, int minRenewalSeconds, int expiryThresholdSeconds) {
            return Math.max(minRenewalSeconds, lease.getLeaseDuration() - expiryThresholdSeconds);
        }
        private boolean isLeaseRenewable(Lease lease) {
            return lease != null && lease.isRenewable();
        }

        public Lease getLease() {
            return currentLeaseRef.get();
        }
    }

    static class OneShotTrigger implements Trigger {

        private static final AtomicIntegerFieldUpdater<OneShotTrigger> UPDATER = AtomicIntegerFieldUpdater.newUpdater(OneShotTrigger.class, "status");

        private static final int STATUS_ARMED = 0;
        private static final int STATUS_FIRED = 1;
        private volatile int status = 0;
        private final long seconds;

        OneShotTrigger(long seconds) {
            this.seconds = seconds;
        }

        @Override
        public Date nextExecutionTime(TriggerContext triggerContext) {
            if (UPDATER.compareAndSet(this, STATUS_ARMED, STATUS_FIRED)) {
                return new Date(System.currentTimeMillis()
                        + TimeUnit.SECONDS.toMillis(seconds));
            }
            return null;
        }
    }

    interface RenewLease {
        Lease renewLease(Lease lease) throws Exception;
    }
}
