# Redis

## 0. 技术的分类

- 解决功能性问题

  Java, Jsp, RDBMS（关系型数据库管理系统）, Tomcat, HTML, Linux, Jdbc, SVN

- 解决扩展性问题

  Struts, Spring, SpringMVC, Hibernate, Mybatis

- 解决性能问题

  NoSQL, Java Thread, Hadoop, Nginx, MQ, ElasticSearch

- 解决 Session 存储问题

  - 方案一：存在 cookie 里

    不安全

    网络负担效率低

  - 方案二：存在文件服务器或者数据库服务器

    大量 IO 效率问题

  - 方案三：session 复制

    session 数据冗余

    节点越多，浪费越大

  - 方案四：缓存数据库

    完全在内存中，速度快数据结构简单

    减少 IO 的读操作

    通过破坏一定的业务逻辑（关系型数据库）来换取性能

    - 水平切分
    - 垂直切分
    - 读写分离

    列式数据库 HD

    文档数据库 MongoDB

## 1. NoSQL 数据库简介

### 1.1 NoSQL 数据库概述

- NoSQL -> Not only SQL 非关系型数据库

- 不依赖业务逻辑方式存储，而以简单的 key-value 模式存储

- 不遵循 SQL 标准

- 不支持 ACID

- 性能高于 SQL

### 1.2 NoSQL 适用场景

- 对数据高并发的读写
- 海量数据读写
- 对高可拓展性的数据

### 1.3 NoSQL 不适用的场景

- 需要事务支持
- 基于 sql 的结构化查询存储，处理复杂的关系，需要即席查询（条件查询）

### 1.4 缓存数据库

- Memcached
  - 很早出现的 NoSQL 数据库
  - 数据都在内存中，一般不持久化
  - 支持简单的 key-value 模式
  - 一般是作为 __缓存数据库__ 辅助持久化数据库
- Redis
  - 几乎覆盖了 Memcached 的绝大部分功能
  - 数据都在内存中，支持持久化，主要用做备份恢复
  - 除了支持简单的 key-value 模式，还支持多种数据结构的存储，比如 list, set, hash, zset 等
  - 一般是作为 __缓存数据库__ 辅助持久化数据库
- Memcached 与 Redis 区别
  - Memcached 一般不持久化，Redis 可以持久化
  - Memcached 的值 key-value 必须是字符串，Redis 可以使用 5 种 数据结构

### 1.5 文档数据库

- MongoDB - 一个环形队列，支持 FIFO
  - 高性能，开源，模式自由 (schema free) 的 __文档型数据库__ 
  - 数据都在内存中，如果内存不足，将首个从队列移出
  - 虽然是 key-value 模式，但是对 value (尤其是 json) 提供把不常用的数据保存到硬盘，丰富了查询功能
  - 支持二进制数据及大对象
  - 可以根据数据的特点替代 RDBMS，成为独立的数据库，或者配合 RDBMS

### 1.6 行/列式数据库

每次查询一行/列 OLTP 事务型处理/OLAP 分析型处理

- HBase
  - HBase 是 Hadoop 项目中的数据库
  - 用于对大量的数据进行随机，实时的读写操作的场景中
  - 目标是处理数据量非常庞大的表
  - 可以用普通的计算机处理超过 10 亿行数据，处理有数百万列元素的数据表
- Cassandra
  - Apache Cassandra 是一款免费开源的 NoSQL 数据库
  - 目的是管理由大量商用服务器构建起来的庞大集群上的 __海量数据集 (PB 1PB=1024TB)__ 
  - 对写入及读取操作进行规模调整
  - 不强调主集群的设计思路 -> 简化各集群的创建与扩展流程

### 1.7 图关系型数据库

- Neo4j
  - 社会关系
  - 公共交通网络
  - 地图及网络拓扑

## 2. Redis 简介安装

### 1.1 Redis 简介

- 开源的 key-value 存储系统
- value 包括字符串，链表，集合，有序集合，哈希
- 这些类型都支持 push/pop add/remove，及取交集并集和差集等，操作都是原子性的
- 支持各种不同方式的排序
- 数据都是缓存在内存中（与 memcached 一样）
- 会周期性地把更新地数据写入磁盘或者把修改操作写入追加的记录文件
- 实现了 master-slave 主从同步

### 1.2 应用场景

- 配合关系型数据库做高速缓存
  - 高频次，热门访问的数据，降低数据库 IO
  - 分布式架构，做 session 共享
- 多样数据结构储存特定的数据
  - 最新 N 个数据 -> List
  - 排行榜，Top N -> zset (sorted set 存入顺序)
  - 时效性的数据（手机验证码） -> Expire 过期
  - 计数器，秒杀 -> 原子性，自增方法 INCR，DECR
  - 去除大量数据种的重复数据 -> Set
  - 构建队列 -> List
  - 发布订阅消息系统 -> pub/sub 模式

### 1.3 Redis 相关知识

