package cc.before30.example.tobytv004;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by before30 on 19/11/2016.
 */
public class WildCardGenerics {

    static void method(List<? extends Comparable> t) {

    }

    static void printList(List<Object> list) {
        list.forEach(s -> System.out.println(s));
    }

    static void printList2(List<?> list) {
        list.forEach(s -> System.out.println(s));
    }

    static class A {}
    static class B extends A {}

    public static <T> void main(String[] args) {
        List<?> list; // ? : wildcards 아무거나 가능하다, 모른다, 알필요없다.
        List<T> list2; // 잘모르지만 나중에 알아서 사용하겠다.

        // List<?> == List<? extends Object> 이런 내용이다
        List list0 = Arrays.asList(1, 2, 3);
        printList(list0);
        printList2(list0);

        List<Integer> list1 = Arrays.asList(1, 2, 3);
//        printList(list1); // error 발생 상속관계에서 문제가 발생한다
        printList2(list1);

        List<B> listB = new ArrayList<B>();
//        List<A> la = listB; // error
        List<? extends A> la = listB;
        List<? super B> l2 = listB;
//        List>? super A> l3 = listB; // error
//        la.add(new A()); // error
//        la.add(new B()); // error
        listB.add(new B());
        // wildcard에서는 왜 A, B모두 안들어갈까? null만 들어갈 수 있다.
        // wildcard는 어디에 쓸수있는 것일까??
        la.add(null);
        // Collections method를 확인해보라
        // TO BE CONTINUED.


    }
}
