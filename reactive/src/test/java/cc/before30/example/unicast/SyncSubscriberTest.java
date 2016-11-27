package cc.before30.example.unicast;

import org.reactivestreams.Subscriber;
import org.reactivestreams.tck.SubscriberBlackboxVerification;
import org.reactivestreams.tck.TestEnvironment;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.testng.Assert.*;

/**
 * Created by before30 on 28/11/2016.
 */

@Test
public class SyncSubscriberTest extends SubscriberBlackboxVerification<Integer> {

    private ExecutorService e;
    @BeforeClass void before() { e = Executors.newFixedThreadPool(4); }
    @AfterClass void after() { if (e != null) e.shutdown(); }

    public SyncSubscriberTest() {
        super(new TestEnvironment());
    }

    @Override
    public Subscriber<Integer> createSubscriber() {

        return new SyncSubscriber<Integer>() {
            private long acc;

            @Override
            protected boolean foreach(Integer element) {
                acc += element;
                return true;
            }

            @Override
            public void onComplete() {
                System.out.println("Accumulated: " + acc);
            }
        };
    }

    @Override
    public Integer createElement(int element) {
        return element;
    }
}