package com.array;

import java.util.PriorityQueue;

/**
 * 215.
 * 数组中的第K个最大元素

 * @Description
 * @author: yangyingyang
 * @date: 2020/10/22.
 */
public class KthNum {

    public static void main(String[] args) {
        int[] array = new int[] {1,2,3,4,5,6,7};
        System.out.println(findKthNum(array, 3));
    }

    /**
     * 常见思路是用排序，然后获取 第 k 个元素，但是这样全部数据都拿来排序
     *
     * 前面第K个最大元素，就只需要处理 前面 k 个元素即可
     *
     * 只要将数组元素全部添加到小顶堆，则堆顶就是 第 k 个大的元素
     *
     * 时间复杂度为 O(Nlogk)
     * @param array
     * @param k
     * @return
     */
    private static int findKthNum(int[] array, int k){
        if (array.length == 0){
            return 0;
        }
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        for (int i : array) {
            if (minHeap.size()<k){
                minHeap.add(i);
            } else if (i > minHeap.peek()){
                minHeap.poll();
                minHeap.add(i);
            }
        }
        return minHeap.poll();
    }
}
