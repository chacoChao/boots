package com.chaco.chao.algorithms;

/**
 * author:zhaopeiyan001
 * Date:2020-04-16 15:57
 */
public class twoDimensionArray {
    /**
     * 在一个二维数组中（每个一维数组的长度相同），
     * 每一行都按照从左到右递增的顺序排序，
     * 每一列都按照从上到下递增的顺序排序。
     * 请完成一个函数，输入这样的一个二维数组和一个整数，判断数组中是否含有该整数。
     */
    public static boolean isExist(int[][] array, int target) {
        boolean isexist = false;
        if (array.length == 0 || array[0].length == 0) {
            return isexist;
        }
        if (array[0][0] > target && array[array.length - 1][array[0].length - 1] < target) {
            return isexist;
        }
        for (int i = 0; i < array.length; i++) {
            if (target > array[i][array[i].length - 1]) {
                continue;
            }
            for (int j = 0; j < array[i].length; j++) {
                if (array[i][j] == target) {
                    isexist = true;
                    return isexist;
                }
            }
        }
        return isexist;
    }

    public static void main(String[] args) {
        int[][] inputArrs = {{1, 5, 9, 13}, {2, 6, 10, 14}, {3, 7, 11, 15}, {4, 8, 12, 16}};
        int[][] inputArrs1 = {{}};
        boolean exist = isExist(inputArrs1, 9);
        System.out.println(exist);
    }
}
