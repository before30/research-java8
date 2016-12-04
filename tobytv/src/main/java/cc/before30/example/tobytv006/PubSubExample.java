package cc.before30.example.tobytv006;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by before30 on 03/12/2016.
 */

// Publisher ->[Data1] -> Operator -> [Data2] -> Op2 -> [Data3] -> Subscriber
// 이런 구조로 Data의 흐름을 디자인하고

    /*
    pub -> [Data1] -> mapPub -> [Data2] -> LogSub
                        <- subscribe(logSub)
                        -> onSubscribe(s)
                        -> onNext
                        -> onNext
                        -> onComplete
     */
@Slf4j
public class PubSubExample {

    public static void main(String[] args) {
        Publisher<Integer> pub = iterPub(Stream
                .iterate(1, i -> i + 1)
                .limit(10)
                .collect(Collectors.toList()));
//        Publisher<Integer> mapPub = mapPub(pub, s -> s * 10);
//        Publisher<Integer> mpa2Pub = mapPub(mapPub, s -> s * 10);
//        pub.subscribe(logSub());
//        mapPub.subscribe(logSub());
//        mpa2Pub.subscribe(logSub());

//        Publisher<Integer> sumPub = sumPub(pub);
//        sumPub.subscribe(logSub());

//        Publisher<Integer> reducePub = reducePub(pub, 0, (a, b) -> a + b);
//        reducePub.subscribe(logSub());


        Publisher<String> map2Pub = map2Pub(pub, s -> "[" + s + "]");
        map2Pub.subscribe(log2Sub());

    }


    private static Publisher<Integer> reducePub(Publisher<Integer> pub, int init, BiFunction<Integer, Integer, Integer> bf) {
        // 1, 2, 3, 4, 5
        // 0 -> (0,1) -> 0+1 = 1
        // 1 -> (1,2) -> 1+2 = 3
        // 3 ....

        return new Publisher<Integer>() {
            @Override
            public void subscribe(Subscriber<? super Integer> sub) {

                pub.subscribe(new DelegateSub(sub) {
                    int result = init;
                    @Override
                    public void onNext(Integer i) {
                        result = bf.apply(result, i);
                    }

                    @Override
                    public void onComplete() {
                        sub.onNext(result);
                        sub.onComplete();
                    }
                });
            }
        };
    }

    private static Publisher<Integer> sumPub(Publisher<Integer> pub) {
        return new Publisher<Integer>() {
            @Override
            public void subscribe(Subscriber<? super Integer> sub) {
                pub.subscribe(new DelegateSub(sub) {
                    int sum = 0;
                    @Override
                    public void onNext(Integer integer) {
                        sum += integer;
                    }

                    @Override
                    public void onComplete() {
                        sub.onNext(sum);
                        sub.onComplete();
                    }
                });
            }

        };
    }


    private static Publisher<Integer> mapPub(Publisher<Integer> pub, Function<Integer, Integer> f) {
        return new Publisher<Integer>() {

            @Override
            public void subscribe(Subscriber<? super Integer> sub) {
//                pub.subscribe(sub);
                pub.subscribe(new DelegateSub(sub) {
                    @Override
                    public void onNext(Integer integer) {
                        sub.onNext(f.apply(integer));
                    }
                });
            }
        };
    }

    private static Subscriber<Integer> logSub() {
        return new Subscriber<Integer>() {

            @Override
            public void onSubscribe(Subscription s) {
                log.info("onSubscribe");
                s.request(Long.MAX_VALUE);
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
        };
    }

    private static Publisher<Integer> iterPub(List<Integer> iter) {
        return new Publisher<Integer>() {
            @Override
            public void subscribe(Subscriber<? super Integer> subscriber) {
                subscriber.onSubscribe(new Subscription() {
                    @Override
                    public void request(long n) {
                        try {
                            iter.forEach(i -> subscriber.onNext(i));
                            subscriber.onComplete();
                        } catch(Throwable t) {
                            subscriber.onError(t);
                        }
                    }

                    @Override
                    public void cancel() {

                    }
                });
            }
        };
    }

    private static <T> Subscriber<T> log2Sub() {
        return new Subscriber<T>() {

            @Override
            public void onSubscribe(Subscription s) {
                log.info("onSubscribe");
                s.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(T t) {
                log.info("onNext {}", t);
            }

            @Override
            public void onError(Throwable t) {
                log.info("onError {}", t.getMessage());
            }

            @Override
            public void onComplete() {
                log.info("onComplete");
            }
        };
    }

    private static <T, R> Publisher<R> map2Pub(Publisher<T> pub, Function<T, R> f) {
        return new Publisher<R>() {

            @Override
            public void subscribe(Subscriber<? super R> sub) {
//                pub.subscribe(sub);
                pub.subscribe(new Delegate2Sub<T, R>(sub) {
                    @Override
                    public void onNext(T t) {
                        sub.onNext(f.apply(t));
                    }
                });
            }
        };
    }


    private static class Delegate2Sub<T, R> implements Subscriber<T> {
        Subscriber sub;
        public Delegate2Sub(Subscriber sub) {
            this.sub = sub;
        }

        @Override
        public void onSubscribe(Subscription s) {
            sub.onSubscribe(s);
        }

        @Override
        public void onNext(T t) {
            sub.onNext(t);
        }

        @Override
        public void onError(Throwable t) {
            sub.onError(t);
        }

        @Override
        public void onComplete() {
            sub.onComplete();
        }
    }
}
