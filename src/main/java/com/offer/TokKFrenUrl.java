package com.offer;

import java.util.*;

/**
 * Created by yang on 2020/10/30.
 */
public class TokKFrenUrl {

    /**
     * 这里创建长度为 10亿的位图，
     * 长度为 10亿 bit 的位图，内存空间是 0.25 G
     */
    private static final int BIT_SIZE = 2000_000_000;
    private static final BitSet BIT_SET = new BitSet(BIT_SIZE);

    //用2个list来模拟原始的URL数据与去掉不重复之后的URL数据
    private static final List<String> BEFORE_LIST = new ArrayList<>();
    private static final List<String> AFTER_LIST = new ArrayList<>();

    /*
    这里用 5 来模拟最终要算出出现次数前 5 的URL，因此小顶堆的容量也是 5
     */

    private static final Integer SIZE_TIMES = 5;

    /*
    优先级队列就是大小顶堆的数据结构，默认是小顶堆，
    这里用比较器 UrlCount 的 count属性 整数比较，也是小顶堆
    小顶堆的容量是 5，就是存放出现次数前5的URL
     */
    private static final PriorityQueue<UrlCount> MIN_HEAP = new PriorityQueue<>(Comparator.comparing(UrlCount::getCount));

    //构造一些测试数据
    static {
        for (int i=10; i>0; i--){
            //添加一些重复的数据
            String url = UUID.randomUUID().toString();
            for (int j=0; j< i; j++){
                BEFORE_LIST.add(url);
            }
            //添加一些不重复的数据
            BEFORE_LIST.add(UUID.randomUUID().toString());
        }
        System.out.println("原始数据为 ");
        for (String s : BEFORE_LIST) {
            System.out.println(s);
        }
        System.out.println("原始数据统计结果");
        countData(BEFORE_LIST);
    }

    private static void countData(List<String> list){
        Map<String, Integer> map = new HashMap<>();
        for (String s : list) {
            if (map.containsKey(s)){
                map.put(s, map.get(s) + 1);
            } else {
                map.put(s, 1);
            }
        }
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.println("URL: " + entry.getKey() + " 出现次数为： " + entry.getValue());
        }
    }

    public static void main(String[] args) {
        /*
        第一步
        这里先把数据去掉那些不重复的
         */
        for (String s : BEFORE_LIST) {
            if (isUrlRepeated(s)){
                if (!AFTER_LIST.contains(s)){
                    /*
                    一个URL出现3次，第一次进位图是不重复的，
                    因此第二次出现重复的时候，要把第一次的URL也加进来到结果集中
                    判断条件是结果集还没有这个URL，如果有的话就不用再添加了
                    不然出现3次的URL，最后只会添加2次到结果集中

                     */
                    AFTER_LIST.add(s);
                }
                AFTER_LIST.add(s);
            }
        }
        System.out.println("原始数据过滤掉不重复后的统计结果 ");
        countData(AFTER_LIST);

        /*
        第二步

        这一步将过滤后的结果数据分治处理，将数据根据哈希分为多份，模拟海量数据分治处理
        使用的方式就是将每一个URL的哈希码堆结果数据的数量长度哈希取模，算出是在 map 中的哪一个 整形的 key
        相同的URL 肯定会分到同一个map 的key 里面的
         */
        Map<Integer, List<String>> map = new HashMap<>();
        int modSize = AFTER_LIST.size()%2==0? AFTER_LIST.size() - 1: AFTER_LIST.size();
        for (String s : AFTER_LIST) {
            Integer index = modSize & hash(s);
            List<String> list = map.get(index);
            if (list == null){
                list = new ArrayList<>();
            }
            list.add(s);
            map.put(index, list);
        }

        // 这里的 map 的value就是最终分成多份数据
        for (List<String> list : map.values()) {
            calcListToHeap(list);
        }

        System.out.println("出现次数排名前5的URL为");

        while (!MIN_HEAP.isEmpty()){
            UrlCount urlCount = MIN_HEAP.poll();
            System.out.println(urlCount.url + " 次数 " + urlCount.getCount());
        }
    }

    private static int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    /**
     * 将处理后的数据分成多份list，每一份调用这个方法
     * 将list转换成 map 之后与小顶堆轮流比较
     */
    private static void calcListToHeap(List<String> list){
        if (list == null || list.size() == 0){
            return;
        }
        /*
        这里是第三步
        将每一份 list转换成 map，就是URL跟次数的对应
         */
        Map<String, Integer> urlCountMap = new HashMap<>();
        urlCountMap.clear();
        for (String s : list) {
            if (urlCountMap.containsKey(s)){
                urlCountMap.put(s, urlCountMap.get(s) + 1);
            } else {
                urlCountMap.put(s, 1);
            }
        }
        /*
        这里是第四步
        将转换成 map 之后与小顶堆轮流比较
         */
        for (String key : urlCountMap.keySet()) {
            UrlCount urlCount = new UrlCount(key, urlCountMap.get(key));
            if (MIN_HEAP.size() < SIZE_TIMES){
                /*
                如果小顶堆的元素数量还没到 5，就就把新的URL跟次数组成一个对象直接添加到堆中
                 */
                MIN_HEAP.offer(urlCount);
            } else if (urlCountMap.get(key) > MIN_HEAP.peek().getCount()){
                /*
                如果小顶堆数量大于等于 5，就要进行堆顶的元素的比较
                如果新的URL的次数比堆顶元素的次数大，就把新的URL跟次数组成一个对象添加到堆中
                添加完之后堆会重新调整为小顶堆
                 */
                MIN_HEAP.poll();
                MIN_HEAP.offer(urlCount);
            }
        }
    }

    /**
     * 这里使用布隆过滤器的思路来实现，不需要存储这么多的数据到内存，只需要
     * 判断重复即可，需要的内存只有 0.12 GB
     *
     * 根据(字符串) 的哈希函数与位图长度计算出位图的下标，如果这个下标被置为1，说明当前数据重复，
     * 直接返回重复，如果下标是 0，说明数据不重复，把下标置为1
     *
     * @param url
     * @return
     */
    private static boolean isUrlRepeated(String url){
        if (url == null || url.equals("")){
            return false;
        }
        int hashcode = url.hashCode();
        int index = (BIT_SIZE - 1) & hashcode;
        if (BIT_SET.get(index)){
            return true;
        }
        BIT_SET.set(index);
        return false;
    }

    /*
    这里使用一个url跟次数组成的对象来当作小顶堆的元素，方便比较与结果输出
     */
    private static class UrlCount{
        private String url;
        private Integer count;

        public Integer getCount() {
            return count;
        }

        public UrlCount(String url, Integer count) {
            this.url = url;
            this.count = count;
        }
    }
}
