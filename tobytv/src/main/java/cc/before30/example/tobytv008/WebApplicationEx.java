package cc.before30.example.tobytv008;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by before30 on 17/12/2016.
 */
@SpringBootApplication
@Slf4j
@EnableAsync
public class WebApplicationEx {

    // blocking io 사용시 context change2번 발생
    // inpustream 기본으로 사용하면 blocking 방식이 된다
    // NIO만 사용한다는것은 장점이 없었다 왜나면 뒤에 워커들은 결국 Thread가 하나씩 있어야했다. (Blocking 이니까)
    // 빨리 쓰고 pool 반납이라면. 괜찮은 방식이지만
    // 서블릿 스레드 안에서 blocking io가 긴것이 있다면 굉장히 안좋다.
    // DB, CPU Bound, API요청 등을 Servlet Thread가 아닌 Worker Thread로 하면 더 좋을꺼다.
    // 서블릿 스레드를 최대한 빨리 반환하고 싶은데.. 이것이 비동기 서블릿 방식이다 3.0
    // IO가 콜백방식 Non blocking으로 되는것이 3.1이다
    //
    //1         ST1
    //2         ST2
    //3   NIO   ST3
    //4         ST4
    // worker thread 많이 만들엇서 쓰는 것이나 servlet thread 만들어 쓰는것이나 뭐가 그리 큰 차이인가??
    // DeferredResult Queue를 사용해서..
    @RestController
    public static class MyController {
        Queue<DeferredResult<String>> results = new ConcurrentLinkedDeque<>();

        @GetMapping("/emitter")
        public ResponseBodyEmitter emitter() throws InterruptedException {
            ResponseBodyEmitter emitter = new ResponseBodyEmitter();
            Executors.newSingleThreadExecutor().submit(() -> {
                try {
                    for (int i=1; i<=50; i++) {
                        emitter.send("<p>Stream " + i + "</p>");
                        Thread.sleep(100);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            return emitter;
        }

        // deferred result로 chatting 방 구현 가능
        // 근데 한대 일때만 되는거 아닌가? scale out 상황에서는?
        @GetMapping("/dr")
        public DeferredResult<String> callable() throws InterruptedException {
            log.info("dr");
            DeferredResult<String> dr = new DeferredResult<>();
            results.add(dr);
            return dr;
        }

        @GetMapping("/dr/count")
        public String drcount() {
            return String.valueOf(results.size());
        }

        @GetMapping("/dr/event")
        public String drevent(String msg) {
            for(DeferredResult<String> dr : results) {
                dr.setResult("Hello " + msg);
                results.remove(dr);
            }
            return "OK";
        }

//        public Callable<String> callable() throws InterruptedException {
//            log.info("callable");
//            return () -> {
//                log.info("async");
//                TimeUnit.SECONDS.sleep(2);
//                return "hello";
//            };
//        }
//        public String callable() throws InterruptedException {
//            log.info("async");
//            TimeUnit.SECONDS.sleep(2);
//            return "hello";
//        }
    }

    public static void main(String[] args) {
        SpringApplication.run(WebApplicationEx.class, args);
    }
}
