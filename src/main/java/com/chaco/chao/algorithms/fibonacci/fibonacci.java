package com.chaco.chao.algorithms.fibonacci;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

/**
 * author:zhaopeiyan001
 * Date:2020-04-21 17:42
 */
public class fibonacci {
    /**
     * 大家都知道斐波那契数列，现在要求输入一个整数n，
     * 请你输出斐波那契数列的第n项（从0开始，第0项为0）。
     * n<=39
     */
    public int Fibonacci(int n) {
        ArrayList<Integer> result = new ArrayList<>();
        result.add(0);
        result.add(1);
        if (n == 0) {
            return result.get(n);
        }
        if (n == 1) {
            return result.get(n);
        }
        int i = 2;
        while (i <= n) {
            result.add(result.get(i - 1) + result.get(i - 2));
            i++;
        }
        return result.get(n);
    }

    @Test
    public void test() {
        int fibonacci = Fibonacci(3);
        System.out.println(fibonacci);
    }

}
