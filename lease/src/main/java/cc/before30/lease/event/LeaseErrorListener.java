package cc.before30.lease.event;

/**
 * Created by before30 on 10/05/2017.
 */
public interface LeaseErrorListener {
    void onLeaseError(SecretLeaseEvent leaseEvent, Exception exception);
}
