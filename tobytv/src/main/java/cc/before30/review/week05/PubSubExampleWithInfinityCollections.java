package cc.before30.review.week05;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.PrimitiveIterator;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * Created by before30 on 01/12/2016.
 */
public class PubSubExampleWithInfinityCollections {
    public static void main(String[] args) {
        Iterator<Integer> it = IntStream.iterate(1, i -> i + 1).iterator();

        IntPublisher intPublisher = new IntPublisher(it);
        IntSubscriber intSubscriber = new IntSubscriber();

        intPublisher.subscribe(intSubscriber);
    }

    static class IntPublisher implements Publisher<Integer> {
        Iterator<Integer> iter;

        public IntPublisher(Iterator<Integer> it) {
            this.iter = it;
        }

        @Override public void subscribe(Subscriber<? super Integer> subscriber) {
            subscriber.onSubscribe(new Subscription() {
                @Override public void request(long n) {
                    for (int i = 0; i<n; i++ ) {
                        if (iter.hasNext()) {
                            subscriber.onNext(iter.next());
                        } else {
                            subscriber.onComplete();
                            break;
                        }
                    }

                }

                @Override public void cancel() {

                }
            });
        }
    }

    static class IntSubscriber implements Subscriber<Integer> {
        Subscription subscription;
        int requestSize = 1;
        int buffer = 0;

        @Override public void onSubscribe(Subscription s) {
            this.subscription = s;
            System.out.println("onSubscribe");
            subscription.request(requestSize);
        }

        @Override public void onNext(Integer item) {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("onNext " + item);
            if (++buffer >= requestSize) {
                System.out.println("hi");
                buffer = 0;
                subscription.request(requestSize);
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }

        @Override public void onError(Throwable t) {
            System.out.println("onError" + t.getMessage());
        }

        @Override public void onComplete() {

            System.out.println("onComplete ");
        }
    }
}
