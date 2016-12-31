package cc.before30.example.tobytv009;

import io.netty.channel.nio.NioEventLoopGroup;
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
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

/**
 * Created by before30 on 31/12/2016.
 */
@SpringBootApplication
@EnableAsync
public class Tobytv009Application {


    @RestController
    public static class MyController {

        @Autowired MyService myService;

//        RestTemplate rt = new RestTemplate();
        AsyncRestTemplate rt = new AsyncRestTemplate(new Netty4ClientHttpRequestFactory(new NioEventLoopGroup(1)));

        @GetMapping("/rest")
//        public ListenableFuture<ResponseEntity<String>> rest(int idx) {
        public DeferredResult<String> rest(int idx) {
//            String res = rt.getForObject("http://localhost:8081/service?req={req}", String.class, "hello" + idx);
            String url1 = "http://localhost:8081/service?req={req}";
            String url2 = "http://localhost:8081/service2?req={req}";

            ListenableFuture<ResponseEntity<String>> f1 = rt.getForEntity(url1, String.class, idx);

            DeferredResult<String> dr = new DeferredResult<>();

            f1.addCallback(s1 -> {
                ListenableFuture<ResponseEntity<String>> f2 = rt.getForEntity(url2, String.class, s1.getBody());
                f2.addCallback(s2 -> {
                    ListenableFuture<String> f3 = myService.work(s2.getBody());
                    f3.addCallback(s3 -> {
                        dr.setResult(s3);
                    }, e -> {
                        dr.setErrorResult(e.getMessage());
                    });
                }, e -> {
                    dr.setErrorResult(e.getMessage());
                });
            }, e -> {
                dr.setErrorResult(e.getMessage());
            });

            return dr;
        }

        @GetMapping("/rest2")
        public CompletableFuture<String> rest2(int idx) {
            String url1 = "http://localhost:8081/service?req={req}";
            String url2 = "http://localhost:8081/service2?req={req}";

            return buildCompletableFuture(rt.getForEntity(url1, String.class, idx))
                    .thenCompose(r -> buildCompletableFuture(rt.getForEntity(url2, String.class, r)))
                    .exceptionally(ex -> ex.getMessage());
        }


        <T> CompletableFuture<T> buildCompletableFuture(final ListenableFuture<ResponseEntity<T>> listenableFuture) {
            CompletableFuture<T> completableFuture = new CompletableFuture<T>() {
                @Override
                public boolean cancel(boolean mayInterruptIfRunning) {
                    boolean result = listenableFuture.cancel(mayInterruptIfRunning);
                    super.cancel(mayInterruptIfRunning);
                    return result;
                }
            };

            // add callback
            listenableFuture.addCallback(new ListenableFutureCallback<ResponseEntity<T>>() {
                @Override
                public void onSuccess(ResponseEntity<T> result) {
                    completableFuture.complete(result.getBody());
                }

                @Override
                public void onFailure(Throwable ex) {
                    completableFuture.completeExceptionally(ex);
                }

            });

            return completableFuture;
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
        SpringApplication.run(Tobytv009Application.class, args);
    }

}
