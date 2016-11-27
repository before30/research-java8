package cc.before30.example.unicast;

import java.util.Collections;
import java.util.Iterator;
import java.util.concurrent.Executor;

/**
 * Created by before30 on 27/11/2016.
 */
public class NumberIterablePublisher extends AsyncIterablePublisher<Integer> {
    public NumberIterablePublisher(final int from, final int to, final Executor executor) {
        super(new Iterable<Integer>() {
            { if (from > to) throw new IllegalArgumentException("from must be equal or greater than to!"); }

            @Override public Iterator<Integer> iterator() {
                return new Iterator<Integer>() {
                    private int at = from;
                    @Override public boolean hasNext() { return at < to; }
                    @Override public Integer next() {
                        if (!hasNext()) return Collections.<Integer>emptyList().iterator().next();
                        else return at++;
                    }
                    @Override public void remove() { throw new UnsupportedOperationException(); }
                };
            }

        }, executor);
    }
}
