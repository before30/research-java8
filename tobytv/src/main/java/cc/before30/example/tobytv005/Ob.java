package cc.before30.example.tobytv005;

import java.util.*;

/**
 * Created by before30 on 26/11/2016.
 */
public class Ob {

    // Functional Reactive Programming
    // Reactive Functional Programming
    // 외부의 Event, Data 발생에 대한 대응을 하는 방식으로 프로그래밍을 작성하는 것을 통털어서 이야기한다.
    // Duality
    // Observer Pattern
    // Reactive Streams - Standard

    /**
     *
     *
     // Iterable <----> Observable (duality)
    // pull 방식 <-----> push 방식
    public static void main(String[] args) {
        Iterable<Integer> iter = () ->
            new Iterator() {

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

        for (Integer i : iter) {
            System.out.println(i);
        }

        for (Iterator<Integer> it = iter.iterator(); it.hasNext();) {
            System.out.println(it.next());
        }
    }
                */

    static class IntObservable extends Observable implements Runnable {

        @Override
        public void run() {
            for (int i=1; i<=10; i++) {
                setChanged();
                notifyObservers(i);
            }
        }
    }

    // DATA method(void) <-> void method(DATA)

    public static void main(String[] args) {
//        Observable Source (event 발생/Data) -> Observer
        Observer ob = new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                System.out.println(arg);
            }
        };

        IntObservable io = new IntObservable();
        io.addObserver(ob);

        io.run();
    }
}
