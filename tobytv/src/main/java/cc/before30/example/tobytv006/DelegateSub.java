package cc.before30.example.tobytv006;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/**
 * Created by before30 on 03/12/2016.
 */
public class DelegateSub implements Subscriber<Integer> {

    Subscriber sub;
    public DelegateSub(Subscriber sub) {
        this.sub = sub;
    }

    @Override
    public void onSubscribe(Subscription s) {
        sub.onSubscribe(s);
    }

    @Override
    public void onNext(Integer integer) {
        sub.onNext(integer);
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
