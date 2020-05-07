##HashMap(JDK1.7)
###类继承关系
   HashMap<K,V> extends AbstractMap<K,V> implements Maps<K,V>, Cloneable, Serializable
###构造函数 (处理是通过传入的初始容量和加载因子,然后初始化Entry数组以及其他的一些变量)
   //初始化容量为16. 容量必须是2的指数倍
   static final int DEFAULT_INITIAL_CAPACITY = 16; 
   
   //最大容量
   static final int MAXIMUM_CAPACITY = 1 << 30;
   
   //加载因子默认是0.75
   static final float DEFAULT_LOAD_FACTOR = 0.75f;
   平衡因子的作用: 当存储的容量超过阈值(存储容量和加载因子的乘积)时,要对哈希表进行扩展操作.
   
   //存储键值对对应的Entry数组
   transient Entry<K,V>[] table;
      //键值对的个数
   transient int size;
   
   //表示一个阈值,当size超过一个threshold就会扩容
   int threshold;
   
   //加载因子
   final float loadFactor;
   
   //map结构修改的次数,累加
   transient int modCount;
   
   //默认阈值
   static final int ALTERNATIVE_HASHING_THRESHOLO_DEFAULT = Integer.MAX_VALUE;
   
###Entry
   构造函数每次都用新的节点指向链表的头结点. 新节点作为链表心的头结点. 也就是说每次插入新的map数据时,就会再一个bucket的最前面(链表头部) 
 
###put()
   1. 通过key的hash值确定table下标
   2. 查找table下标, 如果key存在则更新对应的value
   3. 如果key不存在则调用addEntry()方法 
  当Key为null的时候调用putForNUllKey()函数
  HashMap可以put进去key值为null的数据
  如果HashMap的key值有相同的数据则会覆盖value值,可见HashMap值是唯一的
  
###putForNullKey()
   寻找数组0位置对应的链表key值为null的节点,进而更新Value
   在数组0位置对应的链表上添加一个节点
 
###addEntm,../\\ry()
   如果数据大小已经超过阈值并且数组对应的bucket不为空,则需要扩容
   key为null的时,hash值设为0
 
###HashMap采用一个Entry数组来存储bucket.一个bucket就代表一条hash相同的节点链表

###HashMap内部有三种迭代器 (key迭代器,value迭代器,entry迭代器)
   这三种迭代器继承了HashIterator
   
####在迭代的过程中如果发现modCount!= expectedModCount, 那么就表示有其他的线程对HashMap进行了修改操作,进而就会抛出ConcurrentModificationException异常,所以线程不是安全的

#结论:
   HashMap的Key和value都允许为空
   HashMap是非线程安全的, 如果要求线程安全可以使用Collections.synchronizedMap();
   初始容量和加载因子会影响HashMap的性能
   


###不同的数据结构有着不同的使用场景和优点

###布隆过滤器 ()
   布隆过滤器是一种概率型数据结构,它提供的答案很有可能是正确的. 
    a. 可能会出现错报的情况. 比如: Google可能指出"这个网站已经搜集", 但实际上并没有搜集
    b. 不可能出现漏报的情况. 如果布隆过滤器说这个网站为搜集,那就肯定是没有搜集
    c. 传统的布隆过滤器不支持删除操作. 但是名为Counting Bloom filter的变种可以用来测试元素计数是否绝对小于某个阈值,它支持元素删除
    优点: 
        占用的存储空间很少
        高效的插入和查询
    缺点:
        随着数据的增加,误判率随之增加; 
        无法做到删除数据;
        只能判断数据是否一定不存在, 而无法判断数据是否一定存在;
        
   数据结构: 布隆过滤器是一个bit向量或者说bit数组,可以解决缓存穿透的问题
   
   布隆过滤器添加元素
        将要添加的元素给k个哈希函数
        得到对应于位数组上的k个位置
        将这k位置设为1
    
   布隆过滤器查询元素
        将要查询的元素给k个哈希函数
        得到对应于位数组上的k个位置
        如果k位置有一个为0,则肯定不在集合中
        如果k位置全部为1,则可能在集合中