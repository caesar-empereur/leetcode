package com.num;

/** 7
 * @Description
 *
 * 反转一个整数
 *
 *
 * @author: yangyingyang
 * @date: 2020/10/21.
 */
public class IntegerReverse {

    /**
     * 每次拿末尾数字，然后整个数 除以 10
     * @param num
     * @return
     */
    private static int reverseInteger(int num){
        int res = 0;
        while (num != 0){
            //每次取末尾数字
            int tmp = num % 10;
            //判断是否 大于 最大32位整数
            if (res>214748364 || (res==214748364 && tmp>7)) {
                return 0;
            }
            //判断是否 小于 最小32位整数
            if (res<-214748364 || (res==-214748364 && tmp<-8)) {
                return 0;
            }
            res = res * 10 + tmp;
            num = num / 10;
        }
        return res;
    }
}
