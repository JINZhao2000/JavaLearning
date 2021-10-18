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

- MySQL 配置（目的是多个客户端同时访问）

    先将 mysql 的 jdbc 拷贝到 `$HIVE_HOME/lib` 下

    然后在 `$HIVE_HOME/conf` 下创建 `hive-site.xml` 

    ```xml
    <?xml version="1.0"?>
    <?xml-stylesheet type="text/xsl" href="configuration.xsl"?>
    <configuration>
        <property>
            <name>javax.jdo.option.ConnectionURL</name>
            <value>jdbc:mysql://xxx:3306/hadoop?useSSL=false</value>
        </property>
        <property>
            <name>javax.jdo.option.ConnectionDriverName</name>
            <value>com.mysql.cj.jdbc.Driver</value>
        </property>
        <property>
            <name>javax.jdo.option.ConnectionUserName</name>
            <value>hadoop</value>
        </property>
        <property>
            <name>javax.jdo.option.ConnectionPassword</name>
            <value>Hadoop2021</value>
        </property>
        <property>
            <name>hive.metastore.schema.verification</name>
            <value>false</value>
        </property>
        <property>
            <name>hive.metastore.event.db.notification.api.auth</name>
            <value>false</value>
        </property>
        <property>
            <name>hive.metastore.warehouse.dir</name>
            <value>/user/hive/warehouse</value>
        </property>
    </configuration>
    ```

- 再次初始化带有 MySQL 的 Hive

    ```bash
    schematool -initSchema -dbType mysql -verbose
    ```


### 3.1 使用元数据服务访问 Hive

1. 在 hives-site.xml 中添加配置

    ```xml
    <property>
    	<name>hive.metastore.uris</name>
        <value>thrift://hadoop01:9803</value>
    </property>
    ```

2. 开启服务

    ```bash
    hive --service metastore
    ```

### 3.2 使用 JDBC 访问 Hive

1. 在 hive-site.xml 中添加配置

    ```xml
    <property>
    	<name>hive.server2.thrift.bind.host</name>
        <value>hadoop01</value>
    </property>
    <property>
    	<name>hive.server2.thrift.port</name>
        <value>10000</value>
    </property>
    ```

    防止连接被拒绝在 core-site.xml 中配置并重启 hadoop

    ```xml
    <property>
    	<name>hadoop.proxyuser.root.hosts</name>
    	<value>*</value>
    </property>
    <property>
    	<name>hadoop.proxyuser.root.groups</name>
    	<value>*</value>
    </property>
    ```

2. 启动 hiveserver2

    ```bash
    hive --service hiveserver2
    ```

3. 启动客户端

    ```bash
    beeline -u jdbc:hive2://hadoop01:10000 -n root
    ```

### 3.3 Hive 启动脚本

```shell
#!/bin/bash
HIVE_LOG_DIR=$HIVE_HOME/logs
if [ ! -d $HIVE_LOG_DIR ];then
	mkdir -p $HIVE_LOG_DIR
fi

function check_process() {
	pid=$(ps -ef 2>/dev/null | grep -v grep | grep -i $1 | awk '{print $2}')
	ppid=$(netstat -nltp 2>/dev/null | grep $2 | awk '{print $7}' | cut -d '/' -f 1)
	echo $pid
	[[ "$pid" =~ "$ppid" ]] && [ "$ppid" ] && return 0 || return 1
}

function hive_start() {
	metapid=$(check_process HiveMetastore 9083)
	cmd="nohup hive --service metastore >$HIVE_LOG_DIR/metastore.log 2>&1 &"
	[ -z "$metapid" ] && eval $cmd || echo "Metastore start"
	server2pid=$(check_process HiveServer2 10000)
	cmd="nohup hive --service hiveserver2 >$HIVE_LOG_DIR/hiveServer2.log 2>&1 &"
	[ -z "$server2pid" ] && eval $cmd || echo "HiveServer2 start"
}

function hive_stop() {
	metapid=$(check_process HiveMetastore 9083)
	[ "$metapid" ] && kill $metapid || echo "Metastore stop"
	server2pid=$(check_process HiveServer2 10000)
	[ "$server2pid" ] && kill $server2pid || echo "HiveServer2 stop"
}

case $1 in
"start")
	hive_start
	;;
"stop")
	hive_stop
	;;
"restart")
	hive_stop
	sleep 2
	hive_start
	;;
"status")
	check_process HiveMetastore 9083 >/dev/null && echo "Metastore is running" || echo "Metastore is not running"
	check_process HiveServer2 10000 >/dev/null && echo "HiveServer2 is running" || echo "HiveServer2 is not running"
	;;
*)
	echo "Invalid Args"
	echo "Usage : '$(basename $0)' start | stop | restart | status"
	;;
esac
```

### 3.4 其它交互方式

- command

    ```bash
    hive -e <sql in command>
    hive -f <sql file>
    ```

