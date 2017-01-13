package cc.before30.contorller;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.TimeUnit;

/**
 * Created by before30 on 13/01/2017.
 */
@RestController
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

    @GetMapping("/rest")
    public DeferredResult<String> rest(String idx) throws InterruptedException {
        DeferredResult result = new DeferredResult();

        ListenableFuture<String> future = new AsyncResult<>(idx + "/rest");
        future.addCallback(s -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            result.setResult(s);
        }, e -> {});
        return result;
    }
}
