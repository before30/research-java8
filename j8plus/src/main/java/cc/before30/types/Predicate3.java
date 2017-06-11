package cc.before30.types;

import java.util.Objects;

/**
 * Created by before30 on 12/06/2017.
 */
@FunctionalInterface
public interface Predicate3<T1, T2, T3> {
    boolean test(final T1 input1, final T2 input2, final T3 input3);

    default Predicate2<T2, T3> curried(final T1 t1) {
        return (t2, t3) -> test(t1, t2, t3);
    }

    default Predicate3<T1, T2, T3> and(final Predicate3<? super T1, ? super T2, ? super T3> other) {
        Objects.requireNonNull(other);
        return (final T1 input1, final T2 input2, final T3 input3) ->
                test(input1, input2, input3) &&
                        other.test(input1, input2, input3);
    }

    default Predicate3<T1, T2, T3> or(final Predicate3<? super T1, ? super T2, ? super T3> other) {
        Objects.requireNonNull(other);
        return (final T1 input1, final T2 input2, final T3 input3) ->
                test(input1, input2, input3) ||
                        other.test(input1, input2, input3);
    }
}
