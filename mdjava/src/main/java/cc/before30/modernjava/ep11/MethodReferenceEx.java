package cc.before30.modernjava.ep11;

import java.util.Arrays;

/**
 * Created by before30 on 16/12/2016.
 */
public class MethodReferenceEx {

    public static void main(String[] args) {

        Arrays.asList(1, 2, 3, 4, 5, 6)
                .forEach(System.out::println);

        // Method Reference

    }
}
