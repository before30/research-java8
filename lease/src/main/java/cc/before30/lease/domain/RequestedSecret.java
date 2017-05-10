package cc.before30.lease.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.util.Assert;

/**
 * Created by before30 on 10/05/2017.
 */
@AllArgsConstructor
@ToString
@Getter
public class RequestedSecret {

    private final String path;
    private final Mode mode;

    public static RequestedSecret renewable(String path) {
        return new RequestedSecret(path, Mode.RENEW);
    }

    public static RequestedSecret rotating(String path) {
        return new RequestedSecret(path, Mode.ROTATE);
    }

    public static RequestedSecret from(Mode mode, String path) {
        Assert.notNull(mode, "Mode must not be null");
        return mode == Mode.ROTATE ? rotating(path) : renewable(path);
    }

    public enum Mode {
        /* renew lease of the requested secret until secret expires its max lease time.
         */
        RENEW,

        /* Renew lease of the requested secret.
        Obtains new secret along a new lease once the previous lease expires its max lease time
         */
        ROTATE;
    }
}
