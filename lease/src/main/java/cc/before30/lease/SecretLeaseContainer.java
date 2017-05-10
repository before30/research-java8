package cc.before30.lease;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * Created by before30 on 10/05/2017.
 */
public class SecretLeaseContainer extends SecretLeaseEventPublisher implements
        InitializingBean, DisposableBean {

    @Override
    public void destroy() throws Exception {

    }
}
