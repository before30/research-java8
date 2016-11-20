package cc.before30.example.tobytv004;

import java.util.function.Consumer;

/**
 * Created by before30 on 20/11/2016.
 */
public class AnotherIntersectionType {
    interface DelegateTo<T> {
        T delegate();

    }

    interface Hello extends DelegateTo<String> {
        default void hello() {
            System.out.println("Hello " + delegate());
        }
    }

    interface UpperCase extends DelegateTo<String> {
        default void upperCase() {
            System.out.println(delegate().toUpperCase());
        }
    }

    public static void main(String[] args) {
        run((DelegateTo<String> & Hello)() -> "TEST TEST", o -> {
            o.hello();
        });

        run((DelegateTo<String> & Hello & UpperCase)() -> "test Test TEST", o -> {
            o.hello();
            o.upperCase();
        });
    }

    private static <T extends DelegateTo<S>, S> void run(T t, Consumer<T> consumer) {
        consumer.accept(t);

    }
}
