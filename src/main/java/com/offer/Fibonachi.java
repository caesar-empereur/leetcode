package com.offer;

/**
 * @Description 斐波那契数列问题
 *
 *
它的数列： 1 1 2 3 5 8 ...

规律：除了第一和第二项，其他的数字均为前两项之和

 * @author: yangyingyang
 * @date: 2020/10/12.
 */
public class Fibonachi {

    public static void main(String[] args) {
        System.out.println(febonaccisFor(6));
    }

    /**
    递归方式实现
     时间复杂度为O(2^n), 复杂度指数提升
     */
    private static int febonaccis(int i){
        if(i == 1 || i == 2){
            return 1;
        }
        return febonaccis(i-1) + febonaccis(i - 2);
    }

    private static int febonaccisFor(int n){
        if(n == 1 || n == 2){
            return 1;
        }
        //设置第一项，第二项，第N项
        int first = 1;
        int second = 1;
        int nNum = 0;

        for (int i=3; i<=n; i++){
            //第N项 是前面2项的和
            nNum = first + second;

            //把第二项变成第一项
            first = second;

            //把第n项变成第二项
            second = nNum;
        }
        return nNum;
    }

}
