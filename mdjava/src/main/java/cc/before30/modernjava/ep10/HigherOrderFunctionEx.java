package cc.before30.modernjava.ep10;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * Created by before30 on 13/12/2016.
 */
/*
두가지 조건중 하나를 만족하면 Higher Order Function이라고 부른다

 */
public class HigherOrderFunctionEx {
    public static void main(String[] args) {
        /*
        Function<Function<Integer, String>, String> func = f -> f.apply(10);

            f = x -> x.apply(10)
            f.apply(i -> “#” + i)

         */

        Function<Function<Integer, String>, String> func = f -> f.apply(10);
        System.out.println(func.apply(i -> "#" + i));

        Function<Integer, Function<Integer, Integer>> func2 = i -> i2 -> i + i2;

        System.out.println(func2.apply(1).apply(9));

        // 어떤 경우에 유용한것인가????
        final List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        List<String> map = map(list, i -> "$" + i);
        System.out.println(map);

        Function<Integer, Function<Integer, Integer>> f3 = i1 -> i2 -> i1 + i2;
        Function<Integer, Integer> plus10 = f3.apply(10);
        System.out.println(plus10.apply(1));
        System.out.println(plus10.apply(2));
        System.out.println(plus10.apply(3));
        System.out.println(plus10.apply(4));

    }

    private static <T, R> List<R> map(List<T> list, Function<T, R> mapper) {
        final List<R> result = new ArrayList<R>();
        for (final T t : list) {
            result.add(mapper.apply(t));
        }

        return result;
    }
}
