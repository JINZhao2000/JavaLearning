# Hadoop

## 1. 大数据概论

### 1.1 大数据概念

大数据：指无法在一定时间范围内用常规软件进行捕捉，管理和处理的数据集合，是需要新处理模式才能具有更强的决策力，洞察发现力和流程化能力的海量，高增长率和多样化的信息资产

大数据主要解决海量数据的采集，存储和分析计算问题

### 1.2 大数据的特点（4V）

1. Volume 大量

    截至目前，人类生产所有的印刷材料的数据量是 200 PB，而历史上人类总共说过的话的数据量大约是 5 EB，当前，典型个人计算机硬盘的容量为 TB 量级，而一些大企业的数据量已经接近 EB 量级

2. Velocity 高速

3. Variety 多样

    这种类型的多样性也让数据被分为结构化数据和非结构化数据，相对于以往便于存储的以数据库、文本为主的结构化数据，非结构化数据越来越多，包括网络日志，音频，视频，图片，地理位置信息等，这些多类型的数据对数据的处理能力提出了更高的要求

4. Value 低价值密度

    价值密度的高低与数据总量的大小成反比

    如何对有价值数据 “提纯” 成为目前大数据背景下待解决的难题

### 1.3 大数据的应用场景

- 内容推荐
- 商品搭配
- 物流仓储
- 精算预测
- AI 5G IoT VR AR

### 1.4 大数据部门内部的组织结构

- 平台组

    Hadoop，Flume，Kafka，HBase，Spark 等框架平台搭建

    集群性能监控

    集群性能调优

- 数据仓库组

    ETL 清洗工程师（数据清洗）

    数据分析，数据仓库建模

- 实时组

    实时指标分析与性能调优

- 数据挖掘组

    算法工程师

    推荐系统工程师

    用户画像工程师

- 报表开发组

    JavaEE 工程师

    前端工程师

## 2. Hadoop 入门

### 2.1 Hadoop 概念

#### 2.1.1 Hadoop 是什么

1. Hadoop 是一个由 Apache 基金会所开发的分布式系统基础架构
2. 主要解决，海量数据的存储和海量数据分析计算问题
3. 广义上来说，Hadoop 通常是指一个更广泛的概念 —— Hadoop 生态圈
    - Hadoop
    - HBase
    - Hive
    - Zookeeper
    - HAMA
    - Oozie
    - Thrift
    - Nutch
    - Solr
    - ......

#### 2.1.2 Hadoop 发展

1. Hadoop 创始人 Doug Cutting，为了实现与 Google 类似的全文搜索功能，他在 Lucene 框架基础上进行优化升级，查询引擎和搜索引擎
2. 2001 年年底，Lucene 成为 Apache 基金会的一个子项目
3. 对于海量数据的场景，Lucene 框架面对与 Google 同样的困难，存储海量数据难，检索海量速度慢
4. 学习和模仿 Google 解决这些问题的办法：微型版 Nutch
5. 可以说 Google 是 Hadoop 的思想之源
    - [GFS - HDFS](https://static.googleusercontent.com/media/research.google.com/zh-CN//archive/gfs-sosp2003.pdf) 
    - [Map-Reduce - MR](https://static.googleusercontent.com/media/research.google.com/zh-CN//archive/mapreduce-osdi04.pdf) 
    - [Bigtable - HBase](https://static.googleusercontent.com/media/research.google.com/zh-CN//archive/bigtable-osdi06.pdf) 
6. 2003 - 2004 年，Google 公开了部分 GFS 和 MapReduce 思想细节，以此为基础 Doug Cutting 等人用了 2 年业余时间实现了 DFS 和 MapReduce 机制，使 Nutch 性能提升
7. 2005 年 Hadoop 作为 Lucene 的子项目 Nutch 的一部分正式引入 Apache 基金会
8. 2006 年 3 月份，Map-Reduce 和 Nutch Distributed File System（NDFS）分别被纳入到 Hadoop 项目中，Hadoop 就此正式诞生，标志着大数据时代的来临
9. 名字来源于 Doug Cutting 儿子的玩具大象

#### 2.1.3 Hadoop 3 大发行版本

Apache，Cloudera，Hortonworks

Apache 版本最原始最基础的版本，对于入门学习最好 2006

Cloudera 内部集成了很多大数据框架，对应产品 CDH 2008

Hortonworks 文档较好，对应产品 HDP

Hortonworks 现在已经被 Cloudera 公司收购，推出新的品牌 CDP

#### 2.1.4 Hadoop 优势

1. 高可靠性：Hadoop 底层维护多个数据版本，所以即使 Hadoop 某个计算元素或存储出现故障，也不会导致数据的丢失

#### 2.1.5 Hadoop 组成

#### 2.1.6 大数据生态体系

#### 2.1.7 推荐系统案例

### 2.2 环境准备

#### 2.2.1 模板虚拟机的准备

#### 2.2.2 克隆

#### 2.2.3 JDK 与 Hadoop

### 2.3 生产集群搭建

#### 2.3.1 本地模式

#### 2.3.2 生产环境集群（分布式）

### 2.4 常见错误的解决方案

## 3. HDFS

## 4. MapReduce

## 5. Yarn

