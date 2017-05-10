package cc.before30.lease.event;

import cc.before30.lease.domain.Lease;
import cc.before30.lease.domain.RequestedSecret;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * Created by before30 on 10/05/2017.
 */
@Getter
public abstract class SecretLeaseEvent extends ApplicationEvent {

    private static final long serialVersionUID = 1L;

    private final Lease lease;

    protected SecretLeaseEvent(RequestedSecret requestedSecret, Lease lease) {
        super(requestedSecret);

        this.lease = lease;
    }

    @Override
    public RequestedSecret getSource() {
        return (RequestedSecret)super.getSource();
    }
}
