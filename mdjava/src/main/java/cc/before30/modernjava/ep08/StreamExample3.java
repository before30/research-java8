package cc.before30.modernjava.ep08;

/**
 * Created by before30 on 03/12/2016.
 */

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Laxy Evaluation
 *
 * - Intermediate Operation Method => 중간 단계 Operation
 * (filter, map
 * - Terminal Operation Method => 끝내는 Opertion
 * (findFirst, Collect
 */
public class StreamExample3 {
    public static void main(String[] args) {
        System.out.println("hello world");

        final List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);
        numbers.stream();

        Stream<Integer> stream = Stream.of(1, 2, 2, 3, 3, 4, 4, 5, 5);
        // Terminate -> Optional, T, R, void
        // Intermediate -> Stream

        System.out.println("collect toList:" + Stream.of(1, 2, 2, 3, 3, 4, 4, 5, 5)
                .filter(i -> i > 2)
                .map(i -> i * 2)
                .map(i -> "#" + i)
                .collect(Collectors.toList())
        );

        System.out.println("collect toSet:" + Stream.of(1, 2, 2, 3, 3, 4, 4, 5, 5)
                .filter(i -> i > 2)
                .map(i -> i * 2)
                .map(i -> "#" + i)
                .collect(Collectors.toSet())
        );

        System.out.println("collect joining:" + Stream.of(1, 2, 2, 3, 3, 4, 4, 5, 5)
                .filter(i -> i > 2)
                .map(i -> i * 2)
                .map(i -> "#" + i)
                .collect(Collectors.joining())
        );

        System.out.println("collect joining:" + Stream.of(1, 2, 2, 3, 3, 4, 4, 5, 5)
                .filter(i -> i > 2)
                .map(i -> i * 2)
                .map(i -> "#" + i)
                .collect(Collectors.joining(", ", "[", "]"))
        );

        System.out.println("collect joining with distinct:" + Stream.of(1, 2, 2, 3, 3, 4, 4, 5, 5)
                .filter(i -> i > 2)
                .map(i -> i * 2)
                .map(i -> "#" + i)
                .distinct()
                .collect(Collectors.joining(", ", "[", "]"))
        );

        System.out.println(
                Stream.of(1, 2, 3, 4, 5)
                .findAny()
        );

        System.out.println(
                Stream.of(1, 2, 3, 4, 5)
                    .findFirst()
        );

        System.out.println(
                Stream.of()
                        .findAny()
        );

        Optional optional = Stream.of().findAny();
        optional.ifPresent(new Consumer() {
            @Override
            public void accept(Object o) {
                System.out.println(o);
            }
        });

        Consumer<String> c1 = x -> System.out.println(x.toLowerCase());
        Consumer<String> c2 = x -> System.out.println(x.toUpperCase());
        c1.andThen(c2).accept("test1234");

        // AutoBoxing -> Integer.valueOf(1) -> Integer Object Return
        // UnBoxing
        // Primitive Type, Object Type

        System.out.println(
                Stream.of(1, 2, 3, 4, 5, 128)
                .filter(i -> i.equals(new Integer(128)))
                .findFirst()
        );
    }
}
