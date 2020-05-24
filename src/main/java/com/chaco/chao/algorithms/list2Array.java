package com.chaco.chao.algorithms;

import java.util.ArrayList;
import java.util.Stack;

/**
 * author:zhaopeiyan001
 * Date:2020-04-16 19:31
 */
public class list2Array {
    /**
     * 输入一个链表，按链表从尾到头的顺序返回一个ArrayList。
     */

    // 递归
    ArrayList<Integer> list = new ArrayList<>();

    public ArrayList<Integer> printListFromTailToHead(ListNode listNode) {
        if (null != listNode) {
            printListFromTailToHead(listNode.next);
            list.add(listNode.val);
        }
        return list;
    }

    // 使用栈结构
    public ArrayList<Integer> printListFromTailToHead1(ListNode listNode) {
        ArrayList integers = new ArrayList();
        Stack stack = new Stack();
        while (null != listNode) {
            stack.push(listNode.val);
            listNode = listNode.next;
        }
        while (!stack.empty()) {
            integers.add(stack.pop());
        }
        return integers;
    }

    class ListNode {
        int val;
        ListNode next = null;

        ListNode(int val) {
            this.val = val;
        }
    }

    public static void main(String[] args) {

    }
}