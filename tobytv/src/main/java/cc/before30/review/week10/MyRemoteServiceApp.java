package cc.before30.review.week10;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * Created by before30 on 08/01/2017.
 */
@SpringBootApplication
public class MyRemoteServiceApp {

    @RestController
    public static class MyController {
        @GetMapping("/src1")
            public String service1(String req) {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return req + "/src1";
            }

        @GetMapping("/src2")
        public String service2(String req) {

            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return req + "/src2";
        }
    }

    public static void main(String[] args) {
        System.setProperty("SERVER_PORT", "8081");
        System.setProperty("server.tomcat.max-threads", "1000");
        SpringApplication.run(MyRemoteServiceApp.class, args);
    }
}
