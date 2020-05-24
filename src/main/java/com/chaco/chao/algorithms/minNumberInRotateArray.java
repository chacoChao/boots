package com.chaco.chao.algorithms;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

/**
 * author:zhaopeiyan001
 * Date:2020-04-21 10:23
 */
public class minNumberInRotateArray {

    /**
     * 把一个数组最开始的若干个元素搬到数组的末尾，我们称之为数组的旋转。
     * 输入一个非递减排序的数组的一个旋转，输出旋转数组的最小元素。
     * 例如数组{3,4,5,1,2}为{1,2,3,4,5}的一个旋转，该数组的最小值为1。
     * NOTE：给出的所有元素都大于0，若数组大小为0，请返回0。
     */
    public int minNumberInRotateArray(int[] array) {
        if (array.length == 0) {
            return 0;
        }
        int end = array.length - 1;
        int result = array[0];
        while (result >= array[end]) {
            result = array[end];
            end -= 1;
        }
        return result;
    }

    @Test
    public void test() {
        int[] array = {3, 4, 5, 1, 2};
        int i = this.minNumberInRotateArray(array);
        System.out.println("===================" + i);
    }

}
