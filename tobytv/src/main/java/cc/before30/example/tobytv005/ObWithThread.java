package cc.before30.example.tobytv005;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by before30 on 26/11/2016.
 */

/*
문제점
1. Complete???
2. Error???
이런 경우를 전달하고 싶을때 Observer Pattern을 바로 사용할 수 없었다
그래서 reactive에서 이걸 해결하려고 Observerble Pattern을 새로 정의 했다.

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
