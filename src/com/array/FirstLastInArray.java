package com.array;

/**
 * 34
 * 排序数组中寻找元素的第一个与最后一个位置
 *
 * 直观的思路肯定是从前往后遍历一遍。用两个变量记录第一次和最后一次遇见 target 的下标，
 * 但这个方法的时间复杂度为 O(n)，没有利用到数组升序排列的条件。
 *
 * 由于数组已经排序，因此整个数组是单调递增的，我们可以利用二分法来加速查找的过程
 *
 */
public class FirstLastInArray {

    public static void main(String[] args) {
        int[] array = new int[] { 3, 5, 6, 6, 8, 11, 14, 15, 20,};
//        int[] array = new int[] { 3, 5, 6, 7, 8, 11, 14, 14, 20,};

        int[] result = findFirstAndLast(array, 6);
        System.out.println(result[0] + " " + result[1]);
    }

    /**
     * 时间复杂度：O(logn) ，其中 nn 为数组的长度。二分查找的时间复杂度为 O(logn)，一共会执行两次，因此总时间复杂度为 O(logn)。
     *
     * 空间复杂度：O(1)O(1) 。只需要常数空间存放若干变量
     */
    private static int[] findFirstAndLast(int[] nums, int target){
        int[] empty = new int[]{-1, -1};
        if (nums.length == 0){
            return empty;
        }
        int first = findFirst(nums, target);
        if (first == -1){
            return empty;
        }
        int last = findLast(nums, target);
        if (last == -1){
            return empty;
        }
        return new int[]{first, last};
    }

    /**
    二分查找中查找左边的元素
     */
    private static int findFirst(int[] nums, int target){
        int left = 0;
        int right = nums.length - 1;
        while (left < right){
            //算出2个位置的中间位置
            int mid = (left + right) / 2;
            if (nums[mid] < target){
                //如果中间位置的数比目标数小，说明目标值在数组的右边部分，左边的位置变为中间位置加1，只寻找右边部分
                left = mid + 1;
            } else if (nums[mid] == target){
                right = mid;
            } else {
                ////如果中间位置的数比目标数大，说明目标值在数组的左边部分，右边的位置变为中间位置减1，只寻找左边部分
                right = mid - 1;
            }
        }
        //left位置停止计算说明左边部分的大小比较查找结束，查找结束后与目标相等，说明目标存在
        if (nums[left] == target){
            return left;
        }
        return -1;
    }

    /**
     * 二分查找中查找右边的元素
     */
    private static int findLast(int[] nums, int target){
        int left = 0;
        int right = nums.length - 1;
        while (left < right){
            //右边的二分查找 的中间位置需要比左二分查找大一位，因为中间位置已经被左二分查找用过一次了
            int mid = (left + right + 1) / 2;
            if (nums[mid] > target){
                right = mid - 1;
            } else if (nums[mid] == target){
                left = mid;
            } else {
                left = mid + 1;
            }
        }
        if (nums[right] == target){
            return right;
        }
        return -1;
    }
}
