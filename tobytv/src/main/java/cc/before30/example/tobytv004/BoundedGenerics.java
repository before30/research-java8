package cc.before30.example.tobytv004;

import java.io.Closeable;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * Created by before30 on 19/11/2016.
 */
public class BoundedGenerics {
    // Bounded Type Parameter
    static <S extends List & Serializable & Comparable & Closeable> void print(S s) {
        // multiple bound가 가능하다 extends List & B & C

    }

    static long countGreaterThan(Integer[] arr, Integer elem) {
        return Arrays.stream(arr).filter(s -> s > elem).count();
    }

    static <T extends Comparable> long countGreaterThanByG(T[] arr, T elem) {
        // return Arrays.stream(arr).filter(s -> s > elem).count();
        // > 가 문제가 된다
        // CompareTo를 사용해보자
        // T type의 Compare가 있는지 알수없다
        // 그래서 Comaprable 을 bounded Type으로 사용한다
        return Arrays.stream(arr).filter(s -> s.compareTo(elem) > 0).count();
    }

    public static void main(String[] args) {
        Integer[] arr = new Integer[] {1, 2, 3, 4, 5, 6, 7};
        System.out.println(countGreaterThan(arr, 4));

        String[] strArr = new String[] {"a", "b", "c", "d", "e", "f"};
        System.out.println(countGreaterThanByG(strArr, "a"));
        System.out.println(countGreaterThanByG(arr, 4));
    }
}
