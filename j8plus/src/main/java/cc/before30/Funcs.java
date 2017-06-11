package cc.before30;

import cc.before30.types.*;

import java.util.Comparator;
import java.util.Objects;
import java.util.function.Consumer;
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

    public static <O, T> Predicate <O> satisfying(final Predicate2<? super O, ? super T> predicate,
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

    public static <O, T, T2, T3> Predicate<O> satisfying(final Predicate4<? super O, ? super T, ? super T2, ? super T3> predicate,
                                                         final T param1,
                                                         final T2 param2,
                                                         final T3 param3) {
        Objects.requireNonNull(predicate, "The function cannot be null.");
        return object -> predicate.test(object, param1, param2, param3);
    }

    public static <O, T, T2, T3, T4> Predicate<O> satisfying(final Predicate5<? super O, ? super T, ? super T2, ? super T3, ? super T4> predicate,
                                                             final T param1,
                                                             final T2 param2,
                                                             final T3 param3,
                                                             final T4 param4) {
        Objects.requireNonNull(predicate, "The function cannot be null.");
        return object -> predicate.test(object, param1, param2, param3, param4);
    }

    public static <O, T, R> Function<O, R> applying(final Function2<? super O, ? super T, ? extends R> function, final T param) {
        Objects.requireNonNull(function, "The function cannot be null.");
        return object -> function.apply(object, param);
    }

    public static <O, T, T2, R> Function<O, R> applying(final Function3<? super O, ? super T, ? super T2, ? extends R> function, final T param1, final T2 param2) {
        Objects.requireNonNull(function, "The function cannot be null.");
        return object -> function.apply(object, param1, param2);
    }

    public static <O, T, T2, T3, R> Function<O, R> applying(final Function4<? super O, ? super T, ? super T2, ? super T3, ? extends R> function,
                                                            final T param1,
                                                            final T2 param2,
                                                            final T3 param3) {
        Objects.requireNonNull(function, "The function cannot be null.");
        return object -> function.apply(object, param1, param2, param3);
    }

    public static <O, T, T2, T3, T4, R> Function<O, R> applying(final Function5<? super O, ? super T, ? super T2, ? super T3, ? super T4, ? extends R> function,
                                                                final T param1,
                                                                final T2 param2,
                                                                final T3 param3,
                                                                final T4 param4) {
        Objects.requireNonNull(function, "The function cannot be null.");
        return object -> function.apply(object, param1, param2, param3, param4);
    }

    public static <O, T> Consumer<O> accepting(final Consumer2<? super O, ? super T> function, final T param) {
        Objects.requireNonNull(function, "The function cannot be null.");
        return object -> function.accept(object, param);
    }

    public static <O, T, T2> Consumer<O> accepting(final Consumer3<? super O, ? super T, ? super T2> function, final T param1, final T2 param2) {
        Objects.requireNonNull(function, "The function cannot be null.");
        return object -> function.accept(object, param1, param2);
    }

    public static <O, T, T2, T3> Consumer<O> accepting(final Consumer4<? super O, ? super T, ? super T2, ? super T3> function,
                                                       final T param1,
                                                       final T2 param2,
                                                       final T3 param3) {
        Objects.requireNonNull(function, "The function cannot be null.");
        return object -> function.accept(object, param1, param2, param3);
    }

    public static <O, T, T2, T3, T4> Consumer<O> accepting(final Consumer5<? super O, ? super T, ? super T2, ? super T3, ? super T4> function,
                                                           final T param1,
                                                           final T2 param2,
                                                           final T3 param3,
                                                           final T4 param4) {
        Objects.requireNonNull(function, "The function cannot be null.");
        return object -> function.accept(object, param1, param2, param3, param4);
    }



}
