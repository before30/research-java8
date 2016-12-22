package cc.before30.cc.before30.java8tests;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Created by before30 on 19/12/2016.
 */
public class Currying {

    public static void main(String[] args) {

        BiFunction<Integer, Integer, Integer> adder = (x, y) -> x + y;

        Function<Integer, Function<Integer, Integer>> currier1 = (a) -> ((b) -> ((a * 2) + (b / 2) ));

        Function<Integer, Function<Integer, Integer>> currier = a -> b -> adder.apply(a, b);

        System.out.println(currier1.apply(4).apply(100));

        Function<Integer, Integer> add3 = (a) -> a + 3;
        Function<Integer, Integer> times2 = (a) -> a * 2;

        Function<Integer, Integer> composedA = add3.compose(times2);
        Function<Integer, Integer> composedB = times2.compose(add3);

        System.out.println(composedA.apply(6)); // (6*2)+3
        System.out.println(composedB.apply(6)); // (6+3)*2

    }

    static Function<Integer, Function<Integer, Function<Integer, Integer>>> calculation =
            x -> y -> z -> x + y + z;
    static Function<Integer, Integer> calculation(Integer x, Integer y) {
        return calculation.apply(x).apply(y);
    }


    private Stream<Integer> calculate(Stream<Integer> stream, Integer a, Integer b) {
        return stream.map(calculation(b, a));
    }

}
