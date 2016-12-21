package cc.before30.example.tobytv007;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by before30 on 10/12/2016.
 */
@Slf4j
public class FluxScEx {
    public static void main(String[] args) throws InterruptedException {

//        Flux.range(1, 10)
//            .log()
//            .map(FluxScEx::intToString)
//            .subscribe(System.out::println);
//        Publisher<Integer> pub = sub -> {
//            sub.onSubscribe(new Subscription() {
//
//                @Override public void request(long n) {
//
//                }
//
//                @Override public void cancel() {
//
//                }
//            };
//        };

        Flux.range(1, 10)
            .log()
            .publishOn(Schedulers.newParallel("test", 10))
            .flatMap(i -> Flux.just(i).map(FluxScEx::intToString), 10)
            .log()

            .subscribe(System.out::println);
//        Flux.range(1, 10)
//                .subscribe(System.out::println);
//
//        Flux.range(1, 10)
//                .log()
//                .subscribe(System.out::println);
//
//        Flux.range(1, 10)
//                .log()
//                .subscribeOn(Schedulers.newSingle("sub"))
//                .subscribe(System.out::println);
//
//        Flux.range(1, 10)
//                .publishOn(Schedulers.newSingle("pub"))
//                .log()
//                .subscribe(System.out::println);
//
//        Flux.range(1, 10)
//                .publishOn(Schedulers.newSingle("pub"))
//                .log()
//                .subscribeOn(Schedulers.newSingle("sub"))
//                .subscribe(System.out::println);

        // 그냥 아래 코드만 있으면 동작이 안된다.
        // Thread 가 생겨서 돌아야하는데.... 그전에 끝나버리나봐
        // 근데 원래 안그렇거든..user thread의 경우는 main thread가 종료가 되어도 종료가 되지 않는다.
        // JVM은 user thread가 모두 끝날때까지 기다린다
        // interval의 timer thread는.. user thread가 아닌 daemon thread를 만든다
        // JVM은 user thread가 남아 있지 않으면 종료
        // daemon thread는 .... 상관없이 종료
//        Flux.interval(Duration.ofMillis(500))
//                .take(10)
//                .subscribe(s -> log.debug("onNext:{}", s));

        log.debug("exit");
        TimeUnit.SECONDS.sleep(10);

    }

    private static String intToString(int i) {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return String.valueOf(i);
    }
    private static void printWithSleep(int i) {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(i);
    }
}
