package cc.before30.lease.event;

/**
 * Created by before30 on 10/05/2017.
 */
public class LeaseListenerAdapter implements LeaseListener, LeaseErrorListener {
    @Override
    public void onLeaseEvent(SecretLeaseEvent leaseEvent) {
        // empty listener method
    }

    @Override
    public void onLeaseError(SecretLeaseEvent leaseEvent, Exception exception) {
        // empty listener method
    }
}
