package cc.before30.modernjava.ep08;

import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

/**
 * Created by before30 on 08/12/2016.
 */
public class StreamExamples4 {

    public static void main(String[] args) {
        System.out.println(Stream.of(1, 2, 3, 4, 5)
                .collect(toList()));

        Stream.of(1, 2, 3, 4, 5)
                .collect(toSet());

        Stream.of(1, 2, 3, 4, 5)
                .map(String::valueOf)
                .collect(joining());
    }
}
