package cc.before30.example.tobytv004;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by before30 on 20/11/2016.
 */
public class WildCardCapture {
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 3, 2);
        reverse(list);
        System.out.println(list);
    }

    static <T> void reverse(List<T> list) {
        List<T> temp = new ArrayList<T>(list);
        for (int i=0; i<list.size(); i++) {
            list.set(i, temp.get(list.size() - i - 1));
        }
    }

    // type을 추론하는 순간을 capture라고 하는데
    // 아는 정보가 없으니 object를 return하려고하는데 set하는데 문제가 된다
    // 1. helper method를 만들어서 처리한다
    // 2. Raw type으로 변경해서 처리한다
//    static <T> void reverse2(List<?> list) {
//        List<?> temp = new ArrayList<>(list);
//        for (int i=0; i<list.size(); i++) {
//            list.set(i, temp.get(list.size() - i - 1));
//        }
//    }

    static void reverseWithRawType(List<?> list) {
        List temp = new ArrayList<>(list);
        List list2 = list;
        for (int i=0; i<list.size(); i++) {
            list2.set(i, list2.get(list2.size() - i - 1));
        }
    }

    static void reverseWithHelper(List<?> list) {
        reverseHelper(list);
    }

    private static <T> void reverseHelper(List<T> list) {
        List<T> temp = new ArrayList<>(list);
        for (int i=0; i<list.size(); i++) {
            list.set(i, temp.get(list.size() - i -1));
        }
    }

}
