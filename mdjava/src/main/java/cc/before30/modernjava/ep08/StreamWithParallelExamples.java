package cc.before30.modernjava.ep08;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by before30 on 09/12/2016.
 */

/*
폰노이만 아키텍쳐 - 메모리 공유
Folk Join Architecture

 */
public class StreamWithParallelExamples {
    public static void main(String[] args) {

        final int[] sum = { 0 };

        IntStream.range(0, 100)
            .forEach(i -> sum[0] += i);
        System.out.println("sum: " + sum[0]);

        // multicore programming
        final int[] sum1 = { 0 };
        IntStream.range(0, 100)
                .parallel()
                .forEach(i -> sum1[0] += i);

        System.out.println("parallel sum (with side-effect):" + sum1[0]);
        // race condition이 발생했다

        System.out.println("Stream Sum (w/o side-effect): " +
                IntStream.range(1, 100)
                .parallel()
                .sum());

        Stream.of(1, 2, 3, 4, 5, 6, 7, 8)
                .parallel()
                .forEach(i -> System.out.println(i));
//
//        final long start = System.currentTimeMillis();
//        Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8)
//                .parallelStream()
//                .map(i -> {
//                    try {
//                        TimeUnit.SECONDS.sleep(1);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    return i;
//                })
//                .forEach(i -> System.out.println(i));
//        System.out.println(System.currentTimeMillis() - start);
//
//        System.out.println("======================");
//        final long start2 = System.currentTimeMillis();
//        Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8)
//                .stream()
//                .map(i -> {
//                    try {
//                        TimeUnit.SECONDS.sleep(1);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    return i;
//                })
//                .forEach(i -> System.out.println(i));
//        System.out.println(System.currentTimeMillis() - start2);
//
//        System.out.println("========");
//        final long start3 = System.currentTimeMillis();
//        Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8)
//                .parallelStream()
//                .map(i -> {
//                    try {
//                        TimeUnit.SECONDS.sleep(1);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    return i;
//                })
//                .forEachOrdered(i -> System.out.println(i));
//        System.out.println(System.currentTimeMillis() - start3);
//
        System.out.println("========");
        final long start4 = System.currentTimeMillis();

        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "7");
        Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8)
                .parallelStream()
                .map(i -> {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return i;
                })
                .forEach(i -> System.out.println(i));
        System.out.println(System.currentTimeMillis() - start4);
        int numOfCores = Runtime.getRuntime().availableProcessors();
        System.out.println(numOfCores);

    }
}
