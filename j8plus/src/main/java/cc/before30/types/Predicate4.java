package cc.before30.types;

import java.util.Objects;

/**
 * Created by before30 on 12/06/2017.
 */
@FunctionalInterface
public interface Predicate4<T1, T2, T3, T4> {
    boolean test(final T1 input1, final T2 input2, final T3 input3, final T4 input4);

    default Predicate3<T2, T3, T4> curried(final T1 t1) {
        return (t2, t3, t4) -> test(t1, t2, t3, t4);
    }

    default Predicate4<T1, T2, T3, T4> and(final Predicate4<? super T1, ? super T2, ? super T3, ? super T4> other) {
        Objects.requireNonNull(other);

        return (final T1 i1, final T2 i2, final T3 i3, final T4 i4) ->
                test(i1, i2, i3, i4) &&
                        other.test(i1, i2, i3, i4);
    }

    default Predicate4<T1, T2, T3, T4> or(final Predicate4<? super T1, ? super T2, ?super T3, ? super T4> other) {
        Objects.requireNonNull(other);

        return (final T1 i1, final T2 i2, final T3 i3, final T4 i4) ->
                test(i1, i2, i3, i4) ||
                        other.test(i1, i2, i3, i4);
    }
}
