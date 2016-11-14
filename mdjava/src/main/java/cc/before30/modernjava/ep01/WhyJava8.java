package cc.before30.modernjava.ep01;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.joining;

/**
 * Created by before30 on 2016. 11. 15..
 */
public class WhyJava8 {
    private static final List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

    public static void main(String[] args) {
        simpleTask();

//        readFile();
    }

    private static void simpleTask() {
        final StringBuilder stringBuilder1 = new StringBuilder();
        final int size = numbers.size();
        for (int i=0; i<size; i++) {
            stringBuilder1.append(numbers.get(i));
            if (i != size - 1) {
                stringBuilder1.append(" : ");
            }
        }
        System.out.println(stringBuilder1.toString());

        final StringBuilder stringBuilder = new StringBuilder();
        final String seperator =  " : ";
        for (final Integer number : numbers) {
            stringBuilder.append(number).append(seperator);
        }

        final int stringLength = stringBuilder.length();
        if (stringLength > 0) {
            stringBuilder.delete(stringLength - seperator.length(), stringLength);
        }
        System.out.println(stringBuilder.toString());

        final String result = numbers.stream()
                .map(String::valueOf)
                .collect(joining(" : "));
        System.out.println(result);
    }



}
