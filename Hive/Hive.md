# Hive

## 1. Hive 基本概念

### 1.1 什么是 Hive

- Hive 简介

    - Hive 是由 Facebook 开源用于解决海量结构化日志的数据统计工具

    - Hive 是基于 Hadoop 的一个数据仓库工具，可以将结构化的数据文件映射为一张表，并提供类 SQL 查询功能

- Hive 本质

    将 HQL 转化为 MapReduce 程序

    Hive 处理的数据存储在 HDFS

    Hive 分析的数据底层的实现是 MapReduce

    执行程序运行在 Yarn 上

    1. 数据仓库通过 SQL 进行统计分析
    2. 将 SQL 语言中常用的操作（select，where，group 等）用 MapReduce 写成很多模板
    3. 用户根据业务需求编写相应的 SQL 语句
    4. 所有的 MapReduce 模板封装在 Hive 中
    5. 通过 Hive 框架匹配出相应的 MapReduce 模板
    6. 运行 MapReduce 程序生成相应的分析结果

### 1.2 Hive 的优缺点

- 优点
    - 操作接口采用类 SQL 语法，提供快速开发的能力（简单，容易）
    - 避免了去写 MapReduce，减少了开发人员的学习成本
    - Hive 的执行延迟比较高，因此 Hive 常用于数据分析，对实时性要求不高的场合
    - Hive 优势在于处理大数据，对于处理小数据没有优势，因为 Hive 的执行延迟比较高
    - Hive 支持用户自定义函数，用户可以根据自己的需求来实现自己的函数
- 缺点
    - Hive 的 HQL 表达能力有限
        - 迭代式算法无法表达
        - 数据挖掘方面不擅长，由于 MapReduce 数据处理流程的限制，效率更高的算法却无法实现
    - Hive 的效率比较低
        - Hive 自动生成的 MapReduce 作业，通常情况下不够智能化
        - Hive 调优比较困难，粒度较粗

### 1.3 Hive 架构原理

- Meta store

    元数据包括：表名，表所属的数据库（默认的 default），表的拥有者，列/分区字段，表的类型（是否是外部表），表的数据所在目录等

    默认存储在自带的 derby 数据库中，推荐使用 MySQL 存储 Metastore

- Client

    用户接口，commande-line interface，JDBC/ODBC（jdbc 访问 hive），WEBUI（浏览器访问 hive）

    - CLI

    - JDBC/ODBC

    - Driver

        - SQL Parser

            解析器：将 SQL 字符串转换成抽象的语法树 AST，这一步一般都用第三方工具库完成，比如 antlr，对 AST 进行语法分析，比如表是否存在，字段是否存在，SQL 语义是否有误

        - Physical Plan

            编译器：将 AST 编译生成逻辑执行计划

        - Query Optimizer

            优化器：对逻辑执行计划进行优化

        - Execution

            执行器：把逻辑执行计划转换成可以运行的物理计划，对于 Hive 来说就是 MR/Spark

- Hadoop

    使用 HDFS 进行存储，MapReduce 进行计算

Hive 的运行机制

- 用户创建 table
- 通过映射关系向表中导数据到 hdfs
- Metastore 中记录着表对应文件的 path
- Hive 中的元数据库 Metastore
- 用户针对数据表进行数据分析：select ... from table where ...
- 用户创建表，将表与数据建立映射关系，编写 SQL 分析语句
- 将 SQL 语言解析成对应的 MapReduce 程序，并生成相应的 jar 包到 Hive 中的解析器
- 解析器查询输入文件的 path
- 最后通过 Hive 中的解析器传到 MapReduce 体系架构

### 1.4 Hive 和数据库比较

- 查询语言

    Hive 拥有类 SQL 语言 HQL，熟悉 SQL 可以方便地使用 Hive 开发

- 数据更新

    Hive 中不建议对数据地改写，所有数据都是在加载的时候确定好的，而数据库中的数据通常是需要经常进行修改的，因此可以使用 `insert` 和 `update` 修改数据

- 执行延迟

    Hive 在查询数据的时候，由于没有索引，需要扫描整个表，因此延迟较高，此外 Hive 依赖于 MapReduce，MapReduce 本身就是一个延迟较高的框架，相对地数据库地执行延迟较低（数据规模小），当数据规模大到超过数据库处理能力的时候，Hive 地并行计算才能体现出优势

- 数据规模

    Hive 建立在集群上可以利用 MapReduce 进行并行计算，可以支持很大规模的数据，而数据库的数据规模较小

## 2. Hive 安装

- export

- 解决 jar 冲突

    ```bash
    mv $HIVE_HOME/lib/log4j-slf4j-impl-2.10.0.jar $HIVE_HOME/lib/log4j-slf4j-impl-2.10.0.bak
    ```

- 初始化元数据

    ```bash
    bin/schematool -dbType derby -initSchema
    ```

- 启动

    ```bash
    bin/hive
    # 启动的目录必须和初始化 schema 的目录一致
    # Hive 默认使用的元数据库为 derby，开启 Hive 之后就会占用元数据库，
    ```

## 3. Hive 使用

- 基本使用

    ```hql
    show databases;
    show tables;
    create table <name>(id string);
    insert into <table> values(...);
    -- /user/hive/warehouse/<table>
    select * from <table>;
    ```

    
