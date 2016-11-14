package cc.before30.example.concurrency;

import lombok.AllArgsConstructor;

import java.util.concurrent.CountDownLatch;

/**
 * Created by before30 on 2016. 11. 14..
 */
public class CountDownLatchExample2 {

    @AllArgsConstructor
    static class Worker implements Runnable {
        private final String name;
        private final CountDownLatch startSignal;
        private final CountDownLatch doneSignal;

        @Override
        public void run() {
            try {
                startSignal.await();
                System.out.println("name:" + name + " is UP");

                doneSignal.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        final CountDownLatch startSignal = new CountDownLatch(1);
        final CountDownLatch doneSignal = new CountDownLatch(10);

        for (int i=0; i<10; i++) {
            new Thread(new Worker("name"+i, startSignal, doneSignal)).start();
        }

        startSignal.countDown();
        try {
            doneSignal.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Every Job Finished!");

    }
}
