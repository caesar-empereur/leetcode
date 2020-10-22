package com.array;

import java.util.*;

/** 247
 * 频率前 k 高的元素
 * @Description
 *
 * 347
给定一个非空的整数数组，返回其中出现频率前 k 高的元素。

示例 1:

输入: nums = [1,1,1,2,2,3], k = 2
输出: [1,2]


 * @author: yangyingyang
 * @date: 2020/10/22.
 */
public class TopKFreqNum {

    public static void main(String[] args) {
        int[] array = new int[] { 3, 5, 5, 5, 23, 2, 6, 7, 8, 9, 8 };
        System.out.println(getTopFreqNum(array, 2).toString());
    }

    /**
     * 使用一个map来保存数组种的数字与次数的对应关系,key是数字，value是数字对应的次数
     *
     * 普通思路是对value进行排序，再拿出前面 k 个数字
     *
     * top k 的问题可以用堆来处理，不需要全部排序，只要处理前面 k 个元素即可
     *
     * 时间复杂度：O(nlogk) n 表示数组的长度，k 是前面k个，对应的小顶堆的建堆时间
     *
     * @param array
     * @param k
     * @return
     */
    private static List<Integer> getTopFreqNum(int[] array, int k){
        if (array.length == 0){
            return new ArrayList<>();
        }
        Map<Integer, Integer> map = new HashMap<>();
        for (int i : array) {
            if (map.containsKey(i)){
                //如果数字在map中重复，将次数加1
                Integer times = map.get(i);
                map.put(i, times + 1);
            } else {
                map.put(i, 1);
            }
        }

        /*
        常见的大小顶堆是根据关键字维护的，但是在这个例子中
        不能用数字本身来比较，而是要用数字出现的次数，就是map 的value 来比较
         */
        PriorityQueue<Integer> minHeap = new PriorityQueue<>(Comparator.comparing(map::get));
        for (Integer key : map.keySet()) {
            if (minHeap.size() < k){
                minHeap.add(key);
            } else if (map.get(key) > map.get(minHeap.peek())){
                /*
                小顶堆的堆顶的频率最小，先构建出一个小顶堆，如果后面的频率比堆顶还大,
                则将新的元素把堆顶替换，堆会重新调整
                 */
                minHeap.poll();
                minHeap.add(key);
            }
        }
        List<Integer> res = new ArrayList<>();
        while (!minHeap.isEmpty()) {
            res.add(minHeap.remove());
        }
        return res;

    }
}
