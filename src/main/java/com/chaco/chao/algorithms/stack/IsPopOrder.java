package com.chaco.chao.algorithms.stack;

import org.junit.jupiter.api.Test;

import java.util.Stack;

/**
 * author:zhaopeiyan001
 * Date:2020-05-20 14:50
 */
public class IsPopOrder {
    /**
     * 输入两个整数序列，
     * 第一个序列表示栈的压入顺序，
     * 请判断第二个序列是否可能为该栈的弹出顺序。
     * 假设压入栈的所有数字均不相等。
     * 例如序列1,2,3,4,5是某栈的压入顺序，
     * 序列4,5,3,2,1是该压栈序列对应的一个弹出序列，
     * 但4,3,5,1,2就不可能是该压栈序列的弹出序列。
     * （注意：这两个序列的长度是相等的）
     *
     * @param pushA
     * @param popA
     * @return
     */
    public boolean IsPopOrder(int[] pushA, int[] popA) {
        if (popA.length == 0) {
            return false;
        }
        Stack<Integer> stack = new Stack<>();
        for (int i = 0, j = 0; i < pushA.length; i++) {
            stack.push(pushA[i]);
            while (j < popA.length && stack.peek() == popA[j]) {
                stack.pop();
                j++;
            }
        }
        return stack.isEmpty();
    }

    @Test
    public void test() {
        int[] pushA = {1, 2, 3, 4, 5};
        int[] popB = {4, 5, 3, 2, 1};
        int[] popC = {4, 3, 5, 1, 2};
        boolean b = IsPopOrder(pushA, popB);
        boolean c = IsPopOrder(pushA, popC);
        System.out.println(b);
        System.out.println(c);
    }
}