- 端口 6379[num] Alessia Merz

    默认 16 个数据库，类似数组下标从 0 开始，初始默认用 0 号库，使用 select \<num> 来切换库

    `dbsize` 查看当前库 key 的数量

    `flushdb` 清空当前库

    `flushall` 通杀全部库

- Redis 单线程 + 多路 IO 复用技术（select poll epoll 调用）

## 3. Redis 数据类型

### 3.1 Redis 键（key）

- `keys *` 查看当前库所有 key

- `exists key` 判断某个 key 是否存在

- `type key` 查看 key 的类型

- `del key` 删除指定的 key 数据

- `unlink key` 根据 value 选择非阻塞删除

    仅将 keys 从 keyspace 元数据中删除，真正的删除会在后续异步操作

- `expire key 10` 为给定的 key 设置过期时间

- `ttl key` 查看还有多少秒过期，-1 为永不过期，-2 表示已经过期

### 3.2 String 

String 是 Redis 的最基本数据类型，与 Memcached 一模一样的类型，一个 key 对应一个 value

String 类型是二进制类型安全的，可以包含任何数据，比如图片或者序列化对象，string 作为 value 最多是 512M

- 常用命令

    > set \<key> \<value>
    >
    > ​	NX：当数据库中 key 不存在时，可以将 key-value 添加到数据库
    >
    > ​	XX：当数据库中 key 存在时，可以将 key-value 添加到数据库，与 NX 互斥
    >
    > 
    >
    > ​	EX seconds：key 的超时秒数
    >
    > ​	PX milleseconds：key 的超时毫秒数，与 EX 互斥
    >
    > 
    >
    > get \<key>
    >
    > append \<key> \<value>
    >
    > strlen \<key>
    >
    > setnx \<key> \<value> 只有在 key 不存在时，设置 key 的值
    >
    > 
    >
    > incr \<key>
    >
    > decr \<key>
    >
    > 
    >
    > incrby/decrby \<key> \<diff>
    >
    > 
    >
    > mset \<key1> \<value1> \<key1> \<value2> ...
    >
    > 同时设置一个或者多个 key-value
    >
    > mget \<key1> \<key2> ...
    >
    > 同时获取一个或者多个 value
    >
    > msetnx \<key1> \<value1> \<key2> \<value2> ...
    >
    > 同时设置一个或者多个 key-value，当且仅当 key 都不存在（原子性，一个失败，则全部失败）
    >
    > 
    >
    > getrange \<key> \<begin> \<end>
    >
    > 获得值得范围，类似 substring, str, begin, end + 1
    >
    > 
    >
    > setrange \<key> \<begin> \<value>
    >
    > 用 value 覆写 key 所存储得字符串的值，从 begin 开始（索引从 0 开始）
    >
    > 
    >
    > setex \<key> \<expire_time> \<value>
    >
    > 设置键值对以及过期时间，单位：秒
    >
    > getset \<key> \<value>
    >
    > 以新换旧，设置新值获取旧值

String 的数据结构为简单动态字符串（SDS：Simple Dynamic String），是可以修改的字符串，内部结构实现类似于 Java 的 ArrayList，采用预分配冗余空间的方式来减少内存的频繁分配

String 内部为当前字符串实际分配的空间 capacity 一般要高于实际字符串长度 len，当字符串长度小于 1M 时，扩容都是加倍现有的空间，如果超过 1M，扩容一次只会多扩 1M 的空间，字符串最大长度是 512M

### 3.3 List

单键多值

列表是简单字符串列表，按照插入顺序排序

底层是一个双向链表

- 常用命令

    > lpush/rpush <key\> <value1\> <value2\> <value3\> ...
    >
    > 从左边/右边插入一个或者多个值
    >
    > 从左边放是倒序
    >
    > 
    >
    > lpop/rpop <key\>
    >
    > 从左边/右边弹出一个值，当列表为空的时候，自动删除 key
    >
    > 
    >
    > rpoplpush <key1\> <key2\> 
    >
    > 从 <key1\> 列表右边弹出一个值放入 <key2\> 左边
    >
    > 
    >
    > lrange <key\> <start\> <stop\>
    >
    > 按照索引获取下标元素（从左到右）
    >
    > 
    >
    > lindex <key\> <index\>
    >
    > 按照索引下标获取元素（从左到右）
    >
    > llen <key\> 获得列表长度
    >
    > 
    >
    > linsert <key\> before/after <value\> <new_value\>
    >
    > 在第一个 value 前后插入 new_value
    >
    > 
    >
    > lrem <key\> <n\> <value\> 
    >
    > 从左边删除 n 个 value
    >
    > 
    >
    > lset <key\> <index\> <value\> 
    >
    > 将列表 key 下标为 index 的值替换成 value

List 的数据结构为快速链表 quickList

首先在列表元素较少的情况下会使用一块连续的内存存储，这个结构是压缩列表 ziplist

