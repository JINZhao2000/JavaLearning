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

