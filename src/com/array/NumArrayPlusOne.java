package com.array;

import java.util.Arrays;

/**
 * 66
 * 给定一个由 整数 组成的 非空 数组所表示的非负整数，在该数的基础上加一。
 * 最高位数字存放在数组的首位， 数组中每个元素只存储单个数字。
 * 你可以假设除了整数 0 之外，这个整数不会以零开头
 *
 * 示例 2：
 *
 * 输入：digits = [4,3,2,1]
 * 输出：[4,3,2,2]
 * 解释：输入数组表示数字 4321。
 */
public class NumArrayPlusOne {

    public static void main(String[] args) {
        int[] initNum = new int[]{4, 3, 2, 1};
        System.out.println(Arrays.toString(plusOne(initNum)));

        int[] initNum1 = new int[]{9, 9, 9, 9};
        System.out.println(Arrays.toString(plusOne(initNum1)));

        int[] initNum2 = new int[]{1, 2, 9, 9};
        System.out.println(Arrays.toString(plusOne(initNum2)));
    }

    /**
     * 时间复杂度 O(N)
     */
    private static int[] plusOne(int[] nums){
        if (nums.length == 0) {
            return new int[]{};
        }
        for (int i = nums.length - 1; i >= 0; i--){
            //先直接把个位数加1
            nums[i]++;
            //如果个位数的数字不等于 10, 说明不用进位，数据直接返回
            if (nums[i] != 10) {
                return nums;
            }
            //如果当前位数+1等于10，说明要进位了，进位是在下一次循环里面加,当前位数要变为0
            nums[i] = 0;
        }
        //如果没有在循环里面返回，说明每一位都产生进位了，除了第一位是1，其他都是0
        int[] res = new int[nums.length+1];
        res[0] = 1;
        return res;
    }
}
