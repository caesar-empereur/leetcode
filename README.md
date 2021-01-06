## 双指针(快慢指针)问题
- 找到链表的中间节点(876)
```
/**
 * 我们可以继续优化方法二，用两个指针 slow 与 fast 一起遍历链表。slow 一次走一步，fast 一次走两步。
 * fast 的速度是 slow 的2倍
 * 那么当 fast 到达链表的末尾时，slow 必然位于中间

 时间复杂度：O(n)
 * @param head
 * @return
 */
private static Node findMiddleNode(Node head){
    if (head == null || head.next == null){
        return null;
    }

    Node slow = head;
    Node fast = head;
    while (fast != null && fast.next != null){
        slow = slow.next;
        fast = fast.next.next;
    }
    return slow;
}
```

- 原地删除重复数组(26)
```
给定一个排序数组，你需要在 原地 删除重复出现的元素，使得每个元素只出现一次，返回移除后数组的新长度。

不要使用额外的数组空间，你必须在 原地 修改输入数组 并在使用 O(1) 额外空间的条件下完成。

/**
 * 双指针法

 数组完成排序后，我们可以放置两个指针 i 和 j，其中 i 是慢指针，而 j 是快指针。
 只要 nums[i] = nums[j]，我们就增加 j 以跳过重复项。

 当我们遇到 nums[j] =nums[i] 时，跳过重复项的运行已经结束，
 因此我们必须把它（nums[j]）的值复制到 nums[i + 1]nums[i+1]。然后递增 i，
 接着我们将再次重复相同的过程，直到 j 到达数组的末尾为止

 时间复杂度 O(N)
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
```

- 链表判断是否为环形(141)
```
我们可以根据上述思路来解决本题。具体地，我们定义两个指针，一快一满。慢指针每次只移动一步，
而快指针每次移动两步。初始时，慢指针在位置 head，而快指针在位置 head.next。
这样一来，如果在移动的过程中，快指针反过来追上慢指针，就说明该链表为环形链表。
否则快指针将到达链表尾部，该链表不为环形链表

/**
 * 时间复杂度：O(n)
 * @param head
 * @return
 */
private static boolean isLinkCircle(Node head){
    if (head == null || head.next == null){
        return false;
    }
    Node slow = head;
    Node fast = head.next;
    while (slow != fast){
        //如果链表不是环，则尾节点的next肯定为空
        if (fast == null || fast.next == null){
            return false;
        }
        slow = slow.next;
        fast = fast.next.next;
    }
    //跳出循环说明 快慢2个指针遇上了
    return true;
}
```

- 三数之和(15)
```
/** 15
 * 给你一个包含 n 个整数的数组 nums，判断 nums 中是否存在三个元素 a，b，c ，使得 a + b + c = 0 ？
 *
 * 请你找出所有满足条件且不重复的三元组。
 *
 * 注意：答案中不可以包含重复的三元组。
 */

/**
 * 排序 + 双指针
 * 本题的难点在于如何去除重复解
 *
 * 对数组进行排序。
 *
 * 遍历排序后数组：
     * 若 nums[i]>0nums[i]>0：因为已经排序好，所以后面不可能有三个数加和等于 00，直接返回结果。
     * 对于重复元素：跳过，避免出现重复解
     * 令左指针 L=i+1L=i+1，右指针 R=n-1R=n−1，当 L<RL<R 时，执行循环：
         * 当 nums[i]+nums[L]+nums[R]==0nums[i]+nums[L]+nums[R]==0，执行循环，判断左界和右界是否和下一位置重复，
         * 去除重复解。并同时将 L,RL,R 移到下一位置，寻找新的解
         * 若和大于 00，说明 nums[R]nums[R] 太大，RR 左移
         * 若和小于 00，说明 nums[L]nums[L] 太小，LL 右移
 *
 *
 * 复杂度分析
 * 时间复杂度：O(N^2)
 * 数组排序 O(NlogN), 遍历数组 O(n)，双指针遍历 O(n)
 * 总体 O(NlogN) + O(n) * O(n), O(N^2)
 *
 * 空间复杂度：O(1)
 *
 */
private static List<ThreeNum> getSum(int[] nums){
    List<ThreeNum> rtList = new ArrayList<>();
    if (nums.length<3){
        return rtList;
    }
    //先把数组排序
    Arrays.sort(nums);
    if (nums[0]>0){
        return rtList;
    }
    for (int i=0; i< nums.length - 1; i++){
        if (nums[i]==nums[i+1]){
            continue;
        }
        int left = i+1;
        int right = nums.length-1;
        while (left<right){
            //如果出现3数之和等于 0
            if (nums[i]+nums[left]+nums[right] == 0){
                //先把当前结果保存起来
                rtList.add(new ThreeNum(nums[i], nums[left], nums[right]));
                //如果左边的数出现跟下一个重复，则左边指针右移
                if (nums[left] == nums[left+1]){
                    left = left + 1;
                }
                //如果右边的数出现跟上一个重复，则右指针左移
                if (nums[right] == nums[right-1]){
                    right = right - 1;
                }
                //不存在重复的情况下，左指针右移，右指针左移
                left = left + 1;
                right = right - 1;
                //如果出现结果大于0，说明右边的指针的数太大，再向左边移动一位
            } else if (nums[i]+nums[left]+nums[right] > 0){
                right = right - 1;
            } else {
                left = left + 1;
            }
        }
    }
    return rtList;
}
```

