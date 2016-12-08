package cc.before30.modernjava.ep08;

import java.math.BigDecimal;
import java.util.function.*;

/**
 * Created by before30 on 08/12/2016.
 */
public class FunctionReferenceExamples {
    public static void main(String[] args) {
        BiFunction<Integer, Integer, Integer> operator;

        operator = (x, y) -> Calculator.staticMethod(x, y);
        System.out.println(operator.apply(1, 2));

        operator = Calculator::staticMethod;
        System.out.println(operator.apply(3, 4));

        Calculator calc = new Calculator();
        operator = (x, y) -> calc.instanceMethod(x, y);
        System.out.println(operator.apply(4, 5));

        operator = calc::instanceMethod;
        System.out.println(operator.apply(6, 7));

        BiFunction<BigDecimal, BigDecimal, BigDecimal> operator1;
        operator1 = BigDecimal::add;


        Consumer<String> operator2;
        operator2 = System.out::println;
    }
}

class Calculator {
    public static int staticMethod(int x, int y) {
        return x + y;
    }

    public int instanceMethod(int x, int y) {
        return x + y;
    }
}
