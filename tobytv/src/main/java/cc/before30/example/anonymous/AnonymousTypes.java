package cc.before30.example.anonymous;

import java.util.function.Consumer;

/**
 * Created by before30 on 21/11/2016.
 */
public class AnonymousTypes {
    public static <T extends Anon> void with(T t, Consumer<T> consumer) {
        consumer.accept(t);
    }

    public interface Anon {
        Object f(Object o);
    }

    public static <T, U extends DelegatesTo<T>> void with(U u, Consumer<U> consumer) {
        consumer.accept(u);
    }

}
