package com.chaco.chao.algorithms;

/**
 * author:zhaopeiyan001
 * Date:2020-04-24 16:09
 */
public class reOrderArray {
    /**
     * 输入一个整数数组，实现一个函数来调整该数组中数字的顺序，
     * 使得所有的奇数位于数组的前半部分，
     * 所有的偶数位于数组的后半部分，
     * 并保证奇数和奇数，偶数和偶数之间的相对位置不变。
     */
    public void reOrderArray(int[] array) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] % 2 == 0) {
                for (int j = i + 1; j < array.length; j++) {
                    int tp = j;
                    if (array[j] % 2 == 1) {
                        int step = j - i;
                        while (step != 0) {
                            array[tp] = array[tp] + array[tp -1];
                            array[tp - 1] = array[tp] - array[tp - 1];
                            array[tp] = array[tp] - array[tp - 1];
                            --step;
                            --tp;
                        }
                        break;
                    }
                }
            }
        }
    }
}
