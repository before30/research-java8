package cc.before30.example.tobytv010;

import cc.before30.example.tobytv009.Tobytv009Application;
import io.netty.channel.nio.NioEventLoopGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.Netty4ClientHttpRequestFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Created by before30 on 07/01/2017.
 */
public class Tobytv010Application {

    @RestController
    public static class MyController {

        @Autowired
        Tobytv009Application.MyService myService;

        //        RestTemplate rt = new RestTemplate();
        AsyncRestTemplate rt = new AsyncRestTemplate(new Netty4ClientHttpRequestFactory(new NioEventLoopGroup(1)));

        @GetMapping("/rest")
//        public ListenableFuture<ResponseEntity<String>> rest(int idx) {
        public DeferredResult<String> rest(int idx) {
//            String res = rt.getForObject("http://localhost:8081/service?req={req}", String.class, "hello" + idx);
            String url1 = "http://localhost:8081/service?req={req}";
            String url2 = "http://localhost:8081/service2?req={req}";


            DeferredResult<String> dr = new DeferredResult<>();

            Completion
                    .from(rt.getForEntity(url1, String.class, "h" +idx))
                    .andApply(s -> rt.getForEntity(url2, String.class, s.getBody()))
                    .andAccept(s -> dr.setResult(s.getBody()));

//            ListenableFuture<ResponseEntity<String>> f1 = rt.getForEntity(url1, String.class, idx);
//            f1.addCallback(s1 -> {
//                ListenableFuture<ResponseEntity<String>> f2 = rt.getForEntity(url2, String.class, s1.getBody());
//                f2.addCallback(s2 -> {
//                    ListenableFuture<String> f3 = myService.work(s2.getBody());
//                    f3.addCallback(s3 -> {
//                        dr.setResult(s3);
//                    }, e -> {
//                        dr.setErrorResult(e.getMessage());
//                    });
//                }, e -> {
//                    dr.setErrorResult(e.getMessage());
//                });
//            }, e -> {
//                dr.setErrorResult(e.getMessage());
//            });

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

    public static class Completion {
        Completion next;


        public Completion() {

        }

        public Consumer<ResponseEntity<String>> con;
        public Completion(Consumer<ResponseEntity<String>> con) {
            this.con = con;
        }

        public Function<ResponseEntity<String>, ListenableFuture<ResponseEntity<String>>> fn;
        public Completion(Function<ResponseEntity<String>, ListenableFuture<ResponseEntity<String>>> fn) {
            this.fn = fn;
        }


        public void andAccept(Consumer<ResponseEntity<String>> con) {
            Completion c = new Completion(con);
            this.next = c;

        }

        public Completion andApply(Function<ResponseEntity<String>, ListenableFuture<ResponseEntity<String>>> fn) {
            Completion c = new Completion(fn);
            this.next = c;

            return c;

        }

        public static Completion from(ListenableFuture<ResponseEntity<String>> lf) {
            Completion c = new Completion();
            lf.addCallback(s -> {
                c.complete(s);
            }, f -> {
                c.error(f);
            });

            return c;
        }

        void complete(ResponseEntity<String> s) {
            if (next != null) next.run(s);
        }

        void run(ResponseEntity<String> value) {
            if (con != null) con.accept(value);
            else if (fn != null) {
                ListenableFuture<ResponseEntity<String>> lf = fn.apply(value);
                lf.addCallback(s -> complete(s), e -> error(e));
            }
        }

        void error(Throwable e) {
        }
    }

    @Service
    public static class MyService {
        @Async
        public ListenableFuture<String> work(String arg) {
            return new AsyncResult<String>(arg + "/work");
        }
    }

}
