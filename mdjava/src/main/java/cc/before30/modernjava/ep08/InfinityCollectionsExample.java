package cc.before30.modernjava.ep08;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by before30 on 01/12/2016.
 */
public class InfinityCollectionsExample {
    public static void main(String[] args) {
        IntStream.range(1, 10).forEach(i -> System.out.println(i));
        IntStream.rangeClosed(1, 10).mapToObj(i -> i).collect(Collectors.toList());
        IntStream.rangeClosed(1, 10).boxed().collect(Collectors.toList());

//        Stream.iterate(BigInteger.ONE, i -> i.add(BigInteger.ONE)).forEach(i -> System.out.print(i + " "));


    }
}
