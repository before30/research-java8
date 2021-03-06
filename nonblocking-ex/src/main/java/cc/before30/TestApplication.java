package cc.before30;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * Created by before30 on 20/01/2017.
 */
@SpringBootApplication
public class TestApplication {

    @RestController
    public static class MyController {
        @GetMapping("/service")
        public String service(String req) {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return req + "/service1";
        }

        @GetMapping("/service2")
        public String service2(String req) {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return req + "/service2";
        }
    }

    public static void main(String[] args) {
        System.setProperty("SERVER_PORT", "8888");
        System.setProperty("server.tomcat.max-threads", "1000");
        SpringApplication.run(TestApplication.class, args);
    }
}
