package com.chaco.chao.algorithms;

import java.util.Stack;

/**
 * author:zhaopeiyan001
 * Date:2020-04-20 10:35
 */
public class makeQueueWithStack {
    /**
     * 用两个栈来实现一个队列，完成队列的Push和Pop操作。 队列中的元素为int类型。
     */
    Stack<Integer> stack1 = new Stack<Integer>();
    Stack<Integer> stack2 = new Stack<Integer>();

    public void push(int node) {
        synchronized (this) {
            while (!stack2.isEmpty()) {
                stack1.push(stack2.pop());
            }
            stack1.push(node);
            while (!stack1.isEmpty()) {
                stack2.push(stack1.pop());
            }
        }
    }

    public int pop() {
        return stack2.pop();
    }

    public static void main(String[] args) {

    }
}
