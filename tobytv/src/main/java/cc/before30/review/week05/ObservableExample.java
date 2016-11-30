package cc.before30.review.week05;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by before30 on 30/11/2016.
 */
public class ObservableExample {
    public static void main(String[] args) {
        IntObserver observer1 = new IntObserver("observer1");
        IntObserver observer2 = new IntObserver("observer2");
        IntObservable observable = new IntObservable();
        observable.addObserver(observer1);
        observable.doSomething();

        System.out.println("-------------");

        observable.addObserver(observer2);
        observable.doSomething();

        System.out.println("-------------");
        observable.deleteObserver(observer1);
        observable.doSomething();


    }

    static class IntObservable extends Observable {
        public void doSomething() {
            for (int i = 0; i <= 10; i++) {
                setChanged();
                notifyObservers(i);
            }
        }
    }

    static class IntObserver implements Observer {
        private String name;

        public IntObserver(String name) {
            this.name = name;
        }

        @Override
        public void update(Observable o, Object arg) {
            System.out.println(name + ":" + arg);
        }
    }
}
