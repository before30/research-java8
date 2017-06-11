package cc.before30.types;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Created by before30 on 12/06/2017.
 */
@FunctionalInterface
public interface Consumer2<T1, T2> extends BiConsumer<T1, T2> {
    @Override
    void accept(T1 i1, T2 i2);

    default Consumer<T2> curried(final T1 t1) {
        return (t2) -> accept(t1, t2);
    }

    @Override
    default Consumer2<T1, T2> andThen(BiConsumer<? super T1, ? super T2> after) {
        Objects.requireNonNull(after);
        return (i1, i2) -> {
            accept(i1, i2);
            after.accept(i1, i2);
        };
    }
}
