package cc.before30.review.week05;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.TimeUnit;

/**
 * Created by before30 on 20/12/2016.
 */
public class FluxEx {

    public static void main(String[] args) {

        Flux.range(1, 10)
                .log()
//                .map(FluxEx::intToString)
                .flatMap(v ->
                        Mono.fromSupplier(() -> intToString(v))
                        .subscribeOn(Schedulers.parallel()), 3
                )
                .subscribe(System.out::println);
    }

    private static String intToString(int arg) {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return String.valueOf(arg);
    }
}
