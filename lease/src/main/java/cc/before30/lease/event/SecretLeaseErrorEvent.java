package cc.before30.lease.event;

import cc.before30.lease.domain.Lease;
import cc.before30.lease.domain.RequestedSecret;
import lombok.Getter;

/**
 * Created by before30 on 10/05/2017.
 */
@Getter
public class SecretLeaseErrorEvent extends SecretLeaseEvent {

    private static final long serialVersionUID = 1L;

    private final Throwable exception;

    public SecretLeaseErrorEvent(RequestedSecret requestedSecret, Lease lease, Throwable exception) {
        super(requestedSecret, lease);
        this.exception = exception;
    }
}