它将所有的元素紧挨着一起存储，分配的是一块连续的内存

当数据量比较多的时候才会改成 quicklist

因为普通链表需要的附加指针空间太大，会比较浪费空间

quicklist 是将多个 ziplist 使用双向指针串起来使用，满足了快速插入删除的性能，也不会有空间冗余

### 3.4 Set

提供的功能与 list 类似，是一个列表功能，set 是可以自动排重的

Set 是 string 类型的无序集合，底层其实是一个 value 为 null  的 hash 表，所以添加，查找，删除的复杂度都是 O(1)

- 常用命令

    >sadd <key\> <value1\> <value2\> ...
    >
    >将一个或者多个成员元素加入到集合 key 中，已经存在的成员元素将被忽略
    >
    >
    >
    >smembers <key\> 
    >
    >取出 key 对应的集合的所有值
    >
    >
    >
    >sismember <key\> <value\> 
    >
    >判断 key f的集合是否为含有该 value 的值：结果 1，0
    >
    >
    >
    >scard <key\> 
    >
    >返回该集合的元素个数
    >
    >
    >
    >srem <key\> <value1\> <value2\> ...
    >
    >删除集合中的某个元素
    >
    >
    >
    >spop <key\> 
    >
    >随机从 set 中弹出一个值
    >
    >
    >
    >srandmember <key\> <n\> 
    >
    >随机从集合中取出 n 个值，不会从集合中删除
    >
    >
    >
    >smove <source\> <destination\> <value\>
    >
    >把集合中一个值从一个 set 移动到另一个 set
    >
    >
    >
    >sinter <key1\> <key2\> 
    >
    >返回两个集合的交集元素
    >
    >
    >
    >sunion <key1\> <key2\> 
    >
    >返回两个集合的并集元素
    >
    >
    >
    >sdiff <key1\> <key2\> 
    >
    >返回两个集合的差集元素

Set 的数据结构是 dict 字典，用 hash 表实现

### 3.5 Hash

Redis 中 Hash 是一个键值对集合

是一个 string 类型的 field 和 value 的映射表，适合用于存储对象，类似于 Java 中的 Map<String, Object>

- 常用命令

    > hset <key\> <field\> <value\> 
    >
    > 给 key 集合中的 field 键赋值 value
    >
    > 
    >
    > hget <key\> <field\> 
    >
    > 从 key 集合 field 中取出 value
    >
    > 
    >
    > hmset <key\> <field1\> <value1\> <field2\> <value2\> ...
    >
    > 批量设置 hash 的值
    >
    > 
    >
    > hexists <key\> <field\> 
    >
    > 查看 hash 表 key 中，给定域 field 是否存在
    >
    > 
    >
    > hkeys <key\> 
    >
    > 列出该 key 对应的 hash 集合的所有 field
    >
    > 
    >
    > hvals <key\> 
    >
    > 列出该 key 对应的 hash 集合的所有 value
    >
    > 
    >
    > hincriby <key\> <field\> <increment\> 
    >
    > 为 hash 表 key 中的 field 的值增量 +/-
    >
    > 
    >
    > hsetnx <key\> <field\> <value\> 将 hash 表 key 中的域 field 的值设置为 value，当且仅当 field 不存在

Hash 类型对应的数据结构是两种：ziplist 和 hashtable

当 field-value 长度较短且个数较少时，用 ziplist，否则使用 hashtable

### 3.6 Zset（Sorted Set）

zset 与 set 非常相似，是一个没有重复元素的字符串集合

不同之处是 zset 的每个成员都关联了一个 score，这个 score 被用来按照从最低分到最高分的方式排序集合中的成员，集合成员是唯一的，但是评分是可以重复的

因为元素是有序的，所以可以快速根据 score 或者 position 来获取一个范围的元素，访问 zset 的中间元素的时间开销也是比较低的

- 常用命令

    > zadd <key\> <score1\> <value1\> <score2\> <value2\> ...
    >
    > 将一个或者多个 member 的 value 及其 score 加入到 key 中
    >
    > 
    >
    > zrange <key\> <start\> <stop\> [WITHSCORES]
    >
    > 返回有序集 key 中，下标在 <start\> <stop\> 之间的元素
    >
    > 带 WITHSCORES，可以让分数一起和值返回到结果集
    >
    > 
    >
    > zrangebyscore <key\> <min\> <max\> [WITHSCORES] [LIMIT offset count]
    >
    > 返回有序集 key 中所有 score 介于 min 和 max 之间（闭区间）的元素，有序集按 score 值递增次序排列
    >
    > 
    >
    > zrevrangebyscore <key\> <max\> <min\> [WITHSCORES] [LIMIT offset count]
    >
    > 上面命令的倒序
    >
    > 
    >
    > zincrby <key\> <increment\> <value\> 
    >
    > 为元素的 score 加上增量
    >
    > 
    >
    > zrem <key\> <value\> 
    >
    > 删除该集合下指定的元素
    >
    > 
    >
    > zcount <key\> <min\> <max\> 
    >
    > 统计集合在分区 min 和 max 之间的元素
    >
    > 
    >
    > zrank <key\> <value\> 返回该值在集合中的排名，从 0 开始

