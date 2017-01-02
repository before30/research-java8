package cc.before30.review.week09;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.client.AsyncRestTemplate;

import java.util.concurrent.ExecutionException;

/**
 * Created by before30 on 02/01/2017.
 */
@Slf4j
public class AnotherListenableFutureEx {

    public static void main(String[] args) {
        AsyncRestTemplate rt = new AsyncRestTemplate();
        String url = "http://www.google.com";
        ListenableFuture<ResponseEntity<String>> future = rt.getForEntity(url, String.class);

        try {
            ResponseEntity<String> entity = future.get();
            log.info("{}", entity.getBody());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
