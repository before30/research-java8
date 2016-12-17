package cc.before30.example.tobytv008;

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
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Created by before30 on 17/12/2016.
 */
@SpringBootApplication
@Slf4j
@EnableAsync
public class FutureApplicationEx {

    @Component
    public static class MyService {

        // 굉장히 오랜 시간을 사용하는 경우에는 이점이 있다.
        @Async
        public Future<String> hello() throws InterruptedException {
            log.info("hello()");
            TimeUnit.SECONDS.sleep(1);
            return new AsyncResult<>("Hello");
        }

        @Async
        public ListenableFuture<String> hello2()  throws InterruptedException {
            log.info("hello()");
            TimeUnit.SECONDS.sleep(1);
            return new AsyncResult<>("Hello");
        }

        @Async
        public CompletableFuture<String> hello3()  throws InterruptedException {
            log.info("hello()");
            TimeUnit.SECONDS.sleep(1);

            return new CompletableFuture<String>() {
                @Override
                public String get() throws InterruptedException, ExecutionException {
                    return "hello";
                }
            };
        }
    }


    public static void main(String[] args) {
        try(ConfigurableApplicationContext c = SpringApplication.run(FutureApplicationEx.class, args)) {

        }
    }

    @Autowired MyService myService;

    @Bean
    ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10); // 초기 값
        executor.setMaxPoolSize(100); //
        executor.setQueueCapacity(200); //Core -> Queue -> MaxPoolsize로 간다
//        executor.setAllowCoreThreadTimeOut();
//        executor.setKeepAliveSeconds();
//        executor.setTaskDecorator(); // log를 걸어서 ㅇ얼마나 사용 반환 되는가 분석을 위해
        executor.setThreadNamePrefix("myThread");
        return executor;
    }

    @Bean
    ApplicationRunner run() {
//        return args -> {
//            log.info("run()");
//            Future<String> res = myService.hello();
//            log.info("exit {}", res.isDone());
//            log.info("result {}", res.get());
//        };
        return args -> {
            log.info("run()");
            ListenableFuture<String> f = myService.hello2();
            f.addCallback(s -> System.out.println(s), e -> System.out.println(e.getMessage()));
            log.info("exit");
        };
    }
}
