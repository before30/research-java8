package cc.before30.review.week10;

import io.netty.channel.nio.NioEventLoopGroup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.Netty4ClientHttpRequestFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Created by before30 on 08/01/2017.
 */
@SpringBootApplication
@Slf4j
@EnableAsync
public class MyWeek10App {

    @RestController
    public static class MyController {
        @Autowired MyService myService;

        public static final String URL_1 = "http://localhost:8081/src1?req={req}";
        public static final String URL_2 = "http://localhost:8081/src2?req={req}";

        AsyncRestTemplate restTemplate = new AsyncRestTemplate(new Netty4ClientHttpRequestFactory(new NioEventLoopGroup(1)));

        @GetMapping("/rest")
        public DeferredResult<String> rest(String idx) {
            DeferredResult<String> result = new DeferredResult<>();

            MyCompletion
                    .from(restTemplate.getForEntity(URL_1, String.class, "h" + idx))
                    .andApply(s -> restTemplate.getForEntity(URL_2, String.class, s.getBody()))
                    .andError(e -> result.setErrorResult(e))
                    .andAccept(s -> result.setResult(s.getBody()));

            return result;
        }
    }

    public static class AcceptMyCompletion extends MyCompletion {
        Consumer<ResponseEntity<String>> consumer;
        public AcceptMyCompletion(Consumer<ResponseEntity<String>> consumer) {
            this.consumer = consumer;
        }

        @Override
        void run(ResponseEntity<String> value) {
            consumer.accept(value);
        }
    }

    public static class ApplyMyCompletion extends MyCompletion {
        Function<ResponseEntity<String>, ListenableFuture<ResponseEntity<String>>> fn;
        public ApplyMyCompletion(Function<ResponseEntity<String>, ListenableFuture<ResponseEntity<String>>> fn) {
            this.fn = fn;
        }

        @Override
        void run(ResponseEntity<String> value) {
            ListenableFuture<ResponseEntity<String>> lf = fn.apply(value);
            lf.addCallback(s -> {
                complete(s);
            }, e -> {
                error(e);
            });
        }
    }

    public static class ErrorMyCompletion extends MyCompletion {
        Consumer<Throwable> econ;
        public ErrorMyCompletion(Consumer<Throwable> econ) {
            this.econ = econ;
        }

        @Override
        void run(ResponseEntity<String> value) {
            if (next != null) next.run(value);
        }

        @Override
        void error(Throwable e) {
            econ.accept(e);
        }
    }

    public static class MyCompletion {
        MyCompletion next;

        public MyCompletion() {

        }

        public MyCompletion andApply(Function<ResponseEntity<String>, ListenableFuture<ResponseEntity<String>>> fn) {
            MyCompletion c = new ApplyMyCompletion(fn);
            this.next = c;

            return c;
        }

        public MyCompletion andError(Consumer<Throwable> econ) {
            MyCompletion c = new ErrorMyCompletion(econ);
            this.next = c;

            return c;
        }

        public void andAccept(Consumer<ResponseEntity<String>> consumer) {
            MyCompletion c = new AcceptMyCompletion(consumer);
            next = c;
        }


        public static MyCompletion from(ListenableFuture<ResponseEntity<String>> listenableFuture) {
            MyCompletion c = new MyCompletion();
            listenableFuture.addCallback(s -> {
                c.complete(s);
            }, e -> {
                c.error(e);
            });
            return c;
        }

        void error(Throwable e) {
            if (next != null) next.error(e);
        }

        void complete(ResponseEntity<String> s) {
            if (next != null) next.run(s);
        }

        void run(ResponseEntity<String> value) {
        }
    }

    @Service
    public static class MyService {
        @Async
        public ListenableFuture<String> work(String arg) {
            return new AsyncResult<String>(arg + "/work");
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(MyWeek10App.class, args);
    }

}
