## hash
### 什么是hash
 1. hash是一个很特殊的数据结构, 主要的特点是可以快速实现寻址,删除和插入.(运用于大数据)
 2. 哈希表也叫散列表,是存储Key-Value映射的集合.对于某一个Key,散列表可以在接近O(1)的时间内进行读写操作
 3. 散列表通过哈希函数实现Key和数组下标的转换,通过开放寻址法和链表法来解决哈希冲突
 
### 哈希表的实现方法
1. 哈希函数
    哈希函数也叫散列函数,可以对任意输入的长度变成固定的长度。 理想的哈希函数对于不同的输入应该产生不同的结构，
    同时散列结果应当具有同一性(输出值尽量均匀)和雪崩效应(微小的输入值变化使得输出值产生巨大的变化)
    实现:
        在不同的语言中,哈希函数的实现方式是不一样的. 
        在HashMap的实现中, 每一个对象都有属于自己的hashcode,这个hashcode是区分不同对象的重要标识. 无论对象自身的类型是什么,它们的hashcode都是一个整型变量.
            转化成数组的下标的方式:
                index = HashCode(Key) % Array.length

2.冲突解决
   当两个不同的输入值对应一个输出值时, 就会产生"碰撞", 这个时候就需要解决冲突
   常见的冲突解决方法有: 开放寻址法, 链表法
    开放寻址法:  (把冲突的数据项放在数组的其他的位置)
        原理: 当一个Key通过哈希函数获得对应的数组下标已被占用时,可以寻找下一个空挡位置 (ThreadLocal所使用的就是开放寻址法)
        方法: 线性探测, 二次探测,再哈希法
            线性探测: 线性地查找空白单元,如果要插入的数据的位置被占用了,那么数组下标一直递增,直到找到空位.
                允许有重复值吗? 不允许,因为有重复的值会要搜索整个表,非常耗时
                聚集 : 当哈希表变得越来越满时,聚集变得越来越严重,会使性能下降. 设计哈希表的关键是确保它不会超过整个数组容量的一半,最多三分之一
                序列: x,x+1,x+2,x+3,以此类推
            二次探测和再哈希法的性能相当,要比线性探测略好
            二次探测的序列: x,x+1,x+4,x+9,x+16,以此类推
                
   链表法: (被应用在Java的集合类HashMap当中)
        HashMap数组的每一个元素不仅是一个Entry对象,还是一个链表的头节点. 每一个Entry对象通过next指针指向它的下一个Entry节点.
     当新来的Entry映射到与之冲突的数组位置时,只需要插入到对应的链表中即可.
 
### 散列表的读写操作
1.写操作(put) 
    在散列表中插入新的键值对 (Entry-{key,value})
2.读操作(get)
    通过给定的Key,在散列表中查找对应的Value
3.扩容(resize)
    a. 为什么要进行扩容?
        当经过多次元素的插入,散列表达到一定饱和度时,Key映射位置发生冲突的概率会逐渐提高.
     大量的元素拥挤在相同的数组下标位置,形成很长的链表, 对后续的插入和查询操作的性能都有很大影响.
    b.影响扩容的因素有两个:
        Capacity: HashMap当前的长度
        LoadFactor: HashMap的负载因子,默认值为0.75f
    c.衡量HashMap是否需要进行扩容的条件如下:
        HashMap.Size >= Capacity x LoadFactor
    d.散列表的扩容操作,具体做了什么事情?
        扩容,创建一个新的Entry空数组,长度是原数组的2倍
        重新Hash,遍历原Entry数组,把所有的Entry数组重新Hash到新数组中. 为什么要重新Hash,因为长度扩大以后,Hash的规则也随之改变
    经过扩容,原本拥挤的散列表重新变得稀疏,可以让原有的Entry也可以重新得到了尽可能均匀分配
        
  
    
    