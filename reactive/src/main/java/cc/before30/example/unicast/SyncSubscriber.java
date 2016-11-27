package cc.before30.example.unicast;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/**
 * Created by before30 on 28/11/2016.
 */
public abstract class SyncSubscriber<T> implements Subscriber<T> {
    private Subscription subscription;
    private boolean done = false;

    @Override
    public void onSubscribe(Subscription s) {
        if (s == null) throw null;

        if (subscription != null) {
            try {
                s.cancel();
            } catch (final Throwable t) {
                (new IllegalStateException(s + " violated the Reactive Streams rule 3.15 by throwing an exception from cancel.", t)).printStackTrace(System.err);
            }

        } else {
            subscription = s;
            try {
                s.request(1);
            } catch (final Throwable t) {
                (new IllegalStateException(s + " violated the Reactive Streams rule 3.16 by throwing an exception from request.", t)).printStackTrace(System.err);
            }
        }
    }

    @Override
    public void onNext(final T element) {
        if (subscription == null) {
            (new IllegalStateException("Publisher violated the Reactive Streams rule 1.09 signalling onNext prior to onSubscribe.")).printStackTrace(System.err);
        } else {
            if (element == null) throw null;
            if (!done) { // If we aren't already done
                try {
                    if (foreach(element)) {
                        try {
                            subscription.request(1); // Our Subscriber is unbuffered and modest, it requests one element at a time
                        } catch (final Throwable t) {
                            // Subscription.request is not allowed to throw according to rule 3.16
                            (new IllegalStateException(subscription + " violated the Reactive Streams rule 3.16 by throwing an exception from request.", t)).printStackTrace(System.err);
                        }
                    } else {
                        done();
                    }
                } catch (final Throwable t) {
                    done();
                    try {
                        onError(t);
                    } catch (final Throwable t2) {
                        //Subscriber.onError is not allowed to throw an exception, according to rule 2.13
                        (new IllegalStateException(this + " violated the Reactive Streams rule 2.13 by throwing an exception from onError.", t2)).printStackTrace(System.err);
                    }
                }
            }
        }
    }

    private void done() {
        done = true;
        try {
            subscription.cancel();
        } catch (final Throwable t) {
            (new IllegalStateException(subscription + " violated the Reactive Streams rule 3.15 by throwing an exception from cancel.", t)).printStackTrace(System.err);
        }
    }

    protected abstract boolean foreach(final T element);

    @Override
    public void onError(Throwable t) {
        if (subscription == null) {
            (new IllegalStateException("Publisher violated the Reactive Streams rule 1.09 signalling onError prior to onSubscribe.")).printStackTrace(System.err);
        } else {
            if (t == null) throw null;
        }
    }

    @Override
    public void onComplete() {
        if (subscription == null) {
            (new IllegalStateException("Publisher violated the Reactive Streams rule 1.09 signalling onComplete prior to onSubscribe.")).printStackTrace(System.err);
        } else {

        }
    }
}
