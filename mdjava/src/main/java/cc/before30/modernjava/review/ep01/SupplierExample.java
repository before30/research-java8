package cc.before30.modernjava.review.ep01;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * Created by before30 on 07/12/2016.
 */
public class SupplierExample {
    public static void main(String[] args) {
        //Lazy Evaluation

        // 1
        final Supplier<String> helloSupplier1 = new Supplier<String>() {
            @Override
            public String get() {
                return "hello ";
            }
        };

        System.out.println(helloSupplier1.get() + "world");

        // 2
        final Supplier<String> helloSupplier2 = () -> "hello ";

        System.out.println(helloSupplier2.get() + "world");

        long start = System.currentTimeMillis();
        printValidString(-1, getString());
        printValidString(0, getString());
        printValidString(1, getString());
        System.out.println("It took " + (System.currentTimeMillis() - start)/1000 + " secs");

        start = System.currentTimeMillis();
        printValidString(-1, () -> getString());
        printValidString(0, () -> getString());
        printValidString(1, () -> getString());
        System.out.println("It took " + (System.currentTimeMillis() - start)/1000 + " secs");

    }

    private static void printValidString(int idx, Supplier<String> valueSupplier) {
        if (idx > 0) {
            System.out.println("Valid : " + valueSupplier.get());
        } else {
            System.out.println("Invalid");
        }
    }

    private static String getString() {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "takes too much time.";
    }

    private static void printValidString(int idx, String value) {
        if (idx > 0) {
            System.out.println("Valid : " + value);
        } else {
            System.out.println("Invalid");
        }
    }



}
