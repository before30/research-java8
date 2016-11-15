package com.example;

import java.util.Scanner;
import java.util.Stack;

/**
 * Created by before30 on 2016. 11. 15..
 */
public class Main {
    public static Boolean isGoodString(String arg) {
        Stack<Character> stack = new Stack<>();

        for (int i=0; i<arg.length(); i++) {
            if ('a' == arg.charAt(i)) {
                stack.push('a');
            } else {
                if (stack.isEmpty()) {
                    return false;
                }
                stack.pop();
            }
        }

        return stack.isEmpty();
    }

    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);

        int count = scanner.nextInt();
        for (int i=0; i<count; i++) {
            char[] arg1 = scanner.next().toCharArray();
            char[] arg2 = scanner.next().toCharArray();

            if (arg1.length != arg2.length) {
                System.out.println("-1");
                continue;
            }

            if (!isGoodString(new String(arg1)) || !isGoodString(new String(arg2))) {
                System.out.println("-1");
                continue;
            }

            int moveCount = 0;
            for (int j=0; j<arg1.length; j++) {
                if (arg1[j] == arg2[j]) {
                    continue;
                } else {
                    for (int k=j+1; k<arg1.length; k++) {
                        moveCount++;
                        if (arg1[j] == arg2[k]) {
                            char tmp = arg2[j];
                            arg2[j] = arg2[k];
                            arg2[k] = tmp;
                            break;
                        }

                    }
                }

            }

            System.out.println(moveCount);

        }
    }
}
