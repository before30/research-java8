package cc.before30.types;

import java.util.Objects;
import java.util.function.Function;

/**
 * Created by before30 on 12/06/2017.
 */
@FunctionalInterface
public interface Function3<T1, T2, T3, R> {

    R apply(T1 input1, T2 input2, T3 input3);

    default Function2<T2, T3, R> curried(final T1 t1) {
        return (t2, t3) -> apply(t1, t2, t3);
    }

    default <V> Function3<T1, T2, T3, V> andThen(final Function<? super R, ? extends V> after) {
        Objects.requireNonNull(after);
        return (i1, i2, i3) -> after.apply(apply(i1, i2, i3));
    }
}
