package cc.before30.contorller;

import com.sun.xml.internal.ws.util.CompletedFuture;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskDecorator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Queue;
import java.util.concurrent.*;

/**
 * Created by before30 on 13/01/2017.
 */
@RestController
@Slf4j
public class MyController {

//    @GetMapping("/rest")
//    public String blocking(String idx) throws InterruptedException {
//
//        TimeUnit.SECONDS.sleep(2);
//
//        return idx + "/rest";
//
//    }

//    @GetMapping("/rest")
//    @Async
//    public ListenableFuture<String> rest(String idx) throws InterruptedException {
//
//        TimeUnit.SECONDS.sleep(2);
//
//        return new AsyncResult<>(idx + "/rest");
//    }

    @Bean
    ThreadPoolTaskExecutor tp() {
        ThreadPoolTaskExecutor te = new ThreadPoolTaskExecutor();
        te.setCorePoolSize(10);
        te.setMaxPoolSize(100);
        te.setQueueCapacity(200);
        te.setThreadNamePrefix("mythreadpool");
        te.setTaskDecorator(new TaskDecorator() {
            @Override
            public Runnable decorate(Runnable runnable) {

                return () -> {
                    log.info("before run");
                    runnable.run();
                    log.info("after run");
                };
            }
        });
        te.initialize();
        return te;
    }

//    @GetMapping("/rest")
//    @Async(value = "tp")
//    public CompletableFuture<String> rest(String idx) throws InterruptedException {
////        CompletableFuture<String> future = new CompletableFuture<>();
//
//        log.info("request");
//        TimeUnit.SECONDS.sleep(2);
//        log.info("request...");
//        return CompletableFuture.completedFuture(idx + "/rest");
//    }

//    @GetMapping("/rest")
//    public Callable<String> rest(String idx) {
//        log.info("cbefore callable");
//        return new Callable<String>() {
//            @Override
//            public String call() throws Exception {
//                log.info("async");
//                TimeUnit.SECONDS.sleep(2);
//                return idx + "/rest";
//            }
//        };
//    }

    @GetMapping("/rest")
    public DeferredResult<String> rest(String idx) {
        log.info("deferred result");
        DeferredResult<String> dr = new DeferredResult<>();

        Executors.newSingleThreadExecutor().submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            dr.setResult(idx + "/rest");
        });

        return dr;
    }

//    @GetMapping("/rest")
//    public DeferredResult<String> rest(String idx) throws InterruptedException {
//        DeferredResult result = new DeferredResult();
//
//        FutureTask<String> f = new FutureTask<String>(() -> {
//            try {
//                TimeUnit.SECONDS.sleep(2);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            return idx + "/rest";
//        });
//
//        ListenableFuture<String> future = new AsyncResult<>(idx + "/rest");
//        future.addCallback(s -> {
//            try {
//                TimeUnit.SECONDS.sleep(2);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            result.setResult(s);
//        }, e -> {});
//        return result;
//    }
}