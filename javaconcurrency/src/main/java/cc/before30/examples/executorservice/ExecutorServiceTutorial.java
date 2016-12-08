package cc.before30.examples.executorservice;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by before30 on 08/12/2016.
 */
public class ExecutorServiceTutorial {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        Runnable runnable = () -> {
          try {
              System.out.println("Running RunnableTask");
              TimeUnit.SECONDS.sleep(3);
              System.out.println("End Running RunnableTask");
          } catch (InterruptedException e) {
              e.printStackTrace();
          }
        };

        Callable<String> callableTask = new Callable<String>() {

            @Override
            public String call() throws Exception {
                final int callableCode = (int)(Math.random() * 1000);
                System.out.println("Starting callable code " + callableCode);
                TimeUnit.SECONDS.sleep(3);
                return "task's execution. " + callableCode;
            }
        };

        List<Callable<String>> callableTasks = new ArrayList<>();
        callableTasks.add(callableTask);
        callableTasks.add(callableTask);
        callableTasks.add(callableTask);

        executorService.execute(runnable);

        Future<String> future = executorService.submit(callableTask);

        try {
            System.out.println("Single Callable isDone before get : " + future.isDone());
            System.out.println("Single Callable result : " + future.get(3100, TimeUnit.MILLISECONDS));
            System.out.println("Single Callable isDone after get : " + future.isDone());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

        try {
            List<Future<String>> futuresOfInvokeAll = executorService.invokeAll(callableTasks);
            for (Future<String> callResult : futuresOfInvokeAll) {

                try {
                    System.out.println("invokeAll result : " + callResult.get());
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        List<Future<String>> futuresOfInvokeAll = executorService.invokeAll(callableTasks);
//        for (Future<String> callResult : futuresOfInvokeAll) {
//            System.out.println("invokeAll result : " + callResult.get());
//        }


        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(10, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }
    }
}
