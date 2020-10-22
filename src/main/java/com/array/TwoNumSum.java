package com.array;

import java.util.HashMap;
import java.util.Map;

/** 1
 * @Description
 *
 *
给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标。

你可以假设每种输入只会对应一个答案。但是，数组中同一个元素不能使用两遍
给定 nums = [2, 7, 11, 15], target = 9

因为 nums[0] + nums[1] = 2 + 7 = 9
所以返回 [0, 1]

 * @author: yangyingyang
 * @date: 2020/10/20.
 */
public class TwoNumSum {

    public static void main(String[] args) {

    }

    /**
     * @param array
     * @param sum
     * @return
     */
    private static int[] findSumIndex(int[] array, int sum){
        Map<Integer,Integer> map = new HashMap<>();
        for (int i=0; i< array.length; i++){
            // value + array[i] = sum, 因此做个减法找出 array[i] 的剩下那部分
            int value = sum - array[i];
            if (map.containsKey(value)){
                return new int[]{map.get(value), i};
            }
            map.put(array[i], i);
        }
        return new int[]{0};
    }
}
