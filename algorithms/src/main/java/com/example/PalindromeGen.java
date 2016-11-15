package com.example;

import java.util.Scanner;

/**
 * Created by before30 on 2016. 11. 16..
 */
public class PalindromeGen {
    public static Boolean isPalindrome(String arg) {
        for (int i=0, j=arg.length()-1; i<arg.length()/2; i++,j--) {
            if (i == j)
                return true;
            else if (arg.charAt(i) == arg.charAt(j)) {
                continue;
            } else {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);

//        System.out.println(isPalindrome(scanner.next()));
        String input = scanner.next();
        String temp = "";
        int count = 0;

        if (!isPalindrome(input)) {
            for (int i=0; i<input.length(); i++) {
                count++;

                temp = input.charAt(i) + temp;
                if (isPalindrome(input + temp)) {
                    break;
                }

            }
        }

        System.out.println(count);


    }
}
