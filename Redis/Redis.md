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



​    

