package com.link;

import java.util.Stack;

/**206
 * @Description 链表反转，从尾到头打印链表
 * @author: yangyingyang
 * @date: 2020/10/12.
 */
public class LinkReverse {

    public static void main(String[] args) {
        //构造一个链表
        Node head = new Node();
        head.data = 1;
        Node curr = head;
        for (int i=2; i<10; i++){
            Node next = new Node();
            next.data = i;
            curr.next = next;
            curr = next;
        }
        Node toHead = head;
        while (head.next != null){
            System.out.println("反转前的数据" + head.data);
            head = head.next;
        }
        if (head.data != null){
            System.out.println("反转前的数据" + head.data);
        }

//        reverseNodePrint(head);
        Node newHead = reverseNodeStack(toHead);
        while (newHead.next != null){
            System.out.println("反转后的数据" + newHead.data);
            newHead = newHead.next;
        }
        if (newHead.data != null){
            System.out.println("反转后的数据" + newHead.data);
        }
    }

    /**
     * 反向打印链表
     * 利用栈的先进后厨的特性，把链表的数据依次压入到栈里面，再从栈弹出
     *
     * @param head
     */
    private static void reverseNodePrint(Node head){
        Stack<Integer> stack = new Stack<>();
        while (head.next != null){
            stack.push(head.data);
            head = head.next;
        }

        while (!stack.isEmpty()){
            System.out.println("反转数据 " + stack.pop());
        }
    }

    /**
     * 反转链表--栈方式实现
     * @param head
     * @return
     */
    private static Node reverseNodeStack(Node head){
        Stack<Node> stack = new Stack<>();
        /*
        依次摘除链表的引用关系，把节点放入栈里面
         */
        while (head != null){
            stack.push(head);
            head = head.next;
        }
        if (stack.isEmpty()){
            return null;
        }
        /*
        pop出来的第一个节点是新的头结点
        头节点先保存起来
         */
        Node headNode = stack.pop();
        Node newHead = headNode;
        while (!stack.isEmpty()){
            /*
            把pop出来的节点逐步的挂到上一个节点
            不断的把下一个节点当成头节点
             */
            Node temp = stack.pop();
            headNode.next = temp;
            headNode = headNode.next;
        }
        /*
         * 最后一个节点就是反转之前的头节点，现在变成尾节点
         * 要让尾节点的 next 为空，否则会形成环
         */
        headNode.next = null;
        return newHead;
    }

    /**
     * 反转链表-递归方式实现
     * @param head
     * @return
     */
    private static Node recursiveReverseNode(Node head){
        if (head == null || head.next == null){
            return head;
        }
        Node next = head.next;
        Node reverse = recursiveReverseNode(next);
        next.next = head;
        head.next= null;
        return reverse;
    }


    private static class Node{
        private Integer data;
        private Node next;
    }
}
