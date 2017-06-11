package cc.before30.types;

import java.util.Objects;

/**
 * Created by before30 on 12/06/2017.
 */
@FunctionalInterface
public interface Consumer5<T1, T2, T3, T4, T5> {

    void accept(T1 input1, T2 input2, T3 input3, T4 input4, T5 input5);

    default Consumer4<T2, T3, T4, T5> curried(final T1 t1) {
        return (t2, t3, t4, t5) -> accept(t1, t2, t3, t4, t5);
    }

    default Consumer5<T1, T2, T3, T4, T5> andThen(Consumer5<? super T1, ? super T2, ? super T3, ? super T4, ? super T5> after) {
        Objects.requireNonNull(after);
        return (i1, i2, i3, i4, i5) -> {
            accept(i1, i2, i3, i4, i5);
            after.accept(i1, i2, i3, i4, i5);
        };
    }
}
