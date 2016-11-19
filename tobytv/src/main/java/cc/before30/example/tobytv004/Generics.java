package cc.before30.example.tobytv004;

import com.sun.tools.javac.jvm.Gen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by before30 on 19/11/2016.
 */
public class Generics {
    static class Hello<T> { // T -> type parameter

    }

    <T> void print(T t) {
        System.out.println(t.toString());
    }

    <S, T> T print(T t, S s) {
        System.out.println(t.toString() + " " + s.toString());
        return t;
    }

    public static void main(String[] args) {
//        new Hello<String>(); // type argument
//        print("test");
//
//
//        List list = new ArrayList();
//        list.add(1);
//        list.add(2);
//        list.add("asdf"); // get할때 Casting 이슈가 있겠지 기본이 Object니까

        List list = new ArrayList<Integer>(); // raw type
        List<Integer> ints = Arrays.asList(1, 2, 3);
        List rawTypeInts = ints; // 경고 없음

        /*
         uses unchecked or unsafe operations.
         */
//        @SuppressWarnings("unchecked")
        List<Integer> ints2 = rawTypeInts; // 컴파일 에러는 없지만 경고가 나옴
        // 예를 들어서 이렇게 잘못 사용할 수 있다
//        @SuppressWarnings("unchecked")
//        List<String> strs = rawTypeInts;
//        String str = strs.get(0); // casting error
//        // Information:java: Recompile with -Xlint:unchecked for details.

        new Generics().print("hello");
        new Generics().print(1);
    }
}
