package cc.before30.modernjava.ep11;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * Created by before30 on 19/12/2016.
 */
public class MethodReferenceEx3 {
    public static void main(String[] args) {
        methodReference03();

    }


    private static void methodReference03() {
        /* First Class Function */
        // Function can be passed as a parameter to another function.
        // Using Lambda Expression
        System.out.println(testFirstClassFunction1(3, i -> String.valueOf(i * 2)));

        // Using Method Reference
        System.out.println(testFirstClassFunction1(3, MethodReferenceEx3::doubleThenToString));

        /*
        Function can be returned as the result of another function
        Using Lambda Expression
         */
        final Function<Integer, String> f1 = getDoubleThenThenToStringUsingLambdaExpression();
        final String resultFromF1 = f1.apply(3);
        System.out.println(resultFromF1);

        /*
        Using Method Reference
         */
        final Function<Integer, String> fmr = getDoubleThenToStringUsingMethodReference();
        final String resultFromFMR = fmr.apply(3);
        System.out.println(resultFromFMR);

        /*
        Function can be stored in the data structure.
         */
        // Using Lambda Expression
        final List<Function<Integer, String>> fsL = Arrays.asList(i -> String.valueOf(i * 2));
        for (final Function<Integer, String> f : fsL) {
            System.out.println(f.apply(3));
        }

        // Using Method Reference
        final List<Function<Integer, String>> fmrL = Arrays.asList(MethodReferenceEx3::doubleThenToString);
        for (final Function<Integer, String> f : fmrL) {
            System.out.println(f.apply(3));
        }

        final Function<Integer, String> fl2 = i -> String.valueOf(i * 2);

        final Function<Integer, String> fmr2 = MethodReferenceEx3::doubleThenToString;


        final List<Function<Integer, String>> functions = Arrays.asList(
                i -> String.valueOf(i * 2),
                MethodReferenceEx3::doubleThenToString,
                i -> "#" + i,
                MethodReferenceEx3::withHash
        );

        for (final Function<Integer, String> f : functions) {
            System.out.println(f.apply(100));
        }
    }

    private static String withHash(int n) {
        return "#" + n;
    }

    private static Function<Integer, String> getDoubleThenToStringUsingMethodReference() {
        return MethodReferenceEx3::doubleThenToString;
    }

    private static String doubleThenToString(int i) {
        return String.valueOf(i * 2);
    }

    private static String testFirstClassFunction1(int n, Function<Integer, String> f) {
        return "The result is " + f.apply(n);
    }

    private static Function<Integer, String> getDoubleThenThenToStringUsingLambdaExpression() {
        return i -> String.valueOf(i * 2);
    }
}
