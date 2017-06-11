package cc.before30;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * Created by before30 on 11/06/2017.
 */
public class ArrayFunc {
    private ArrayFunc() {
    }

    public static <T> boolean isEmpty(final T[] array) {
        return Objects.isNull(array) || array.length == 0;
    }


    public static <T> boolean isEmpty(Stream<T> stream) {
        return Objects.isNull(stream) || stream.count() == 0;
    }

    public static <T> boolean isNotEmtpy(T[] array) {
        return !isEmpty(array);
    }

    public static <T> boolean isNotEmtpy(Stream<T> stream) {
        return !isEmpty(stream);
    }

    public static boolean isEmpty(int[] array) {
        return Objects.isNull(array) || array.length == 0;
    }

    public static boolean isNotEmpty(int[] array) {
        return !isEmpty(array);
    }

    public static boolean isEmpty(long[] array) {
        return Objects.isNull(array) || array.length == 0;
    }

    public static boolean isNotEmpty(long [] array) {
        return !isEmpty(array);
    }

    public static boolean isEmpty(double[] array) {
        return Objects.isNull(array) || array.length == 0;
    }

    public static boolean isNotEmpty(double[] array) {
        return !isEmpty(array);
    }
}
