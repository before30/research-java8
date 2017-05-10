package cc.before30.lease.event;

import cc.before30.lease.domain.Lease;
import cc.before30.lease.domain.RequestedSecret;

/**
 * Created by before30 on 10/05/2017.
 */
public class AfterSecretLeaseRenewedEvent extends SecretLeaseEvent {

    private static final long serialVersionUID = 1L;

    public AfterSecretLeaseRenewedEvent(RequestedSecret requestedSecret, Lease lease) {
        super(requestedSecret, lease);
    }
}
