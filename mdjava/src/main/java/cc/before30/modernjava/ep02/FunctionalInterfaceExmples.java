package cc.before30.modernjava.ep02;

import java.util.function.Function;

/**
 * Created by before30 on 26/11/2016.
 */
public class FunctionalInterfaceExmples {


    public static void main(String[] args) {
        Function<String, Integer> toInt = new Function<String, Integer>() {
            @Override
            public Integer apply(String value) {
                return Integer.parseInt(value);
            }
        };

        final Integer number = toInt.apply("100");
        System.out.println(number);

        final Function<String, Integer> toInt2 = value -> Integer.parseInt(value);
        final Integer number2 = toInt2.apply("100");
        System.out.println(number2);

        final Function<Integer, Integer> identity = Function.identity();
        System.out.println(identity.apply(999));

        final Function<Integer, Integer> identity2 = t -> t;
        System.out.println(identity2.apply(119));


    }
}
