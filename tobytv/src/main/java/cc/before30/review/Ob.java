package cc.before30.review;

import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

/**
 * User: before30 
 * Date: 2016. 11. 29.
 * Time: 오후 12:29
 */
public class Ob {
	// Duality
	// Observer Pattern
	// Reactive Streams - 표준
	// Iterable <---> Observable
	public static void main(String[] args) {
		Iterable<Integer> iterable = () ->
			new Iterator<Integer>() {
				int i = 0;
				final static int MAX = 10;

				@Override public boolean hasNext() {
					return i < MAX;
				}

				@Override public Integer next() {
					return ++i;
				}
			};

		for (Integer i : iterable) {
			System.out.println(i);
		}

		for (Iterator<Integer> it = iterable.iterator(); it.hasNext();) {
			System.out.println(it.next());
		}

		///////
		// subscriber
		// observer
		Observer observer = new Observer() {
			@Override public void update(Observable o, Object arg) {
				System.out.println(arg);
			}
		};

		IntObservable intObservable = new IntObservable();
		intObservable.addObserver(observer);

		intObservable.run();

	}

	// subject
	// publisher
	static class IntObservable extends Observable implements Runnable {

		@Override public void run() {
			for (int i=1; i<=10; i++) {
				setChanged();
				notifyObservers(i);
			}
		}
	}



}
