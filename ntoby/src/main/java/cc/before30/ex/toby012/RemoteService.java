package cc.before30.ex.toby012;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatReactiveWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;

/**
 * Created by before30 on 31/12/2016.
 */
@SpringBootApplication
public class RemoteService {

//    @Bean
//    TomcatReactiveWebServerFactory tomcatReactiveWebServerFactory() {
//        TomcatReactiveWebServerFactory tomcatReactiveWebServerFactory = new TomcatReactiveWebServerFactory();
//        tomcatReactiveWebServerFactory.setPort(8081);
//        return tomcatReactiveWebServerFactory;
//    }

    @RestController
    public static class MyController {
        @GetMapping("/service")
        public Mono<String> service(String req) throws InterruptedException {
            TimeUnit.SECONDS.sleep(1);
            return Mono.just(req + "/service1");
        }

        @GetMapping("/service2")
        public Mono<String> service2(String req) throws InterruptedException {
            TimeUnit.SECONDS.sleep(1);
            return Mono.just(req + "/service2");
        }
    }

    public static void main(String[] args) {
        System.setProperty("SERVER_PORT", "8081");
        System.setProperty("server.tomcat.max-threads", "1000");
        SpringApplication.run(RemoteService.class, args);
    }
}