- 最接近的三数之和(16)
```
/**
 *首先进行数组排序，时间复杂度 O(nlogn)O(nlogn)
 * 在数组 nums 中，进行遍历，每遍历一个值利用其下标i，形成一个固定值 nums[i]
 * 再使用前指针指向 start = i + 1 处，后指针指向 end = nums.length - 1 处，也就是结尾处
 * 根据 sum = nums[i] + nums[start] + nums[end] 的结果，判断 sum 与目标 target 的距离，
 * 如果更近则更新结果 ans 同时判断 sum 与 target 的大小关系，因为数组有序，
 * 如果 sum > target 则 end--，如果 sum < target 则 start++，如果 sum == target 则说明距离为 0 直接返回结果
 *
 *
 * 总时间复杂度：O(nlogn) + O(n^2) = O(n^2)
 */
private static int getCloseSum(int[] nums, int targetSum){

    //先把数组排序
    Arrays.sort(nums);
    //初始化的最小的和是前面3个数字
    int minSum = nums[0] + nums[1] + nums[2];

    for (int i=0; i < nums.length; i++){

        //还是头尾2个指针同时向中间移动
        int left = i + 1;
        int right = nums.length - 1;

        while (left < right){
            int sum = nums[i] + nums[left] + nums[right];

            //如果和刚好是目标的和一样，直接返回
            if (sum == targetSum){
                return sum;
            }
            //如果和比目标的和大，说明右指针对应的值大了，把右指针向左移动一次
            if (sum > targetSum){
                right = right - 1;
            }
            //如果和比目标的和小，说明左指针对应的值小了，把左指针向右移动一次
            if (sum < targetSum){
                left = left + 1;
            }
            /*
            如果当前和与目标和绝对值小于上一个和与目标和绝对值
            说明当前和更加接近目标和，更新一下目标和
             */
            if (Math.abs(targetSum - sum) < Math.abs(targetSum - minSum)){
                minSum = sum;
            }
        }
    }
    return minSum;
}
```

## top k 问题
- 大数据量的前多少的问题
    - 位图过滤，小顶堆筛选
    
    - 题目：20G的文件，每行存放1个URL，请用1G的内存计算出重复次数最多的前100个URL
    
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
    
## 递归问题
- 链表反转(206)
```
/**
 * 反转链表-递归方式实现
 * @param head
 * @return
 */
private static Node recursiveReverseNode(Node head){
    if (head == null || head.next == null){
        return head;
    }
    Node next = head.next;
    Node reverse = recursiveReverseNode(next);
    next.next = head;
    head.next= null;
    return reverse;
}
```

- 数字各位相加(258)
```
给定一个非负整数 num，反复将各个位上的数字相加，直到结果为一位数。

示例:

输入: 38
输出: 2
解释: 各位相加的过程为：3 + 8 = 11, 1 + 1 = 2。 由于 2 是一位数，所以返回 2。

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
```

