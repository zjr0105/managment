##缓存类别
####本地缓存
常用的本地缓存有ehcache,guava cache, Caffeine
1. ehcache: 是一个纯java的进程内缓存框架,具有快速,精干等特点, 是Hibernate中m默认 CacheProvider
    #####特性: 
        a. 快速, 简单, 多种缓存
        b. 缓存数据有两级: 内存和磁盘,无需担心容量的问题
        c. 缓存数据会在虚拟机重启的过程中写入磁盘
        d. 可以通过RMI,可插入API等方式进行分布式缓存
        e. 具有缓存和缓存管理器的侦听接口
        f. 支持多缓存管理器实例,以及一个实例的多个缓存区域
        g. 提供Hibernate的缓存实现

2. guavaCache: 是在内存中缓存数据,相比较数据库或redis存储,访问内存中的数据会更加高效
    #####下面的几种情况可以考虑使用Guava Cache
        a. 愿意消耗一些内存空间来提升速度
        b. 预料到某些键会被多次查询
        c. 缓存中存放的数据总量不会超出内存容量
    #####特性
        a.控制数据量大小
        b.控制数据生命周期
    ####注意: 凡是可以使用GuavaCache的地方都可以使用RedisCache, 但是使用RedisCache的地方不一定可以使用GuavaCache
       
3. Caffeine: 高性能缓存库. 提供的内存缓存使用参考Google guava的API. 基于Google Guava Cache设计经验上改进的成果
    #####可以通过建造者模式灵活的组合一下特性
        a. 通过异步自动加载实体到缓存中
        b. 基于大小的回收策略
        c. 基于时间的回收策略
        d. 自动刷新
        e. key自动封装虚引用, value自动封装弱引用或软引用
        f. 实体过期或被删除的通知
        g. 写入外部资源
        h. 统计累计访问缓存
        
    #####填充策略
        Caffeine提供了3种加载策略: 手动加载, 同步加载, 异步加载
        
#### 分布式缓存
常用的分布式缓存有Redis, Memcached
1. Redis 支持服务端的数据操作,拥有更丰富的数据结构与操作api,是单线程模型, 支持原生集群模式
    #####redis的线程模型
        单线程模型
            文件事件处理器 File Event Handler
                redis基于reactor模式开发了网络事件处理器,这个处理器叫做文件事件处理器.文件事件处理器是单线程的,redis才叫做单线程模型, 采用IO多路复用机制同时监听多个socket
                
    #####为什么redis是单线程但是还可以支撑高并发?
        a. 纯内存操作
        b. 核心是基于非阻塞的IO多路复用机制
        c. 单线程反而避免了多线程的频繁上下文切换问题
    
    #####redis的数据类型
        a. string:  基本类型, 可以做简单的kv缓存
        b. hash: 类似于map的一种结构
        c. list: 有序列表
        d. set: 无序集合, 自动去重
        e. sorted set: 排序的set, 去重但是可以排序
        
     #####redis的过期策略
        a. 定期删除
            redis默认每隔100ms就随机抽取一些设置了过期时间的key,检查其是否过期,如果过期就删除
        b. 惰性删除
            在获取某个key的时候,redis会检查一下,这个key如果设置了过期时间那么是否过期了?如果过期了此时就会删除,不会给你返回任何东西
      ###一般是配合定期删除和惰性删除一起使用
      
      #####redis的哨兵原理
        1.哨兵(sentinal)主要功能
            a. 集群监控,负责监控redis master和slave进程是否正常工作
            b. 消息通知, 如果某个redis实例有故障,那么哨兵负责发送消息作为报警通知给管理员
            c. 故障转移, 如果master node挂掉了, 会自动转移到slave node上
            d. 配置中心, 如果故障转移发生了,通知客户端新的master地址
            哨兵也是分布式的, 作为一个哨兵集群去运行, 互相协同工作
            
        2.哨兵的核心
            a. 哨兵至少需要3个实例, 来保证自己的健壮性
            b. 哨兵 + redis主从的部署架构, 是不会保证数据零丢失的, 只能保证redis集群的高可用性
            c. 对于哨兵 + redis主从的部署架构, 尽量在测试环境和生产环境, 都进行重组的测试和演练
            
      #####在集群模式下,redis的key是如何寻址的
            a. hash取模寻址
            b. 一致性hash算法(自动缓存迁移) + 虚拟节点(自动负载均衡)
            c. redis cluster的hash slot算法
                redis cluster有固定的16384个hash slot
           
      #####redis的雪崩和redis的穿透
            a. redis的雪崩
                某一瞬间大量的键集体过期或由于机器宕机后重启导致大量内存中的数据丢失, 导致某一刻的大量数据直接打到数据库层
                解决方法: 可以使用本地ehcache缓存作为二级缓存 + hystrix做限流和降级处理
            b. redis穿透(恶意攻击)
                非法用户大量请求缓存和db中不存在的数据.比如根据订单号查询订单的时候,查询条件全是负数,这样一来,大量的请求就会直接穿过redis直接打到数据库,这样就造成数据库压力激增
                解决方法: 将不存在的键存入redis值设为null或者某个标记符号, 并设置过期时间, 这样一来在过期时间内再次请求就会直接返回
      
