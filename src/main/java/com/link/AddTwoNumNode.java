package com.link;

/** 2
 * @Description
 *
给出两个 非空 的链表用来表示两个非负的整数。其中，它们各自的位数是按照 逆序 的方式存储的，并且它们的每个节点只能存储 一位 数字。
如果，我们将这两个数相加起来，则会返回一个新的链表来表示它们的和。
您可以假设除了数字 0 之外，这两个数都不会以 0 开头。

示例：

输入：(2 -> 4 -> 3) + (5 -> 6 -> 4)
输出：7 -> 0 -> 8
原因：342 + 465 = 807

 * @author: yangyingyang
 * @date: 2020/10/20.
 */
public class AddTwoNumNode {

    /**
     * 两次遍历
     * 第一次遍历：两个链表对应每个节点分别取和，若含有空节点则空节点取0，产生一个新链表。
     * 第二次遍历：对取完和的新链表遍历，判断当前的val是否大于等于10，大于或等于则其自身-10其next加1，若next为空则新建0节点
     * @param node1
     * @param node2
     * @return
     */
    private static Node addTwoNumbers(Node node1, Node node2){
        Node rs = new Node(node1.data+node2.data);
        node1 = node1.next;
        node2 = node2.next;
        Node temp = rs;
        while (node1 != null || node2 != null){
            int a = 0,b =0;
            if (node1 != null){
                a = node1.data;
            }
            if (node2 != null){
                b = node2.data;
            }
            int sum = a + b;
            temp.next = new Node(sum);
            temp = temp.next;
            if (node1 != null){
                node1 = node1.next;
            }
            if (node2 != null){
                node2 = node2.next;
            }
        }
        temp = rs;
        while (temp != null){
            if (temp.data >= 10){
                temp.data = temp.data - 10;
                if (temp.next == null){
                    temp.next = new Node(0);
                }
                temp.next.data = temp.next.data + 1;
            }
            temp = temp.next;
        }
        return rs;
    }

    private static class Node{
        private Integer data;
        private Node next;

        public Node(Integer data) {
            this.data = data;
        }
    }
}
