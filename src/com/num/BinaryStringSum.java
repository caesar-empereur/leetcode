package com.num;

/**
 * 67
 * 给你两个二进制字符串，返回它们的和（用二进制表示）
 *
 * 示例 2:
 *
 * 输入: a = "1010", b = "1011"
 * 输出: "10101"
 */
public class BinaryStringSum {

    public static void main(String[] args) {
        String a1 = "10011";
        String b1 = "10010";
        System.out.println(addBinary(a1, b1));

        String a2 = "110011";
        String b2 =   "1001";
        System.out.println(addBinary(a2, b2));

        String a3 =   "1001";
        String b3 = "110011";
        System.out.println(addBinary(a3, b3));
    }

    /**
     * 时间复杂度：O(n)
     */
    private static String addBinary(String a, String b) {
        if (a == null || a.equals("") || b == null || b.equals("")){
            return "";
        }
        int len = Math.max(a.length(), b.length());
        //2个字符串的长度的差
        int lenMinus = Math.abs(a.length() - b.length());

        StringBuilder res = new StringBuilder();
        int carry = 0;

        //按照2个字符串的最大长度来遍历
        for (int i = len - 1; i >= 0; i--){
            //charAt(i) 的取值需要计算哪个字符串是长度更大的
            int sum = Integer.valueOf((a.length()>=b.length()?a:b).charAt(i)+"") + carry;
            if (i>=lenMinus){
                //charAt(i-lenMinus)需要计算哪个字符串长度是短的
                sum = sum + Integer.valueOf((a.length()>=b.length()?b:a).charAt(i-lenMinus)+"");
            }
            //如果和大于等于2，说明要进位
            carry = sum >= 2?1:0;
            //当前位是0或者2的话，则还是0，否之为1
            res.append(sum%2==0?"0":"1");
        }
        String returnS = res.reverse().toString();
        //如果首字符是0说明进位了，再加个1到前面
        if ((returnS.charAt(0)+"").equals("0")){
            returnS = "1" + returnS;
        }
        return returnS;
    }

}
