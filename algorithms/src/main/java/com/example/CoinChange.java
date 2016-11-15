package com.example;

import java.util.List;

/**
 * Created by before30 on 2016. 11. 16..
 */
public class CoinChange {
    public static int total(int n, int[] v, int i) {
        if (n<0) {
            return 0;
        } else if (n ==0) {
            return 1;
        } else if (i== v.length && n > 0) {
            return 0;
        } else {
            return total(n - v[i], v, i) + total(n, v, i+1);
        }
    }
    public static void main(String[] args) {
//        System.out.println(solve(100));
        int[] coins = {1, 5, 10};
        System.out.println(total(100,coins, 0));

    }
}
