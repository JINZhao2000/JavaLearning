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

Join 表的时候的顺序关系：前面的表都会被加载到内存中，后面的表进行磁盘扫描

Hive 1.1.0 后默认开启 Cost Based Optimizer 来对 HQL 执行进行优化

CBO，成本优化器，代价最小的执行计划就是最好的执行计划

```shell
set hive.cbo.enable = true;
set hive.compute.query.using.stats = true;
set hive.stats.fetch.column.stats = true;
set hive.stats.fetch.partition.stats = true;
```

#### 1.3.7 谓词下推

将 where 谓词逻辑都尽可能提前执行，减少下游处理的数据量

对应的逻辑优化器：`PredicatePushDown` ，配置项为 `hive.optimize.ppd = true` ，默认为 true 

#### 1.3.8 MapJoin

MapJoin 是将 Join 双方比较小的表直接分发到各个 Map 进程的内存中，在 Map 进程中进行 Join 操作，这样就不用进行 Reduce 步骤，从而提高了速度，如果不指定 MapJoin 或者不符合 MapJoin 条件，那么 Hive 解析器会将 Join 操作转换成 Common Join

```shell
set hive.auto.convert.join = true;
awr hive.mapjoin.smalltable.filesize = 25000000;
```

#### 1.3.9 大表，大表 SMB Join (Sort Merge Bucket Join)

```shell
set hive.optimize.bucketmapjoin = true;
set hive.optimize.bucketmapjoin.sortedmerge = true;
set hive.input.format = org.apache.hadoop.hive.ql.io.BucketizedHiveInputFormat;
```

#### 1.3.10 笛卡尔积

Join 不加 on 或者无效的 on 条件，只能使用一个 Reducer 来完成笛卡尔积

```shell
set hive.mapred.mode = strict;
```

### 1.4 数据倾斜

从 HQL 角度可以把数据倾斜分为

- 单表携带了 `groupby` 字段的查询
- 两表 `join` 的查询

#### 1.4.1 单表数据倾斜优化

__设置参数__ 

当任务中存在 groupby 操作同时聚合函数为 count 或者 sum 

```shell
set hive.map.aggr = true;
set hive.groupby.mapaggr.checkinterval = 100000;
set hive.groupby.skewindata = true;
# 数据倾斜时的负载均衡
```

__增加 Reduce 数量（多个 Key 同时导致数据倾斜）__ 

- 方法一

    ```shell
    set hive.exec.reducers.bytes.per.reducer = 256000000;
    set hive.exec.reducers.max = 1009;
    # N = min(param2, input/param1)   param1 : 256M, param2 : 1009 ↑
    ```

- 方法二

    ```shell
    # mapred-default.xml
    set mapreduce.job.reduces = 15;
    ```

#### 1.4.2 Join 数据倾斜优化

__设置参数__ 

```shell
# Join 的键对应的记录条数超过这个值则会进行分拆
set hive.skewjoin.key = 100000;
# Join 出现倾斜则设置为 true
set hive.optimize.skewjoin = false;
```

`hive,skewjoin.key` 会将倾斜的 key 写入对应文件中，然后启动另一个 Job 做 MapJoin 生成结果

```shell
set hive.skewjoin.mapjoin.map.tasks = 10000;
# 控制另一个 Job 的 Mapper 的数量
```

__MapJoin__ 

=> 1.3.9

### 1.5 Job 优化

#### 1.5.1 Map 优化

__复杂文件增加 Map 数__ 

当 input 文件很大，任务逻辑复杂，map 执行慢时，可以增加 map 数

根据 `computeSliteSize(Math.max(minSize, Math.min(maxSize, blocksize)))` = 128

调整 maxSize 最大值，让 maxSize 低于 blocksize 就可以增加 map 数

__小文件合并__ 

map 执行前合并小文件，减少 map 数：CombineHiveInputFormat 具有对小文件进行合并的功能

```shell
set hive.input.format = org.apache.hadoop.hive.ql.io.CombineHiveInputFormat;
```

MR 结束时合并小文件的设置

```shell
# map only 结束时合并文件
set hive.merge.mapfiles = true;
# mr 结束时合并
set hive.merge.mapredfiles = true;
# 合并文件大小 256M
set hive.merge.size.per.task = 268435456;
# 当输出文件小于该值时，启动一个独立的 map-reduce 任务进行 merge
set hive.merge.smallfiles.avgsize = 16777216;
```

__Map 端聚合__ 

```shell
set hive.map.aggr = true;
```

__推测执行__ 

```shell
set mapred.map.tasks.speculative.execution = true;
```

#### 1.5.2 Reduce 优化

__合理设置 reduce 数__ 

=> 1.4.1 增加 reduce 个数

__推测执行__ 

```shell
set mapred.map.tasks.speculative.execution = true;
set hive.mapred.reduce.tasks.speculative.execution = true;
```

#### 1.5.3 HIve 任务整体优化

__Fetch 抓取__ 

Fetch 指在某些情况下的查询可以不必使用 MapReduce 计算

`hive-default.xml.template` 

```xml
<property>
	<name>hive.fetch.task.conversion</name>
    <value>more</value>
    <description>none, minimal, more   
    	none : 不启用
        minimal : SELECT STAR, FILTER on partition columns, LIMIT only
        more : SELECT, FILTER, LIMIT only (support TABLESAMPLE and virtual columns)
    </description>
</property>
```

__本地模式__ 

大多数 Hadoop Job 是需要 Hadoop 提供完整的可扩展性来处理大数据集的

Hive 可以通过本地模式在单台机器上处理所有的任务，对于小数据集，执行时间可以明显被缩短

```shell
set hive.exec.mode.local.auto = true;
set hive.exec.mode.local.auto.inputbytes.max = 50000000;
set hive.exec.mode.local.auto.input.files.max = 10;
```

__并行执行__ 

默认情况下 Hive 只会执行一个阶段，特定的 Job 可能包含众多阶段，这些阶段可能并非完全互相依赖的，可以并行执行

```shell
set hive.exec.parallel = true;
set hive.exec.parallel.thread.number = 16;
```

建议在数据量大，sql 长的时候使用

__严格模式__ 

防止危险操作

- 分区表不适用分区过滤

    ```shell
    set hive.strict.checks.no.partition.filter = true;
    ```

- 使用 order by 没有 limit 过滤

    ```shell
    set hive.strict.checks.orderby.no.limit = true;
    ```

- 笛卡尔积

    ```shell
    set hive.strict.checks.cartesian.product = true;
    ```

__JVM 重用__ （主要是小文件）

## 2. 源码

## 3. 面试题

