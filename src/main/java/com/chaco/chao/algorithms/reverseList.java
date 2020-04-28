package com.chaco.chao.algorithms;

import org.junit.jupiter.api.Test;

/**
 * author:zhaopeiyan001
 * Date:2020-04-24 17:34
 */
public class reverseList {
    /**
     * 输入一个链表，反转链表后，输出新链表的表头。
     */
    public ListNode ReverseList(ListNode head) {
        if (null == head) {
            return null;
        }
        ListNode newHead = null;
        ListNode pNode = head;
        ListNode pPrev = null;
        while (null != pNode) {
            ListNode pNext = pNode.next;
            if (null == pNext) {
                newHead = pNode;
            }
            pNode.next = pPrev;
            pPrev = pNode;
            pNode = pNext;
        }
        return newHead;
    }

    /**
     * 三指针法
     */
    public ListNode ReverseList1(ListNode head) {
        ListNode p1 = new ListNode(-1);
        p1.next = head;
        if (head == null || head.next == null) {
            return head;
        }
        ListNode p2 = head.next;
        ListNode p3 = head.next.next;
        while (p3 != null) {
            p2.next = p3.next;
            p3.next = p1.next;
            p1.next = p3;
            p3 = p2.next;
        }
        return p1.next;
    }

    /**
     * 三节点法
     */
    public ListNode ReverseList2(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode prev, cur, end;
        prev = null;
        cur = head;
        end = head.next;

        while (cur != null) {
            cur.next = prev;
            prev = cur;
            cur = end;
            if (end != null) {
                end = end.next;
            }
        }
        return prev;
    }

    /**
     * 递归
     */
    public ListNode ReverseList3(ListNode head) {
        if (null == head || null == head.next) {
            return head;
        }
        ListNode newHead = ReverseList3(head.next);
        head.next.next = head;
        head.next = null;
        return newHead;
    }

    /**
     * 链表复制方式
     */
    public ListNode ReverseList4(ListNode head) {
        ListNode newHead = null;
        ListNode node;
        while (head != null) {
            //对原链表进行头删
            node = head;
            head = head.next;

            //新链表头插
            node.next = newHead;
            newHead = node;
        }
        return newHead;
    }

    @Test
    public void test() {
        ListNode listNode = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5, null)))));
        ListNode p1 = this.ReverseList2(listNode);
        System.out.println("===================");
    }
}


