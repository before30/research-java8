package cc.before30.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;

/**
 * Created by before30 on 04/01/2017.
 */
@Slf4j
public class EmbeddedServletContainerConfiguration implements EmbeddedServletContainerCustomizer{

    @Value("${servlet.container.maxThreads}")
    private int MAX_THREADS;


    @Override
    public void customize(ConfigurableEmbeddedServletContainer container) {
        if (container instanceof TomcatEmbeddedServletContainerFactory) {
            customizeTomcat((TomcatEmbeddedServletContainerFactory)container);
        }
    }

    public void customizeTomcat(TomcatEmbeddedServletContainerFactory factory) {
        factory.addConnectorCustomizers(connector -> {
            Object defaultMaxThreads = connector.getAttribute("maxThreads");
            connector.setAttribute("maxThreads", MAX_THREADS);
            log.info("tomcat connector maxthreads changed {} to  {}", defaultMaxThreads, MAX_THREADS);
        });
    }
}
