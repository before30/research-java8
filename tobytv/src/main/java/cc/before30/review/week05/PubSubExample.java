package cc.before30.review.week05;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * User: before30 
 * Date: 2016. 12. 1.
 * Time: 오전 11:17
 */
public class PubSubExample {
	public static void main(String[] args) {
//		List<Integer> list = Arrays.asList(1, 2, 3, 4);
		List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
		IntPublisher intPublisher = new IntPublisher(list);
		IntSubscriber intSubscriber = new IntSubscriber();

		intPublisher.subscribe(intSubscriber);
	}

	static class IntPublisher implements Publisher<Integer> {
		Iterator<Integer> iter;
		public IntPublisher(Iterable<Integer> it) {
			this.iter = it.iterator();
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
		int requestSize = 3;
		int buffer = 0;

		@Override public void onSubscribe(Subscription s) {
			this.subscription = s;
			System.out.println("onSubscribe");
			subscription.request(requestSize);
		}

		@Override public void onNext(Integer item) {
			System.out.println("onNexte " + item);
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
