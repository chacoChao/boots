package com.chaco.chao.algorithms;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.Test;

/**
 * author:zhaopeiyan001
 * Date:2020-04-24 16:53
 */
public class findKthToTail {
    /**
     * 输入一个链表，输出该链表中倒数第k个结点。
     */
    public ListNode FindKthToTail(ListNode head, int k) {
        int total = 0;
        ListNode l = head;
        while (null != l) {
            total++;
            l = l.next;
        }
        int order = total - k + 1;
        int count = 0;
        while (null != head) {
            count++;
            if (order == count) {
                return head;
            }
            head = head.next;
        }
        return null;
    }

    @Test
    public void test() {
        ListNode listNode = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5, null)))));
        this.FindKthToTail(listNode, 1);
    }
}

@Data
@AllArgsConstructor
class ListNode {
    int val;
    ListNode next = null;

    ListNode(int val) {
        this.val = val;
    }
}
