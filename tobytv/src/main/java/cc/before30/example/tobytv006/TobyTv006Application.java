package cc.before30.example.tobytv006;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by before30 on 03/12/2016.
 */
@SpringBootApplication
public class TobyTv006Application {
    public static void main(String[] args) {
        SpringApplication.run(TobyTv006Application.class, args);
    }

    @RestController
    public static class Controller {
        @RequestMapping("/hello")
        public Publisher<String> hello(String name) {
            return new Publisher<String>() {

                @Override
                public void subscribe(Subscriber<? super String> s) {
                    s.onSubscribe(new Subscription() {
                        @Override
                        public void request(long n) {
                            s.onNext("Hello " + name);
                        }

                        @Override
                        public void cancel() {

                        }
                    });
                }
            };
        }
    }
}
