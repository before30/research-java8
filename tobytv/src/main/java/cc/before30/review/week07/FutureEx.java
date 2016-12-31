package cc.before30.review.week07;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;


/**
 * Created by before30 on 31/12/2016.
 */
@Slf4j
public class FutureEx {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ExecutorService es = Executors.newCachedThreadPool();
//        es.execute(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    TimeUnit.SECONDS.sleep(2);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                log.info("hello");
//            }
//        });
//
//        Future<String> future = es.submit(() -> {
//            try {
//                TimeUnit.SECONDS.sleep(2);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            log.info("Async");
//            return "Hello";
//        });
//
//        log.info("from future {}", future.get());
//
//        log.info("exit");


        FutureTask<String> f = new FutureTask<String>(() -> {
            TimeUnit.SECONDS.sleep(2);
            log.info("Async2");
            return "Hello2";
        }) {
            @Override
            protected void done() {
                try {
                    log.info("get() {}", get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        };

        es.execute(f);
        es.shutdown();
    }

}