## 哈希表相关问题
- 2数之和(1)
```
给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标。

你可以假设每种输入只会对应一个答案。但是，数组中同一个元素不能使用两遍
给定 nums = [2, 7, 11, 15], target = 9

因为 nums[0] + nums[1] = 2 + 7 = 9
所以返回 [0, 1]

/**
 * 时间复杂度 O(N)
 *
 * @param array
 * @param sum
 * @return
 */
private static int[] findSumIndex(int[] array, int sum){
    Map<Integer,Integer> map = new HashMap<>();
    for (int i=0; i< array.length; i++){
        // value + array[i] = sum, 因此做个减法找出 array[i] 的剩下那部分
        int value = sum - array[i];
        if (map.containsKey(value)){
            return new int[]{map.get(value), i};
        }
        map.put(array[i], i);
    }
    return new int[]{0};
}
```

- 字符串中的第一个唯一字符(387)
```
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
```


## 栈问题
- 链表反转(206)
```
/**
 * 反转链表--栈方式实现
 * @param head
 * @return
 */
private static Node reverseNodeStack(Node head){
    Stack<Node> stack = new Stack<>();
    /*
    依次摘除链表的引用关系，把节点放入栈里面
     */
    while (head != null){
        stack.push(head);
        head = head.next;
    }
    if (stack.isEmpty()){
        return null;
    }
    /*
    pop出来的第一个节点是新的头结点
    头节点先保存起来
     */
    Node headNode = stack.pop();
    Node newHead = headNode;
    while (!stack.isEmpty()){
        /*
        把pop出来的节点逐步的挂到上一个节点
        不断的把下一个节点当成头节点
         */
        Node temp = stack.pop();
        headNode.next = temp;
        headNode = headNode.next;
    }
    /*
     * 最后一个节点就是反转之前的头节点，现在变成尾节点
     * 要让尾节点的 next 为空，否则会形成环
     */
    headNode.next = null;
    return newHead;
}
```

## 大小顶堆问题
- 数组中的第K个最大元素(215)
```
/**
 * 常见思路是用排序，然后获取 第 k 个元素，但是这样全部数据都拿来排序
 *
 * 前面第K个最大元素，就只需要处理 前面 k 个元素即可
 *
 * 只要将数组元素全部添加到小顶堆，则堆顶就是 第 k 个大的元素
 *
 * 时间复杂度为 O(Nlogk)
 * @param array
 * @param k
 * @return
 */
private static int findKthNum(int[] array, int k){
    if (array.length == 0){
        return 0;
    }
    PriorityQueue<Integer> minHeap = new PriorityQueue<>();
    for (int i : array) {
        if (minHeap.size()<k){
            minHeap.add(i);
        } else if (i > minHeap.peek()){
            minHeap.poll();
            minHeap.add(i);
        }
    }
    return minHeap.poll();
}
```
- 找出数组的中位数(41)
```
/**
 * 不用排序的方式，而是用大顶堆，小顶堆的思路来实现
 * 不断的遍历数组，把数组的数拿出来判断，放到2个堆里面，放完之后调整堆
 *
 * 大顶堆，用来存放数组较小的数，放在左边
 * 小顶堆，用来存放数组较大的数，放在右边
 *
 * 如果数组长度是奇数，左边的大顶堆中存放 n/2+1 个数，右边的小顶堆存放剩下的数
 * 数组遍历完之后，左边的大顶堆的堆顶就是中位数
 *
 * 如果数组长度是奇数，左边的大顶堆中存放 n/2 个数，右边的小顶堆存放n/2 个数
 * 数组遍历完之后，大顶堆和小顶堆的堆顶的平均数就是 中位数
 *
 * 时间复杂度 O(logN)
 * @param array
 * @return
 */
private static double findMinddleNum(int[] array) {
    //小顶堆，用来存放数组较大的数，放在右边
    PriorityQueue<Integer> minHeap = new PriorityQueue<>();

    //大顶堆，用来存放数组较小的数，放在左边
    PriorityQueue<Integer> maxHeap = new PriorityQueue<>((o1, o2) -> o2 - o1);

    int mindLenght = 0;
    if (array.length % 2 == 0){
        mindLenght = array.length / 2;
    } else {
        mindLenght = array.length / 2 + 1;
    }
    for (int i : array) {
        //默认放到左边的大顶堆 (用来存放数组较小的数)
        if (maxHeap.size() == 0 || i <= maxHeap.peek()){
            maxHeap.add(i);
            //如果大顶堆的个数已经比小顶堆的多
            if (maxHeap.size() > mindLenght){
                //则把大顶堆的堆顶 转移到 小顶堆
                minHeap.add(maxHeap.poll());
            }
        } else {
            minHeap.add(i);
            //如果小顶堆的个数 比大顶堆的个数多
            if (maxHeap.size() < mindLenght){
                //则把小顶堆的堆顶转移到大顶堆
                maxHeap.add(minHeap.poll());
            }
        }
    }
    if (maxHeap.size() == minHeap.size()){
        return (maxHeap.peek() + minHeap.peek()) / 2;
    }
    return maxHeap.peek();
}
```

