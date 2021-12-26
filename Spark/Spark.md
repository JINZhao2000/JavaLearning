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

触发整个计算执行的方法

