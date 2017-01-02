package cc.before30.review.week09;

import org.springframework.core.task.AsyncListenableTaskExecutor;
import org.springframework.core.task.support.TaskExecutorAdapter;
import org.springframework.util.concurrent.ListenableFuture;
import org.testng.annotations.Test;

import static org.junit.Assert.*;

/**
 * Created by before30 on 02/01/2017.
 */
public class ListenablefutureExTest {

    private final AsyncListenableTaskExecutor executor = new TaskExecutorAdapter(Runnable::run);

    @Test
    public void testTransformationFromSpring() {
    }

}