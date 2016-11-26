package cc.before30.modernjava.ep04;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * Created by before30 on 26/11/2016.
 */
public class FunctionalInterfaceExamples {

    public static void main(String[] args) {
        Predicate<Integer> isPositive = i -> i > 0;
        System.out.println(isPositive.test(1));
        System.out.println(isPositive.test(0));
        System.out.println(isPositive.test(-1));

        List<Integer> numbers = Arrays.asList(-5, -4, -3, -2, -1, 0, 1, 2, 3, 4, 5);

        List<Integer> positiveNumbers = new ArrayList<>();
        for (Integer num : numbers) {
            if (isPositive.test(num)) {
                positiveNumbers.add(num);
            }
        }
        System.out.println("Positive integers: " + positiveNumbers);
        System.out.println("Positive integers: " + filter(numbers, isPositive));
        Predicate<Integer> lessThen3 = i -> i < 3;
        List<Integer> lessThen3Numbers = new ArrayList<>();
        for (Integer num : numbers) {
            if (lessThen3.test(num)) {
                lessThen3Numbers.add(num);
            }
        }
        System.out.println("lessthen3 integers: " + lessThen3Numbers);
        System.out.println("lessthen3 integers: " + filter(numbers, lessThen3));
        System.out.println("lessthen3 integers: " + filter(numbers, i -> i < 3));
    }

    private static <T> List<T> filter(List<T> list, Predicate<T> filter) {
        List<T> result = new ArrayList<T>();
        for (T input : list) {
            if (filter.test(input)) {
                result.add(input);
            }
        }
        return result;
    }
}
