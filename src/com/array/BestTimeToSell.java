package com.array;

/** 121
 * @Description
给定一个数组，它的第 i 个元素是一支给定股票第 i 天的价格。
如果你最多只允许完成一笔交易（即买入和卖出一支股票一次），设计一个算法来计算你所能获取的最大利润。
注意：你不能在买入股票前卖出股票。

输入: [7,1,5,3,6,4]
输出: 5
解释: 在第 2 天（股票价格 = 1）的时候买入，在第 5 天（股票价格 = 6）的时候卖出，最大利润 = 6-1 = 5 。
注意利润不能是 7-1 = 6, 因为卖出价格需要大于买入价格；同时，你不能在买入前卖出股票

 * @author: yangyingyang
 * @date: 2020/10/21.
 */
public class BestTimeToSell {

    public static void main(String[] args) {

    }

    /**
     * 时间复杂度 O(N)
     * @param prices
     * @return
     */
    private static int getMaxProfit(int[] prices){
        //这里初始价格是 整数最大值，正常情况下任何整数都不能大于这个值
        int minPrice = Integer.MAX_VALUE;
        int maxProfit = 0;
        for (int i = 0; i< prices.length; i++){
            if (prices[i] < minPrice){
                /*
                 * 不断的找出最低的价格。例如1，后面的数都不会比1还小的话，1
                 * 就是最低价格，不会变
                 */
                minPrice = prices[i];
            } else if (prices[i] - minPrice > maxProfit){
                /*
                找出1后面的数字与1的差最大的，后面的每个数与1做比较
                差值会不断被重新赋值，直到 6-1=5, 这个5后面不会被改变了
                 */
                maxProfit = prices[i] - minPrice;
            }
        }
        return maxProfit;
    }
}
