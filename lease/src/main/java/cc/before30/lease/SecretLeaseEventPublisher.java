package cc.before30.lease;

import cc.before30.lease.domain.Lease;
import cc.before30.lease.domain.RequestedSecret;
import cc.before30.lease.event.*;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created by before30 on 10/05/2017.
 */
public class SecretLeaseEventPublisher implements InitializingBean {

    private final Set<LeaseListener> leaseListeners = new CopyOnWriteArraySet<>();

    private final Set<LeaseErrorListener> leaseErrorListeners = new CopyOnWriteArraySet<>();

    public void addLeaseListener(LeaseListener listener) {
        Assert.notNull(listener, "LeaseListener must not be null");
        this.leaseListeners.add(listener);
    }

    public void removeLeaseListener(LeaseListener listener) {
        this.leaseListeners.remove(listener);
    }

    public void addErrorListener(LeaseErrorListener listener) {
        Assert.notNull(listener, "LeaseListener must not be null");
        this.leaseErrorListeners.add(listener);
    }

    public void removeLeaseErrorListener(LeaseErrorListener listener) {
        this.leaseErrorListeners.remove(listener);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (this.leaseErrorListeners.isEmpty()) {
            addErrorListener(LoggingErrorListener.INSTANCE);
        }
    }

    protected void onSecretsObtained(RequestedSecret requestedSecret, Lease lease,
                                     Map<String, Object> body) {

        for (LeaseListener leaseListener : leaseListeners) {
            leaseListener.onLeaseEvent(new SecretLeaseCreatedEvent(requestedSecret,
                    lease, body));
        }
    }

    protected void onAfterLeaseRenewed(RequestedSecret requestedSecret, Lease lease) {

        for (LeaseListener leaseListener : leaseListeners) {
            leaseListener.onLeaseEvent(new AfterSecretLeaseRenewedEvent(requestedSecret,
                    lease));
        }
    }

    protected void onBeforeLeaseRevocation(RequestedSecret requestedSecret, Lease lease) {

        for (LeaseListener leaseListener : leaseListeners) {
            leaseListener.onLeaseEvent(new BeforeSecretLeaseRevocationEvent(
                    requestedSecret, lease));
        }
    }

    protected void onAfterLeaseRevocation(RequestedSecret requestedSecret, Lease lease) {

        for (LeaseListener leaseListener : leaseListeners) {
            leaseListener.onLeaseEvent(new AfterSecretLeaseRevocationEvent(
                    requestedSecret, lease));
        }
    }

    protected void onLeaseExpired(RequestedSecret requestedSecret, Lease lease) {

        for (LeaseListener leaseListener : leaseListeners) {
            leaseListener
                    .onLeaseEvent(new SecretLeaseExpiredEvent(requestedSecret, lease));
        }
    }

    protected void onError(RequestedSecret requestedSecret, Lease lease, Exception e) {

        for (LeaseErrorListener leaseErrorListener : leaseErrorListeners) {
            leaseErrorListener.onLeaseError(new SecretLeaseErrorEvent(requestedSecret,
                    lease, e), e);
        }
    }

    @CommonsLog
    public enum LoggingErrorListener implements LeaseErrorListener {
        INSTANCE;

        @Override
        public void onLeaseError(SecretLeaseEvent leaseEvent, Exception exception) {
            log.warn(
                    String.format("[%s] %s %s", leaseEvent.getSource(),
                            leaseEvent.getLease(), exception.getMessage()), exception);
        }
    }
}
