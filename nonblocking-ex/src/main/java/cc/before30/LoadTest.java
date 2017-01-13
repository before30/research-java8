package cc.before30;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by before30 on 13/01/2017.
 */
@Slf4j
public class LoadTest {

    public static void main(String[] args) {
        AtomicInteger counter = new AtomicInteger(0);
        ExecutorService executorService = Executors.newFixedThreadPool(100);

        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/rest?idx={idx}";

        StopWatch mainWatch = new StopWatch();
        mainWatch.start();

        for (int i=0; i<100; i++) {
            executorService.execute(() -> {
                int idx = counter.addAndGet(1);
                StopWatch watch = new StopWatch();
                watch.start();

                String res = restTemplate.getForObject(url, String.class, idx);

                watch.stop();
                log.info("Elapsed time: {} {} / {}", idx, watch.getTotalTimeMillis(), res);
            });
        }

        executorService.shutdown();

        try {
            executorService.awaitTermination(100, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        mainWatch.stop();
        log.info("Total Elapsed Time : {}", mainWatch.getTotalTimeMillis());
    }
}
