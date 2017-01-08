package cc.before30.review.week10;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by before30 on 08/01/2017.
 */
@Slf4j
public class MyLoadTest {
    static AtomicInteger counter = new AtomicInteger(0);

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(100);

        RestTemplate rt = new RestTemplate();
        String url = "http://localhost:8080/rest?idx={idx}";
        CyclicBarrier barrier = new CyclicBarrier(101);

        for (int i=0; i<100; i++) {
            executorService.execute(() -> {

                int idx = counter.addAndGet(1);
                log.info("hi {}", idx);
                try {
                    barrier.await();
                } catch (InterruptedException e) {
                } catch (BrokenBarrierException e) {
                }

                log.info("Thread {}", idx);

                StopWatch sw = new StopWatch();
                sw.start();

                String res = rt.getForObject(url, String.class, idx);
                sw.stop();
                log.info("Elapsed time: {} {} / {}", idx, sw.getTotalTimeSeconds(), res);

            });
        }

        try {
            barrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }

        StopWatch mainSw = new StopWatch();
        mainSw.start();

        executorService.shutdown();
        try {
            executorService.awaitTermination(100, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        mainSw.stop();
        log.info("Total Elapsed Time : {}", mainSw.getTotalTimeSeconds());

    }
}
