package cc.before30.example.tovytv011.temp;

import io.netty.channel.nio.NioEventLoopGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.client.Netty4ClientHttpRequestFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Created by before30 on 18/02/2017.
 */
@SpringBootApplication
public class TobyTv011Application {

    public static void main(String[] args) {
        SpringApplication.run(TobyTv011Application.class, args);
    }

    @RestController
    public static class MyController {

        @Autowired
        MyService myService;

        //        RestTemplate rt = new RestTemplate();
        AsyncRestTemplate rt = new AsyncRestTemplate(new Netty4ClientHttpRequestFactory(new NioEventLoopGroup(1)));

        @GetMapping("/rest")
        public DeferredResult<String> rest(int idx) {
            String url1 = "http://localhost:8081/service?req={req}";
            String url2 = "http://localhost:8081/service2?req={req}";


            DeferredResult<String> dr = new DeferredResult<>();

            toCF(rt.getForEntity(url1, String.class, "h" + idx))
                    .thenCompose(s -> toCF(rt.getForEntity(url2, String.class, s.getBody())))
                    .thenCompose(s2 -> toCF(myService.work(s2.getBody())))
                    .thenApplyAsync(s3 -> myService.work2(s3))
                    .thenAccept(s4 -> dr.setResult(s4))
                    .exceptionally(e -> {
                        dr.setErrorResult(e.getMessage());
                        return (Void)null;
                    });

            return dr;
        }

        <T> CompletableFuture<T> toCF(ListenableFuture<T> lf) {
            CompletableFuture<T> cf = new CompletableFuture<T>();
            lf.addCallback(s -> {cf.complete(s);}, e -> {cf.completeExceptionally(e);});
            return cf;

        }
    }

    @Service
    public static class MyService {
        @Async
        public ListenableFuture<String> work(String arg) {
            return new AsyncResult<String>(arg + "/workasyncannotation");
        }

        public String work2(String arg) {
            return arg + "/work2";
        }
    }

}
