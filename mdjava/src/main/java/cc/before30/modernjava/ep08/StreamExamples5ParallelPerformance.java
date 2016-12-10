package cc.before30.modernjava.ep08;

import java.util.concurrent.TimeUnit;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * Created by before30 on 10/12/2016.
 */
public class StreamExamples5ParallelPerformance {

    private static void slowDown() {
        try {
            TimeUnit.MILLISECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static long iterativeSum(long n) {
        long result = 0;
        for (long i=0; i<=n; i++) {
            result += i;
            slowDown();
        }
        return result;
    }

    public static long sequentialSum(long n) {
        return Stream.iterate(1L, i -> i + 1).limit(n).peek(i -> slowDown()).reduce(Long::sum).get();
    }

    public static long parallelSum(long n) {
        return Stream.iterate(1L, i -> i + 1).limit(n).parallel().peek(i -> slowDown()).reduce(Long::sum).get();
    }

    public static long rangedSum(long n) {
        return LongStream.rangeClosed(1, n).peek(i -> slowDown()).reduce(Long::sum).getAsLong();
    }

    public static long parallelRangedSum(long n) {
        return (LongStream.range(1, n).parallel().peek(i -> slowDown()).reduce(Long::sum).getAsLong());
    }

    /*
    이렇게 느린데 왜 Stream, Funtional을 쓰는가?
    성능은 떨어지는데...
    Stream.iterate를 사용하기 때문에 parallel이 더 느리게 만 만든다

     */
    public static void main(String[] args) {
        final long n = 1_000;
        final long start = System.currentTimeMillis();
//    1 + 2 + 3 + ... + 98 + 99 + 100
        System.out.println((1 + n) * (n / 2));
        System.out.println("           Gauss Way: " + (System.currentTimeMillis() - start));

        final long start1 = System.currentTimeMillis();
        System.out.println("     iterativeSum(n): " + iterativeSum(n));
        System.out.println("                      " + (System.currentTimeMillis() - start1) + " ms\n");
        final long start2 = System.currentTimeMillis();
        System.out.println("    sequentialSum(n): " + sequentialSum(n));
        System.out.println("                      " + (System.currentTimeMillis() - start2) + " ms\n");
        final long start3 = System.currentTimeMillis();
        System.out.println("      parallelSum(n): " + parallelSum(n));
        System.out.println("                      " + (System.currentTimeMillis() - start3) + " ms\n");
        final long start4 = System.currentTimeMillis();
        System.out.println("        rangedSum(n): " + rangedSum(n));
        System.out.println("                      " + (System.currentTimeMillis() - start4) + " ms\n");
        final long start5 = System.currentTimeMillis();
        System.out.println("parallelRangedSum(n): " + parallelRangedSum(n));
        System.out.println("                      " + (System.currentTimeMillis() - start5) + " ms\n");
    }
}
