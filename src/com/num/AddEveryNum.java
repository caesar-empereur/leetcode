package com.num;

/** 258
 * @Description
 *
给定一个非负整数 num，反复将各个位上的数字相加，直到结果为一位数。

示例:

输入: 38
输出: 2
解释: 各位相加的过程为：3 + 8 = 11, 1 + 1 = 2。 由于 2 是一位数，所以返回 2。

 *
 * @author: yangyingyang
 * @date: 2020/10/22.
 */
public class AddEveryNum {

    public static void main(String[] args) {
        addEveryNumRecursive(381);
    }

    /**
     * 使用递归调用得方式实现
     * @param num
     * @return
     */
    private static int addEveryNumRecursive(int num){
        if (num < 10){
            return num;
        }
        int next = 0;
        while (num != 0){
            /*
            这一步是通过对 10 取模将各个位依次相加
             */
            next = next + num % 10;

            /*
            这一步是逐渐将 4位数取前面3位，3位数取前面2位
             */
            num = num / 10;
        }
        return addEveryNumRecursive(next);
    }

    /**
     * 数根是将一正整数的各个位数相加（即横向相加），若加完后的值大于10的话，
     * 则继续将各位数进行横向相加直到其值小于十为止[1]
     * 例如54817的数根为7，因为5+4+8+1+7=25，25大于10则再加一次，2+5=7，7小于十，则7为54817的数根
     *
     * 原数: 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30
       数根: 1 2 3 4 5 6 7 8 9  1  2  3  4  5  6  7  8  9  1  2  3  4  5  6  7  8  9  1  2  3

     n 是 0 ，数根就是 0。

     n 不是 9 的倍数，数根就是 n 对 9 取余，即 n mod 9。

     n 是 9 的倍数，数根就是 9
     * @param num
     * @return
     */
    private static int addEveryNum(int num){
        return (num - 1) % 9 + 1;
    }
}
