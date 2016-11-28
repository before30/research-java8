package cc.before30.modernjava.ep07;

import java.math.BigDecimal;

/**
 * Created by before30 on 28/11/2016.
 */
public class CustomFunctionalInterface {

    public static void main(String[] args) {

        BigDecimalToCurrency bigDecimalToCurrency = bd -> "$" + bd.toString();
        System.out.println(bigDecimalToCurrency.toCurrency(new BigDecimal("120.00")));

//        final InvalidFunctionalInterface invalidFunctionalInterface = (i) -> "test";
    }
}

@FunctionalInterface
interface Function3<T1, T2, T3, R> {
    R apply(T1 t1, T2 t2, T3 t3);
}

@FunctionalInterface
interface BigDecimalToCurrency {
    String toCurrency(BigDecimal value);
}

@FunctionalInterface
interface InvalidFunctionalInterface {
    <T> String mkString(T value);
}