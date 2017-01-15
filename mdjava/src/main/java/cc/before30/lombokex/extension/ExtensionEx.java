package cc.before30.lombokex.extension;

import lombok.experimental.ExtensionMethod;

/**
 * Created by before30 on 16/01/2017.
 */

// https://projectlombok.org/features/experimental/ExtensionMethod.html

@ExtensionMethod({java.util.Arrays.class, Extensions.class})
public class ExtensionEx {
    public String test() {
        int[] intArray = {5, 3, 8, 2};
//        intArray.sort();

        String iAmNull = null;

//        return iAmNull.or("hEllo, World".toTitleCase());
        return "";
    }

    public static void main(String[] args) {
        ExtensionEx ex = new ExtensionEx();
        ex.test();
    }
}

class Extensions {
    public static <T> T or (T obj, T ifNull) {
        return obj != null ? obj : ifNull;
    }

    public static String toTitleCase(String in) {
        if (in.isEmpty()) return in;
        return "" + Character.toTitleCase(in.charAt(0)) + in.substring(1).toLowerCase();
    }
}
