package cc.before30.ex.toby012;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by before30 on 31/12/2016.
 */
@Slf4j
public class LoadTest {
    static AtomicInteger counter = new AtomicInteger(0);
    public static void main(String[] args) {
        ExecutorService es = Executors.newFixedThreadPool(100);

        RestTemplate rt = new RestTemplate();
        String url = "http://localhost:8080/rest2?idx={idx}";

        CyclicBarrier barrier = new CyclicBarrier(101);


        for (int i=0; i<100; i++) {
            es.execute(() -> {
                int idx = counter.addAndGet(1);
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
                log.info("Elapsed: {} {} / {}", idx, sw.getTotalTimeSeconds(), res);
            });
        }

        try {
            barrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
        StopWatch main = new StopWatch();
        main.start();

        es.shutdown();
        try {
            es.awaitTermination(100, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        main.stop();
        log.info("Total : {}", main.getTotalTimeSeconds());

    }
}