zset 一方面等价于 Java 的 Map<String, Double> 来存 score，另一方面，又类似 TreeSet，内部元素会按照 score 排序，可以得到每个元素的名次，还可以通过 score 范围来获取元素列表

zset 底层用了两种数据结构

- hash，用于关联 value 和 score，保障 value 的唯一性，可以通过 value 找到 score
- 跳表，给 value 排序，根据 score 范围获取元素

### 3.7 Bitmap

bitmap 是对存储进行位操作

- bitmap 本身不是一种数据类型，实际上是字符串，但是它可以对字符串进行一个位操作

- bitmap 单独提供了一套命令，bitmap 类似一个 bit 数组，数组下标在 bitmap 中叫偏移量

- 常用命令

    > setbit <key\> <offset\> <value\> 
    >
    > 设置 bitmap 中某个偏移量的值 0/1
    >
    > 
    >
    > getbit <key\> <offset\> 
    >
    > 获取 bitmap 中某个偏移量的值
    >
    > 
    >
    > bitcount <key\> [start end]
    >
    > 统计字符串被设置为 1 的 bit 数
    >
    > 
    >
    > bitop and(or/and/xor) <destkey\> [key ......]
    >
    > 复合操作，它可以做多个 bitmap 的交集，并集，非，异或操作，并存在保存在 destkey 中

- bitmap 与 set 对比

    如果存储的是很多 0 （空的内容）bitmap 会比较浪费，但是存比较多的时候 bitmap 的空间利用效率会比较高

### 3.8 HyperLogLog

用于统计 UV（Unique Visitor）独立访客，独立 IP 数，搜索记录数等不重复元素的个数问题（基数问题）

优点是：在输入元素数量或者体积非常大的时候，计算基数所需的空间是固定的

每个 HyperLogLog 键只需要 12KB 内存，可以计算接近 2^64 个不同元素的基数

因为 HyperLogLog 只会根据输入元素计算基数，不会储存输入元素的本身，所以不能像集合一样返回各个元素

- 常用命令

    >pfadd <key\> <element\> [element ...]
    >
    >添加指定元素到 HyperLogLog 中
    >
    >
    >
    >pfcount <key\> [key ...]
    >
    >计算 HLL 的近似基数，可以计算多个 HLL
    >
    >
    >
    >pfmerge <destkey\> <sourcekey\> [sourcekey ...]
    >
    >将一个或者多个 HLL 合并后的结果存在另一个 HLL 中

### 3.9 Geospatial

Redis 3.2 中增加了对 GEO 的支持，是元素的二维坐标，redis 基于该类型，提供经纬度设置，查询，范围查询，距离查询，经纬度 Hash 等操作

- 常用命令

    > geoadd <key\> <longitude\> <latitude\> <member\> [longitude latitude member ...]
    >
    > 添加一个 member 的 longitude 经度和 latitude 纬度到 key 中
    >
    > 
    >
    > geopos <key\> <member\> [member ...]
    >
    > 获取坐标值
    >
    > 
    >
    > geodist <key\> <member1\> <member2\> [m|km|ft|mi] 
    >
    > 获取两个位置之间的直线距离
    >
    > 
    >
    > georadius <key\> <longitude\> <latitude\> <radius\> m|km|ft|mi
    >
    > 以给定的经纬度为中心，找出某一半径内的元素

## 4. Redis 配置文件

- 使用配置文件

    ```conf
    # ./redis-server /path/to/redis.conf
    ```

- 单位设置方式

    ```conf
    # 1k => 1000 bytes
    # 1kb => 1024 bytes
    # 1m => 1000000 bytes
    # 1mb => 1024*1024 bytes
    # 1g => 1000000000 bytes
    # 1gb => 1024*1024*1024 bytes
    ```

- 包含其它配置

    ```conf
    # include /path/to/local.conf
    # include /path/to/other.conf
    ```

