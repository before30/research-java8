package cc.before30.review.week05;

import java.util.Iterator;
import java.lang.Integer;
/**
 * Created by before30 on 30/11/2016.
 */
public class IterableExample {

    public static void main(String[] args) {
        IntIterable<Integer> iterable = new IntIterable<>();
        for (Integer i : iterable) {
            System.out.println(i);
        }
    }

    static class IntIterable<Integer> implements Iterable<Integer> {

        @Override
        public Iterator<Integer> iterator() {
            return new Iterator() {
                int i = 0;
                final static int MAX = 10;

                @Override
                public boolean hasNext() {
                    return i < MAX;
                }

                @Override
                public Object next() {
                    return ++i;
                }
            };
        }
    }
}

