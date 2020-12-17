package com.link;

/** 876
 * @Description
 *
 * 找出链表的中间节点
 *
 * @author: yangyingyang
 * @date: 2020/10/21.
 */
public class LinkMiddleNode {

    /**
     * 我们可以继续优化方法二，用两个指针 slow 与 fast 一起遍历链表。slow 一次走一步，fast 一次走两步。
     * fast 的速度是 slow 的2倍
     * 那么当 fast 到达链表的末尾时，slow 必然位于中间

     时间复杂度：O(n)
     * @param head
     * @return
     */
    private static Node findMiddleNode(Node head){
        if (head == null || head.next == null){
            return null;
        }

        Node slow = head;
        Node fast = head;
        while (fast != null && fast.next != null){
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    private static class Node{
        private Integer data;
        private Node next;

        public Node(Integer data) {
            this.data = data;
        }
    }
}
