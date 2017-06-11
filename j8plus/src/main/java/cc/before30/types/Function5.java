package cc.before30.types;

import java.util.Objects;
import java.util.function.Function;

/**
 * Created by before30 on 12/06/2017.
 */
@FunctionalInterface
public interface Function5<T1, T2, T3, T4, T5, R> {

    R apply(T1 input1, T2 input2, T3 input3, T4 input4, T5 input5);

    default Function4<T2, T3, T4, T5, R> curried(final T1 t1) {
        return (t2, t3, t4, t5) -> apply(t1, t2, t3, t4, t5);
    }

    default <V> Function5<T1, T2, T3, T4, T5, V> andThen(final Function<? super R, ? extends V> after) {
        Objects.requireNonNull(after);
        return (i1, i2, i3, i4, i5) -> after.apply(apply(i1, i2, i3, i4, i5));
    }
}
