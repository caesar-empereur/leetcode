package com.string;

import java.util.HashMap;
import java.util.Map;

/** 387
 * 给定一个字符串，找到它的第一个不重复的字符，并返回它的索引。如果不存在，则返回 -1
 */
public class UnDuplicateChar {

    public static void main(String[] args) {
        System.out.println(firstUniqChar("loveleetcode"));
    }

    /**
     * 将字符与次数放到 map 中，
     * 遍历字符数组，找出第一个次数位1 的字符，返回下标
     * 时间复杂度 O(N)
     */
    private static int firstUniqChar(String s) {
        if (s == null || s.equals("")){
            return -1;
        }

        Map<Character, Integer> map = new HashMap<>();
        int len = s.length();
        for (int i=0; i< len; i++){
            Character character = s.charAt(i);
            map.put(character, map.getOrDefault(character, 0) + 1);
        }
        for (int i=0; i< len; i++){
            if (map.get(s.charAt(i)) == 1){
                return i;
            }
        }
        return -1;
    }
}
