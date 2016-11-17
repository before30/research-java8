package cc.before30.example.concurrency;

import lombok.AllArgsConstructor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by before30 on 2016. 11. 18..
 */
public class ProducerConsumerExample {

    @AllArgsConstructor
    public static class Producer implements Runnable {
        private BlockingQueue<Integer> sharedQueue;

        @Override
        public void run() {
            for (int i=0; i<10; i++) {
                try {
                    System.out.println("Produced: " + i);
                    sharedQueue.put(i);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @AllArgsConstructor
    public static class Consumer implements Runnable {
        private BlockingQueue<Integer> sharedQueue;

        @Override
        public void run() {
            while(true) {
                try {
                    System.out.println("Consumed : " + sharedQueue.take());

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        BlockingQueue<Integer> sharedQueue = new LinkedBlockingQueue<>();

        Thread producerThread = new Thread(new Producer(sharedQueue));
        Thread consumerThread = new Thread(new Consumer(sharedQueue));

        producerThread.start();
        consumerThread.start();
    }
}
