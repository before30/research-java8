package cc.before30.example.tobytv005;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Created by before30 on 26/11/2016.
 */
public class PubSub {

    public static void main(String[] args) throws InterruptedException {
        // Publisher = Observable
        // Subscriber = Observer

        // 람다냐 익명클래스냐


        Iterable<Integer> itr = Arrays.asList(1, 2, 3, 4, 5);
        ExecutorService es = Executors.newSingleThreadExecutor();

        Publisher<Integer> p = new Publisher<Integer>() {
            Iterator<Integer> it = itr.iterator();

            @Override
            public void subscribe(Subscriber subscriber) {
                subscriber.onSubscribe(new Subscription() {
                    @Override
                    public void request(long n) {

                        Future<?> f = es.submit(() -> {
                            int i = 0;
                            try {
                                while (i++ < n) {
                                    if (it.hasNext()) {
                                        subscriber.onNext(it.next());
                                    } else {
                                        subscriber.onComplete();
                                        break;
                                    }
                                }
                            } catch (RuntimeException ex) {
                                subscriber.onError(ex);
                            }
                        });
                    }

                    @Override
                    public void cancel() {

                    }
                });
            }
        };

        Subscriber<Integer> s = new Subscriber<Integer>() {
            Subscription subscription;

            @Override
            public void onSubscribe(Subscription subscription) {
                System.out.println("onSubscribe");
                this.subscription = subscription;
//                subscription.request(Long.MAX_VALUE);
                subscription.request(1);
            }

            int bufferSize = 1;
            @Override
            public void onNext(Integer item) {
                System.out.println("onNext " + item);
                if (--bufferSize <= 0) {
                    bufferSize = 1;
                    subscription.request(1);
                }
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("onError");
            }

            @Override
            public void onComplete() {
                System.out.println("onComplete");
            }
        };

        Subscriber<Integer> s2 = new Subscriber<Integer>() {
            @Override
            public void onSubscribe(Subscription subscription) {
                System.out.println("s2:onSubscribe");
//                subscription.request(Long.MAX_VALUE);
                subscription.request(3);
            }

            @Override
            public void onNext(Integer item) {
                System.out.println("s2:onNext " + item);
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("s2:onError");
            }

            @Override
            public void onComplete() {
                System.out.println("s2:onComplete");
            }
        };

        p.subscribe(s);
//        p.subscribe(s2);
        es.awaitTermination(10, TimeUnit.MINUTES);
        es.shutdown();
    }
}
