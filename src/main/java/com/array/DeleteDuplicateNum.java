package com.array;

/** 26
 * @Description
给定一个排序数组，你需要在 原地 删除重复出现的元素，使得每个元素只出现一次，返回移除后数组的新长度。

不要使用额外的数组空间，你必须在 原地 修改输入数组 并在使用 O(1) 额外空间的条件下完成。

 * @author: yangyingyang
 * @date: 2020/10/21.
 */
public class DeleteDuplicateNum {

    public static void main(String[] args) {

    }

    /**
     * 双指针法

     数组完成排序后，我们可以放置两个指针 ii 和 jj，其中 ii 是慢指针，而 jj 是快指针。
     只要 nums[i] = nums[j]nums[i]=nums[j]，我们就增加 jj 以跳过重复项。

     当我们遇到 nums[j] \neq nums[i]nums[j]=nums[i] 时，跳过重复项的运行已经结束，
     因此我们必须把它（nums[j]nums[j]）的值复制到 nums[i + 1]nums[i+1]。然后递增 ii，
     接着我们将再次重复相同的过程，直到 jj 到达数组的末尾为止

     * @param array
     * @return
     */
    private static int returnLengthAfterRemoveDup(int[] array){
        if (array.length == 0){
            return 0;
        }
        int i = 0;
        for (int j = 1; j< array.length; j++){
            if (array[i] != array[j]){
                i++;
                array[i] = array[j];
            }
        }
        return i+1;
    }
}
