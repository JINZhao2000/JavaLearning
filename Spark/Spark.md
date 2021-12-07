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



