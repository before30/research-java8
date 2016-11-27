package cc.before30.example.unicast;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.Collections;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by before30 on 27/11/2016.
 */
public class AsyncIterablePublisher<T> implements Publisher<T> {
    private final static int DEFAULT_BATCHSIZE = 1024;

    private final Iterable<T> elements;
    private final Executor executor;
    private final int batchSize;

    public AsyncIterablePublisher(final Iterable<T> elements, final Executor executor) {
        this(elements, DEFAULT_BATCHSIZE, executor);
    }

    public AsyncIterablePublisher(final Iterable<T> elements, final int batchSize, final Executor executor) {
        if (elements == null) throw null;
        if (executor == null) throw null;
        if (batchSize < 1) throw new IllegalArgumentException("batchSize must be greater than zero!");
        this.elements = elements;
        this.executor = executor;
        this.batchSize = batchSize;
    }

    @Override
    public void subscribe(Subscriber<? super T> s) {
        new SubscriptionImpl(s).init();
    }

    static interface Signal {};
    enum Cancel implements Signal { Instance; }
    enum Subscribe implements Signal { Instance; }
    enum Send implements Signal { Instance; }
    static final class Request implements Signal {
        final long n;
        Request(final long n) {
            this.n = n;
        }
    }

    final class SubscriptionImpl implements Subscription, Runnable {

        final Subscriber<? super T> subscriber;
        private boolean cancelled = false;
        private long demand = 0;
        private Iterator<T> iterator;

        SubscriptionImpl(final Subscriber<? super T> subscriber) {
            if (subscriber == null) throw null;
            this.subscriber = subscriber;
        }

        private final ConcurrentLinkedQueue<Signal> inboundSignals = new ConcurrentLinkedQueue<>();
        private final AtomicBoolean on = new AtomicBoolean(false);

        private void doRequest(final long n) {
            if (n < 1) {
                terminateDueTo(new IllegalArgumentException(subscriber + " violated the Reactive Streams rule 3.9 by requesting a non-positive number of elems."));
            } else if (demand + n < 1) {
                demand = Long.MAX_VALUE;
                doSend();
            } else {
                demand += n;
                doSend();
            }
        }

        private void doCancel() {
            cancelled = true;
        }

        private void doSubscribe() {
            try {
                iterator = elements.iterator();
                if (iterator == null) {
                    iterator = Collections.<T>emptyList().iterator();
                }
            } catch (final Throwable t) {
                subscriber.onSubscribe(new Subscription() {

                    @Override
                    public void request(long n) {

                    }

                    @Override
                    public void cancel() {

                    }
                });
                terminateDueTo(t);
            }

            if ( !cancelled) {
                try {
                    subscriber.onSubscribe(this);

                } catch (final Throwable t) {
                    terminateDueTo(new IllegalStateException(subscriber + " violated the Reactive Streams rule 2.13 by throwing an exception from subscriotion", t));

                }

                boolean hasElements = false;
                try {
                    hasElements = iterator.hasNext();
                } catch(final Throwable t) {
                    terminateDueTo(t);
                }

                if (!hasElements) {
                    try {
                        doCancel();
                        subscriber.onComplete();
                    } catch(final Throwable t) {
                        (new IllegalStateException(subscriber + " violated the Reactive Streams rule 2.13 by throwing an exception from onComplete.", t)).printStackTrace(System.err);
                    }
                }
            }
        }

        private void doSend() {
            try {
                int leftInBatch = batchSize;
                do {
                    T next;
                    boolean hasNext;
                    try {
                        next = iterator.next();
                        hasNext = iterator.hasNext();

                    } catch (final Throwable t) {
                        terminateDueTo(t);
                        return;
                    }

                    subscriber.onNext(next);
                    if (!hasNext) {
                        doCancel();
                        subscriber.onComplete();
                    }
                } while(!cancelled
                        && --leftInBatch > 0
                        && --demand > 0);

                if (!cancelled && demand > 0) {
                    signal(Send.Instance);
                }
            } catch (final Throwable t) {
                doCancel();
                (new IllegalStateException(subscriber + " violated the Reactive Streams rule 2.13 by throwing an exception from onNext or onComplete." , t)).printStackTrace(System.err);
            }
        }

        private void terminateDueTo(final Throwable t) {
            cancelled = true;
            try {
                subscriber.onError(t);

            } catch(final Throwable t2) {
                (new IllegalStateException(subscriber + " violated the Reactive Streams rule 2.13 by throwing an exception from onError.", t2)).printStackTrace(System.err);
            }
        }

        private void signal(final Signal signal) {
            if (inboundSignals.offer(signal)) {
                tryScheduleToExecute();
            }
        }


        @Override
        public void run() {
            if (on.get()) {
                try {
                    final Signal s = inboundSignals.poll();
                    if (!cancelled) {
                        if (s instanceof Request) {
                            doRequest(((Request)s).n);
                        } else if (s == Send.Instance) {
                            doSend();
                        } else if (s == Cancel.Instance) {
                            doCancel();
                        } else if (s == Subscribe.Instance) {
                            doSubscribe();
                        }
                    }
                } finally {
                    on.set(false);
                    if (!inboundSignals.isEmpty()) {
                        tryScheduleToExecute();
                    }
                }
            }
        }

        private final void tryScheduleToExecute() {
            if (on.compareAndSet(false, true)) {
                try {
                    executor.execute(this);
                } catch (Throwable t) {
                    if (!cancelled) {
                        doCancel();
                        try {
                            terminateDueTo(new IllegalStateException("Publisher terminated due to unavailable Executor.", t));
                        } finally {
                            inboundSignals.clear();
                            on.set(false);
                        }
                    }
                }
            }
        }

        @Override
        public void request(long n) {
            signal(new Request(n));
        }

        @Override
        public void cancel() {
            signal(Cancel.Instance);
        }

        void init() {
            signal(Subscribe.Instance);
        }
    }
}
