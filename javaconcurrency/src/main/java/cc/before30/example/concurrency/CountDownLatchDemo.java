package cc.before30.example.concurrency;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.CountDownLatch;

/**
 * Created by before30 on 2016. 11. 14..
 */
public class CountDownLatchDemo {


    @AllArgsConstructor
    public static class Service implements Runnable {
        private final String name;
        private final int timeToStart;
        private final CountDownLatch latch;

        @Override
        public void run() {

            try {
                Thread.sleep(timeToStart);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(name + " is UP");
            latch.countDown();
        }
    }

    public static void main(String[] args) {
        final CountDownLatch latch = new CountDownLatch(4);
        Thread cacheService = new Thread(new Service("CacheService", 3000, latch));
        Thread alertService = new Thread(new Service("AlertService", 2000, latch));
        Thread validationService = new Thread(new Service("ValidationService", 5000, latch));

        cacheService.start();
        alertService.start();
        validationService.start();

        // A synchronization aid that allows one or more threads to wait until a set of operations being performed
        // in other threads completes.
        try {
            latch.await();
            System.out.println("All services are UP, application is starting now.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
