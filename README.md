# leetcode
算法刷题

## 20G的文件，每行存放1个URL，请用1G的内存计算出重复次数最多的前100个URL

- 解题思路：位图过滤不重复，分治法，HashMap，小顶堆筛选
    
    - 1 先用位图过滤不重复的数据
        ```
        为什么要过滤不重复的数据，因为不重复的数据在后面根本不用计算，
        这样筛选掉不重复的是为了减少不必要的计算量
        ```
        - 先构建一个长度为 20亿bit的位图，需要内存为 0.25 GB
        - 把 20亿个URL(640GB)的数据依次根据位图长度算出位图下标
        - 如果对应下标被设置为1，说明数据重复，将重复数据输出到另一个文件
        - 如果下标是 0，说明当前数据不存在重复，将对应下标设置为 1
        - 20亿的数据经过处理之后输出的文件结果，数量为 X，大小范围为 1-10亿
        - 如果结果文件小于 100 万个(0.64GB), 则不用分治处理了，直接放到 map 一次处理完
        - 如果结果文件大于 100 万个(0.64GB), 则分成 Y=X/100万 份，例如1亿个，则分成100份
    - 2 将上一步的结果集 分治处理 
        - 将大文件的URL依次对 Y 哈希取模算出是在 Y 份文件的第几份
        - 相同的URL肯定会分到同一份文件
    - 3 将每一份文件的数据放到 HashMap 中处理
        ```
        在单个 Map 里面能处理的最大数据为 100 万个 (0.64 GB)
        因此在分治处理的时候要把每一份数据切割到能放到内存处理,因此每份数据最大 100 万个 (0.64 GB)
        ```
        - 将每一份文件的结果放到 HashMap 中，key是URL，value是出现次数
        - 每一份文件的URL放到 Map 的方式为先判断是否存在 key 相同的URL,存在则value加1，否则把url put 进去
    - 4 构建一个容量为 1000 的小顶堆，堆的关键字的比较器是 Map 的value 次数大小比较
        - 每生成一个Map,就将 Map 遍历，如果小顶堆的元素数量小于1000个，则将 Map 的key放到堆中
        - 如果小顶堆的元素数量等于 1000，则与堆顶的元素的次数比较，比堆顶大，则将堆顶替换为新的key
        - 每一份文件拿到 Map 中处理完之后，将map清空，继续将下一份文件拿进来用相同方式处理
        - 这样每一份文件用一个map处理之后，都是通过遍历map与小顶堆比较，通过不断与堆的比较替换
        - 最终所有文件处理完了之后，容量为 1000 的小顶堆的结果输出，就是出现次数 前 1000 的URL

- 代码
```
package com.offer;

import java.util.*;

/**
 * Created by yang on 2020/10/30.
 */
public class TokKFrenUrl {

    /**
     * 这里创建长度为 20亿的位图，
     * 长度为 20亿 bit 的位图，内存空间是 0.25 G
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

```

