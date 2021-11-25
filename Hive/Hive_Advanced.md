# Hive Advanced

## 1. 调优

### 1.1 Explain 查看执行计划

`EXPLAIN [EXTENDED | DEPENDENCY | AUTHORIZATION] query-sql` 

### 1.2 建表优化

#### 1.2.1 分区表

分区表对应 HDFS 上一个独立的文件夹，该文件夹下是分区的所有文件

Hive 中的分区就是分目录，把一个大的数据集根据业务需要分割成小的数据集

查询时通过 WHERE 子句中的表达式选择查询所需要指定的分区，提高查询效率

分区字段不能是表中已经存在的数据，可以将分区字段看作表的伪列

加载数据时必须指定分区

#### 1.2.2 分桶表

分区提供一个隔离数据和优化查询的便利的方式

而分桶是将数据集分解成更容易管理的若干部分的一种方法

分区针对的是路径，分桶针对的是数据文件

=> 抽样查询：`TABLESAMPLE(BUCKET X OUT OF y)` 

#### 1.2.3 合适的文件格式

TEXTFILE，SEQUENCEFILE，ORC，PARQUET

#### 1.2.4 合适的压缩方式

DEFLATE，Gzip，bzip2，LZO，Snappy

### 1.3 HQL 语法优化

#### 1.3.1 列裁剪与分区裁剪

列裁剪就是在查询时只读取需要的列，分区裁剪就是只读取需要的分区

当数据量很大的时候，如果 `select *` 或者不指定分区，全列扫描和全表扫描效率都很低

#### 1.3.2 Group by

默认情况下，Map 阶段同一 key 数据分发给一个 Reduce，当一个 key 数据过大时就数据倾斜了

可以在 Map 端开启聚合操作

```shell
set hive.map.aggr = true;
set hive.groupby.mapaggr.checkinterval = 100000;
set hive.groupby.skewindata = true;
会产生两个 MR Job
```

#### 1.3.3 Vectorization

矢量计算技术，在计算类似 scan，filter，aggregation 的时候，vectorization 技术以设置批处理的增加大小为 1024 行代词来达到比单条记录获得更高的效率

```shell
set hive.vectorized.execution.enabled = true;
set hive.vectorized.execution.reduce.enabled = true;
```

#### 1.3.4 多重模式

将 insert / select ... from 改为 from table insert ... insert ... insert ... / select ... select ... select ...

#### 1.3.5 in/exist 语句

--in / exist 可以改为 left semi join 

#### 1.3.6 CBO 优化

#### 1.3.7 谓词下推

#### 1.3.8 MapJoin

#### 1.3.9 大表，大表 SMB Join 

#### 1.3.10 笛卡尔积

## 2. 源码

## 3. 面试题

