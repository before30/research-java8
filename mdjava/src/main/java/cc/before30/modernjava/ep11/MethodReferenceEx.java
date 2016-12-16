package cc.before30.modernjava.ep11;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;

/**
 * Created by before30 on 16/12/2016.
 */
public class MethodReferenceEx {

    public static void main(String[] args) {

        Arrays.asList(1, 2, 3, 4, 5, 6)
                .forEach(System.out::println);

        // Method Reference

        System.out.println(
        Arrays.asList(new BigDecimal("10.0"), new BigDecimal("23"), new BigDecimal("5"))
                .stream()
//                .sorted(Comparator.naturalOrder())
//                .sorted((bd1, bd2) -> bd2.compareTo(bd2))
                .sorted(BigDecimal::compareTo)
                .collect(Collectors.toList()));



        final String targetString = "c";
        System.out.println(
        Arrays.asList("a", "b", "c", "d")
                .stream()
//                .anyMatch(x -> x.equals("c")));
//                .anyMatch(targetString::equals)
                .anyMatch("c"::equals)
        );
    }
}

class BigDecimalUtil {
    public static int compare(BigDecimal bd1, BigDecimal bd2) {
        return bd1.compareTo(bd2);
    }
}
