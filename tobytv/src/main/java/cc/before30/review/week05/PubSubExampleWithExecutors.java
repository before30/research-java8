package cc.before30.review.week05;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.*;

/**
 * User: before30 
 * Date: 2016. 12. 1.
 * Time: 오후 7:30
 */
public class PubSubExampleWithExecutors {
	public static void main(String[] args) throws InterruptedException {
		List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 11, 12, 13, 14);
		ExecutorService es = Executors.newSingleThreadExecutor();
//		ExecutorService es = Executors.newFixedThreadPool(2);
		IntPublisher publisher = new IntPublisher(list, es);
		PubSubExampleWithExecutors.IntSubscriber subscriber1 = new IntSubscriber("sub1");
		PubSubExampleWithExecutors.IntSubscriber subscriber2 = new IntSubscriber("sub2");
		PubSubExampleWithExecutors.IntSubscriber subscriber3 = new IntSubscriber("sub3");
		PubSubExampleWithExecutors.IntSubscriber subscriber4 = new IntSubscriber("sub4");


		publisher.subscribe(subscriber1);
		publisher.subscribe(subscriber2);
		publisher.subscribe(subscriber3);
		publisher.subscribe(subscriber4);

		es.awaitTermination(10, TimeUnit.MINUTES);
		es.shutdown();

	}

	static class IntPublisher implements Publisher<Integer> {
		private Iterable<Integer> iter;
		private ExecutorService executorService;

		public IntPublisher(Iterable<Integer> it, ExecutorService executorService) {
			this.iter = it;
			this.executorService = executorService;
		}

		@Override public void subscribe(Subscriber<? super Integer> subscriber) {
			subscriber.onSubscribe(new Subscription() {
				Iterator<Integer> iterator = iter.iterator();
				@Override public void request(long n) {

					executorService.submit(() -> {
						try {
							for (int i = 0; i < n; i++) {
								if (iterator.hasNext()) {
									subscriber.onNext(iterator.next());
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

				@Override public void cancel() {

				}
			});
		}
	}

	static class IntSubscriber implements Subscriber<Integer> {
		Subscription subscription;
		int requestSize = 3;
		int buffer = 0;
		String name;

		public IntSubscriber(String name) {
			this.name = name;
		}
		@Override public void onSubscribe(Subscription s) {
			this.subscription = s;
			System.out.println("onSubscribe");
			subscription.request(requestSize);
		}

		@Override public void onNext(Integer item) {
			System.out.println(Thread.currentThread().getName() + ":" + name + ":" + " onNext : " + item);
			if (++buffer >= requestSize) {
				buffer = 0;
				subscription.request(requestSize);
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
