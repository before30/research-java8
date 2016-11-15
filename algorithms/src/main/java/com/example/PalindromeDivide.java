package com.example;

import org.springframework.util.StringUtils;

/**
 * Created by before30 on 2016. 11. 16..
 */
public class PalindromeDivide {
    public static Boolean isPalindrome(String arg) {
        if (StringUtils.isEmpty(arg) || arg.length() == 1) {
            return true;
        }
        String reversed = new StringBuilder(arg).reverse().toString();
        return arg.equals(reversed);
    }



    public static int minSplitCount(String arg) {
        if (isPalindrome(arg)) {
            return 1;
        }

        int count = arg.length();
        for (int i=1; i<arg.length(); i++) {
            count = Math.min(count,
                    minSplitCount(arg.substring(0, i))
                            + minSplitCount(arg.substring(i, arg.length())));
        }

        return count;
    }

    public static void main(String[] args) {
        System.out.println(minSplitCount("oncoder"));
        System.out.println(minSplitCount("bceffecbzzz"));
        System.out.println(minSplitCount("cocaagng"));
    }
}
