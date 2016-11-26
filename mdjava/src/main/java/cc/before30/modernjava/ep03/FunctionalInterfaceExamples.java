package cc.before30.modernjava.ep03;

import java.util.function.Consumer;

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
    }
}