- 代码输出结果
```
原始数据为 
3b430af0-fd71-4ba8-8d4e-e96b958e44f8
3b430af0-fd71-4ba8-8d4e-e96b958e44f8
3b430af0-fd71-4ba8-8d4e-e96b958e44f8
3b430af0-fd71-4ba8-8d4e-e96b958e44f8
3b430af0-fd71-4ba8-8d4e-e96b958e44f8
3b430af0-fd71-4ba8-8d4e-e96b958e44f8
3b430af0-fd71-4ba8-8d4e-e96b958e44f8
3b430af0-fd71-4ba8-8d4e-e96b958e44f8
3b430af0-fd71-4ba8-8d4e-e96b958e44f8
3b430af0-fd71-4ba8-8d4e-e96b958e44f8
9e489402-3a87-45a1-8530-fe0f8ae008bb
85295055-bc46-43db-9c82-3b29dd83fe2d
85295055-bc46-43db-9c82-3b29dd83fe2d
85295055-bc46-43db-9c82-3b29dd83fe2d
85295055-bc46-43db-9c82-3b29dd83fe2d
85295055-bc46-43db-9c82-3b29dd83fe2d
85295055-bc46-43db-9c82-3b29dd83fe2d
85295055-bc46-43db-9c82-3b29dd83fe2d
85295055-bc46-43db-9c82-3b29dd83fe2d
85295055-bc46-43db-9c82-3b29dd83fe2d
6e438c66-3ba6-4905-9287-f8276542b53f
c9eb3280-abc4-459a-943a-939e9f5a80f4
c9eb3280-abc4-459a-943a-939e9f5a80f4
c9eb3280-abc4-459a-943a-939e9f5a80f4
c9eb3280-abc4-459a-943a-939e9f5a80f4
c9eb3280-abc4-459a-943a-939e9f5a80f4
c9eb3280-abc4-459a-943a-939e9f5a80f4
c9eb3280-abc4-459a-943a-939e9f5a80f4
c9eb3280-abc4-459a-943a-939e9f5a80f4
100172c0-3bbe-432c-99dd-e57103072cb7
78d0b9b1-a348-4609-bbb1-e4d61c900b7b
78d0b9b1-a348-4609-bbb1-e4d61c900b7b
78d0b9b1-a348-4609-bbb1-e4d61c900b7b
78d0b9b1-a348-4609-bbb1-e4d61c900b7b
78d0b9b1-a348-4609-bbb1-e4d61c900b7b
78d0b9b1-a348-4609-bbb1-e4d61c900b7b
78d0b9b1-a348-4609-bbb1-e4d61c900b7b
78cdfe6b-21d0-4d29-8ad0-f8894ab8efd8
5f06f65b-9d1a-4746-9abf-9d0cc6321efc
5f06f65b-9d1a-4746-9abf-9d0cc6321efc
5f06f65b-9d1a-4746-9abf-9d0cc6321efc
5f06f65b-9d1a-4746-9abf-9d0cc6321efc
5f06f65b-9d1a-4746-9abf-9d0cc6321efc
5f06f65b-9d1a-4746-9abf-9d0cc6321efc
2346c02f-9a45-41aa-bcdf-d416d6cf9434
7e217d04-d0df-4424-a14f-1260d589a862
7e217d04-d0df-4424-a14f-1260d589a862
7e217d04-d0df-4424-a14f-1260d589a862
7e217d04-d0df-4424-a14f-1260d589a862
7e217d04-d0df-4424-a14f-1260d589a862
d58212bf-3183-4722-8229-e8b70a0c2eb5
a5b30f59-bfb6-4683-9194-dbdbed3b9448
a5b30f59-bfb6-4683-9194-dbdbed3b9448
a5b30f59-bfb6-4683-9194-dbdbed3b9448
a5b30f59-bfb6-4683-9194-dbdbed3b9448
fb6c7729-dc7e-474c-a0e5-709ab93f5675
7d686a91-4815-4999-b03a-47cb4da64931
7d686a91-4815-4999-b03a-47cb4da64931
7d686a91-4815-4999-b03a-47cb4da64931
b369bf63-8e3e-4cd2-bff9-9e10c94c4a97
a0229f07-5e90-4da4-ad5a-662792c4f6b3
a0229f07-5e90-4da4-ad5a-662792c4f6b3
53c748c4-1494-4d20-9069-6d58c84a3fe4
7d71aa1e-09d9-4b8e-b6be-65539d303aa8
816ce6ad-db9f-4a7d-8c05-e94420d32256
原始数据统计结果
URL: 7e217d04-d0df-4424-a14f-1260d589a862 出现次数为： 5
URL: 7d686a91-4815-4999-b03a-47cb4da64931 出现次数为： 3
URL: b369bf63-8e3e-4cd2-bff9-9e10c94c4a97 出现次数为： 1
URL: d58212bf-3183-4722-8229-e8b70a0c2eb5 出现次数为： 1
URL: 2346c02f-9a45-41aa-bcdf-d416d6cf9434 出现次数为： 1
URL: fb6c7729-dc7e-474c-a0e5-709ab93f5675 出现次数为： 1
URL: 78d0b9b1-a348-4609-bbb1-e4d61c900b7b 出现次数为： 7
URL: a0229f07-5e90-4da4-ad5a-662792c4f6b3 出现次数为： 2
URL: 78cdfe6b-21d0-4d29-8ad0-f8894ab8efd8 出现次数为： 1
URL: 7d71aa1e-09d9-4b8e-b6be-65539d303aa8 出现次数为： 1
URL: 816ce6ad-db9f-4a7d-8c05-e94420d32256 出现次数为： 1
URL: 3b430af0-fd71-4ba8-8d4e-e96b958e44f8 出现次数为： 10
URL: c9eb3280-abc4-459a-943a-939e9f5a80f4 出现次数为： 8
URL: a5b30f59-bfb6-4683-9194-dbdbed3b9448 出现次数为： 4
URL: 53c748c4-1494-4d20-9069-6d58c84a3fe4 出现次数为： 1
URL: 9e489402-3a87-45a1-8530-fe0f8ae008bb 出现次数为： 1
URL: 85295055-bc46-43db-9c82-3b29dd83fe2d 出现次数为： 9
URL: 6e438c66-3ba6-4905-9287-f8276542b53f 出现次数为： 1
URL: 5f06f65b-9d1a-4746-9abf-9d0cc6321efc 出现次数为： 6
URL: 100172c0-3bbe-432c-99dd-e57103072cb7 出现次数为： 1
原始数据过滤掉不重复后的统计结果 
URL: 7e217d04-d0df-4424-a14f-1260d589a862 出现次数为： 5
URL: 7d686a91-4815-4999-b03a-47cb4da64931 出现次数为： 3
URL: 3b430af0-fd71-4ba8-8d4e-e96b958e44f8 出现次数为： 10
URL: c9eb3280-abc4-459a-943a-939e9f5a80f4 出现次数为： 8
URL: a5b30f59-bfb6-4683-9194-dbdbed3b9448 出现次数为： 4
URL: 85295055-bc46-43db-9c82-3b29dd83fe2d 出现次数为： 9
URL: 5f06f65b-9d1a-4746-9abf-9d0cc6321efc 出现次数为： 6
URL: 78d0b9b1-a348-4609-bbb1-e4d61c900b7b 出现次数为： 7
URL: a0229f07-5e90-4da4-ad5a-662792c4f6b3 出现次数为： 2
出现次数排名前5的URL为
5f06f65b-9d1a-4746-9abf-9d0cc6321efc 次数 6
78d0b9b1-a348-4609-bbb1-e4d61c900b7b 次数 7
c9eb3280-abc4-459a-943a-939e9f5a80f4 次数 8
85295055-bc46-43db-9c82-3b29dd83fe2d 次数 9
3b430af0-fd71-4ba8-8d4e-e96b958e44f8 次数 10
```
