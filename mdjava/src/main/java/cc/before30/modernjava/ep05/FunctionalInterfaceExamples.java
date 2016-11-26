package cc.before30.modernjava.ep05;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * Created by before30 on 26/11/2016.
 */
public class FunctionalInterfaceExamples {
    public static void main(String[] args) {
        // lazy value evaluation 가능하다.
        // supplier

        final Supplier<String> helloSupplier = () -> "Hello ";
        System.out.println(helloSupplier.get() + "world");

        printIfValidIndex(0, "Joseph");
        printIfValidIndex(1, "Joseph");
        printIfValidIndex(-1, "Joseph");

        long start = System.currentTimeMillis();
        printIfValidIndex(0, getVeryExpensiveValue());
        printIfValidIndex(-1, getVeryExpensiveValue());
        printIfValidIndex(-2, getVeryExpensiveValue());
        // call by value가 되기 때문에 뒤에 함수가 먼저 실행된다.
        System.out.println("It took " + ((System.currentTimeMillis() - start) / 1000));

        start = System.currentTimeMillis();
        printIfValidIndex(0, new Supplier<String>() {
            @Override
            public String get() {
                return getVeryExpensiveValue();
            }
        });
        printIfValidIndex(-1, () -> getVeryExpensiveValue());
        printIfValidIndex(-2, () -> getVeryExpensiveValue());
        // call by value가 되기 때문에 뒤에 함수가 먼저 실행된다.
        System.out.println("It took " + ((System.currentTimeMillis() - start) / 1000));
    }

    private static void printIfValidIndex(int number, String value) {
        if (number >= 0) {
            System.out.println("The value is " + value + ".");
        } else {
            System.out.println("Invalid");
        }
    }

    private static void printIfValidIndex(int number, Supplier<String> valueSupplier) {
        if (number >= 0) {
            System.out.println("The value is " + valueSupplier.get() + ".");
        } else {
            System.out.println("Invalid");
        }
    }

    private static String getVeryExpensiveValue() {
        // it has very expensive calculation
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Joseph";
    }
}
