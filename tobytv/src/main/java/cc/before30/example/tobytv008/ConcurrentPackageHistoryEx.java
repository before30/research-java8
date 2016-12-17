package cc.before30.example.tobytv008;

import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.concurrent.*;

/**
 * Created by before30 on 17/12/2016.
 */
@Slf4j
public class ConcurrentPackageHistoryEx {
    interface SuccessCallback {
        void onSuccess(String result);
    }

    interface ExceptionCallback {
        void onError(Throwable t);
    }

    public static class CallbackFutureTask extends FutureTask<String> {
        SuccessCallback callback;
        ExceptionCallback ec;

        public CallbackFutureTask(Callable<String> callable, SuccessCallback callback, ExceptionCallback ec) {
            super(callable);
            this.callback = Objects.requireNonNull(callback, "call back must not null");
            this.ec = Objects.requireNonNull(ec, "callback must not null");
        }

        @Override
        protected void done() {

            try {
                callback.onSuccess(get());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (ExecutionException e) {
                ec.onError(e.getCause());
            }
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService es = Executors.newCachedThreadPool();

        CallbackFutureTask callbackFutureTask = new CallbackFutureTask(() -> {
            TimeUnit.SECONDS.sleep(2);
            if (1==1) throw new RuntimeException("Async ERROR!!!");
            log.info("Async");
            return "hello";
        },
                res -> log.info("Result: {}", res),
                e -> log.error("{}", e.getMessage()));

        es.execute(callbackFutureTask);

//        FutureTask<String> futureTask = new FutureTask<String>(() -> {
//            TimeUnit.SECONDS.sleep(2);
//            log.info("CALLABLE hello");
//            return "HELLO";
//        }) {
//            @Override
//            protected void done() {
//                try {
//                    log.info(get());
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } catch (ExecutionException e) {
//                    e.printStackTrace();
//                }
//            }
//        };
//
//
//        es.execute(futureTask);

//        es.execute(() -> {
//            try {
//                TimeUnit.SECONDS.sleep(2);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            log.info("ASYNC hello");
//        });
//
//        Future<String> future = es.submit(() -> {
//            TimeUnit.SECONDS.sleep(2);
//            log.info("CALLABLE hello");
//            return "HELLO";
//        });
//
//        System.out.println(future.get());
        log.info("exit");
        es.shutdown();
    }
}
