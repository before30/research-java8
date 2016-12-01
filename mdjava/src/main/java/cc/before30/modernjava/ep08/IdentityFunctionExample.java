package cc.before30.modernjava.ep08;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by before30 on 01/12/2016.
 */
public class IdentityFunctionExample {
    public static void main(String[] args) {
        final List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        System.out.println(map(numbers, i -> i * 2));
        System.out.println(map(numbers, null));

        System.out.println(map2(numbers, i -> i * 2));
        System.out.println(map2(numbers, null));

        System.out.println(map3(numbers, i -> i * 2));
        System.out.println(map3(numbers, i -> i));

        System.out.println(map3(numbers, Function.identity()));
    }

    private static <T, R> List<R> map3(final List<T> list, final Function<T, R> mapper) {
        final List<R> result = new ArrayList<R>();
        for (T t: list) {
            result.add(mapper.apply(t));
        }

        return result;
    }

    private static <T, R> List<R> map2(final List<T> list, final Function<T, R> mapper) {
        final Function<T, R> function;
        if (mapper != null) {
            function = mapper;
        } else {
            function = t -> (R)t;
        }

        final List<R> result = new ArrayList<R>();
        for (T t: list) {
            result.add(function.apply(t));
        }

        return result;
    }

    private static <T, R> List<R> map(final List<T> list, final Function<T, R> mapper) {
        if (mapper == null) {
            return new ArrayList<>((List<R>)list);
        } else {
            return list.stream().map(mapper).collect(Collectors.toList());
        }
    }
}
