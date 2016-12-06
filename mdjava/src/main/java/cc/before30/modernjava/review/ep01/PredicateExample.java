package cc.before30.modernjava.review.ep01;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * Created by before30 on 07/12/2016.
 */
public class PredicateExample {
    public static void main(String[] args) {

        // 1
        Predicate<Integer> isPositive1 = new Predicate<Integer>() {
            @Override
            public boolean test(Integer integer) {
                return integer > 0;
            }
        };

        System.out.println(isPositive1.test(1000));
        System.out.println(isPositive1.test(0));
        System.out.println(isPositive1.test(-1000));

        // 2
        Predicate<Integer> isPositive2 = (Integer integer) -> {
                return integer > 0;
        };

        System.out.println(isPositive2.test(1000));
        System.out.println(isPositive2.test(0));
        System.out.println(isPositive2.test(-1000));

        // 3
        Predicate<Integer> isPositive3 = (integer) -> integer > 0;

        System.out.println(isPositive3.test(1000));
        System.out.println(isPositive3.test(0));
        System.out.println(isPositive3.test(-1000));


        //////

        List<Integer> numbers = Arrays.asList(-5, -4, -3, -2, -1, 0, 1, 2, 3, 4, 5);
        List<Integer> positiveNumbers = new ArrayList<>();
        for (Integer num : numbers) {
            if (isPositive1.test(num)) {
                positiveNumbers.add(num);
            }
        }

        System.out.println(positiveNumbers);

        /// 3보다 큰것을 추가로 하고 싶다면 Predicate를 copy paste 해야한다
        Predicate<Integer> lessthan3 = integer -> integer < 3;
        List<Integer> numbersLessThan3 = new ArrayList<>();
        for (Integer num : numbers) {
            if (lessthan3.test(num)) {
                numbersLessThan3.add(num);
            }
        }
        System.out.println(numbersLessThan3);


        System.out.println(filter(numbers, isPositive3));
        System.out.println(filter(numbers, lessthan3));
    }

    private static<T> List<T> filter(List<T> list, Predicate<T> filter) {
        List<T> result = new ArrayList<T>();
        for (T i : list) {
            if (filter.test(i)) {
                result.add(i);
            }
        }

        return result;
    }
}
