package cc.before30.example.concurrency;

import lombok.AllArgsConstructor;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by before30 on 2016. 11. 17..
 */
public class CyclicBarrierExample {



    @AllArgsConstructor
    private static class Task implements Runnable {
        private CyclicBarrier barrier;

        @Override
        public void run() {
            final int sleepMillis = new Random().nextInt(10000);
            try {
                System.out.println(Thread.currentThread().getName() + " is going to sleep for " + sleepMillis);

                Thread.sleep(sleepMillis);

                System.out.println(Thread.currentThread().getName() + " is waiting on barrier");

                // wait till every thread call barrier.await()
                barrier.await();
                System.out.println(Thread.currentThread().getName() + " has crossed the barrier");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }

        }
    }

    public static void main(String[] args) {
        final CyclicBarrier cycleBarrier = new CyclicBarrier(3,() -> {
            System.out.println(Thread.currentThread().getName() + " - All parties are arrived at barrier, let's play!");
        });

        Thread t1 = new Thread(new Task(cycleBarrier), "Thread 1");
        Thread t2 = new Thread(new Task(cycleBarrier), "Thread 2");
        Thread t3 = new Thread(new Task(cycleBarrier), "Thread 3");

        t1.start();
        t2.start();
        t3.start();
    }
}
