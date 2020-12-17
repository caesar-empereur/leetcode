package com.array;

import java.util.Arrays;

/**
 * 最接近的3数之和
 *
 * 给定一个包括 n 个整数的数组 nums 和 一个目标值 target。找出 nums 中的三个整数，
 * 使得它们的和与 target 最接近。返回这三个数的和。假定每组输入只存在唯一答案
 *
 */
public class CloseThreeNumSum {

    public static void main(String[] args) {
        int[] array = new int[] {1, 3, 4, 5, 7, 8, 10, 11, 14};
        System.out.println(getCloseSum(array, 12));
    }

    /**
     *首先进行数组排序，时间复杂度 O(nlogn)O(nlogn)
     * 在数组 nums 中，进行遍历，每遍历一个值利用其下标i，形成一个固定值 nums[i]
     * 再使用前指针指向 start = i + 1 处，后指针指向 end = nums.length - 1 处，也就是结尾处
     * 根据 sum = nums[i] + nums[start] + nums[end] 的结果，判断 sum 与目标 target 的距离，
     * 如果更近则更新结果 ans 同时判断 sum 与 target 的大小关系，因为数组有序，
     * 如果 sum > target 则 end--，如果 sum < target 则 start++，如果 sum == target 则说明距离为 0 直接返回结果
     *
     *
     * 总时间复杂度：O(nlogn) + O(n^2) = O(n^2)
     */
    private static int getCloseSum(int[] nums, int targetSum){

        //先把数组排序
        Arrays.sort(nums);
        //初始化的最小的和是前面3个数字
        int minSum = nums[0] + nums[1] + nums[2];

        for (int i=0; i < nums.length; i++){

            //还是头尾2个指针同时向中间移动
            int left = i + 1;
            int right = nums.length - 1;

            while (left < right){
                int sum = nums[i] + nums[left] + nums[right];

                //如果和刚好是目标的和一样，直接返回
                if (sum == targetSum){
                    return sum;
                }
                //如果和比目标的和大，说明右指针对应的值大了，把右指针向左移动一次
                if (sum > targetSum){
                    right = right - 1;
                }
                //如果和比目标的和小，说明左指针对应的值小了，把左指针向右移动一次
                if (sum < targetSum){
                    left = left + 1;
                }
                /*
                如果当前和与目标和绝对值小于上一个和与目标和绝对值
                说明当前和更加接近目标和，更新一下目标和
                 */
                if (Math.abs(targetSum - sum) < Math.abs(targetSum - minSum)){
                    minSum = sum;
                }
            }
        }
        return minSum;
    }
}
