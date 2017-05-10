package cc.before30.lease.event;

import cc.before30.lease.domain.Lease;
import cc.before30.lease.domain.RequestedSecret;
import lombok.Getter;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by before30 on 10/05/2017.
 */
@Getter
public class SecretLeaseCreatedEvent extends SecretLeaseEvent {

    private static final long serialVersionUID = 1L;

    private final Map<String, Object> secrets;

    protected SecretLeaseCreatedEvent(RequestedSecret requestedSecret, Lease lease,
                                      Map<String, Object> secrets) {
        super(requestedSecret, lease);
        this.secrets = Collections.unmodifiableMap(new HashMap<>(secrets));
    }

}
