package com.link;

/** 141
 * @Description
 *
 * 判断链表是否为环
 *
 * 我们可以根据上述思路来解决本题。具体地，我们定义两个指针，一快一满。慢指针每次只移动一步，
 * 而快指针每次移动两步。初始时，慢指针在位置 head，而快指针在位置 head.next。
 * 这样一来，如果在移动的过程中，快指针反过来追上慢指针，就说明该链表为环形链表。
 * 否则快指针将到达链表尾部，该链表不为环形链表

 *
 * @author: yangyingyang
 * @date: 2020/10/21.
 */
public class CircleLinkNode {

    public static void main(String[] args) {

    }

    /**
     * 时间复杂度：O(n)
     * @param head
     * @return
     */
    private static boolean isLinkCircle(Node head){
        if (head == null || head.next == null){
            return false;
        }
        Node slow = head;
        Node fast = head.next;
        while (slow != fast){
            //如果链表不是环，则尾节点的next肯定为空
            if (fast == null || fast.next == null){
                return false;
            }
            slow = slow.next;
            fast = fast.next.next;
        }
        //跳出循环说明 快慢2个指针遇上了
        return true;
    }

    private static class Node{
        private Integer data;
        private Node next;

        public Node(Integer data) {
            this.data = data;
        }
    }
}
