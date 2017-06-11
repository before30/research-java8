package cc.before30.types;

import java.util.Objects;

/**
 * Created by before30 on 12/06/2017.
 */
@FunctionalInterface
public interface Consumer3<T1, T2, T3> {

    void accept(T1 input1, T2 input2, T3 input3);

    default Consumer2<T2, T3> curried(final T1 t1) {
        return (t2, t3) -> accept(t1, t2, t3);
    }

    default Consumer3<T1, T2, T3> andThen(Consumer3<? super T1, ? super T2, ? super T3> after) {
        Objects.requireNonNull(after);
        return (i1, i2, i3) -> {
            accept(i1, i2, i3);
            after.accept(i1, i2, i3);
        };
    }
}