- 查看 hdfs 系统

    ```bash
    hive> dfs -ls /;
    ```

- 历史命令路径：`.hivehistory` 

### 3.5 Hive 配置文件

- Log 默认在 `/tmp/user/hive.log` 下

    修改 `hive-log4j2.properties` 

    ```properties
    hive.log.dir = /xxxxx
    ```

- 打印库和表头

    ```xml
    <property>
    	<name>hive.cli.print.header</name>
        <value>true</value>
    </property>
    <property>
    	<name>hive.cli.print.current.db</name>
        <value>true</value>
    </property>
    ```

- 修改配置文件的 3 种方法

    - 全局参数修改 `hive-site.xml` 

    - 启动客户端前参数

        ```bash
        hive --hiveconf xxx=xxx;
        ```

    - 在启动客户端后修改

        ```hql
        set xxx=xxx;
        ```

## 4. Hive 数据类型

### 4.1 基本数据类型

| Hive 数据类型 | Java 数据类型 | 长度   | 例子               |
| ------------- | ------------- | ------ | ------------------ |
| TINYINY       | byte          | 1 byte | 20                 |
| SMALLINT      | short         | 2 byte | 20                 |
| INT           | int           | 4 byte | 20                 |
| BIGINT        | long          | 8 byte | 20                 |
| BOOLEAN       | boolean       | 1 bit  | TRUE, FALSE        |
| FLOAT         | float         | 4 byte | 3.14               |
| DOUBLE        | double        | 8 byte | 3.14               |
| STRING        | string        |        | 'string', "string" |
| TIMESTAMP     |               |        |                    |
| BINARY        |               |        |                    |

### 4.2 集合数据类型

| 数据类型 | 描述          | 语法                                      |
| -------- | ------------- | ----------------------------------------- |
| STRUCT   | 与 C 语言类似 | struct() <br>struct<name:string, age:int> |
| MAP      | K -> V        | map() <br>map<string, int>                |
| ARRAY    | [...]         | Array() <br>array\<string>                |

举例

```hql
create table test(
	name string,
	relations array<string>,
	properties map<string, int>
	action struct<name:string, time:int>
)
row format delimited fields terminated by ','
collection items terminated by '_'
map keys terminated by ';'
lines terminated by '\n';

select relations[0] from test;
select properties['key'] from test;
select action.n
```

### 4.3 类型转化

Hive 的原子数据类型是可以进行隐式转换的，类似于 Java 的类型转换，例如 TINYINT 会自动转换为 INT 类型，但是 Hive 不会进行反向转化，除非用 CAST

- 隐式类型转换规则

    1. 任何整数类型都可以隐式转换为一个范围更广的类型，如 TINYINT 可以转换成 INT，INT 可以转换成 BIGINT
    2. 所有整数类型，FLOAT 和 STRING 类型都可以隐式转换成 DOUBLE
    3. TINYINT，SMALLINT，INT 都可以转换为 FLOAT
    4. BOOLEAN 类型不可以转换为任何其它的类型

- 可以使用 CAST 操作显示进行数据类型转换

    ```hql
    CAST('1' AS INT) -- 1
    CAST('X' AS INT) -- NULL
    ```

## 5. DDL 数据定义

### 5.1 创建数据库

```hql
CREATE DATABASE [IF NOT EXISTS] <db_name>
[COMMENT <db_comment>]
[LOCATION <hdfs_path>]
[WITH DBPROPERTIES (<property_name>=<property_value>, ... )];
```

