package cc.before30;

import cc.before30.types.*;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created by before30 on 12/06/2017.
 */
public class Curry {

    public static <T, U, R> Function<U, R> currying(final Function2<T, U, R> function, final T first) {
        Objects.requireNonNull(function);

        return second -> function.apply(first, second);
    }

    public static <T1, T2, T3, R> Function2<T2, T3, R> currying(final Function3<T1, T2, T3, R> function, final T1 t1) {
        Objects.requireNonNull(function);
        return function.curried(t1);
    }

    public static <T1, T2, T3, T4, R> Function3<T2, T3, T4, R> currying(final Function4<T1, T2, T3, T4, R> function, final T1 t1) {
        Objects.requireNonNull(function);
        return function.curried(t1);
    }

    public static <T1, T2, T3, T4, T5, R> Function4<T2, T3, T4, T5, R> currying(final Function5<T1, T2, T3, T4, T5, R> function, final T1 t1) {
        Objects.requireNonNull(function);
        return function.curried(t1);
    }

    public static <T1, T2> Predicate<T2> currying(final Predicate2<T1, T2> predicate, final T1 t1) {
        Objects.requireNonNull(predicate);
        return t2 -> predicate.test(t1, t2);
    }

    public static <T1, T2, T3> Predicate2<T2, T3> currying(final Predicate3<T1, T2, T3> predicate, final T1 t1) {
        Objects.requireNonNull(predicate);
        return predicate.curried(t1);
    }

    public static <T1, T2, T3, T4> Predicate3<T2, T3, T4> currying(final Predicate4<T1, T2, T3, T4> predicate, final T1 t1) {
        Objects.requireNonNull(predicate);
        return predicate.curried(t1);
    }

    public static <T1, T2, T3, T4, T5> Predicate4<T2, T3, T4, T5> currying(final Predicate5<T1, T2, T3, T4, T5> predicate, final T1 t1) {
        Objects.requireNonNull(predicate);
        return predicate.curried(t1);
    }

    public static <T1, T2> Consumer<T2> currying(final Consumer2<T1, T2> consumer, final T1 t1) {
        Objects.requireNonNull(consumer);
        return t2 -> consumer.accept(t1, t2);
    }

    public static <T1, T2, T3> Consumer2<T2, T3> currying(final Consumer3<T1, T2, T3> consumer, final T1 t1) {
        Objects.requireNonNull(consumer);
        return consumer.curried(t1);
    }

    public static <T1, T2, T3, T4> Consumer3<T2, T3, T4> currying(final Consumer4<T1, T2, T3, T4> consumer, final T1 t1) {
        Objects.requireNonNull(consumer);
        return consumer.curried(t1);
    }

    public static <T1, T2, T3, T4, T5> Consumer4<T2, T3, T4, T5> currying(final Consumer5<T1, T2, T3, T4, T5> consumer, final T1 t1) {
        Objects.requireNonNull(consumer);
        return consumer.curried(t1);
    }
}
