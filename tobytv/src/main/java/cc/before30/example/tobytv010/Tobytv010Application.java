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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.context.request.async.DeferredResult;

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
        public DeferredResult<String> rest(int idx) {
            String url1 = "http://localhost:8081/service?req={req}";
            String url2 = "http://localhost:8081/service2?req={req}";


            DeferredResult<String> dr = new DeferredResult<>();

            Completion
                    .from(rt.getForEntity(url1, String.class, "h" +idx))
                    .andApply(s -> rt.getForEntity(url2, String.class, s.getBody()))
                    .andApply(s -> myService.work(s.getBody()))
                    .andError(e -> dr.setErrorResult(e))
                    .andAccept(s -> dr.setResult(s));

            return dr;
        }
    }

    public static class AcceptCompletion<S> extends Completion<S, Void> {
        public Consumer<S> con;
        public AcceptCompletion(Consumer<S> con) {
            this.con = con;
        }

        @Override
        void run(S value) {
            con.accept(value);
        }
    }

    public static class ApplyCompletion<S, T> extends Completion<S, T> {
        public Function<S, ListenableFuture<T>> fn;
        public ApplyCompletion(Function<S, ListenableFuture<T>> fn) {
            this.fn = fn;
        }

        @Override
        void run(S value) {
            ListenableFuture<T> lf = fn.apply(value);
            lf.addCallback(s -> complete(s), e -> error(e));
        }
    }

    public static class ErrorCompletion<T> extends Completion<T, T> {
        public Consumer<Throwable> econ;
        public ErrorCompletion(Consumer<Throwable> econ) {
            this.econ = econ;
        }

        @Override
        void run(T value) {
            if (next != null) next.run(value);
        }

        @Override
        void error(Throwable e) {
            econ.accept(e);
        }
    }

    public static class Completion<S, T> {
        Completion next;


        public void andAccept(Consumer<T> con) {
            Completion<T, Void> c = new AcceptCompletion(con);
            this.next = c;

        }

        public Completion<T, T> andError(Consumer<Throwable> econ) {
            Completion<T, T> c = new ErrorCompletion<>(econ);

            return c;
        }

        public <V> Completion<T, V> andApply(Function<T, ListenableFuture<V>> fn) {
            Completion<T, V> c = new ApplyCompletion<>(fn);
            this.next = c;

            return c;

        }

        public static <S, T> Completion<S, T> from(ListenableFuture<T> lf) {
            Completion<S, T> c = new Completion<>();
            lf.addCallback(s -> {
                c.complete(s);
            }, f -> {
                c.error(f);
            });

            return c;
        }

        void error(Throwable e) {
            if (next != null) next.error(e);
        }

        void complete(T s) {
            if (next != null) next.run(s);
        }

        void run(S value) {

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