默认数据库创建在 /user/hive/warehouse/*.db

### 5.2 查询数据库

```hql
-- 显示数据库
show databases;
-- 过滤显示数据库
show databases like 'xxx';
-- 显示数据库信息
desc database <db_name>;
-- 显示数据库详细信息
desc database extended <db_name>;
-- 切换数据库
use <db_name>;
```

### 5.3 修改数据库

```hql
-- 修改 properties
alter database <db_name> set dbproperties('createtime'='20210101');
```

### 5.4 删除数据库

```hql
-- 删除空数据库
drop database <db_name>
drop database if exists <db_name>
-- 数据库不为空
drop database <db_name> cascade;
```

### 5.5 创建表

```hql
CREATE [EXTERNAL] TABLE [IF NOT EXISTS] <table_name>
[(<col_name> <data_type> [COMMENT <comment>], ...)]
[COMMENT <table_comment>]
[PARTITIONED BY (<col_name> <data_type> [COMMENT <col_comment>], ...)]
[
CLUSTERED BY (<col_name>, <col_name2>, ....)
[SORTED BY (<col_name> [ASC|DESC], ...)] INTO <num_buckets> BUCKETS
]
[ROW FORMAT <row_format>]
[STORED AS <file_format>]
[LOCATION <hdfs_path>]
[TBLPROPERTIES (<property_name>=<property_value>, ...)]
[AS <select_statement>];
```

- EXTERNAL：外部表

    - 管理表

        - 默认创建的都是管理表，也就是内部表，因为这种表，Hive 会控制着数据的生命周期，Hive 默认情况下会将这些表的数据存储在由配置项 `hive.metastore.warehouse.dir` 所定义的目录的子目录下
        - 当删除管理表时，Hive 会删除这个表中的数据，不适合和其他工具共享数据

    - 外部表

        - 因为表是外部表，Hive 不认为完全拥有这份数据，删除该表不会删除掉这份数据，不过描述表的元数据信息会被删除掉

    - 相互转换

        ```hql
        -- 查询表的类型
        desc formatted <table_name>;
        -- 修改内部表的类型
        alter table <table_name> set tblproperties('EXTERNAL'='TRUE');
        -- 修改外部表的类型
        alter table <table_name> set tblproperties('EXTERNAL'='FALSE');
        ```

- IF NOT EXISTS：是否存在，不存在才创建

- COMMENT：注释

- PARTITIONED BY：分区表

- CLUSTERED BY：分桶表

- SORTED BY：-> 分桶表

- ROW FORMAT：行格式（connected by）

    ```hql
    CREATE TABLE xxx (xxx ...)
    ROW FORMAT DELIMITED FIELDS TERMINATED BY ',';
    ```

- STORED AS：压缩方式（默认 text）

- LOCATION：指定表的存储位置

- TBLPROPERTIES：表属性

- AS：查询方式建表

### 5.6 修改表

```hql
-- 重命名表
ALTER TABLE <table_name> RENAME TO <table_name_new>;
-- 增加/修改/替换列信息
ALTER TABLE <table_name> CHANGE [COLUMN] <col_old_name> <col_new_name>;
ALTER TABLE <table_name> ADD | REPLACE COLUMNS (<col_name> <data_type> [COMMENT <col_comment>], ...);
```

### 5.7 删除表

```hql
DROP TABLE <table_name>;
```

## 6. DML 数据操作

### 6.1 数据导入

- 向表中装载数据（Load）

    ```hql
    load data [local] inpath '<src_path>' [overwrite] into table <table_name> [partition (<part_col1>=<val1>, ...)];
    ```

    - load data：表示加载数据
    - local：表示从本地加载数据到 hive 表，否则从 HDFS 加载数据到 hive 表
    - inpath：表示加载数据的路径
    - overwrite：表示覆盖表中已有数据，否则就是追加
    - into table：表示加载到哪张表
    - partition：上传到指定分区
    
- 通过查询语句向表中插入数据（Insert）

    ```hql
    insert into table <table_name> values(<value1>)[, (value2), ...];
    insert overwrite table <table_name> select ....;
    from <table_name>
    insert overwrite table <table_name> partition(<name>=<value>)
    select xxxxx
    insert overwrite table <table_name> partition(<name>=<value>)
    select xxxxx;
    ```

- 查询语句中创建表并加载数据

    ```hql
    create table <table_name> as select xxx;
    ```

- 创建表时通过 Location 指定加载数据路径

    ```hql
    create external table <table_name> ()
    row format delimited fields terminated by <delimited>
    location <hdfs_path>;
    ```

- 第三方框架 import

    ```hql
    import table <table_name> from <location>;
    -- 要先 export z
    ```


### 6.2 数据导出

- Insert 导出

    - 查询结果导出到本地

    	```hql
    	insert overwrite local directory <local_location> select xxx;
    	```
    	
    - 查询结果格式化导出到本地
    
        ```hql
        insert overwrite local directory <local_location>
        row format dilimited fields terminated by '\t'
        select xxx;
        ```
    
    - 将查询的结果导出到 HDFS 上（没发 local）
    
        ```hql
        insert overwrite directory <hdfs_path>
        row format delimited fields terminated by '\t'
        select xxx;
        ```
    
 - Hadoop 命令导出到本地
   
   ```hql
   dfs -get <hdfs_path> <local_path>
   ```
   

- Hive Shell

    ```shell
    hive -e 'select xxx;' >> <file_name>
    ```

- Export 导出到 HDFS 上

    ```hq;
    export table <database_name>.<table_name> to <hdfs_location>;
    ```

- Sqoop 导出

- 清除表中数据

    ```hql
    truncate table <table_name>;
    -- 仅限管理表
    ```

## 7. 查询

查询语句语法

[Manual](https://cwiki.apache.org/confluence/display/Hive/LanguageManual+Select) 

```hql
[WITH CommonTableExpression (, CommonTableExpression)*]
SELECT [ALL | DISTINCT] select_expr, select_expr, ...
  FROM table_reference
  [WHERE where_condition]
  [GROUP BY col_list]
  [ORDER BY col_list]
  [CLUSTER BY col_list
    | [DISTRIBUTE BY col_list] [SORT BY col_list]
  ]
 [LIMIT [offset,] rows]
```