2. Memcached 可以使用多线程模型,不支持原生的集群模式,需要将数据取回到客户端修改后再set回去
    #####对于key-value信息,最好不要超过1m的大小;同时信息长度最好相对是比较均衡稳定的, 这样能够保障最大限度的使用内存; 同时, memcached采用的LRU清理策略,合理甚至过期时间,提高命中率

#### 本地集群缓存
  集群下不适合用本地缓存,会导致数据不一致的问题
  
####其他缓存
1. hibernate的缓存机制

    一级缓存: session级别的缓存, 也称为线程级别的缓存, 只在session的范围内有效
    
    二级缓存: sessionFactory级别的缓存, 也称为进程级别的缓存, 在所有的session中都有效
    
   一般需要配置第三方的缓存支持, 比如Ehcache
   
   查询缓存: 依赖于二级缓存, 在HQL的查询语句中生效
   
2. 浏览器缓存

    对于web应用来说它是提升页面性能同时减少服务器压力的利器
    
    #####优点: 使用有效缓存时, 没有网络消耗, 速度快; 即使有网络消耗, 但对失效缓存使用304响应做到网络消耗
    #####缺点: 仅提升一个用户的体验
    
    #####浏览器缓存类型
        a. 强缓存: 不会向服务器发送请求, 直接从缓存中读取资源
        b. 协商缓存: 向服务器发送请求, 服务器会根据这个请求的request header的一些参数来判断是否民众协商缓存, 
        如果命中, 则返回304状态码并带上新的response header通知浏览器从缓存中读取资源
        
        两者的共同点: 都是从客户端缓存中读取资源
        区别: 强缓存不会发请求, 协商缓存会发请求
        
3. nginx缓存
    
    #####优点: 提升所有用户体验, 相比浏览器缓存, 有效降低上游服务的负载, 通过304响应减少nginx与上游服务间的流量消耗
    #####缺点: 用户仍然保持网络消耗
    
4. CND (网络延迟)
    #####优点: 减少了用户的访问延时, 减少了源服务器的负载
    #####不足: 当源服务器更新后, 如果CDN节点上缓存数据还未过期, 用户访问到的依旧是过期的缓存资源, 会导致用户最终访问出现偏差. 因此开发者需要手动刷新相关资源, 使CDN缓存保持为最新的状态
    
    #####CND缓存刷新
        a. 主动更新: 同名资源在源服务器更新之后, 开发者手动刷新文件
        b. 被动刷新: 等文件在CDN节点的缓存过期之后, 节点回源拉取源服务器上最新的文件
         
####spring cache
Spring的缓存技术具备相当的灵活性,不仅能够使用SpEL(Spring Expression Language)来定义缓存的key和各种condition, 还提供开箱即用的缓存临时存储方案,也支持和主流的专业缓存集成
#####特点:
   a. 少量的配置annotation注释即可使用既有代码支持缓存;
   
   b. 支持开箱即用, 不用安装和部署额外的第三方组件即可使用缓存;
   
   c. 支持Spring Expression Language(SpEL), 能使用对象的任何属性或者方法来定义缓存的key和使用规则条件
   
   d. 支持自定义key和自定义缓存管理者, 具有相当的灵活性和可扩展性
   
   和Spring的事务管理类似, Spring Cache的关键原理是Spring AOP, 通过Spring AOP实现了在方法调用前,调用后获取方法的入参和返回值, 进而实现了缓存的逻辑

1. 缓存命中率
    
    a. 命中率 = 从缓存中读取次数/(总数取次数[从缓存中读取次数 + 从慢速设备上读取的次数])
    
    b. Miss率 = 没有从缓存中读取的次数/(总数取次数[从缓存中读取的次数 + 从慢速设备上读取的次数])
  
2. TTL(Time To Live)
存活期,即从缓存中创建时间点开始直到它到期的一个时间段 (不管这个时间段内有没有访问都将过期)

3. TTI(Time To Idle)
空闲期, 即一个数据多久没被访问将从缓存中移除时间

