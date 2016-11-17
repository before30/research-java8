package cc.before30.example.concurrency;

import java.util.concurrent.Semaphore;

/**
 * Created by before30 on 2016. 11. 17..
 */
public class SemaphoreExample {
    Semaphore binary = new Semaphore(1);

    private void mutualExclusion() {
        try {
            binary.acquire();
            System.out.println(Thread.currentThread().getName() + " inside mutual exclusive region");
            Thread.sleep(3000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            binary.release();
            System.out.println(Thread.currentThread().getName() + " outside of mutual exclusive region");
        }
    }

    public static void main(String[] args) {
        final SemaphoreExample example = new SemaphoreExample();
        new Thread() {
            @Override
            public void run() {
                example.mutualExclusion();
            }
        }.start();

        new Thread() {
            @Override
            public void run() {
                example.mutualExclusion();
            }
        }.start();
    }
}
