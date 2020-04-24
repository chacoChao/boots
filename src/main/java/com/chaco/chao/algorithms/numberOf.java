package com.chaco.chao.algorithms;

import org.junit.jupiter.api.Test;

/**
 * author:zhaopeiyan001
 * Date:2020-04-22 15:44
 */
public class numberOf {
    /**
     * 输入一个整数，输出该数二进制表示中1的个数。其中负数用补码表示。
     */
    /**
     * & 是所有的2进制位数“与”出的最终结果，“与”的规则是两者都为1时才得1，否则就得0
     * 举个例子
     * 7 & 6=？
     * 7的2进制是：1 1 1
     * 6的2进制是：1 1 0
     * 结果： 1 1 0
     * 得到结果为110，2进制转换为10进制110=6
     * 所以：7 & 6 = 6
     */
    /**
     * | 是所有的2进制位数“或”出的最终结果，“或”的规则是两者之一有一个1就得
     * 1，否则就得0
     * 举个例子
     * 7 | 6 =？
     * 7的2进制是：1 1 1
     * 6的2进制是：1 1 0
     * 结果： 1 1 1
     * 得到结果为111，2进制转换为10进制111=7
     * 所以：7 | 6 = 7
     */
    public int NumberOf1(int n) {
        int count = 0;
        while (n != 0) {
            ++count;
            n = n & (n - 1);
        }
        return count;
    }

    public int NumberOf2(int n) {
        String s = Integer.toBinaryString(n);
        int sum = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '1') {
                sum++;
            }
        }
        return sum;
    }

    @Test
    public void testNumberOf() {
        int i = this.NumberOf1(-9);
        int i2 = this.NumberOf2(-9);
        int i8 = this.NumberOf1(9);
        int i9 = this.NumberOf2(9);
        System.out.println(i);
    }
}
