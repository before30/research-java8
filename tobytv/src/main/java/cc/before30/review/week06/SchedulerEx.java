package cc.before30.review.week06;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by before30 on 31/12/2016.
 */
@Slf4j
public class SchedulerEx {

    // Reactive Streams

    /**
     * 1. Publisher
     * 2. Subscriber
     * 3. Subscritpion
     * 4. Processor
     *
     * onSubscribe onNext* (onError | onComplete)?
     *
     * Reactive Sterams 표준에 들어있는....
     * onNext* -> 0 ~ 무한대
     * onComplete, onError 이렇게 두가지 경우로 끝날 수 있다.
     */

    public static void main(String[] args) {

        Publisher<Integer> pub = sub -> {
          sub.onSubscribe(new Subscription() {
              @Override
              public void request(long n) {
                  log.debug("!request()");
                  sub.onNext(1);
                  sub.onNext(2);
                  sub.onNext(3);
                  sub.onNext(4);
                  sub.onNext(5);
                  sub.onComplete();

              }

              @Override
              public void cancel() {

              }
          });
        };
// Subscribe On을 사용하는 경우는 생성하는데 오래걸리는 경우
//        Flux flux = Flux.from(pub);
//        flux.subscribeOn(Schedulers.elastic()).subscribe(
//                new Subscriber() {
//                    Subscription subscription;
//
//                    @Override
//                    public void onSubscribe(Subscription s) {
//                        log.info("onSubscribe");
//                        this.subscription = s;
//                        subscription.request(Long.MAX_VALUE);
//                    }
//
//                    @Override
//                    public void onNext(Object o) {
//                        log.info("onNext {}", o);
//                    }
//
//                    @Override
//                    public void onError(Throwable t) {
//                        log.info("onError {}", t.getMessage());
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        log.info("onComplete");
//                    }
//                }
//        );
//
//        try {
//            TimeUnit.SECONDS.sleep(10);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }


        Publisher<Integer> subOnPub = sub -> {
            ExecutorService es = Executors.newFixedThreadPool(10);
            es.execute(() -> pub.subscribe(sub));
        };

        subOnPub.subscribe(new Subscriber<Integer>() {
            Subscription subscription;

            @Override
            public void onSubscribe(Subscription s) {
                log.info("onSubscribe");
                this.subscription = s;
                subscription.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(Integer integer) {
                log.info("onNext {}", integer);
            }

            @Override
            public void onError(Throwable t) {
                log.info("onError {}", t.getMessage());
            }

            @Override
            public void onComplete() {
                log.info("onComplete");
            }
        });

        log.info("exit");
    }
}
