package cc.before30.example.tobytv007;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by before30 on 10/12/2016.
 */
@Slf4j
public class SchedulerEx {
    public static void main(String[] args) {
        Publisher<Integer> pub =  sub  -> {
            sub.onSubscribe(new Subscription() {
                @Override
                public void request(long n) {
                    log.debug("request()");
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
        // pub
// subscribe on 을 만들어본것
//        Publisher<Integer> subOnPub = sub -> {
//            // main thread 를 block하지 않고 사용할 수 있다.
//            // Typically used for slow publisher e.g., blocking IO, fast consumer(s) scenarios.
//            ExecutorService es = Executors.newSingleThreadExecutor(new CustomizableThreadFactory() {
//                @Override
//                public String getThreadNamePrefix() {
//                    return "subOn-";
//                }
//            });
//            es.execute(() -> pub.subscribe(sub));
//        };

        // 생성은 빠른데 소모가 느린 경우..
        Publisher<Integer> pubOnPub = sub -> {
            pub.subscribe(new Subscriber<Integer>() {
                ExecutorService es = Executors.newSingleThreadExecutor(new CustomizableThreadFactory() {
                    @Override
                    public String getThreadNamePrefix() {
                        return "pubOn-";
                    }
                });

                @Override
                public void onSubscribe(Subscription s) {
                    sub.onSubscribe(s);
                }

                @Override
                public void onNext(Integer integer) {
                    es.execute(() -> sub.onNext(integer));
                }

                @Override
                public void onError(Throwable t) {
                    es.execute(() -> sub.onError(t));
                    es.shutdown();
                }

                @Override
                public void onComplete() {
                    es.execute(() -> sub.onComplete());
                    es.shutdown();
                }
            });
        };

        // sub
//        subOnPub.subscribe(new Subscriber<Integer>() {
//            @Override
//            public void onSubscribe(Subscription s) {
//                log.debug("onSubscribe");
//                s.request(Long.MAX_VALUE);
//            }
//
//            @Override
//            public void onNext(Integer integer) {
//                log.debug("onNext:{}", integer);
//            }
//
//            @Override
//            public void onError(Throwable t) {
//                log.debug("onError:{}", t);
//            }
//
//            @Override
//            public void onComplete() {
//                log.debug("onComplete");
//            }
//        });

        pubOnPub.subscribe(new Subscriber<Integer>() {
            @Override
            public void onSubscribe(Subscription s) {
                log.debug("onSubscribe");
                s.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(Integer integer) {
                log.debug("onNext:{}", integer);
            }

            @Override
            public void onError(Throwable t) {
                log.debug("onError:{}", t);
            }

            @Override
            public void onComplete() {
                log.debug("onComplete");
            }
        });


        System.out.println("exit");
    }
}