4. Eviction policy
移除策略, 如果缓存满了, 从缓存中移除数据的策略
    #####常见:
        a. FIFO(First In First Out): 先进先出算法, 即先放入缓存的先被移除, 底层实现是队列
        b. LRU(Least Recently Used): 最久未使用算法, 使用时间距离现在最久的那个被移除, 底层实现是链表 (使用时间差异来决定)
        c. LFU(Least Frequently Used): 最近最少使用算法, 一定时间段内使用次数(频率)最少的那个被移除 (使用次数的差异来决定)
        d. 带过期时间:
                ① volatile-lru: 从已设置过期时间的数据集中淘汰[最近最少使用]的数据
                ② volatile-ttl: 从已设置过期时间的数据集中淘汰[将要过期]的数据
                ③ volatile-random: 从已设置过期时间的数据集中淘汰[随机]的数据
                ④ allKeys-lru
                ⑤ allKeys-random
                
    #####LinkedHashMap
        a. LinkedHashMap
            1. 是HashMap的子类, 大部分实现与HashMap相同, 两者最大的区别在于, HashMap的对哈希表进行迭代是无序的, LinkedHashMap对哈希表迭代是有序的
            2. LinkedHashMap默认的规则是,迭代输出的结果保持和插入key-value pair的顺序一致
            3. LinkedHashMap除了像HashMap一样用数组,单链表和红黑树来组织数据外, 还额外维护了一个双向链表,每次插入键值对, 除了将其插入到哈希表对应的位置上, 还要将其插入到双向循环链表的尾部
            
        ````java
        //继承HashMap的Node内部类,Node是一个单链表结构
        static class entry<k,v> extends HashMap.Node<k,v> {
            //添加前继引用和后继引用, 是一个双向链表的节点
            Entry<k,v> before,after;
            Entry(int hash, K key, V value, Node<k,v> next) {
                super(hash,key,value,next);
            }
        } 
        
        b. LinkedHashMap定义了三个重要的字段
            
            //双链表
            transient LinkedHashMap.Entry<K,V> head;
                        
            //双链表的尾节点
            transient LinkedHashMap.Entry<K,V> tail;
            
            accessOrder为false表示按插入顺序迭代
            accessOrder为true表示按访问顺序迭代,则将最近访问的节点调整至双向队列的队尾, 这也就保证了按照访问顺序迭代时Entry的有序性. 
          remove方法就是当前元素从双向链表中移除, addBefore方法再将当前元素插入到链表的头部去,这样最近读到的元素,在迭代的时候是优先被迭代出来的!
            
           ```` java [afterNodeAccess(e)]方法
           void afterNodeAccess(Node<k,v> e) {  //将节点移到最后 
           
                //用last表示插入e前的尾节点
                LinkedHashMap.Entry<k,v> last;
                // 如果访问序,且当前节点并不是尾节点
                //将该节点设置为双向链表的尾部
                if (accessOrder && (last = tail) != e) {
                    //p: 当前节点
                    //b: 前一个节点
                    //a: 后一个节点
                    //结构为 b <=> p <=> a
                    LinkedHashMap.Entry<k,v> p = (LinkedHashMap.Entry<k,v>)e,b = p.before, a = p.after;
                    //把p节点从双向链表中移除
                    // 结构变成: b <=> p <- a
                    p.after = null;
                    
                    //如果当前节点p本身是头节点, 那么头节点要改成 a
                    if (b == null) {
                        head = a;
                    } else {
                        //如果p不是头尾节点, 把前后节点连接, 变成: b -> a
                        b.after = a;
                    } 
                    //如果a不为null, 则就和b连接, 变成: b <- a
                    if (a != null) {
                        a.before = b;
                    } else {
                        //如果a为空, 说明p是尾节点, b就是它的前一个节点, 符合last的定义
                        last = b;
                    }
                    
                    //如果这是个空链表, p改成头结点
                    if (last == null) {
                        head = p;
                    } else {
                        //否则把p插入到链表尾部
                        p.before = last;
                        last.after = p;
                    }
                    
                    //尾节点等于p
                    tail = p;
                    ++modCount;
           }
           
           [get(key)]方法
           public V get(Object key) {
                Node<K,V> e;
                if ((e == getNode(hash(key), key)) ==  null) {
                    return null;                    
                }
                //accessOder为true,则调用afterNodeAccess()方法把访问的节点移到双向链表的末尾
                if (accessOrder) {
                    afterNodeAccess(e);
                }
                return e.value;
           }
             
           [containsValue(value)] 方法
           public boolean containsValue(Object value) {
                for (LinkedHashMap.Entry<K,V> e = head; e != null; e = e.after) {
                    V v = e.value;
                    if (v == value || (value != null && value.equals(v))) {
                        return true;
                    }
                }
                retrun false;
           }
           
    #####何时被移除
       a. 访问key A时, 移除举例最远的过期key
       b. 定时任务
       c. CPU空闲时
   
    #####Cache API提供的默认实现
       a. ConcurrentMapCache: 使用ConcurrentHashMap实现的cache
       b. GuavaCache: 对Google包中的cache进行的Wrapper, 需要Google Guava12.0或更高的版本
       c. EhCacheCache: 使用EhCache实现的
       d. JCacheCache
   
    #####Spring还提供了CacheManager抽象,用于缓存的管理, 还提供了CompositeCacheManager用于组合CacheManager, 即可以从多个CacheManager中轮询得到相应的Cache
    #####除了GuavaCacheManager之外, 其他的cache都支持Spring事务, 如果事务回滚了, Cache的数据也会移除掉
    #####Spring不进行Cache的缓存策略的维护, 这些都是由底层Cache自己实现, Spring只是提供了一个Wrapper, 提供一套对外一直的API
    #####Cache注解
       a. 启用Cache注解(使用@EnableCaching启用Cache注解支持)
       b. @CachePut(方法支持缓存功能,在执行前不会去检查缓存中是否已经存在之前执行的结果), 
       c. @CacheEvict(从缓存中移除相对应数据), 
       d. @Cacheable(可标记在类或方法上), 
       e. @Caching(组合多个注解标签), 
       f. 自定义缓存注解

####缓存穿透(Cache penetration)
查询一条不存在的数据,也就是缓存和数据库都查询不到这条数据,但是请求每次都会打到数据库上面去,这种现象我们称为缓存穿透
#####穿透带来的问题
   a. 如果查询请求中不存在大量数据, 那么这些大量请求将会落入数据库, 数据库压力将急剧增加,这可能会导致系统崩溃
#####如何避免
   a. 缓存空数据: 缓存穿透的原因是缓存中没有用于存储这些空数据的密钥,导致所有这些请求都到数据库.
 将具有空数据查询结果的密钥存储在缓存中,当再次发生查询请求时,缓存直接返回null而不是查数据库
 
   b.布隆过滤器(BloomFilter) : 它需要在缓存之前添加一个屏障, 它存储当前数据库中存在的所有key. 当业务系统有查询请求时, 首先转到BloomFilter以检查key是否存在
   
   *如果它不存在, 则表示数据库中不存在数据,因此不应检查缓存, 并直接返回null
   
   *如果存在, 继续执行后续过程, 首先查询缓存, 如果没有缓存,则查询数据库
 
####缓存击穿(Cache breakdown) 又称为[热点数据集失效(Hotspot data set is invalid)]
1. 通常会为缓存设置一个到期时间. 到期之后,数据库将被缓存直接删除, 从而在一定程度上保证数据的实时性, 但是对于一些请求非常高的热点数据,一旦失效时间过去,大量请求落在数据库上,这会导致数据库崩溃   
    #####如何避免
       *互斥(Mutex): 可以避免因热点数据失败而导致数据库损坏的问题
       *设置不同的到期时间: 将这些数据存储在缓存中, 我们可以错开缓存到期时间
  
####缓存雪崩(Cache avalanche)
1. 如果由于某种原因缓存出现故障,那么最初被缓存阻止的大量查询请求将像疯狗一样涌向数据库,此时,如果数据库无法承受,则它就会崩溃,这就是缓存雪崩
    #####如何避免
       * 缓存集群确保高可用: 在发生雪崩之前,采取预防措施以防止雪崩的发生
       * 熔断器: Hystrix是一种开源的"抗雪崩工具", 降级(downgrade)和限流(current limit)来减少雪崩后的损耗
   #####Hystrix
        a. 是一个试用命令模式的java类库,每个服务处理请求都有自己的处理器;
        b. 处理器记录当前服务的请求失败率;
        c. 一旦发现当前服务的故障率达到预设值, 将拒绝所有后续服务请求并返回磨人结果,这就是所谓的保险丝,即熔断器;
        d. Hystrix将释放部分服务请求,并在此计算其请求失败率;
        e. 如果请求失败率此时满足预设值,则流量限制开关完全打开;
        f. 如果请求失败率仍然很高,则拒绝所有服务请求,是为限流;
        g. Hystrix将默认结果直接返回给那些被拒绝的请求,是为降级;
        


####Redis为什么这么快
  a. 采用了多路复用的io阻塞机制
  b. 数据结构简单,操作节省时间
  c. 运行在内存中,自然速度快

###后续补充[缓存与数据库双写性和一致性哈希算法]
        



    
       

  
  
       
        
        



