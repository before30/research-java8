package cc.before30.types;

import java.util.Objects;
import java.util.function.Function;

/**
 * Created by before30 on 12/06/2017.
 */
@FunctionalInterface
public interface Function4<T1, T2, T3, T4, R> {

    R apply(T1 input1, T2 input2, T3 input3, T4 input4);

    default Function3<T2, T3, T4, R> curried(final T1 t1) {
        return (t2, t3, t4) -> apply(t1, t2, t3, t4);
    }

    default <V> Function4<T1, T2, T3, T4, V> andThen(final Function<? super R, ? extends V> after) {
        Objects.requireNonNull(after);
        return (i1, i2, i3, i4) -> after.apply(apply(i1, i2, i3, i4));
    }
}
