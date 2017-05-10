package cc.before30.lease.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.util.Assert;

/**
 * Created by before30 on 10/05/2017.
 */
@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class Lease {

    private static final Lease NONE = new Lease(null, 0, false);

    private final String leaseId;

    private final long leaseDuration;

    private final boolean renewable;

    public static Lease of(String leaseId, long leaseDuration, boolean renewable) {

        Assert.hasText(leaseId, "LeaseId must not be empty");
        return new Lease(leaseId, leaseDuration, renewable);
    }

    public static Lease none() {
        return NONE;
    }

}
