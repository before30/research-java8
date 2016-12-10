package cc.before30.modernjava.ep08;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Created by before30 on 10/12/2016.
 */
public class StreamExamples5ParallelPerformancePractical {

    private static final String[] priceStrings = {"1.0", "100.99", "35.75", "21.30", "88.00"};
    private static final BigDecimal[] targetPrices = {new BigDecimal("30"), new BigDecimal("20"), new BigDecimal("31")};
    private static final Random random = new Random(123);
    private static final Random targetPriceRandom = new Random(111);

    private static final List<MyProduct> products;

    static {
        final int length = 8_000_000;
        final MyProduct[] list = new MyProduct[length];


        for (int i = 1; i <= length; i++) {
            list[i - 1] = new MyProduct((long) i, "Product" + i, new BigDecimal(priceStrings[random.nextInt(5)]));
        }
        products = Collections.unmodifiableList(Arrays.asList(list));
    }

    private static BigDecimal imperativeSum(final List<MyProduct> products, final Predicate<MyProduct> predicate) {
        BigDecimal sum = BigDecimal.ZERO;
        for (final MyProduct product: products) {
            sum = sum.add(product.getPrice());
        }

        return sum;
    }

    private static BigDecimal streamSum(final Stream<MyProduct> stream, final Predicate<MyProduct> predicate) {
        return stream.filter(predicate).map(MyProduct::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private static void imperativeTest(final BigDecimal targetPrice, final boolean printResult) {

        if (printResult) {
            System.out.println("============================================");
            System.out.println("\nImperative Sum\n--------------------------------------------");
        }
        final long start = System.currentTimeMillis();
        final BigDecimal result = imperativeSum(products, product -> product.getPrice().compareTo(targetPrice) >= 0);
        final long howLong = System.currentTimeMillis() - start;
        if (printResult) {
            System.out.println("Sum: " + result);
            System.out.println("It took " + howLong + " ms.");
            System.out.println("============================================");
        }
    }

    private static void streamTest(final BigDecimal targetPrice, final boolean printResult) {
        if (printResult) {
            System.out.println("============================================");
            System.out.println("\nStream Sum\n--------------------------------------------");
        }
        final long start = System.currentTimeMillis();
        final BigDecimal result = streamSum(products.stream(), product -> product.getPrice().compareTo(targetPrice) >= 0);
        final long howLong = System.currentTimeMillis() - start;
        if (printResult) {
            System.out.println("Sum: " + result);
            System.out.println("It took " + howLong + " ms.");
            System.out.println("============================================");
        }
    }

    private static void parallelStreamTest(final BigDecimal targetPrice, final boolean printResult) {
        if (printResult) {
            System.out.println("============================================");
            System.out.println("\nParallel Stream Sum\n--------------------------------------------");
        }
        final long start = System.currentTimeMillis();
        final BigDecimal result = streamSum(products.parallelStream(), product -> product.getPrice().compareTo(targetPrice) >= 0);
        final long howLong = System.currentTimeMillis() - start;
        if (printResult) {
            System.out.println("Sum: " + result);
            System.out.println("It took " + howLong + " ms.");
            System.out.println("============================================");
        }
    }

    private static void test1() {

        final BigDecimal targetPrice = new BigDecimal("40");

        imperativeTest(targetPrice, false);
        streamTest(targetPrice, false);
        parallelStreamTest(targetPrice, false);

        System.out.println("\n\n================================================================\nTest1 Starts!");
        for (int i = 0; i < 5; i++) {
            BigDecimal price = targetPrices[targetPriceRandom.nextInt(3)];

            imperativeTest(price, true);
            streamTest(price, true);
            parallelStreamTest(price, true);
        }
    }

    private static void test2() {

        final BigDecimal targetPrice = new BigDecimal("40");

        parallelStreamTest(targetPrice, false);
        imperativeTest(targetPrice, false);
        streamTest(targetPrice, false);

        System.out.println("\n\n================================================================\nTest2 Starts!");
        for (int i = 0; i < 5; i++) {
            BigDecimal price = targetPrices[targetPriceRandom.nextInt(3)];

            parallelStreamTest(price, true);
            imperativeTest(price, true);
            streamTest(price, true);
        }
    }

    private static void test3() {

        final BigDecimal targetPrice = new BigDecimal("40");

        streamTest(targetPrice, false);
        parallelStreamTest(targetPrice, false);
        imperativeTest(targetPrice, false);

        System.out.println("\n\n================================================================\nTest3 Starts!");
        for (int i = 0; i < 5; i++) {
            BigDecimal price = targetPrices[targetPriceRandom.nextInt(3)];

            streamTest(price, true);
            parallelStreamTest(price, true);
            imperativeTest(price, true);
        }
    }

    public static void main(String[] args) {

        test1();
        test2();
        test3();
    }
}

@AllArgsConstructor
@Data
class MyProduct {
    private Long id;
    private String name;
    private BigDecimal price;

}
