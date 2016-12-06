package cc.before30.modernjava.review.ep01;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Created by before30 on 07/12/2016.
 */
public class ConsumerExamples {
    public static void main(String[] args) {

        // 1
        final Consumer<String> print1 = new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println("hello : " + s);
            }
        };

        print1.accept("joel1");

        // 2
        final Consumer<String> print2 = (String s) -> {
                System.out.println("hello : " + s);
        };

        print2.accept("joel2");

        // 3
        final Consumer<String> print3 = (s) -> System.out.println("hello : " + s);

        print3.accept("joel3");

        //// as function
        // final Function<String, Void> printAsFunction = s -> System.out.println(s);
        // 불가능하다! Void 가 허용되지 않음


    }
}
