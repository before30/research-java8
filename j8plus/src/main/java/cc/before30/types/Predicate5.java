package cc.before30.types;

import java.util.Objects;

/**
 * Created by before30 on 12/06/2017.
 */
@FunctionalInterface
public interface Predicate5 <T1, T2, T3, T4, T5> {
    boolean test(final T1 i1, final T2 i2, final T3 i3, final T4 i4, final T5 i5);

    default Predicate4<T2, T3, T4, T5> curried(final T1 t1) {
        return (t2, t3, t4, t5) -> test(t1, t2, t3, t4, t5);
    }

    default Predicate5<T1, T2, T3, T4, T5> and(final Predicate5<? super T1, ? super T2, ? super T3, ? super T4, ? super T5> other) {
        Objects.requireNonNull(other);

        return (final T1 i1, final T2 i2, final T3 i3, final T4 i4, final T5 i5) ->
                test(i1, i2, i3, i4, i5) &&
                        other.test(i1, i2, i3, i4, i5);
    }

    default Predicate5<T1, T2, T3, T4, T5> or(final Predicate5<? super T1, ? super T2, ? super T3, ? super T4, ? super T5> other) {
        Objects.requireNonNull(other);

        return (final T1 i1, final T2 i2, final T3 i3, final T4 i4, final T5 i5) ->
                test(i1, i2, i3, i4, i5) ||
                        other.test(i1, i2, i3, i4, i5);
    }
}
