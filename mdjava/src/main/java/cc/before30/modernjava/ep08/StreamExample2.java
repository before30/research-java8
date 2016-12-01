package cc.before30.modernjava.ep08;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created by before30 on 02/12/2016.
 */
public class StreamExample2 {
    private static final List<Integer> NUMBERS = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

    public static void main(String[] args) {

        Integer result = null;
        int count1 = 0;
        for (final Integer number : NUMBERS) {
            if (number > 3) {
                count1++;
                if (number < 9) {
                    count1++;
                    final Integer newNumber = number * 2;
                    count1++;

                    if (newNumber > 10) {
                        count1++;
                        result = newNumber;
                        break;
                    } else {
                        count1++;
                    }
                } else {
                    count1++;
                }
            } else {
                count1++;
            }
        }
        System.out.println("==================================");
        System.out.println("Imperative Result: " + result);
        System.out.println("Imperative Result Count: " + count1);


        System.out.println("==================================");
        System.out.println("Functional Result: " +
                NUMBERS.stream()
                        .filter(n -> n > 3)
                        .filter(n -> n < 9)
                        .map(n -> n * 2)
                        .filter(n -> n > 10)
                        .findFirst()
        );

        final AtomicInteger count = new AtomicInteger(0);

        NUMBERS.stream()
                .filter(n -> {
                    count.addAndGet(1);
                    return n > 3;
                })
                .filter(n -> {
                    count.addAndGet(1);
                    return n < 9;
                })
                .map(n -> {
                    count.addAndGet(1);
                    return n * 2;
                })
                .filter(n -> {
                    count.addAndGet(1);
                    return n > 10;
                })
                .findFirst()
        ;

        System.out.println("Functional Result: count " + count);

        System.out.println("==================================");
        final AtomicInteger count3 = new AtomicInteger(1);

        final List<Integer> greaterThan3 = filter(NUMBERS, i -> {
            System.out.println(count3.getAndAdd(1) + ": i > 3");
            return i > 3;
        });
        final List<Integer> lessThan9 = filter(greaterThan3, i -> {
            System.out.println(count3.getAndAdd(1) + ": i < 9");
            return i < 9;
        });
        final List<Integer> doubled = map(lessThan9, i -> {
            System.out.println(count3.getAndAdd(1) + ": i * 2");
            return i * 2;
        });
        final List<Integer> greaterThan10 = filter(doubled, i -> {
            System.out.println(count3.getAndAdd(1) + ": i > 10");
            return i > 10;
        });
        System.out.println("My own method result: " + greaterThan10);
        System.out.println("My own method result.get(0): " + greaterThan10.get(0));
        System.out.println("My own method result Count : " + count3);


    }

    private static <T> List<T> filter(List<T> list, Predicate<? super T> predicate) {
        final List<T> result = new ArrayList<>();
        for (final T t : list) {
            if (predicate.test(t)) {
                result.add(t);
            }
        }
        return result;
    }

    private static <T, R> List<R> map(List<T> list, Function<T, R> mapper) {
        final List<R> result = new ArrayList<>();
        for (final T t : list) {
            result.add(mapper.apply(t));
        }
        return result;
    }
}
