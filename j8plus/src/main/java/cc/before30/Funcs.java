package cc.before30;

import cc.before30.types.Predicate3;

import java.util.Comparator;
import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created by before30 on 11/06/2017.
 */
public class Funcs {

    private Funcs() {

    }

    public static <T> Predicate<? super T> isNull() {
        return t -> Objects.isNull(t);
    }

    public static <T> Predicate<? super T> isNotNull() {
        return isNull().negate();
    }

    public static <T> Predicate<? super T> not(final Predicate<? super T> predicate) {
        Objects.requireNonNull(predicate, "The predicate: Predicate<T> cannot be null.");
        return predicate.negate();
    }

    public static <T> Comparator<T> reversed(final Comparator<T> comparator) {
        Objects.requireNonNull(comparator, "The comparator: Comparator<T> cannot be null.");
        return comparator.reversed();
    }

    public static <T, C extends Comparable<? super C>> Comparator<T> comparing(final Function<? super T, ? extends C> toComparable) {
        Objects.requireNonNull(toComparable, "The toComparable: Function<T, C> cannot be null.");
        return (t1, t2) -> toComparable.apply(t1)
                                        .compareTo(toComparable.apply(t2));
    }

    public static <T, R> Function<? super T, String> toStringOf(final Function<? super T, ? extends R> function) {
        Objects.requireNonNull(function, "function: Function<? super T, ? extends R> cannot be null.");
        return input -> function.andThen(str -> String.valueOf(str))
                                .apply(input);
    }

    public static <T> Function<T, Boolean> toTrue() {
        return t -> Boolean.TRUE;
    }

    public static <T> Function<T, Boolean> toFalse() {
        return t -> Boolean.FALSE;
    }

    public static <O, T> Predicate <O> satisfying(final BiPredicate<? super O, ? super T> predicate,
                                                 final T param1) {
        Objects.requireNonNull(predicate, "The function cannot be null.");
        return object -> predicate.test(object, param1);
    }

    public static <O, T, T2> Predicate<O> satisfying(final Predicate3<? super O, ? super T, ? super T2> predicate,
                                                     final T param1,
                                                     final T2 param2) {
        Objects.requireNonNull(predicate, "The function cannot be null.");
        return object -> predicate.test(object, param1, param2);
    }
}
