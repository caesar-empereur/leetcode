package com.array;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @Description 找出数组中的重复数字
 * @author: yangyingyang
 * @date: 2020/10/12.
 */
public class DuplicateNum {

    public static void main(String[] args) {
        int[] array = new int[] { 3, 5, 5, 6, 23, 2, 6, 7, 8, 9, 8 };
        System.out.println(findDuplicateNum(array));
    }

    /**
     * 这里使用 set 数据结构不能重复的原理
     * set 的数据结构是 HashMap, 使用了hashmap 的key不能重复
     *
     * 时间复杂度是 O(n)
     * @param array
     * @return
     */
    private static int findDuplicateNum(int[] array) {
        Set<Integer> set = new HashSet<>();
        for (int i : array) {
            if (set.contains(i)) {
                return i;
            }
            else {
                set.add(i);
            }
        }
        return 0;
    }
}
