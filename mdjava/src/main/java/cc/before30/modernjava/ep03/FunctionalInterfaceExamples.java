package cc.before30.modernjava.ep03;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Created by before30 on 26/11/2016.
 */
public class FunctionalInterfaceExamples {
    public static void main(String[] args) {

        final Consumer<String> print = new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println(s);
            }
        };

        print.accept("hello world");

        final Consumer<String> print2 = value -> System.out.println(value);
        print2.accept("good job");

//        final Function<String, Void> print3 = value -> System.out.println(value);

        final Consumer<String> greetings = value -> System.out.println("hello " + value);
        greetings.accept("Joel");
    }
}
