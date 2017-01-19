package cc.before30.contorller;

import com.sun.xml.internal.ws.util.CompletedFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskDecorator;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.Netty4ClientHttpRequestFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;
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

//    @GetMapping("/rest")
//    public DeferredResult<String> rest(String idx) {
//        log.info("deferred result");
//        DeferredResult<String> dr = new DeferredResult<>();
//
//        Executors.newSingleThreadExecutor().submit(() -> {
//            try {
//                TimeUnit.SECONDS.sleep(2);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            dr.setResult(idx + "/rest");
//        });
//
//        return dr;
//    }

//    Queue<DeferredResult<String>> results = new ConcurrentLinkedDeque<>();
//
//    @GetMapping("/rest")
//    public DeferredResult<String> rest(String idx) {
//        DeferredResult<String> deferredResult = new DeferredResult<>();
//        results.add(deferredResult);
//
//        return deferredResult;
//    }
//
//    @GetMapping("/rest/count")
//    public String count() {
//        return String.valueOf(results.size());
//    }
//
//    @GetMapping("/rest/event")
//    public String event(String msg) {
//        for(DeferredResult<String> dr : results) {
//            dr.setResult("Hello " + msg);
//            results.remove(dr);
//        }
//        return "OK";
//    }

    AsyncRestTemplate restTemplate = new AsyncRestTemplate(new Netty4ClientHttpRequestFactory(new NioEventLoopGroup(1)));
    String url1 = "http://localhost:8888/service?req={req}";

    @GetMapping("/rest")
    public DeferredResult<String> rest(String idx) {
        DeferredResult<String> result = new DeferredResult<>();
        ListenableFuture<ResponseEntity<String>> response = restTemplate.getForEntity(url1, String.class, idx);
        response.addCallback(s -> {
            result.setResult(s.getBody());
        }, e -> {
            result.setErrorResult(e.getMessage());
        });


        return result;

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
