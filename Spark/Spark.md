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

### 2.2 解压缩文件

