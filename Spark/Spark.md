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