- 网络配置

    ```conf
    # ip 绑定
    bind 127.0.0.1 -::1
    # 本机访问保护模式
    # protected-mode yes
    # 端口
    port 6379
    # tcp 连接队列，backlog 队列总和 = 未完成三次握手队列 + 已经完成三次握手队列
    tcp-backlog 511
    # linux 内核会将这个值缩小到 /proc/sys/net/core/somaxcconn 的值 (128)
    # 需要增大的时候需要改变 /proc/sys/net/core/somaxconn 和 /proc/sys/net/ipv4/tcp_max_syn_backlog (128) 的值
    # 关闭超时空闲连接 0 为永不超时 (second)
    timeout 0
    # tcp 连接心跳检查时间 (second)
    tcp-keepalive 300
    # redis 后台运行
    daemonize yes
    # 进程号保存的文件
    pidfile /var/run/redis_6379.pid
    # 日志级别 debug/verbose/notice/warning
    loglevel notice
    # 日志文件路径
    logfile ""
    # 默认的 db 个数
    databases 16
    # 密码需求
    # requirepass foobared
    # 或者用命令行 config set requirepass "xxxx"
    # 登录 auth xxxx
    # 客户端最大连接数
    # maxclients 10000
    # 移除规则
    # maxmemory-policy noeviction
    #	valatile-lru
    #		使用 LRU 算法移除 key，只对设置了过期时间的 key
    #	allkeys-lru
    #		在所有集合 key 中，使用 LRU 算法移除 key
    #	volatile-random
    #		在过期集合中移除随机的 key，只对设置了过期时间的 key
    #	allkeys-random
    #		在所有集合 key 中，移除随机的 key
    #	volatile-ttl
    #		移除那些 ttl 最小的 key，即即将过期的 key
    #	noeviction
    #		不进行移除，针对写操作返回错误信息
    # 设置样本数量
    # maxmemory-samples 5
    # LRU 和 最小 TTL 算法都不是精确的算法，所以设置样本大小，redis 会检查 key 并选择其中 LRU 的一个
    # 一般设置 3-7，数值越小样本越不准确，但性能消耗小
    ```

## 5. Redis 发布与订阅

发布订阅（pub/sub）是一种消息通信模式：发送者发送消息，订阅者接收消息

客户端可以订阅任意数量的频道（个人感觉有点类似 MQ）

- 常用命令

    >subscribe <channel_name\> 
    >
    >订阅一个频道
    >
    >
    >
    >publish <channel_name\> <message\> 
    >
    >发送消息到 channel，返回订阅者数量

## 6. Springboot Jedis

Springboot 2.X 需要添加额外依赖

```xml
<dependency>
    <groupId>org.apache.commons</groupId>
    <artifactId>commons-pool2</artifactId>
    <version>2.11.1</version>
</dependency>
```

配置

```yaml
spring:
	redis:
		host: ip
		port: port
		database: num_db
		timeout: ms
		lettuce:
			pool:
				max-active: max-connection(-1)
				max-wait: max-wait-time(-1)
				max-idle: max-idle-connection
				min-idle: min-idle-connection
```

配置类

```java
@EnableCaching
@Configuration
public class RedisConfig extends CachingConfigurerSupport {
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        RedisSerializer<String> redisSerializer = new StringRedisSerializer();
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = 
            new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        template.setConnectionFactory(factory);
        template.setKeySerializer(redisSerializer);
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        return template;
    }
    
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory factory) {
        RedisSerializer<String> redisSerializer = new StringRedisSerializer();
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = 
            new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
            .entryTtl(Duration.ofSeconds(600))
            .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer))
            .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer))
            .disableCachingNullValues();
        RedisCacheManager cacheManager = RedisCacheManager
            .builder(factory)
            .cacheDefaults(config)
            .build();
        return cacheManager;
    }
}
```

## 7. Redis 事务

### 7.1 事务的定义

