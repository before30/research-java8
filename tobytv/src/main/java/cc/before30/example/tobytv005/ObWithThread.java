package cc.before30.example.tobytv005;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by before30 on 26/11/2016.
 */
public class ObWithThread {

    static class IntObservable extends Observable implements Runnable {

        @Override
        public void run() {
            for (int i=1; i<=10; i++) {
                setChanged();
                notifyObservers(i);
            }
        }
    }

    public static void main(String[] args) {
        Observer ob = new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                System.out.println(Thread.currentThread().getName() + " : " + arg);
            }
        };

        IntObservable io = new IntObservable();
        io.addObserver(ob);

        ExecutorService es = Executors.newSingleThreadExecutor();
        es.execute(io);

        System.out.println(Thread.currentThread().getName() + " EXIT");
        es.shutdown();
    }
}
