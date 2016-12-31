package cc.before30.review.week07;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.Future;

/**
 * Created by before30 on 31/12/2016.
 */
@Slf4j
@SpringBootApplication
@EnableAsync
public class Week8Application {

    public static void main(String[] args) {
        try (ConfigurableApplicationContext c = SpringApplication.run(Week8Application.class, args)) {
        }
    }

    @Component
    public static class MyService {
        @Async
        public Future<String> hello() throws InterruptedException {
            log.info("hello()");
            Thread.sleep(2000);
            return new AsyncResult<>("hello");
        }

        @Async
        public ListenableFuture<String> hello2()  throws InterruptedException {
            log.info("hello2()");
            Thread.sleep(2000);
            return new AsyncResult<>("hello2");

        }
    }


    @Autowired MyService myService;

    @Bean
    ApplicationRunner run() {
        return args -> {
            log.info("run()");
            Future<String> f = myService.hello();
            log.info("exit: {}", f.isDone());
            log.info("result: {}", f.get());
        };
    }

    @Bean
    ApplicationRunner run2() {
        return args -> {
            log.info("run()2");
            ListenableFuture<String> f = myService.hello2();
            f.addCallback(s -> System.out.println(s), e -> System.out.println(e.getMessage()));
            log.info("exit");
        };
    }
}