- 数组出现频率前 k 高的元素(347)
```
给定一个非空的整数数组，返回其中出现频率前 k 高的元素。

输入: nums = [1,1,1,2,2,3], k = 2
输出: [1,2]

private static List<Integer> getTopFreqNum(int[] array, int k){
    if (array.length == 0){
        return new ArrayList<>();
    }
    Map<Integer, Integer> map = new HashMap<>();
    for (int i : array) {
        if (map.containsKey(i)){
            //如果数字在map中重复，将次数加1
            Integer times = map.get(i);
            map.put(i, times + 1);
        } else {
            map.put(i, 1);
        }
    }

    /*
    常见的大小顶堆是根据关键字维护的，但是在这个例子中
    不能用数字本身来比较，而是要用数字出现的次数，就是map 的value 来比较
     */
    PriorityQueue<Integer> minHeap = new PriorityQueue<>(Comparator.comparing(map::get));
    for (Integer key : map.keySet()) {
        if (minHeap.size() < k){
            minHeap.add(key);
        } else if (map.get(key) > map.get(minHeap.peek())){
            /*
            小顶堆的堆顶的频率最小，先构建出一个小顶堆，如果后面的频率比堆顶还大,
            则将新的元素把堆顶替换，堆会重新调整
             */
            minHeap.poll();
            minHeap.add(key);
        }
    }
    List<Integer> res = new ArrayList<>();
    while (!minHeap.isEmpty()) {
        res.add(minHeap.remove());
    }
    return res;

}
```

## 数字问题
- 整数反转(7)
```
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
```
- 二进制求和
```
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
```

## 二分查找
- 排序数组中寻找元素的第一个与最后一个位置(34)
```
/**
 * 时间复杂度：O(logn) ，其中 nn 为数组的长度。二分查找的时间复杂度为 O(logn)，一共会执行两次，因此总时间复杂度为 O(logn)。
 *
 * 空间复杂度：O(1)O(1) 。只需要常数空间存放若干变量
 */
private static int[] findFirstAndLast(int[] nums, int target){
    int[] empty = new int[]{-1, -1};
    if (nums.length == 0){
        return empty;
    }
    int first = findFirst(nums, target);
    if (first == -1){
        return empty;
    }
    int last = findLast(nums, target);
    if (last == -1){
        return empty;
    }
    return new int[]{first, last};
}

/**
二分查找中查找左边的元素
 */
private static int findFirst(int[] nums, int target){
    int left = 0;
    int right = nums.length - 1;
    while (left < right){
        //算出2个位置的中间位置
        int mid = (left + right) / 2;
        if (nums[mid] < target){
            //如果中间位置的数比目标数小，说明目标值在数组的右边部分，左边的位置变为中间位置加1，只寻找右边部分
            left = mid + 1;
        } else if (nums[mid] == target){
            right = mid;
        } else {
            ////如果中间位置的数比目标数大，说明目标值在数组的左边部分，右边的位置变为中间位置减1，只寻找左边部分
            right = mid - 1;
        }
    }
    //left位置停止计算说明左边部分的大小比较查找结束，查找结束后与目标相等，说明目标存在
    if (nums[left] == target){
        return left;
    }
    return -1;
}

/**
 * 二分查找中查找右边的元素
 */
private static int findLast(int[] nums, int target){
    int left = 0;
    int right = nums.length - 1;
    while (left < right){
        //右边的二分查找 的中间位置需要比左二分查找大一位，因为中间位置已经被左二分查找用过一次了
        int mid = (left + right + 1) / 2;
        if (nums[mid] > target){
            right = mid - 1;
        } else if (nums[mid] == target){
            left = mid;
        } else {
            left = mid + 1;
        }
    }
    if (nums[right] == target){
        return right;
    }
    return -1;
}
```
