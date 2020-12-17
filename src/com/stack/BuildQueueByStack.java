package com.stack;

import java.util.Stack;

/**
 * @Description
 * @author: yangyingyang
 * @date: 2020/10/23.
 */
public class BuildQueueByStack {

    public static void main(String[] args) {

        QueueByStack queueByStack = new QueueByStack();
        for (int i=0; i<10;i++){
            queueByStack.push(i);
        }
        while (!queueByStack.isEmpty()){
            System.out.println(queueByStack.pop());
        }
    }

    private static class QueueByStack{
        private Stack<Integer> S1 = new Stack<>();
        private Stack<Integer> S2 = new Stack<>();

        private void push(Integer num){
            S1.push(num);
//            while (!S1.isEmpty()){
//                S2.push(S1.peek());
//            }
        }

        private boolean isEmpty(){
            return S1.isEmpty() && S2.isEmpty();
        }

        private Integer pop(){
//            Integer res = S2.pop();
//            while (!S2.isEmpty()){
//                S1.push(S2.peek());
//            }
//            return res;
            while (!S1.isEmpty()){
                S2.push(S1.pop());
            }
            Integer res = S2.pop();
            while (!S2.isEmpty()){
                S1.push(S2.pop());
            }
            return res;
        }

    }

}