> All the commands in a transaction are serialized and executed sequentially. It can never happen that a request issued by another client is served **in the middle** of the execution of a Redis transaction. This guarantees that the commands are executed as a single isolated operation.
>
> 
>
> Either all of the commands or none are processed, so a Redis transaction is also atomic. The [EXEC](https://redis.io/commands/exec) command triggers the execution of all the commands in the transaction, so if a client loses the connection to the server in the context of a transaction before calling the [EXEC](https://redis.io/commands/exec) command none of the operations are performed, instead if the [EXEC](https://redis.io/commands/exec) command is called, all the operations are performed. When using the [append-only file](https://redis.io/topics/persistence#append-only-file) Redis makes sure to use a single write(2) syscall to write the transaction on disk. However if the Redis server crashes or is killed by the system administrator in some hard way it is possible that only a partial number of operations are registered. Redis will detect this condition at restart, and will exit with an error. Using the `redis-check-aof` tool it is possible to fix the append only file that will remove the partial transaction so that the server can start again.

Redis 事务时一个单独的隔离操作：事务中的所有命令都会序列化，按顺序执行。事务在执行的过程中，不会被其它客户端发送来的命令请求打断

Redis 事务的主要作用就是串联多个命令防止别的命令插队

### 7.2 Multi，Exec，Discard

从输入 Multi 命令开始，输入的命令都会依次进入命令队列中，但不会执行， 直到输入 Exec 后，Redis 会将之前的命令队列中的命令依次执行，组队过程中可以通过 Discard 来放弃组队

### 7.3 错误处理

组队的时候某个命令出现错误，执行时整个的所有队列都会被取消

如果执行阶段某个命令报错，则只有错误的命令不会被执行，其它的都会执行，不会回滚

### 7.4 事务冲突问题

- 悲观锁

- 乐观锁（版本号）

- watch/unwatch key [key ,,,,,,]

    监视一个或者多个 key，如果在事务执行之前，key 被其他命令改动，那么事务将被打断

### 7.5 事务特性

- 单独的隔离操作

    事务中的所哟命令都会被序列化，按顺序地执行，事务在执行过程中，不会被其它客户端地命令打断

- 没有隔离级别的概念

    队列中地命令没有提交之前都不会实际被执行，因为事务提交前任何指令都不会被实际执行

- 不保证原子性

    事务中如果有一条命令失败，其后地命令仍然会被执行，没有回滚

### 7.6 Jedis 并发问题

- JedisConnectionPool
- Jedis watch 乐观锁（但是会造成库存遗留问题）
- Lua 脚本（Redis 2.6）

## 8. RDB 持久化（Redis DataBase）

在指定的时间间隔内将内存中的数据集快照写入磁盘，恢复时，将快照读到内存里

https://redis.io/topics/persistence

- 执行

    Redis 会 fork （写时复制技术）一个子进程来进行持久化，会先将数据写入到一个临时文件中，待持久化进程都结束了，再用这个临时文件替换上次持久化好的文件，整个过程中，主进程是不进行任何 IO 操作的，确保了极高的性能，如果需要进行大规模数据的恢复，而且对于数据完整性不敏感，那么 RDB 比 AOF 更加高效，RDB 缺点是最后一次持久化后的数据可能丢失

- dump.rdb 文件

    在 redis.conf 中配置文件名称，默认为 dump.rdb（SNAPSHOTING - dbfilename）

    `stop-writes-on-bgsave-error yes` 当 Redis 无法写入磁盘时，关闭 Redis 写的操作

    `rdbcompression yes` 是否启用压缩

    `rdbchecksum yes`  检查完整性

- save <seconds\> <write_times\> 

    在 seconds 时间内超过 write_times 次 key 的改变就会进行同步

    只保存，全部阻塞，手动保存

- bgsave

    Redis 会在后台异步进行快照操作，快照还可以同时相应客户端请求

- lastsave 获取上一次快照时间

- rdb 备份

    先通过 config get dir 查询 rdb 目录

    然后备份 *.rdb 文件

- rdb 恢复

    关闭 redis

    把备份文件放到工作目录下

    启动 redis，备份数据会直接加载

- 优势

    - 适合大规模的数据恢复
    - 对数据完整性和一致性要求不高更适合使用
    - 节省磁盘空间
    - 恢复速度快

- 劣势

    - Fork 的时候，内存中的数据被克隆了一份，有 2 倍的膨胀性
    - 虽然 redis 在 fork 的时候使用了写时拷贝技术，但是如果数据庞大时还是比较消耗性能
    - 在备份周期在一定时间做一次备份，所以 redis 意外 down 的时候，就会丢失最后一次快照的所有修改

## 9. AOF（Append Only File）

以日志形式来记录每个写的操作（增量保存），将 Redis 的执行过的所有写指令记录下来（读操作不记录），只许追加文件，但不可以改写文件，redis 启动之初会读取该文件重新构建数据（即重新执行一遍指令来完成数据恢复的工作）

- 持久化过程

    - 客户端的请求写命令会被 append 追加到 AOF 缓冲区内
    - AOF 缓冲区根据 AOF 持久化策略 `[always|everysec|no]` 将操作 sync 同步到磁盘的 AOF 文件中
    - AOF 文件大小超过重写策略或手动重写时，会对 AOF 文件进行重写，压缩 AOF 文件大小
    - Redis 服务重启时，会重新 load 加载 AOF 文件中的写操作达到数据恢复的目的

- AOF 默认不开启

    在 `redis.conf` 中配置文件名称，默认为 `appendonly.aof` 

    AOF 文件保存路径和 RDB 路径一致

    `appendonly no` 

- AOF 与 RDB 同时开启会默认读取 AOF 的数据

- AOF 启动/修复/恢复

    - AOF 的备份机制和性能虽然和 RDB 不同，但是备份和恢复的操作同 RDB 一样，都是拷贝文件，需要恢复时再拷贝到 redis 工作目录下，重启系统加载
    - 正常恢复
        - 修改默认的 appendonly no，修改为 yes
        - 将有数据的 aof 文件复制一份保存到对应目录
        - 恢复：重启 redis 然后重新加载
    - 异常恢复
        - 修改默认的 appendonly no，改为 yes
        - 如果遇到 AOF 文件损坏，通过 `redis-check-aof --fix appendonly.aof` 恢复
        - 备份倍写坏的 AOF 文件
        - 重启 redis，然后重新加载

- AOF 同步频率设置

    `appendfsync`

    - always

        始终同步，每次 redis 的写入都会立刻记入日志，性能较差，但是数据完整性较好

    - everysec

        每秒同步，每秒记入一次日志，如果 down，本秒的数据可能丢失

    - no

        不主动进行同步，把同步时机交给操作系统

- Rewrite 压缩

    AOF 采用文件追加的方式，文件会越来越大，为避免出现此种情况，新增了重写机制，当 AOF 文件的大小超过所设定的阈值的时候，redis 就会启动 AOF 文件的内容压缩，只保留可以恢复数据的最小指令集，可以使用命令 `bgwriteaof` 

    - 原理

        fork 一个新的进程来重写（先写临时文件最后再 rename），redis 4.0 后的重写，就是把 rdb 的快照以二进制的形式附在新的 AOF 同步，作为已有的历史数据，替换掉原来的流水账操作

        `no-appendfsync-on-rewrite` 

        如果 yes，则不写入 AOF 文件，只写入缓存，用户请求不会阻塞，但是这段时间如果 down 会丢失这段时间的缓存数据（降低数据安全性，提高性能）

        如果   no，还是会把数据往磁盘里写，但是遇到重写操作，可能会发生阻塞（数据安全，但是性能降低）

    - 何时重写

        Redis 会记录上次重写时的 AOF 的大小，默认配置是当 AOF 文件大小是上次 rewrite 后大小的一倍且文件大于 64M 时触发

        重写虽然可以节约大量磁盘空间，减少恢复时间，但是每次重写是有负担的，因此设定 Redis 一定要满足条件才会重写

        `auto-aof-rewrite-percentage` 设置重写的基准值，文件达到 100% 时开始重写（文件是原来重写后文件的两倍时触发）

        `auto-aof-rewrite-min-size` 设置重写的基准值，最小文件 64M，达到这个值开始重写

    - 重写流程

        - `bgwriteaof` 触发重写，判断是否当前有 `bgsave` 或 `bgrewriteaof` 在运行，如果有，则等待该命令结束后再继续执行
        - 主进程 fork 出子进程执行重写操作，保证主进程不会阻塞
        - 子进程遍历 redis 内存中数据到临时文件，客户端的写请求同时写入 aof_buf 缓存区和 aof_rewrite_buf 重写缓冲区保证原 AOF 文件完整以及新 AOF 文件生成期间的新的数据修改动作不会丢失
        - 子进程写完新的 AOF 后向主进程发送信号，父进程更新统计信息，主进程把 `aof_rewrite_buf` 中的数据写入到新的 AOF 文件中
        - 使用新的 AOF 文件覆盖旧的 AOF 文件，完成 AOF 重写

- 优势

    - 备份机制更稳健，丢失数据概率更低
    - 可读的日志文本，通过操作 AOF 稳健，可以处理误操作

- 劣势

    - 比起 RDB 占用更多的空间
    - 恢复备份速度要慢
    - 每次读写都同步的话，有一定的性能压力
    - 存在个别 Bug，造成无法恢复

## 10. Redis 主从复制

主机数据更新后，根据配置和策略，自动同步到备机的 master / slave 机制，master 以写为主，slave 以读为主

- rm读写分离
- 容灾快速恢复

配置文件

```shell
include $REDIS_HOME/redis.conf
pidfile /var/run/redis_6379.pid
port 6379
dbfilename dump6379.rdb
```

执行命令

`slaveof host port` 

### 10.1 一主两从

从服务器重新启动会重新变成 master，需要指定 master 服务器，加入后会把 master 数据从头复制，master down 了以后 slave 不会代替 master

### 10.2 传递性 slave

上一个 slave 可以是下一个 slave 的 master

- 一旦其中一个 slave down，那么后面的 slave 都无法同步了
- master down 了以后，所有的服务器都无法工作

### 10.3 可升级为 master 的 slave

`slaveof no one` 

### 10.4 哨兵模式（sentinel）

创建文件 `sentinel.conf` 

```shell
sentinel monitor <name> <ip> <port> <num>
# <num> 至少有 num 个哨兵同意迁移的数量
```

启动哨兵

```bash
redis-sentinel sentinel.conf
# 默认端口 26379
```

down 的主机恢复后不会变回 master，而是变成当前 master 的 slave

__复制延时__ 

从主机同步到从机会有一定的延迟，网络繁忙和从机数量增加会增大延时

__选举规则__ 

- 优先级靠前的优先选举：`replica-priority 100` 值越小，优先级越高
- 选择偏移量最大的选举：和主机数据同步最高的选举
- run id 最小的选举：redis 启动后会随机生成 40 位的 run id

__主从复制__ 

```java
private static JedisSentinelPool jedisSentinelPool = null;

public static Jedis getJedisFromSentinel() {
    if (jedisSentinelPool == null) {
        Set<String> sentinelSet = new HashSet<>();
        sentinelSet.add("192.168.68.11:26379");
        JedisPoolConfig cfg = new JedisPoolConfig();
        cfg.setMaxTotal(10);
        cfg.setMaxIdle(5);
        cfg.setMinIdle(5);
        cfg.setBlockWhenExhausted(true);
        cfg.setMaxWaitMillis(2000);
        cfg.setTestOnBorrow(true);
        jedisSentinelPool = new JedisSentinelPool("mymaster", sentinelSet, cfg);
    }
    return jedisSentinelPool.getResource();
}
```

## 11. Redis 集群

### 11.1 问题

- redis 容量不够
- 并发写操作

=> 无中心化集群（Redis 3.0+）

### 11.2 特点

实现了对 redis 的水平扩容，即启动 N 个节点，将整个数据库分布存储在 N 个节点中，每个节点存储总数居的 1/N

通过分区（partition）来提供一定程度的可用性（availability）：即使集群中有一部分节点失效或者无法进行通讯，集群也可以继续处理命令请求

### 11.3 删除持久化数据

需要先删除 aof 和 rdb

### 11.4 配置文件

```shell
# 追加
cluster-enabled yes
cluster-config-file "node-6379.conf"
cluster-node-timeout 15000
```

### 11.5 设置集群

```bash
# 需要依赖 Ruby 环境
# 1 表示每个主节点创建一个从节点
# 分配原则是尽量保证每个主数据库运行在不同的 IP 地址上，每个从库和主库不在一个 IP 上
./redis-cli --cluster create --cluster-replicas 1 \
192.168.68.10:6379 \
192.168.68.10:6380 \
192.168.68.10:6381 \
192.168.68.10:6389 \
192.168.68.10:6390 \
192.168.68.10:6391
```

__slots__ 

一个 redis 集群包含了 16384 个插槽，数据库中的每个键都属于这 16384 个插槽中的一个

集群用公式 `CRC16(key)%16384` 来计算 key 属于哪个插槽，其中 `CRC16(key)` 语句用于计算 key 和 CRC16 的校验和

不在一个 slot 下的键值是不能使用 mget，mset 等多键操作

可以通过 {}  来定义组的概念，从而使 key 中 {} 内相同的内同的键值放到一个 slot 中去

> cluster keyslot <key\>
>
> 计算 key 的插槽值
>
> cluster countkeysinslot <slot\> 
>
> 计算 slot 中有几个 key
>
> cluster getkeysinslot <slot\> <count\>
>
> 返回 count 个 slot 中的键

### 11.6 故障恢复

如果主节点下线 timeout 时间，从节点会升级为主节点

主节点恢复后，回来变成从节点

如果所有某一段插槽的主从节点都 down，而 cluster-require-full-converage 为 yes，那么整个集群都会 down，如果为 no，那么则无法读写该部分的 slot

### 11.7 集群的优点

- 实现扩容
- 分摊压力
- 无中心配置相对简单

### 11.8 集群的不足

- 不支持多键操作
- 不支持多建事务
- 不支持 Lua 脚本

### 12. Redis 问题

### 12.1 缓存穿透

key 对应的数据源并不存在，每次针对此 key 的请求从缓存获取不到，请求都会压到数据源

__解决方案__ 

- 对空值缓存

    如果一个查询返回的数据为空（不管数据是否不存在），都把这个 null 进行缓存，设置空结果过期最长时间不超过 5 分钟

- 设置可访问的名单（白名单）

    使用 bitmap 定义一个可以访问的名单，名单 id 作为 bitmap 偏移量，每次访问和 bitmap 里的 id 比较，如果 id 不存在，则进行拦截

- 布隆过滤器（Bloom Filter）

    一个很长的二进制向量和一系列随机映射函数

    空间和时间效率高，但是有一定错误率和删除困难

- 进行实时监控

    redis 命中率降低时，需要排查访问对象和访问数据，设置黑名单限制服务

### 12.2 缓存击穿

key 对应数据存在，但在 redis 中过期，此时有大量请求，这些请求发现缓存过期，一般都会从后端 DB 加载数据并回设到缓存，这时候大瞬时并发量会压垮 DB

__解决方案__ 

- 预先设置热门数据

    提前缓存热门 key，加大热门数据 key 的时长

- 实时调整

- 使用锁（futex）

    在缓存失效的时候不是立即去 load db

    先使用缓存工具带成功操作返回值的操作（比如 setnx）去 set 一个 mutex key

    当操作返回成功时再 load db，并回设缓存，最后删除 mutex key

    当返回失败时，让当前线程睡眠一段时间然后重试

### 12.3 缓存雪崩

极少时间段内大量的 key 集中过期

__解决方案__ 

- 构建多层缓存架构

    Nginx + Redis + 其他缓存

- 使用锁或者队列

- 设置过期标志更新缓存

    记录缓存数据是否过期（设置提前值），如果过期回触发通知另外的线程在后台去更新实际 key 的缓存

- 将缓存是失效时间分散开

### 12.4 分布式锁

- 基于数据库实现分布式锁
- 基于缓存 redis
- 基于 Zookeeper

#### 12.4.1 基于 Redis 的分布式锁

```shell
set <key> <value> nx ex <second>
```





