package com.offer;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

/**
 * 10 亿行MD5值，一个MD5算64字节(32长度的字符)，总共的数据量有62GB
 *
 * 相同的MD5算出来的 hashcode 是一样的，HashMap 貌似可以处理
 * 把 md5 不断的先判断是否存在，存在就不添加到map，不存在就添加，然后把map的结果输出到文件
 * 但是这个map 最终需要几十GB的内存，内存消耗较大
 *
 */
public class DeleteDuplicateMd5 {

    /**
     * 这里创建长度为 10亿的位图，
     * 长度为 10亿 bit 的位图，内存空间是 0.12 G
     */
    private static final int BIT_SIZE = 1000_000_000;
    private static final BitSet BIT_SET = new BitSet(BIT_SIZE);

    public static void main(String[] args) {
        //这里模拟从文件中读取的原始数据
        List<String> listRaw = generateMdList();

        //这个list 用来存放经过去重处理的数据
        List<String> listHandle = new ArrayList<>();
        for (String s : listRaw) {
            if (!isStringExisted(s)){
                //只要MD5 不重复，就将结果添加到list，用添加的过程来模拟输出到文件
                listHandle.add(s);
            }
        }

        System.out.println("去重后的结果 ");
        for (String s : listHandle) {
            System.out.println(s);
        }
    }

    /**
     * 这里使用布隆过滤器的思路来实现，不需要存储这么多的数据到内存，只需要
     * 判断重复即可，需要的内存只有 0.12 GB
     *
     * 根据MD5(字符串) 的哈希函数与位图长度计算出位图的下标，如果这个下标被置为1，说明当前数据重复，
     * 直接返回重复，如果下标是 0，说明数据不重复，把下标置为1
     *
     * @param md5
     * @return
     */
    private static boolean isStringExisted(String md5){
        //空字符串认为是重复的，不输出到结果中
        if (md5 == null || md5.equals("")){
            return true;
        }
        int hash = md5.hashCode();
        //这里根据位图长度-1 与 hash值 做与操作，得出一个位图的下标
        int index = (BIT_SIZE - 1) & hash;
        if (BIT_SET.get(index)){
            //如果这个位图的下标是被置为1，说明之前有相同的数据设置到这个下标，就是已经存在
            return true;
        }
        //位图的这个下标没有被设置过，说明当前没有跟这个md重复的数据，把这个md5算出的下标置为1
        BIT_SET.set(index);
        return false;
    }

    /**
     * 生成一些MD5 字符串，模拟从文件中读取
     * @return
     */
    private static List<String> generateMdList(){
        List<String> listRaw = new ArrayList<>();
        listRaw.add("F92696D7AF23495DA9C2F21F0A81EA31");
        listRaw.add("8D72872DB9929623E5F04A83D6D3ABE6");
        listRaw.add("0763E9813A599D712AB528B64C134A09");

        listRaw.add("7F393665F3A2E653400E09C14EF885CA");
        listRaw.add("7F393665F3A2E653400E09C14EF885CA");

        listRaw.add("711E57D30B34A2C6FAD658B55F0728D7");
        listRaw.add("711E57D30B34A2C6FAD658B55F0728D7");
        return listRaw;
    }


}
