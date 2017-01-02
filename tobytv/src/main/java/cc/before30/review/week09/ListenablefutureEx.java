package cc.before30.review.week09;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.async.DeferredResult;

/**
 * Created by before30 on 02/01/2017.
 */
@SpringBootApplication
@EnableAsync
public class ListenablefutureEx {

    public static void main(String[] args) {
        SpringApplication.run(ListenablefutureEx.class, args);
    }


}

@RestController
@Slf4j
class MyController {
    @Autowired
    AsyncRestTemplate restTemplate;

    @GetMapping("/home")
    public DeferredResult<String> home() {
        DeferredResult<String> result = new DeferredResult<String>(5000L);

        ListenableFuture<ResponseEntity<String>> creditRatingFuture = restTemplate.getForEntity("http://www.google.com", String.class);
        creditRatingFuture.addCallback(new ListenableFutureCallback<ResponseEntity<String>>() {

            @Override
            public void onSuccess(ResponseEntity<String> response) {
                log.info("success");
                result.setResult(response.getBody());
            }

            @Override
            public void onFailure(Throwable ex) {
                result.setErrorResult(ex.getMessage());
            }
        });

        return result;

    }
}
