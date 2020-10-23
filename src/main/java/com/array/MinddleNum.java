package com.array;

import java.util.PriorityQueue;

/** offer 41
 * @Description 找出数组的中位数
 * 中位数的概念
 * 1 3 5 7 9    中位数是 5
 * 1 3 5 7 9 10 中位数是 6
 * @author: yangyingyang
 * @date: 2020/10/12.
 */
public class MinddleNum {

    public static void main(String[] args) {
        int[] array = new int[] { 2,5,7,8,4,9 };
        System.out.println(findMinddleNum(array));
    }

    /**
     * 不用排序的方式，而是用大顶堆，小顶堆的思路来实现
     * 不断的遍历数组，把数组的数拿出来判断，放到2个堆里面，放完之后调整堆
     *
     * 大顶堆，用来存放数组较小的数，放在左边
     * 小顶堆，用来存放数组较大的数，放在右边
     *
     * 如果数组长度是奇数，左边的大顶堆中存放 n/2+1 个数，右边的小顶堆存放剩下的数
     * 数组遍历完之后，左边的大顶堆的堆顶就是中位数
     *
     * 如果数组长度是奇数，左边的大顶堆中存放 n/2 个数，右边的小顶堆存放n/2 个数
     * 数组遍历完之后，大顶堆和小顶堆的堆顶的平均数就是 中位数
     *
     * 时间复杂度 O(logN)
     * @param array
     * @return
     */
    private static double findMinddleNum(int[] array) {
        //小顶堆，用来存放数组较大的数，放在右边
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();

        //大顶堆，用来存放数组较小的数，放在左边
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((o1, o2) -> o2 - o1);

        int mindLenght = 0;
        if (array.length % 2 == 0){
            mindLenght = array.length / 2;
        } else {
            mindLenght = array.length / 2 + 1;
        }
        for (int i : array) {
            //默认放到左边的大顶堆 (用来存放数组较小的数)
            if (maxHeap.size() == 0 || i <= maxHeap.peek()){
                maxHeap.add(i);
                //如果大顶堆的个数已经比小顶堆的多
                if (maxHeap.size() > mindLenght){
                    //则把大顶堆的堆顶 转移到 小顶堆
                    minHeap.add(maxHeap.poll());
                }
            } else {
                minHeap.add(i);
                //如果小顶堆的个数 比大顶堆的个数多
                if (maxHeap.size() < mindLenght){
                    //则把小顶堆的堆顶转移到大顶堆
                    maxHeap.add(minHeap.poll());
                }
            }
        }
        if (maxHeap.size() == minHeap.size()){
            return (maxHeap.peek() + minHeap.peek()) / 2;
        }
        return maxHeap.peek();
    }
}
