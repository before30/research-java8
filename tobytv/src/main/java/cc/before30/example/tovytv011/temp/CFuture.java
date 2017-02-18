package cc.before30.example.tovytv011.temp;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * Created by before30 on 18/02/2017.
 */
@Slf4j
public class CFuture {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        Future f;
//        f.get()
        CompletableFuture<Integer> f = CompletableFuture.completedFuture(1);
        log.info("{}", f.get());

//        CompletableFuture<Integer> f2 = new CompletableFuture<>();
//        f2.completeExceptionally(new RuntimeException("exception"));
//        log.info("{}", f2.get());

        CompletableFuture.runAsync(() -> {
           log.info("runAsync");
        });
        log.info("exit");

        CompletableFuture
                .runAsync(() -> log.info("runAsync"))
                .thenRun(() -> log.info("thenRun"))
                .thenRun(() -> log.info("thenRun"));

        CompletableFuture
                .supplyAsync(() -> {
                    log.info("runAsync");
                    return 1;
                })
                .thenApply(s -> {
                    log.info("thenApply {}", s);
                    return s + 1;
                })
                .thenCompose( ss -> {
                    log.info("thenCompose {}", ss);
                    return CompletableFuture.completedFuture(ss + 1);
                })
                .thenAccept(s2 -> log.info("thenAccept {}", s2));

        ExecutorService es = Executors.newFixedThreadPool(10);
        CompletableFuture
                .supplyAsync(() -> {
                    log.info("runAsync");
                    return 1;
                })
                .thenApplyAsync(s -> {
                    log.info("thenApply {}", s);
                    return s + 1;
                }, es)
                .thenComposeAsync( ss -> {
                    log.info("thenCompose {}", ss);
                    return CompletableFuture.completedFuture(ss + 1);
                }, es)
                .thenAcceptAsync(s2 -> log.info("thenAccept {}", s2), es);


        ForkJoinPool.commonPool().shutdown();
        ForkJoinPool.commonPool().awaitTermination(10, TimeUnit.SECONDS);
        es.shutdown();
        es.awaitTermination(10, TimeUnit.SECONDS);

        // tail call optimization with CompletableFuture
        // Future, Promise, Deferred
    }
}
