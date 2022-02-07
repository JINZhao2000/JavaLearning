# Spark

## 1. Spark 概述

Spark 是一种基于内存的快速，通用，可扩展的大数据分析计算引擎

[Apache Spark is a unified analytics engine for large-scale data processing. It provides high-level APIs in Java, Scala, Python and R, and an optimized engine that supports general execution graphs. It also supports a rich set of higher-level tools including [Spark SQL](https://spark.apache.org/docs/latest/sql-programming-guide.html) for SQL and structured data processing, [MLlib](https://spark.apache.org/docs/latest/ml-guide.html) for machine learning, [GraphX](https://spark.apache.org/docs/latest/graphx-programming-guide.html) for graph processing, and [Structured Streaming](https://spark.apache.org/docs/latest/structured-streaming-programming-guide.html) for incremental computation and stream processing.](https://spark.apache.org/docs/latest/) 

__Spark 概要__ 

- 由 Scala 语言开发的快速，通用，可扩展的大数据分析引擎
- Spark Core 中提供了 Spark 最基础与最核心的功能
- Spark SQL 是 Spark 用来操作结构化数据的组件，通过 Spark SQL 可以使用 SQL 或者 HQL 来查询数据
- Spark Streaming 是 Spark 平台上针对实时数据进行流式计算的组件，提供了丰富的处理数据流 API

__Spark 核心模块__ 

- Apache Spark Core

    提供了 Spark 最基础的功能最核心的功能，其它功能都在这个上面扩展的

- Spark SQL

    用于操作结构化的组件，用户可以使用 SQL，HQL 来查询数据

- Spark Streaming

    针对实时数据进行流式计算的组件，提供处理数据流的 API

- Spark MLib

    一个 ML 库，提供了模型评估，数据导入等功能，还提供了一些更底层的机器学习原语

- Spark GraphX

    面向图计算提供的框架于算法库

## 2. Spark 运行环境

### 2.1 Local 模式

不需要其它任何节点资源，可以在本地执行 Spark 代码的环境

- shell

    ```bash
    bin/spark-shell
    ```

    ```scala
    sc.textFile("/apps/modules/spark-3.2.0/data/demo/").flatMap(_.split(" ")).map((_, 1)).reduceByKey(_+_).collect()
    ```

- 提交任务

    ```bash
    bin/spark-submit \
    --class org.apache.spark.examples.SparkPi \ # 应用程序入口
    --master local[2] \ # 数字为分配的 CPU 核数量
    ./examples/jars/spark-examples_2.13-3.2.0.jar \  # 应用类所在 jar 包
    10 # 入口参数，应用的任务数量
    ```

### 2.2 Standalone 模式（独立部署）

版本：3.2.0 配置文件名为 `workers` 以前版本为 `slaves` 

`workers` 文件中写入节点的 ip （或域名）

`spark-env.sh` 中写入

```shell
export JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64
SPARK_MASTER_HOST=hadoop01
SPARK_MASTER_PORT=7077
```

提交任务

```shell
bin/spark-submit \
--class org.apache.spark.examples.SparkPi \
--master spark://host:7077 \
./examples/jars/spark-examples_2.13-3.2.0.jar \
10
```

参数列表

- --class

    含 main 类全限定名

- --master

    运行环境

    - local
    - spark://
    - yarn

- --executor-memory 1G

    指定每个 executor 可用内存

- --total-executor-cores 2

    指定所有 executor 使用的 cpu 核数

- --executor-cores

    指定每个 executor 使用的 cpu 核数

- application-jar

    包含依赖的 jar 包，url 在集群中全局可见，比如 hdfs

### 2.3 历史服务

- spark-default.conf

    ```shell
    spark.eventLog.enabled	true
    spark.eventLog.dir		hdfs://hadoop01:8020/spark_history
    ```

- spark-env.sh

    ```shell
    export SPARK_HISTORY_OPTS="
    -Dspark.history.ui.port=18080
    -Dspark.history.fs.logDirectory=hdfs://hadoop01:8020/spark_history
    -Dspark.history.retainedApplications=30"
    ```

- 必须先启动 HDFS，此外，必须先创建 logDirectory

### 2.4 高可用

- zookeeper

- spark-env 配置

    ```shell
    # SPARK_MASTER_HOST=host
    # SPARK_MASTER_PORT=port
    # 之前的这两条注释掉
    SPARK_MASTER_WEBUI_PORT=8989 # 原因是可能核 zookeeper 端口冲突
    export SPARK_DAEMON_JAVA_OPTS="
    -Dspark.deploy.recoveryMode=ZOOKEEPER
    -Dspark.deploy.zookeeper.url=host1,host2,host3
    -Dspark.deploy.zookeeper.dir=/dir"
    ```

- 然后在要配置备用的 Master 节点单独启动 master

### 2.5 YARN

- yarn-site.xml

    ```xml
    <property>
        <name>yarn.modemanager.pmem-check-enabled</name>
        <value>false</value>
    </property>
    <property>
        <name>yarn.nodemanager.vmem-check-enabled</name>
        <value>false</value>
    </property>
    ```

- spark-env

    ```shell
    export JAVA_HOME=XXX
    YARN_CONF_DIR=/apps/modules/hadoop-3.3.1/etc/hadoop/
    ```

- history 在 spark-default 中

    ```shell
    spark.yarn.historyServer.address=host:port
    spark.history.ui.port=port
    ```

### 2.6 K8S & Mesos

## 3. Spark 运行架构

### 3.1 运行架构

master-slave

- Driver Program
    - SparkContext
- Worker Node
    - Executor
        - Cache
        - Task
- Cluster Manager

### 3.2 核心组件

- Driver

    Spark 驱动器节点，用于执行 Spark 任务中的 main 方法，负责实际代码的执行工作

    - 将用户程序转化为 Job
    - 在 Executor 之间调度 Task
    - 跟踪 Executor 执行情况
    - 通过 UI 展示查询运行的情况

- Executor

    是集群 Worker 中的一个 JVM 进程，负责在 Spark 作业中运行具体的任务，任务彼此之间相互独立，Spark 启动时，Executor 节点被同时启动，并始终伴随整个 Spark 应用的生命周期而存在，如果有 Executor 节点 down 时，Spark 应用也可以继续执行，会将出错的节点上的任务调度到其它 Executor 节点上继续执行

    两个核心功能

    - 运行 Spark 的 Task 并将结果返回给 Driver
    - 通过自身的 Block Manager 为用户程序中要求缓存的 RDD 提供内存式存储，RDD 是直接缓存在 Executor 进程内的，因此任务可以在运行时充分利用缓存数据加速运算

- Master & Worker

    在独立部署的时候，不依赖其它的资源调度框架，自身通过 Master 和 Worker 实现调度（类似于 YARN 的 RM 和 NM）

- ApplicationMaster

    Hadoop 用户向 YARN 集群提交应用程序时，需要包含 ApplicationMaster，用于向资源调度器申请执行任务的 Container 来运行 Job，来监控任务执行，跟踪任务状态

### 3.3 核心概念

#### 3.3.1 Executor 与 Core

Spark Executor 是集群中运行在 Worker 中的一个 JVM 进程，是用于计算的节点，在提交应用中，可以提供参数指定计算的节点，以及对应的资源（指 Executor 的内存大小和是哟个的 VCORE 数量）

参数：

| 名称              | 作用                     |
| ----------------- | ------------------------ |
| --num-executors   | Executor 数量            |
| --executor-memory | 每个 Executor 内存大小   |
| --executors-cores | 每个 Executor vcore 数量 |

#### 3.3.2 Parallelism

整个集群并行执行的任务的数量成为并发度，取决于框架默认配置，可以在运行中动态修改

#### 3.3.3 DAG 有向无环图

### 3.4 提交流程

1. 任务提交
2. Driver 运行
3. 向集群管理器注册应用程序
4. Executor 启动
5. 反向注册到 Driver
6. （资源满足后）执行 main 函数
7. （懒执行）执行到 action 算子
8. （触发 job）stage 划分
9. 创建 taskSet
10. 将 task 分发给指定的 Executor

#### 3.4.1 Yarn Client 模式

#### 3.4.2 Yarn Cluster 模式

## 4. Spark 核心编程

三大数据结构

- RDD：弹性分布式数据集
- 累加器：分布式共享只写变量
- 广播变量：分布式共享只读变量

### 4.1 RDD

RDD（Resilient Distributed Dataset）弹性分布式数据集，是 Spark 最基本的处理模型，代表一个弹性的，不可变，可分区，元素可并行计算的集合（装饰模式），只有在 collect 方法才会真正执行操作

弹性

- 存储的弹性：内存与磁盘自动切换
- 容错的弹性：数据丢失可以自动恢复
- 计算的弹性：计算出错重试
- 分片的弹性：可根据需要重新分片

分布式

- 数据存储在大数据集群不同节点上

数据集

- RDD 封装了计算逻辑，不保存数据

数据抽象

- RDD 是一个抽象类，需要子类具体实现

可分区，并行计算

#### 4.1.1 核心属性

- 分区列表

    用于执行任务时的并行计算，是实现分布式计算的重要属性

- 分区计算函数

    Spark 使用分区计算函数对每一个分区进行计算

- RDD 依赖关系

    RDD 是计算模型的封装，当需求中需要多个计算模型进行组合时，就需要将多个 RDD 建立依赖关系

- RDD key 分区器

    对 key 进行分区，然后分发到不同的 Executor 上

- 首选位置

    判断计算效率最有的节点

#### 4.1.2 执行原理

Spark 执行时

- 先申请资源
- 然后将应用程序的数据处理逻辑分解成计算任务
- 然后将任务发到已经分配资源的计算节点上
- 按照指定的计算模型进行数据计算
- 最后得到结果

#### 4.1.3 创建 RDD 方式

- 从内存创建
- 从文件创建
- 从其它 RDD 创建
- new

#### 4.1.4 RDD 并行度与分区

默认情况下，Spark 将一个作业分成多个任务后，发送给 Executor 节点进行计算，而能够并行计算的任务数量成为并行度，可以在构建 RDD 时指定（指执行任务的数量，而不是切分任务的数量）

`ParallelCollectionRDD,scala` 

```scala
override def getPartitions: Array[Partition] = {
    val slices = ParallelCollectionRDD.slice(data, numSlices).toArray
    slices.indices.map(i => new ParallelCollectionPartition(id, i, slices(i))).toArray
}

def slice[T: ClassTag](seq: Seq[T], numSlices: Int): Seq[Seq[T]] = {
    if (numSlices < 1) {
        throw new IllegalArgumentException("Positive number of partitions required")
    }
    // Sequences need to be sliced at the same set of index positions for operations
    // like RDD.zip() to behave as expected
    def positions(length: Long, numSlices: Int): Iterator[(Int, Int)] = {
        (0 until numSlices).iterator.map { i =>
            val start = ((i * length) / numSlices).toInt
            val end = (((i + 1) * length) / numSlices).toInt
            (start, end)
        }
    }
    seq match {
        case r: Range =>
        positions(r.length, numSlices).zipWithIndex.map { case ((start, end), index) =>
            // If the range is inclusive, use inclusive range for the last slice
            if (r.isInclusive && index == numSlices - 1) {
                new Range.Inclusive(r.start + start * r.step, r.end, r.step)
            } else {
                new Range.Inclusive(r.start + start * r.step, r.start + (end - 1) * r.step, r.step)
            }
        }.toSeq.asInstanceOf[Seq[Seq[T]]]
        case nr: NumericRange[T] =>
        // For ranges of Long, Double, BigInteger, etc
        val slices = new ArrayBuffer[Seq[T]](numSlices)
        var r = nr
        for ((start, end) <- positions(nr.length, numSlices)) {
            val sliceSize = end - start
            slices += r.take(sliceSize).asInstanceOf[Seq[T]]
            r = r.drop(sliceSize)
        }
        slices.toSeq
        case _ =>
        val array = seq.toArray // To prevent O(n^2) operations for List etc
        positions(array.length, numSlices).map { case (start, end) =>
            array.slice(start, end).toSeq
        }.toSeq
    }
}
```

读取文件时，数据按照 Hadoop 文件读取的规则进行切片分区

```scala
class SparkContext(config: SparkConf) extends Logging {
    def textFile(
        path: String,
        minPartitions: Int = defaultMinPartitions): RDD[String] = withScope {
        assertNotStopped()
        hadoopFile(path, classOf[TextInputFormat], classOf[LongWritable], classOf[Text],
                   minPartitions).map(pair => pair._2.toString).setName(path)
    }
    
    def defaultMinPartitions: Int = math.min(defaultParallelism, 2) // local[*]
    
    // totalSize
    // goalSize = totalSize / 2 
    def hadoopFile[K, V](
        path: String,
        inputFormatClass: Class[_ <: InputFormat[K, V]], // TextInputFormat
        keyClass: Class[K],
        valueClass: Class[V],
        minPartitions: Int = defaultMinPartitions): RDD[(K, V)] = withScope {
        assertNotStopped()

        // This is a hack to enforce loading hdfs-site.xml.
        // See SPARK-11227 for details.
        FileSystem.getLocal(hadoopConfiguration)

        // A Hadoop configuration can be about 10 KiB, which is pretty big, so broadcast it.
        val confBroadcast = broadcast(new SerializableConfiguration(hadoopConfiguration))
        val setInputPathsFunc = (jobConf: JobConf) => FileInputFormat.setInputPaths(jobConf, path)
        new HadoopRDD(
            this,
            confBroadcast,
            Some(setInputPathsFunc),
            inputFormatClass,
            keyClass,
            valueClass,
            minPartitions).setName(path)
    }
}
```

#### 4.1.5 RDD 转换算子

RDD 根据处理方式不同，将算子整体上分为 Value 类型，双 Value 类型和 Key-Value 类型

- Value 类型

    - map（分区内执行有序，分区间执行无序）

        `def map[U: ClassTag](f: T=>U): RDD[U]` 

        将数据逐条进行映射转换（类型或值）

    - mapPartitions

        `def mapPartitions[U : ClassTag](f: Iterator[T] => Iterator[U], preservesPartitioning: Boolean = false): RDD[U]` 

        以分区为单位进行数据转换操作，但是会将整个分区数据加载到内存进行引用，处理完的是不会释放掉内存的
        
    - mapPartitionsWithIndex
    
        `def mapPartitionsWithIndex[U : ClassTag](f: (Int, Iterator[T]) => Iterator[U], preservesPartitioning: Boolean = false): RDD[U]` 
    
        将待处理的数据以分区为单位发送到计算节点进行处理（计算，合并，过滤等）
    
    - flatMap
    
        `def flatMap[U : ClassTag](f: T => TraversableOnce[U]): RDD[U]` 
    
        将处理的数据进行扁平化处理后，在进行映射处理（扁平映射）
    
    - glom
    
        `def glom(): RDD[Array[T]]` 
    
        将同一个分区的数据直接转换为相同类型的内存数据进行处理，分区不变
        
    - groupBy
    
        `def groupBy[K](f: T => K)(implicit kt: ClassTag[K]): RDD[(K, Iterable[T])]` 
    
        将数据根据指定的规则进行分组，分区默认不变，但是数据会被打乱重新组合（shuffle），极限情况下，数据可能被分在同一分区中
    
    - filter
    
        `def filter(f: T => Boolean): RDD[T]` 
    
        将数据根据指定的规则进行筛选过滤，符合规则的数据保留，不符合规则的数据丢弃
    
        当数据进行筛选过后，分区不变，但是分区内的数据可能不均衡，可能会出现数据倾斜
    
    - sample
    
        `def sample(withReplacement: Boolean, fraction: Double, seed: Long = Utils.random.nextLong): RDD[T]` 
    
        - withReplacement
    
            抽取数据不放回
    
            false：伯努利算法
    
            true：泊松分布
    
        - fraction
    
            比例
    
        - seed
    
            随机数种子
    
        根据指定的规则从数据集中抽取数据（可以用于数据倾斜场合）
    
    - distinct
    
        `def distinct()(implicit ord: Ordering[T] = null): RDD[T]` 
    
        `def distinct(numPartitions: Int)(implicit ord: Ordering[T] = null): RDD[T]` 
    
        去重
    
        实现方式
    
        ```scala
        map(x => (x, null)).reduceByKey((x, _) => x, numPartitions).map(_._1)
        ```
    
    - coalesce
    
        ```scala
        def coalesce(numPartitions: Int, shuffle: Boolean = false,
                     partitionCoalescer: Option[PartitionCoalescer] = Option.empty)
                    (implicit ord: Ordering[T] = null)
            : RDD[T]
        ```
    
        根据数据量缩减分区，用于大数据集过滤后，提高小数据集的执行效率
    
        默认情况下不会将分区的数据打乱，可能导致数据不均衡
    
        扩大分区：用 shuffle = true 
    
    - repartition
    
        ```scala
        def repartition(numPartitions: Int)(implicit ord: Ordering[T] = null): RDD[T] = withScope {
            coalesce(numPartitions, shuffle = true)
        }
        ```
    
    - sortBy
    
        ```scala
        def sortBy[K](
            f: (T) => K,
            ascending: Boolean = true,
            numPartitions: Int = this.partitions.length)
            (implicit ord: Ordering[K], ctag: ClassTag[K]): RDD[T]
        ```
    
    区别：
    
    - 数据处理角度
    
        Map 算子是分区内一个数据一个数据德执行，类似于串行操作
    
        MapPartition 算子是以分区为单位，进行批处理操作
    
    - 功能的角度
    
        Map 算子主要目的是将数据源中的数据进行转换和改变，但不会减少和增多数据
    
        MapPartition 算子需要传递一个迭代器，返回一个迭代器，没有要求元素个数保持不变，可以增加或减少数据
    
    - 性能的角度
    
        Map 算子类似于串行操作，性能较低
    
        MapPartition 算子类似于批处理，所以性能较高，但是会长时间占用内存，可能导致内存溢出
    
- 双 Value 类型

    - intersection

        `def intersection(other: RDD[T]): RDD[T]` 

        交集

    - union

        `def union(other: RDD[T]): RDD[T]` 

        可重复并集

    - subtract

        `def subtract(other: RDD[T]): RDD[T]` 

        差集

    - zip

        `def zip[U: ClassTag](other: RDD[U]): RDD[(T, U)]` 

        拉链

- Key - Value 类型

    - partitionBy（PairRDDFunctions 隐式转换 implicit）

        `def partitionBy(partitioner: Partitioner): RDD[(K, V)]` 

        根据传入的 Partitioner 重新进行分区，Spark 默认分区器是 HashPartitioner
    
    - reduceByKey
    
        `def reduceByKey(func: (V, V)=>V): RDD[(K, V)]` 
    
        `def reduceByKey(func: (V, V)=>V, numPartitions: Int): RDD[(K, V)]` 
    
        将数据按照相同的 Key 对 Value 进行聚合
    
    - groupByKey
    
        `def groupByKey(): RDD[(K, Iterable[V])]` 
    
        `def groupByKey(partitioner: Partitioner): RDD[(K, Iterable[V])]` 
    
        `def groupByKey(numPartitions: Int): RDD[(K, Iterable[V])]` 
    
        将分区的数据直接转换为相同类型的内存数组进行后续处理
    
        和 `reduceByKey` 区别
    
        - groupByKey 和 reduceByKey 存在 shuffle 操作
        
            shuffle 操作必须落盘处理（写入文件），不能再内存中等待，会导致内存溢出
        
        - 但是 groupByKey 会统一将数据落盘，但是 reduceByKey 会在落盘前进行分区内 combine（预聚合），减少文件 IO
        
        - 功能上 reduceByKey 包含分组和聚合的功能，groupByKey 只能分组，不能聚合
        
    - aggregateByKey
    
        ```scala
        def aggregateByKey[U: ClassTag](zeroValue: U, partitioner: Partitioner)(seqOp: (U, V) => U,
            combOp: (U, U) => U): RDD[(K, U)]
        ```
    
        将数据根据不同的规则进行分区内计算和分区间计算
        
    - combineByKey
    
        ```scala
        def combineByKey[C](
            createCombiner: V => C,
            mergeValue: (C, V) => C,
            mergeCombiners: (C, C) => C,
            partitioner: Partitioner,
            mapSideCombine: Boolean = true,
            serializer: Serializer = null): RDD[(K, C)]
        ```
    
        - 第一个参数是第一次遇到一个 key 后的转换
        - 第二个参数是分区内操作
        - 第三个参数是分区间操作
    
        4 个方法的最终使用同一个方法
    
        ```scala
        def combineByKeyWithClassTag[C](
              createCombiner: V => C,
              mergeValue: (C, V) => C,
              mergeCombiners: (C, C) => C,
              partitioner: Partitioner,
              mapSideCombine: Boolean = true,
              serializer: Serializer = null)(implicit ct: ClassTag[C]): RDD[(K, C)]
        ```
    
    - join
    
        `def join[W](other: RDD[(K, W)], partitioner: Partitioner): RDD[(K, (V, W))]` 
    
        在类型为（K，V）和（K，W）的 RDD 上调用，返回一个相同 key 对应的所有元素连接在一起的（K，（V，W））的 RDD
    
    - leftOuterJoin
    
        `def leftOuterJoin[W](other: RDD[(K, W)], partitioner: Partitioner): RDD[(K, (V, Option[W]))]` 
    
        类似于 SQL 的左外连接
    
    - rightOuterJoin
    
        `def rightOuterJoin[W](other: RDD[(K, W)], partitioner: Partitioner): RDD[(K, (Option[V], W))]` 
    
        类似于 SQL 的右外连接
    
    - cogroup
    
        `def cogroup[W](other1: RDD[(K, W)]): RDD[(K, (Iterable[V], Iterable[W]))]` 
    
        在类型为（K，V）和（K，W）的 RDD 上调用，返回一个（K，（Iterable\<V\>，Iterable\<W\>））类型的 RDD
    
#### 4.1.6 RDD 行动算子

触发整个作业（Job）执行的方法

底层代码调用的是环境对象的 `runJob` 方法

底层代码中会创建 `ActiveJob` 并提交执行

- reduce

    `def reduce(f: (T, T) => T): T` 

    聚集 RDD 中所有元素，先聚合分区内数据，再聚合分区间数据

- collect

    `def collect(): Array[T]` 

    在驱动程序中，以数组 Array 的形式返回数据集的所有元素

    将不同分区的数据按照分区顺序采集到 Driver 内存中，形成数组

- count

    `def count(): Long` 

    返回 RDD 中元素的个数

- first

    `def first(): T` 

    取数据元中第一个元素

- take

    `def take(num: Int): Array[T]` 

    返回一个由 RDD 的前 n 个元素组成的数组

- takeOrdered

    `def takeOrdered(num: Int)(implicit ord: Ordering[T]): Array[T]` 

    返回该 RDD 排序后的前 n 个元素组成的数组

- aggregate

    `def aggregate[U: ClassTag](zeroValue: U)(seqOp: (U, T) => U, combOp: (U, U) => U): U` 

    分区的数据通过初始值和分区内的数据进行聚合，然后再和初始值进行分区间的聚合

    初始值不仅会参与分区内计算，还会参与分区间计算

- fold

    `def fold(zeroValue: T)(op: (T, T) => T): T` 

    折叠操作，aggregate 简化版操作
    
- countByKey

    `def countByKey(): Map[K, Long]` 

    计算 key 出现的个数

- countByValue

    `def countByValue()(implicit ord: Ordering[T] = null): Map[T, Long]` 

    计算 value 出现的个数

- save 相关算子

    ```scala
    def saveAsTextFile(path: String): Unit
    def saveAsObjectFile(path: String): Unit
    // sequence 要求数据格式必须为 key - value 类型
    def saveAsSequenceFile(
          path: String,
          codec: Option[Class[_ <: CompressionCodec]] = None): Unit
    ```
    
    将数据保存到不同格式的文件中
    
- foreach

    ```scala
    def foreach(f: T => Unit): Unit = withScope {
      val cleanF = sc.clean(f)
      sc.runJob(this, (iter: Iterator[T]) => iter.foreach(cleanF))
    }
    ```

    分布式遍历 RDD 中每个元素，调用指定函数

    闭包检测是否可序列化

    ```scala
    private[spark] def clean[F <: AnyRef](f: F, checkSerializable: Boolean = true): F = {
        ClosureCleaner.clean(f, checkSerializable)
        f
    }
    
    private def ensureSerializable(func: AnyRef): Unit = {
        try {
            if (SparkEnv.get != null) {
                SparkEnv.get.closureSerializer.newInstance().serialize(func)
            }
        } catch {
            case ex: Exception => throw new SparkException("Task not serializable", ex)
        }
    }
    ```

#### 4.1.7 RDD 序列化

- 闭包检查

    从计算的角度，算子以外的代码都是在 Driver 端执行，算子里面的代码都是在 Executor 端执行。那么在 scala 的函数式编程中，就会导致算子内经常会用到算子外的数据，这样就形成了闭包的效果，如果使用算子外的数据无法序列化，就意味着无法传值给 Executor 端执行，就会发生错误，所以需要在执行任务计算前，检测闭包内的对象是否可以进行序列化，这个操作成为闭包检测（2.12 后的闭包编译方式发生了改变）

- 序列化方法和属性

    从计算的角度，算子以外的代码都是在 Driver 端执行，算子里面的代码都是在 Executor 端执行

- [Kryo 序列化框架](https://github.com/EsotericSoftware/kryo) 

    Java 序列化能序列化任何类，但是是重量级序列化，Spark 2.0 开始支持另外一种 Kryo 序列化机制，Kryo 速度是 Serializable 的 10 倍。

    当 RDD 在 shuffle 数据的时候，简单数据类型，数组和字符串类型在 Spark 内部会使用 Kryo 来序列化

#### 4.1.8 RDD 依赖关系

- RDD 血缘关系

    RDD 只支持粗粒度转换，即在大量记录上执行的单个操作，将创建 RDD 的一系列 Lineage（血统）记录下来，以便恢复丢失的分区。RDD 的 Lineage 会记录 RDD 的元数据信息和转换行为，当 RDD 的部分分区数据丢失时，可以根据这些信息来重新运算和恢复丢失的数据分区

    ```console
    WordCount : 
    
    (2) dataset/wordcount MapPartitionsRDD[1] at textFile at RDDDependency.scala:11 []
     |  dataset/wordcount HadoopRDD[0] at textFile at RDDDependency.scala:11 []
    
    (2) MapPartitionsRDD[2] at flatMap at RDDDependency.scala:13 []
     |  dataset/wordcount MapPartitionsRDD[1] at textFile at RDDDependency.scala:11 []
     |  dataset/wordcount HadoopRDD[0] at textFile at RDDDependency.scala:11 []
    
    (2) MapPartitionsRDD[3] at map at RDDDependency.scala:15 []
     |  MapPartitionsRDD[2] at flatMap at RDDDependency.scala:13 []
     |  dataset/wordcount MapPartitionsRDD[1] at textFile at RDDDependency.scala:11 []
     |  dataset/wordcount HadoopRDD[0] at textFile at RDDDependency.scala:11 []
    
    (2) ShuffledRDD[4] at reduceByKey at RDDDependency.scala:17 []
     +-(2) MapPartitionsRDD[3] at map at RDDDependency.scala:15 []
        |  MapPartitionsRDD[2] at flatMap at RDDDependency.scala:13 []
        |  dataset/wordcount MapPartitionsRDD[1] at textFile at RDDDependency.scala:11 []
        |  dataset/wordcount HadoopRDD[0] at textFile at RDDDependency.scala:11 []
    ```

- RDD 依赖关系

    指的是两个相邻 RDD 之间的关系

    - `org.apache.spark.OneToOneDependency` (NarrowDependency)

        新的 RDD 的一个分区的数据依赖于旧的 RDD __一个__分区的数据

    - `org.apache.spark.ShuffleDependency` 

        新的 RDD 的一个分区的数据依赖于旧的 RDD __多个__分区的数据

- RDD 窄依赖（Narrow）

    窄依赖表示每一个父（上游）RDD 的 Partition 最多被子（下游）RDD 的一个 Partition 使用

- RDD 宽依赖

    宽依赖表示每一个父（上游）RDD 的 Partition 被多个子（下游）RDD 的 Partition 使用

- RDD 阶段划分

    DAG（Directed Acyclic Graph）有向无环图是由点和线组成的拓扑图形，有向，无闭环
    
- RDD 分阶段源码

    ```scala
    private[spark] class DAGScheduler(
        // ...
    )extends Logging {
        def runJob[T, U](
            // ...
        ): Unit = {
            val waiter = submitJob(rdd, func, partitions, callSite, resultHandler, properties)
            // ...
        }
        
        def submitJob[T, U](
            // ...
        ): JobWaiter[U] = {
            // ...
            val waiter = new JobWaiter[U](this, jobId, partitions.size, resultHandler)
            eventProcessLoop.post(JobSubmitted(
                jobId, rdd, func2, partitions.toArray, callSite, waiter,
                Utils.cloneProperties(properties)))
            waiter
        }
        
        private[scheduler] def handleJobSubmitted(
        	// ...
        ): Unit = {
            var finalStage: ResultStage = null
            try {
                finalStage = createResultStage(finalRDD, func, partitions, jobId, callSite)
            } catch {
                // ...
            }
            // ...
        }
        
        // ResultStage 只会出现一个，最后执行的阶段
        private def createResultStage(
            // ...
        ): ResultStage = {
            val (shuffleDeps, resourceProfiles) = getShuffleDependenciesAndResourceProfiles(rdd)
            // ...
            val parents = getOrCreateParentStages(shuffleDeps, jobId)
            // ...
            val stage = new ResultStage(id, rdd, func, partitions, parents, jobId,
                                        callSite, resourceProfile.id)
            // ...
        }
        
        private[scheduler] def getShuffleDependenciesAndResourceProfiles(
            rdd: RDD[_]): (HashSet[ShuffleDependency[_, _, _]], HashSet[ResourceProfile]) = {
            val parents = new HashSet[ShuffleDependency[_, _, _]]
            val resourceProfiles = new HashSet[ResourceProfile]
            val visited = new HashSet[RDD[_]]
            val waitingForVisit = new ListBuffer[RDD[_]]
            waitingForVisit += rdd
            while (waitingForVisit.nonEmpty) {
                val toVisit = waitingForVisit.remove(0)
                if (!visited(toVisit)) {
                    visited += toVisit
                    Option(toVisit.getResourceProfile).foreach(resourceProfiles += _)
                    toVisit.dependencies.foreach {
                        case shuffleDep: ShuffleDependency[_, _, _] =>
                        parents += shuffleDep
                        case dependency =>
                        waitingForVisit.prepend(dependency.rdd)
                    }
                }
            }
            (parents, resourceProfiles)
        }
        
        private def getOrCreateParentStages(shuffleDeps: HashSet[ShuffleDependency[_, _, _]],
                                            firstJobId: Int): List[Stage] = {
            shuffleDeps.map { shuffleDep =>
                getOrCreateShuffleMapStage(shuffleDep, firstJobId)
            }.toList
        }
        
        // 当 RDD 中存在 shuffle 依赖时，阶段会自动增加一个
       	// 阶段的数量 = shuffle 的数量 + 1
        private def getOrCreateShuffleMapStage(
            shuffleDep: ShuffleDependency[_, _, _],
            firstJobId: Int): ShuffleMapStage = {
            shuffleIdToMapStage.get(shuffleDep.shuffleId) match {
                // ...
                case None =>
                // ...
                createShuffleMapStage(shuffleDep, firstJobId)
            }
        }
        
        def createShuffleMapStage[K, V, C](
            shuffleDep: ShuffleDependency[K, V, C], jobId: Int): ShuffleMapStage = {
            // ...
            val stage = new ShuffleMapStage(
                id, rdd, numTasks, parents, jobId, rdd.creationSite, shuffleDep, mapOutputTracker,
                resourceProfile.id)
            // .
            stage
        }
    }
    ```

- RDD 任务划分

    RDD 任务切分中间分为：Application，Job，Stage 和 Task（每层都是 1 对 N 的关系）

    - Application：初始化一个 SparkContext 即生成一个 Application
    - Job：一个 Action 算子就会生成一个 Job
    - Stage：Stage 等于宽依赖（ShuffleDependency）个数 + 1
    - Task：一个 Stage 阶段中，最后一个 RDD 的分区个数就是 Task 的个数

    任务执行过程

    - RDD Objects

        build operator DAG

    - DAGScheduler

        split graph into stages of tasks

        submit each stage as ready

    - TaskScheduler

        launch tasks via cluster manager

        retry failed or straggling tasks

    - Worker

        execute tasks

        store and serve blocks

#### 4.1.9 RDD 持久化

- RDD Cache

    RDD 通过 Cache 或者 Persist 方法将前面的计算结果缓存，默认情况下会把数据以缓存在 JVM 的堆内存中，但是并不是这两个方法被调用时立即缓存，而是触发后面的 action 算子时，该 RDD 将会被被缓存在计算节点的内存中（因为不触发行动算子还没有数据）

    ```scala
    def cache(): this.type = persist()
    def persist(): this.type = persist(StorageLevel.MEMORY_ONLY)
    
    @DeveloperApi
    def fromString(s: String): StorageLevel = s match {
        case "NONE" => NONE
        case "DISK_ONLY" => DISK_ONLY
        case "DISK_ONLY_2" => DISK_ONLY_2
        case "DISK_ONLY_3" => DISK_ONLY_3
        case "MEMORY_ONLY" => MEMORY_ONLY
        case "MEMORY_ONLY_2" => MEMORY_ONLY_2
        case "MEMORY_ONLY_SER" => MEMORY_ONLY_SER
        case "MEMORY_ONLY_SER_2" => MEMORY_ONLY_SER_2
        case "MEMORY_AND_DISK" => MEMORY_AND_DISK
        case "MEMORY_AND_DISK_2" => MEMORY_AND_DISK_2
        case "MEMORY_AND_DISK_SER" => MEMORY_AND_DISK_SER
        case "MEMORY_AND_DISK_SER_2" => MEMORY_AND_DISK_SER_2
        case "OFF_HEAP" => OFF_HEAP
        case _ => throw new IllegalArgumentException(s"Invalid StorageLevel: $s")
    }
    ```

    存储级别：

    | 级别                | 使用的空间 | CPU 时间 | 是否在内存中 | 是否在磁盘上 | 备注                                                         |
    | ------------------- | ---------- | -------- | ------------ | ------------ | ------------------------------------------------------------ |
    | MEMORY_ONLY         | high       | low      | yes          | no           |                                                              |
    | MEMORY_ONLY_SER     | low        | high     | yes          | no           |                                                              |
    | MEMORY_AND_DISK     | high       | medium   | part         | part         | 如果数据在内存中放不下，则溢写到磁盘上                       |
    | MEMORY_AND_DISK_SER | low        | high     | part         | part         | 如果数据在内存中放不下，则溢写到磁盘上，在内存中存放序列化后的数据 |
    | DISK_ONLY           | low        | high     | yes          | yes          |                                                              |

    缓存有可能丢失，或者存储于内存的数据由于内存不足而被剔除，RDD 的缓存容错机制保证了即使缓存丢失也能保证计算的正确执行，通过基于 RDD 的一些列转换，丢失的数据会被重算，由于 RDD 的各个 Partition 是相对独立的，因此只需要计算丢失的部分即
    
- RDD CheckPoint 检查点

    将 RDD 中间结果写入磁盘

    对 RDD 进行 checkpoint 操作不会马上执行，必须执行 Action 操作才能触发

    checkpoint 需要落盘

- Cache Persist CheckPoint 区别

    - cache：将数据临时存储在内存中进行数据重用

        会在血缘关系中添加新的依赖

    - persist：将数据临时存储在磁盘文件中进行数据重用

        涉及到磁盘 IO，性能较低，但是数据安全

        如果作业执行完毕，临时保存的数据文件就会丢失

    - checkpoint：将数据长久地保存在磁盘文件中进行数据重用

        涉及到磁盘 IO，性能较低，但是数据安全

        为了保证安全，一般情况下，会独立执行作业

        为了能够提高效率，一般情况下，是需要和 cache 联合使用

        执行过程中，会切断血缘关系，重新建立新的血缘关系

        checkpoint 等同于改变数据源

#### 4.1.10 RDD 分区器

Spark 目前支持 Hash 分区和 Range 分区以及用户自定义分区，Hash 分区为当前默认分区

分区器决定了 RDD 中的分区个数，RDD 中每条数据经过 Shuffle 后进入哪个分区，进而决定了 Reduce 的个数

- 只有 Key-Value 类型的 RDD 才有分区器，非 Key-Value 类型的 RDD 分区的值是 None
- 每个 RDD 的分区 ID 范围：0 ~ (numPartitions-1)，决定这个值是属于哪个分区的

#### 4.1.11 RDD 文件读取与保存

Spark 数据读取及数据保存可以从两个维度来做区分：文件格式以及文件系统

文件格式分类：text，csv，sequence，Object

文件系统分类：local，HDFS，HBase，DB

```scala
sc.textFile()
sc.sequenceFile[(K, V)]()
sc.objectFile[(K, V)]()
```

### 4.2 ACC 累加器

#### 4.2.1 实现原理

累加器用来把 Executor 端变量信息聚合到 Driver 端，在 Driver 程序中定义的变量，在 Executor 端的每个 Task 都会得到这个变量的一个新的副本，每个 Task 更新这些副本的值后，传回 Driver 端进行 merge

### 4.3 广播变量

#### 4.3.1 实现原理

广播变量用来高效分发较大的对象，向所有节点发送一个较大的只读值，以供一个或多个 Spark 操作使用。（比如发送一个较大的只读查询表）



# Spark SQL

## 1. 关于 Spark SQL

### 1.1 什么是 Spark SQL

Spark SQL 是 Spark 用于结构化数据处理的 Spark 模块

### 1.2 Spark 与 Hive

Hive on Spark 与 Spark SQL

- Hive on Spark 还是 Hive 的那一套，只不过将 MapReduce 部分改成了 Spark
- Spark SQL 是 Spark 单独有的，不再受限于 Hive，兼容 Hive
- Spark SQL 提供了两个 编程抽象：`DataFrame` `DataSet` 

### 1.3 Spark SQL 特点

- 易整合

    无缝整合了 SQL 查询和 Spark 编程

- 统一的数据访问

    使用相同的方式连接不同的数据源

- 兼容 Hive

    在已有的仓库上直接运行 SQL 或者 HiveSQL

- 标准的数据连接

    通过 JDBC 或者 ODBC 来连接

### 1.4 DataFrame

DataFrame 是一种以 RDD 为基础的分布式数据集，类似于传统数据库中的二维表格

DataFrame 与 RDD 主要区别是在于，DataFrame 带有 schema 信息，即每一列都带有名称和类型

DataFrame 支持嵌套数据类型（array，struct，map）

DataFrame 也是懒执行的，在性能上比 RDD 要高（查询计划通过 Spark catalyst optimizer）进行优化

### 1.5 DataSet

DataSet 是分布式数据集合，是 DataFrame 的一个扩展，可以使用功能性转换（map，flatMap，filter 等）

- DataSet 是 DataFrame API 的一个扩展，是 SparkSQL 最新的数据抽象
- 用户友好的 API 风格，既具有类型安全检查，也具有 DataFrame 优化查询的特性
- 用样例类来对 DataSet 中定义数据的结构信息，样例类中每个属性的名称直接映射到 DataSet 中的字段名称（ORM ?）
- DataSet 是强类型的，即 DataSet[T]
- DataFrame 是 DataSet 的特例，`DataFrame = DataSet[Row]`，所以可以通过 as 方法将 DataFrame 转换为 DataSet

## 2. Spark SQL 编程

### 2.1 Context

Spark Core 中使用的是 SparkContext

Spark SQL 提供了两个 SQL 查询起始点：SQLContext，HiveContext

SparkSession 将 SQLContext 与 HiveContext 整合在一起，通过 SparkContext 完成计算

### 2.2 DataFrame

DataFrame API 可以不需要注册临时表或者生成 SQL 表达式，既有 transformation 操作，也有 action 操作

#### 2.2.1 DataFrame 创建

- 从文件读取

    ```shell
    > spark.read.
    csv	format	jdbc	json	load	option	options	orc	parquet	schema	table	text	textFile
    ```

- 从 RDD 转换

- 从 Hive 中查询返回

#### 2.2.2 SQL 语法

```scala
val df = spark.read.json("xxx.json")
// 创建临时表
df.createOrReplaceTempView("xxx")
val sqlDF = spark.sql("<sql>")
// 结果展示
sqlDF.show()
// 创建全局表 在不同 Session 中访问  select ... from global_temp.xxx
df,createGlobalTempView("xxx")
```

#### 2.2.3 DSL 语法

```scala
val df = spark.read.json("xxx.json")
// 打印 Schema 信息
df.printSchema
df.select("<field>").show()
// 涉及到运算，每列必须用 $ 或者 ‘
df.select($"<field>", $"<field>"+1).show()
df.select('<field>, '<field>+1).show()
df.select($"<field>", $"<field>"+1 as "<new_name>").show()
df.select($"<field>", $"<field>" > 0).show()
df.filter($"<filed>" > 0).show()
df.groupBy("<field>").count.show
```

#### 2.2.4 RDD 转换为 DataFrame

```scala
val df = rdd.toDF("<field>");
val rddR = df.r
```

### 2.3 DataSet

DataSet 是具有强类型的数据集合，需要提供对应信息

#### 2.3.1 创建 DataSet

- 使用样例类创建 DataSet

    ```scala
    case class A(fieldA: type, fieldB: type)
    val list = List(A(x1, y1), A(x2, y2))
    val ds = list.toDS()
    ds.show()
    ```

- DataFrame 创建 DataSet

    ```scala
    case class Emp(fieldA: type, fieldB: type)
    val ds = df.as[Emp]
    ds.show()
    ds.toDF()
    ```

- RDD 创建

    ```scala
    val rdd = sc.makeRDD(List(Emp(x,y)))
    val ds = rdd.toDS()
    ds.rdd
    ```

### 2.4 RDD, DataFrame, DataSet 三者关系

Spark 1.0 => RDD

Spark 2.0 => DataFrame

Spark 3.0 => DataSet

#### 2.4.1 共性

- 都是 Spark 下的分布式弹性数据集
- 都有惰性机制，只有 Action 才会开始运算
- 有许多共同函数
- DataFrame 与 DataSet 都需要 `import spark.implicits` 
- 会根据 Spark 内存自动缓存运算
- 都有 partition 概念
- DataFrame 与 DataSet 都可以使用模式匹配获取各个字段的值和类型

#### 2.4.2 区别

- RDD
    - RDD 一般和 spark mllib 同时使用
    - RDD 不支持 sparksql 操作
- DataFrame
    - 与 RDD 和 DataSet 不同，DataFrame 每一行的类型固定为 Row，每一列的值没法直接访问，只有通过解析，才能获取各个字段的值
    - DataFrame 与 DataSet 一般不与 spark mllib 使用
    - DataFrame 与 DataSet 均支持 SparkSQL 操作
    - DataFrame 与 DataSet 支持一些特别方便的保存方式，比如 csv，可以带上表头
- DataSet
    - DataSet 与 DataFrame 拥有完全相同的成员函数，区别只是每一行的数据类型不同，DataFrame 其实就是 DataSet 的一个特例 `type DataFrame = DataSet[Row]` 
    - DataFrame 也可以叫 DataSet[Row]，每一行类型是 Row，不解析，每一行有哪些字段，什么字段是什么类型无从得知，只能用 getAS 方法或者共性中的第七条提到的模式匹配拿出特定的字段，而 DataSet 中，每一行是什么类型是不一定的，在自定义 `case class` 之后可以自由地获得每一行信息

### 2.5 UDF

通过 spark.udf 功能添加用户自定义函数，实现自定义功能

### 2.6 UDAF

通过继承 Aggregator 来定义强类型聚合函数

### 2.7 数据加载和保存

SparkSQL 提供了通用的保存数据和加载数据的方式，使用相同的 API，根据不同的参数读取和保存不同格式的数据，默认保存和读取的文件格式为 parquet

```scala
spark.read.format("")[.option("")].load("")
// format : csv, jdbc, json, orc, parquet, textFile
df.write.save/csv/jdbc/json/orc/parquet/textFile
// option . SaveMode
// ErrorIfExist | Append | Overwrite | Ignore
```

#### 2.7.1 Parquet

Parquet 是一种能够有效存储嵌套式数据的列式存储格式

`spark.sql.sources.default` 可以修改默认数据源格式

#### 2.7.2 JSON

Spark SQL 可以自动推测 JSON 数据集结构，并将它加载为一个 DataSet[Row]

### 2.7.3 CSV

读取 CSV 文件，CSV 第一行设置为数据列

```scala
spark.read.format("csv").option("sep", ",").option("inferSchema", "true").option("header", "true").load("")
```

### 2.7.4 MySQL

```scala
val df = spark.read.format("jdbc")
	.option("url", "jdbc:mysql://host:3306/db")
	.option("driver", "com.mysql.cj.jdbc.Driver")
	.option("user", "")
	.option("password", "")
	.option("dbtable", "table")
	.load()

spark.write.format("jdbc")
	.option("url", "jdbc:mysql://host:3306/db")
	.option("driver", "com.mysql.cj.jdbc.Driver")
	.option("user", "")
	.option("password", "")
	.option("dbtable", "table2")
	.mode(SaveMode.Append)
	.s()
```

#### 2.7.5 Hive

内置 Hive

```scala
// 会自动初始化 metadata
spark.sql("show tables;").show()
spark.sql("load data local inpath '' into table xxx")
```

 外部 Hive

- shell

    1. 把 `hive-site.xml` 文件放到 spark 的 conf/ 下
    2. 把 MySQL 驱动 放到 jars/ 下
    3. 如果访问不到 HDFS，需要把 `core-site.xml` 和 `hdfs-site.xml` 放到 conf/ 下

- code

    ```scala
    val spark = SparkSession.builder().enableHiveSupport().config(sparkConf).getOrCreate()
    ```

- beeline

    1. 按 shell 操作

    2. 启动 thrift server

        ```bash
        sbin/start-thriftserver.sh
        ```

    3. beeline 连接

        ```bash
        bin/beeline -u jdbc:hive2://host:port -n u
        ```

        
