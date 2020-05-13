package com.chaco.chao.algorithms.array;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

/**
 * author:zhaopeiyan001
 * Date:2020-05-09 11:27
 */
public class PrintMatrix {
    /**
     * 输入一个矩阵，按照从外向里以顺时针的顺序依次打印出每一个数字，
     * 例如，如果输入如下4 X 4矩阵： 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16
     * 则依次打印出数字1,2,3,4,8,12,16,15,14,13,9,5,6,7,11,10
     *
     * @param matrix
     * @return
     */
    public ArrayList<Integer> printMatrix(int[][] matrix) {
        ArrayList<Integer> list = new ArrayList<>();
        while (0 != matrix.length) {
            for (int i = 0; i < matrix[0].length; i++) {
                System.out.println(matrix[0][i]);
                list.add(matrix[0][i]);
            }
            matrix = revert(matrix);
        }
        return list;
    }

    /**
     * 旋转二维矩阵
     *
     * @param matrix
     * @return
     */
    private int[][] revert(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;

        int[][] newMatrix = new int[cols][rows - 1];

        for (int j = cols - 1; j >= 0; j--) {
            for (int i = 1; i < rows; i++) {
                newMatrix[cols - j - 1][i - 1] = matrix[i][j];
            }
        }
        return newMatrix;
    }

    @Test
    public void test() {
        int[][] matrix = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 16}};
        ArrayList<Integer> integers = this.printMatrix(matrix);
        System.out.println(integers.toString());
    }
}
