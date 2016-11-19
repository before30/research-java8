package cc.before30.example.tobytv004;

import java.util.*;

/**
 * Created by before30 on 19/11/2016.
 */
public class TypeInference {
    static class MyList<E, P> implements List<E> {

        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean contains(Object o) {
            return false;
        }

        @Override
        public Iterator<E> iterator() {
            return null;
        }

        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @Override
        public <T> T[] toArray(T[] a) {
            return null;
        }

        @Override
        public boolean add(E e) {
            return false;
        }

        @Override
        public boolean remove(Object o) {
            return false;
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            return false;
        }

        @Override
        public boolean addAll(Collection<? extends E> c) {
            return false;
        }

        @Override
        public boolean addAll(int index, Collection<? extends E> c) {
            return false;
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            return false;
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            return false;
        }

        @Override
        public void clear() {

        }

        @Override
        public E get(int index) {
            return null;
        }

        @Override
        public E set(int index, E element) {
            return null;
        }

        @Override
        public void add(int index, E element) {

        }

        @Override
        public E remove(int index) {
            return null;
        }

        @Override
        public int indexOf(Object o) {
            return 0;
        }

        @Override
        public int lastIndexOf(Object o) {
            return 0;
        }

        @Override
        public ListIterator<E> listIterator() {
            return null;
        }

        @Override
        public ListIterator<E> listIterator(int index) {
            return null;
        }

        @Override
        public List<E> subList(int fromIndex, int toIndex) {
            return null;
        }
    }

    static <T> void method(T t, List<T> list) {
        System.out.println(t.getClass().getName());
    }

    public static void main(String[] args) {
        Integer i = 10;
        Number n = i;

        List<Integer> intList = new ArrayList<>();
//        List<Number> numberList = intList; // List Integer <-> List Number (Integer, Number는 상속관계지만 List가...)

        ArrayList<Integer> arrayList = new ArrayList<>();
        List<Integer> list = arrayList;

        List<String> s1 = new MyList<String, Integer>();
        List<String> s2 = new MyList<String, String>();


        // Type 추촌
        method(1, Arrays.asList(1, 2, 3));
        List<String> strs = new ArrayList<>(); // Compiler가 알아서 앞의 Type을 추론해서 뒤에 <>로 넣어줌
        List<String> strs2 = Collections.emptyList();
    }
}
