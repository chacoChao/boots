package com.chaco.chao.algorithms;

/**
 * author:zhaopeiyan001
 * Date:2020-04-15 11:15
 */
public class arrayTargetIndex {

    /**
     * 给定一个按照升序排列的整数数组 nums，和一个目标值 target。找出给定目标值在数组中的开始位置和结束位置。
     * <p>
     * 你的算法时间复杂度必须是 O(log n) 级别。
     * <p>
     * 如果数组中不存在目标值，返回 [-1, -1]。
     * <p>
     * 示例 1:
     * <p>
     * 输入: nums = [5,7,7,8,8,10], target = 8
     * 输出: [3,4]
     * 示例 2:
     * <p>
     * 输入: nums = [5,7,7,8,8,10], target = 6
     * 输出: [-1,-1]
     */

    public static int[] findTargetIndex(int[] inputArr, Integer target) {
        int first = -1;
        int end = -1;
        for (int i = 0; i < inputArr.length; i++) {
            if (inputArr[i] == target) {
                first = i;
                end = i;
                int mid = first + 1;
                if (mid >= inputArr.length) {
                    end = first;
                    return new int[]{first, end};
                }
                while (inputArr[mid] == target) {
                    end = mid;
                    mid += 1;
                    if (mid >= inputArr.length) {
                        return new int[]{first, end};
                    }
                }
                return new int[]{first, end};
            }
        }
        return new int[]{first, end};
    }

    public static void main(String[] args) {
        int[] input = {2,2};
        int[] targetIndex = findTargetIndex(input, 2);
        for(int i = 0 ; i < targetIndex.length; i++){
            System.out.println("======" + targetIndex[i] + '\n');
        }
    }
}
