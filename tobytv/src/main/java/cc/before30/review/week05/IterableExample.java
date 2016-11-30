package cc.before30.review.week05;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Arrays;
import java.util.Iterator;
import java.lang.Integer;
import java.util.List;

/**
 * Created by before30 on 30/11/2016.
 */
// PULL 방식으로 DATA 가져오는 방법
public class IterableExample {

    public static void main(String[] args) {
        IntIterable<Integer> iterable = new IntIterable<>();
        for (Integer i : iterable) {
            System.out.println(i);
        }

        RandomStringIterable<String> iterable1 = new RandomStringIterable<>();
        for (String s : iterable1) {
            System.out.println(s);
        }

        List<Integer> arrays = Arrays.asList(1, 2, 3, 4, 5);
        SomeIterable<Integer> someIterable = new SomeIterable(arrays);
        for (Integer i : someIterable) {
            System.out.println(i);
        }

        for (Iterator<Integer> it = someIterable.iterator(); it.hasNext();) {
            System.out.println(it.next());
        }



    }

    static class SomeIterable<T> implements Iterable<T> {
        private List<T> list;

        public SomeIterable(List<T> list) {
            this.list = list;
        }

        @Override
        public Iterator iterator() {
            return new Iterator() {
                final int size = list.size();
                int i = 0;

                @Override
                public boolean hasNext() {

                    return i < size;
                }

                @Override
                public Object next() {
                    return list.get(i++);
                }
            };
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

    static class RandomStringIterable<String> implements Iterable<String> {

        @Override
        public Iterator iterator() {
            return new Iterator() {
                int i = 0;
                final static int MAX = 10;

                @Override
                public boolean hasNext() {
                    return i < MAX;
                }

                @Override
                public Object next() {
                    return RandomStringUtils.randomAlphabetic(++i);
                }
            };
        }
    }
}

