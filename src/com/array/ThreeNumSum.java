package com.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** 15
 * 给你一个包含 n 个整数的数组 nums，判断 nums 中是否存在三个元素 a，b，c ，使得 a + b + c = 0 ？
 *
 * 请你找出所有满足条件且不重复的三元组。
 *
 * 注意：答案中不可以包含重复的三元组。
 */
public class ThreeNumSum {

    public static void main(String[] args) {
        int[] array = new int[] { -2, -1, 0, 1, 2, 3 };
        for (ThreeNum threeNum : getSum(array)){
            System.out.println(threeNum);
        }
    }

    /**
     * 排序 + 双指针
     * 本题的难点在于如何去除重复解
     *
     * 对数组进行排序。
     *
     * 遍历排序后数组：
         * 若 nums[i]>0nums[i]>0：因为已经排序好，所以后面不可能有三个数加和等于 00，直接返回结果。
         * 对于重复元素：跳过，避免出现重复解
         * 令左指针 L=i+1L=i+1，右指针 R=n-1R=n−1，当 L<RL<R 时，执行循环：
             * 当 nums[i]+nums[L]+nums[R]==0nums[i]+nums[L]+nums[R]==0，执行循环，判断左界和右界是否和下一位置重复，
             * 去除重复解。并同时将 L,RL,R 移到下一位置，寻找新的解
             * 若和大于 00，说明 nums[R]nums[R] 太大，RR 左移
             * 若和小于 00，说明 nums[L]nums[L] 太小，LL 右移
     *
     *
     * 复杂度分析
     * 时间复杂度：O(N^2)
     * 数组排序 O(NlogN), 遍历数组 O(n)，双指针遍历 O(n)
     * 总体 O(NlogN) + O(n) * O(n), O(N^2)
     *
     * 空间复杂度：O(1)
     *
     */
    private static List<ThreeNum> getSum(int[] nums){
        List<ThreeNum> rtList = new ArrayList<>();
        if (nums.length<3){
            return rtList;
        }
        //先把数组排序
        Arrays.sort(nums);
        if (nums[0]>0){
            return rtList;
        }
        for (int i=0; i< nums.length - 1; i++){
            if (nums[i]==nums[i+1]){
                continue;
            }
            int left = i+1;
            int right = nums.length-1;
            while (left<right){
                //如果出现3数之和等于 0
                if (nums[i]+nums[left]+nums[right] == 0){
                    //先把当前结果保存起来
                    rtList.add(new ThreeNum(nums[i], nums[left], nums[right]));
                    //如果左边的数出现跟下一个重复，则左边指针右移
                    if (nums[left] == nums[left+1]){
                        left = left + 1;
                    }
                    //如果右边的数出现跟上一个重复，则右指针左移
                    if (nums[right] == nums[right-1]){
                        right = right - 1;
                    }
                    //不存在重复的情况下，左指针右移，右指针左移
                    left = left + 1;
                    right = right - 1;
                    //如果出现结果大于0，说明右边的指针的数太大，再向左边移动一位
                } else if (nums[i]+nums[left]+nums[right] > 0){
                    right = right - 1;
                } else {
                    left = left + 1;
                }
            }
        }
        return rtList;
    }

    static class ThreeNum{
        private Integer left;
        private Integer middle;
        private Integer right;
        public ThreeNum(Integer left, Integer middle, Integer right){
            this.left = left;
            this.middle = middle;
            this.right = right;
        }

        @Override
        public String toString() {
            return "ThreeNum{"
                   + "left="
                   + left
                   + ", middle="
                   + middle
                   + ", right="
                   + right
                   + '}';
        }
    }
}
