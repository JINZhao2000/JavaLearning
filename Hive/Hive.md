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

注意

1. SQL 语言大小写不敏感（Linux 中？）
2. SQL 可以写在一行或多行
3. 关键字不能被缩写也不能分行
4. 各子句一般分行写
5. 使用缩进提升可读性（建议）

### 7.1 运算符

A+B, A-B, A*B, A/B, A%B. A&B, A|B, A^B, ~A

### 7.2 常用函数

- 总行数（count）
- 最大值（max）
- 最小值（min）
- 总和（sum）
- 平均值（avg）

### 7.3 比较运算符

A=B, A<=>B (都为 NULL 返回 TRUE，一边为 NULL 返回 FALSE), A<>B, A!=B, A<B, A<=B, A>B, A>=B, A [NOT] BETWEEN B AND C

A IS [NOT] NULL, IN (value1, value2, ...), A [NOT] LIKE B, A RLIKE B, A REGEXP B

### 7.4 Like 和 RLike

- 使用 Like 运算选择类似的值

- 选择条件可以包含字符或数字

    % 代表零个或多个字符

    _ 代表一个字符

- RLIKE

    可以通过 Java 正则表达式来指定匹配条件

### 7.5 逻辑运算符

AND, OR, NOT

### 7.6 JOIN

- 等值连接

    ```hql
    select ... from <tableA> join <tableB> on <tableA>.<fieldA> = <tableB>.<fieldA>;
    ```

- 内连接

    只有进行连接的两个表中都存在与连接条件相匹配的数据才会被保留下来

    ```hql
    select ... from <tableA> inner join <tableB> on <tableA>.<fieldA> = <tableB>.<fieldA>;
    ```

- 左外连接

    ```hql
    select ... from <tableA> left join <tableB> on <tableA>.<fieldA> = <tableB>.<fieldA>;
    ```

- 右外连接

    ```hql
    select ... from <tableA> right join <tableB> on <tableA>.<fieldA> = <tableB>.<fieldA>;
    ```

- 满外连接

    ```hql
    select ... from <tableA> full join <tableB> on <tableA>.<fieldA> = <tableB>.<fieldA>;
    ```

- 左内连接

    ```hql
    select ... from <tableA> left join <tableB> on <tableA>.<fieldA> = <tableB>.<fieldA> where <tableB>.<fieldA> is NULL;
    select ... frmo <tableA>
    where <tableA>.<fieldA> not in (
    	select <fieldA>
    	from <tableB>
    );
    ```

- 右内连接

    ```hql
    select ... from <tableA> r join <tableB> on <tableA>.<fieldA> = <tableB>.<fieldA> where <tableB>.<fieldA> is NULL;
    ```

- $(A \bigcup B)\setminus(A \bigcap B)$ 

    ```hql
    select xxx from <tableA> full join <tableB> on <tableA>.<fieldA> = <tableB>.<fieldA> 
    where <tableA>.<fieldA> is null or <tableB>.<fieldA> is null;
    ```

- `union` 去重

- `union all` 不去重

- 笛卡尔积

    ```hql
    select xxx from <tableA>, <tableB>;
    ```

## 8. 排序

- 全局排序（order by，只有一个 Reducer）

    ```hql
    select xxx order by xxx [ASC | DESC];
    ```

- 每个 Reduce 内部排序（sort by）

    每个 Reducer 产生一个排序文件，每个 Reducer 内部进行排序，对全局结果集来说不是排序

    - 设置 reduce 个数

        ```bash
        set mapreduce.job.reduce=x;
        ```

    - 查看 reduce 个数

        ```bash
        set mapreduce.job.reduces;
        ```

- 分区（distributed by）

    有些情况下，需要控制某个特定行应该到哪个 reducer，通常是为了进行后续的聚集操作，distributed by 类似 MR 中 partition（自定义分区）进行分区，结合 sort by 使用（对于 distributed by，一定要分配多 reduce 进行处理，否则无法看到 distributed by 的效果）

    - distributed by 的分区规则是根据分区字段的 hash 码与 reduce 的个数进行模除后，余数相同的分到一个区
    - Hive 要求 distributed by 语句要写在 sort by 语句之前

- cluster by

    当 distributed by 和 sort by 的字段相同时，可以用 cluster by 的方式（排序只是升序排序）

## 9. 分区表与分桶表

### 9.1 分区表

分区表实际上就是对应一个 HDFS 文件系统上的独立的文件夹，该文件夹下是该分区所有的数据文件。Hive 中分区就是分目录，把一个大的数据集根据业务需要分割成小的数据集，在查询时通过 where 子句中的表达式选择查询所需要的指定的分区来提升效率

- 创建分区表

    ```hql
    create table <table>(...)
    partitioned by (<field> <type>)
    row format delimited fields terminated by '\t';
    ```

- 读取数据

    ```hql
    load data local inpath '<path>' into table <table> partition(<field>='<value>');
    ```

- 查询数据

    ```hql
    select * from <table> where <field>=<value>
    ```

- 二级分区

    ```hql
    create table <table>(...)
    partitioned by (<field1> <type1>, <field2> <type2>);
    ```

- 动态分区调整

    关系型数据库中，对分区表 insert 数据的时候，数据库自动会根据分区字段的值，将数据插入到相应的分区中，Hive 也提供了类似的机制，即动态分区，只不过使用 Hive 的动态分区，需要进行相应的配置

    - 开启动态分区参数配置

        ```shell
        # 开启动态分区功能（默认 true）
        hive.exec.dynamic.partition=true
        # 设置为非严格模式（动态分区的模式，默认 strict，表示必须指定至少一个分区为静态分区，notstrict 模式表示允许所有的分区字段都可以使用动态分区）
        hive.exec.dynamic.partition=nonstrict
        # 在所有执行 MR 的节点上，最大一共可以创建多少个动态分区，默认 1000
        hive.exec.max.dynamic.partitions=1000
        # 在每个执行 MR 的节点上，最大可以创建多少个动态分区。该参数需要根据实际数据来确定
        # 比如：源数据中包含了一年的数据，即 day 字段有 365/366 个值，那么该参数就需要设置成大于 365， 如果使用默认值 100，就会报错
        hive.exec.max.dynamic.partitions.pernode=100
        # 整个 MR Job 中，最大可以创建多少个 HDFS 文件，默认 100 000
        hive.exec.max.created.files=100000
        # 当有空的分区生成的时候，是否抛出异常。默认 false
        hive.error.on.empty.partition
        ```


### 9.2 分桶表

分区提供一个隔离数据和优化查询的便利方式。不过，并非所有的数据集都可形成合理的分区，对于一张表或者分区，Hive 可以进一步组织成桶，也就是更为细粒度的数据范围划分

> 分桶是将数据集分解成更容易管理的若干部分的另一个技术
>
> 分区针对的是数据的存储路径，分桶是针对的数据文件

- 建表

    ```hql
    create table <table>(...)
    clustered by(<col>)
    into to <num> buckets
    row format delimited fields terminated by '\t';
    -- 分区表的字段不能与表中字段相同，而分桶可以
    ```

- 分桶规则

    Hive 分桶采用对分桶字段的值进行 Hash，然后除以桶的个数求余的方式决定该条记录存放在哪个桶中

- 注意事项

    - reduce 的个数设置为 -1，让 job 自行决定需要用多少个 reduce 或将 reduce 的个数设置为大于等于分桶表的个数
    - 从 hdfs 中 load 数据到分桶表中，避免本地文件找不到问题
    - 不要使用本地模式

### 9.3 抽样查询

对于非常大的数据集，有时用户需要使用的是一个具有代表性的查询结果，而不是全部的结果，Hive 可以通过对表进行抽样查询来满足需求

```hql
select xxx from tablesample(bucket <number bucket/numerator> out of <number sdenominator>);
```

## 10. 函数

### 10.1 系统内置函数

- 查看系统自带函数

    ```hql
    show functions;
    ```

- 显示自带函数

    ```hql
    desc function <func_name>;
    ```

- 详细显示自带的函数用法

    ```hql
    desc function extended <func_name>;
    ```

- Hive 中函数分为 UDF，UDAF，UDTF

    > UDF 一个输入一个输出
    >
    > UDAF 多个输入一个输出
    >
    > UDTF 一个输入多个输出
    >
    > 
    >
    > 一和多指的是输入数据的行数，所以 NVL 也是 UDF

### 10.2 常用内置函数

- 空字段赋值（NVL）

    给值为 NULL 的数据赋值，格式是 `NVL(value, default_value)`，它的功能是如果 value 为 NULL，则 NVL 函数返回 default_value 的值，如果两个参数

- CASE WHEN THEN ELSE END

    ```hql
    case <field> when <field_value1> then <display_value1> else <display_value2> end;
    ```

- IF

    ```hql
    if(<condition>, <condition=true => value>, <condition=false => value>);
    ```

- 行转列

    `CONCAT(string A/col, string B/col, ...)`：返回输入字符串连接后的结果，支持输入任意个字符串

    `CONCAT_WS(separator, str1, str2, ...)`：它是一个特殊形式的 `CONCAT()`，第一个参数剩余参数间的分隔符，分隔符可以是与剩余参数一样的字符串，如果分隔符是 NULL，返回值也将为 NULL，这个函数会跳过分隔符后的任何 NULL 和空字符串，分隔符将被加到被连接的字符串之间

    > concat_ws must be "string" or "array\<string>"

    `COLLECT_SET(col)`：函数只接受基本数据类型，它的主要作用是将某个字段的值进行去重汇总，产生 Array 类型的字段
    
- 列转行

    `EXPLODE(col)`：将 Hive 一列中复杂的 Array 或者 Map 结构拆分成多行

    `LATERAL VIEW`：`LATERAL VIEW udtf(expression) tableAlias AS columnAlias` 用于 split，explode 等 UDTF 一起使用，它能够将一列数据拆分成多行数据，在此基础上可以对拆分后的数据进行聚合

    ```hql
    select fieldA, fieldB_exploded
    from tableA
    lateral view explode(split(field_str, ",")) table_tmp as fieldB_exploded
    ```

- 窗口函数（开窗函数）

    - `OVER()`：指定分析函数的工作的数据窗口大小，这个数据窗口大小可能会随着行的变化而变化

        ```hql
        select
        	fieldA,
        	count(*) over()
        from
        	table
        group by fieldA;
        -- group by 之后先有 fieldA，再对 fieldA 中的数据进行 count(*)
        
        select
        	fieldA,
        	fieldB,
        	fieldC,
        	sum(fieldC) over(partition by fieldA, fieldB)
        from tableA;
        
        select
        	fieldA,
        	fieldB,
        	fieldC over(partition by fieldA order by fieldB
        		rows between UNBOUNDED PRECEDING and current row
        	)
        from tableA;
        ```

    - `CURRENT ROW`：当前行
    
    - `N PRECEDING`：往前 n 行数据
    
    - `N FOLLOWING`：往后 n 行数据
    
    - `UNBOUNDED`：起点
    
        - `UNBOUNDED PRECEDING`：表示从前面的起点
        - `UNBOUNDED FOLLOWING`：表示到后面的终点
    
    - `LAG(col, n. default_val)`：往前第 n 行数据
    
        ```hql
        select
        	fieldA,
        	fieldB,
        	lag(fieldB, 1) over(partition by fieldA order by fieldB)
        from
        	tableA;
        ```
    
    - `LEAD(col, n, default_val)`：往后第 n 行数据
    
    - `NTILE(n)`：把有序窗口的行分发到指定数据的组中，各个组有编号，编号从 1 开始，对于每一行，NLITE 返回此行所属的组的编号（n 必须为 int 类型）
    
        ```hql
        select
        	fieldA,
        	fieldB,
        	fieldC,
        	nlite(5) over(order by fieldB) other_name
        from
        	tableA
        where
        	other_name=int;
        ```
    
    - Rank：`rank_func() over(order by xxx)` 
    
        - `RANK()`：排序相同时会重复，总数不会变 1 1 3
        - `DENSE_RANK()`：排序相同时会重复，总数会减少 1 1 2
        - `ROW_NUMBER()`：会根据顺序计算 1 2 3

- 其它常用函数

    ```hql
    unix_timestamp：返回当前或指定时间的时间戳(deprecated: current_timestamp)
    	select unix_timestamp();
    	select unix_timestamp("2021-01-01", "yyyy-MM-dd");
    from_unixtime：将时间戳转换为日期格式
    current_date：当前日期
    current_timestamp：当前的日期加时间
    to_date：抽取日期部分
    year：获取年
    month：获取月
    day：获取日
    hour：获取时
    minute：获取分
    second：获取秒
    weekofyear：当前时间是一年中的第几周
    dayofmonth：当前时间是一个月中的第几天
    months_between：两个日期之间的月份
    add_months：日期加减月
    datediff：两个日期相差的天数
    date_add：日期加天数
    date_sub：日期减天数
    last_day：日期的当月的最后一天
    date_format()：格式化日期
    
    取整函数
    round
    ceil
    floor
    
    字符串函数
    upper
    lower
    length
    trim
    lpad：向左补齐，到指定长度
    rpad：向右补齐，到指定长度
    regexp_replace
    
    集合操作
    size
    map_keys：返回 map 中的 key
    map_values：返回 map 中的 value
    array_contains
    sort_array
    
    grouping sets：多维分析
    
    select a, b sum(c) from tab1 group by a, b grouping sets((a,b), a)
    ===
    select a, b sum(c) from tab1 group by a, b
    union
    select a, b sum(c) from tab1 group by a
    ```

### 10.3 用户自定义函数

> UDF - User Defined Function
>
> UDAF - User Defined Aggregation Function
>
> UDTF - User Defined Table-Generating Function

- 读取 jar 包

    ```hql
    add jar <local_file_path>;
    create temporary function <function_name> as "<full_class_name>";
    ```

## 11. 压缩和存储

=> Hadoop 压缩

- 文件存储格式

    > TEXTFILE ROW
    >
    > SEQUENCEFILE ROW
    >
    > ORC COLUMN
    >
    > PARQUET COLUMN

- 列式存储和行式存储

    > 行存储的特点
    >
    > 查询满足条件的一整行数据的时候列存储则需要每个聚集字段找到对应的列的值，行存储只需要找到其中一个值，其余的值都在相邻的地方没所以此时行查询速度比较快

- TEXTFILE

    默认格式，数据不做压缩，磁盘开销大，数据解析开销大，可结合 Gzip，Bzip2 使用，但使用 Gzip 这种方式，hive 不会对数据进行切分，从而无法对数据进行并行操作

- ORC（Optimized Row Colimnar）

    每个 ORC 文件由一个或者多个 stripe 组成，每个 stripe 一般为 HDFS 的块大小，每一个 stripe 包含多条记录，这些记录按照列进行独立存储，对应到 Parquet 中的 row group 的概念，，每个 stripe 里有三部分组成，分别是 Index Data，Row Data，Stripe Footer

    - Index Data

        一个轻量级 Index，默认是每隔 1w 行做一个索引，这里做的索引应该只是记录某行的各字段在 Row Data 中的 offset

    - Row Data

        存的是具体数据，先取部分行，然后对这些列按列进行存储，对每个列进行了编码分成多个 Stream 来存储

    - Stripe Footer

        存的是各个 Stream 的类型，长度等信息

        每个文件有一个 File Footer，这里面存的是每个 Stripe 的行数，每个 Column 的数据类型信息等，每个文件的尾部是一个 PostScript，这里面记录了整个文件的压缩类型以及 File Footer 的长度信息等，在读取文件时，会 seek 到文件尾部读 PostScript，从里面解析到 File Footer 的长度，再读 File Footer，从里面解析到各个 Stripe 的信息，再读各个 Stripe，即从前往后读

- Parquet

    Parquet 的文件是以二进制方式存储的，所以是不可以直接读取的，文件中包括该文件的数据和元数据，因此 Parquet 格式的文件是自解析的

    - 行组（Row Group）

        每一个行组都包含一定的行数，在一个 HDFS 文件中至少存储一个行组，类似于 ORC 的 stripe 的概念

    - 列块（Column Chunk）

        在一个行组中每一列保存在一个列块中，行组中的所有列连续的存储在这个行组文件中，一个列块中的值都是相同类型的，不同的列块可能使用不同的算法进行压缩

    - 页（Page）

        每一个列块划分为多个页，一个也是最小的编码的单位，在同一个列块的不同页可能使用不同的编码方式

    通常情况下，在存储 Parquet 数据的时候会按照 Block 大小设置行组的大小，由于一般情况下，每一个 Mapper 任务处理数据的最小单位是一个 Block，这样可以把每一个行组由一个 Mapper 任务处理，增大任务执行的并行度，

- 存储对比 ORC > Parquet > TextFile

## 11. 调优

### 11.1 执行计划（Explain）

- 语法

    ```hql
    EXPLAIN [EXTENDED|DEPENDENCY|AUTHORIZATION] query
    ```

### 11.2 Fetch 抓取

Fetch 抓取是指，Hive 中对某些情况的查询可以不必使用 MapReduce 计算，例如 `select * from tab`，在这种情况下，Hive 可以简单读取 employee 对应的存储目录文件，然后输出查询结果到控制台

在 `hive-default.xml.template` 文件中 `hive.fetch.task.conversion` 默认是 more，老版本 Hive 默认是 minimal，该属性修改为 more 之后，在全局查找，字段查找，limit 查找等都不走 MapReduce

### 11.3 本地模式

大多数 Hadoop Job 是需要 Hadoop 提供完整的可扩展性来处理大数据集的，不过有时的 Hive 的输入数据量是非常小的，在这种情况下，为查询触发执行任务消耗的时间可能会比实际 job 执行的时间要多得多，对于大多数这种情况，Hive 可以通过本地模式在单台机器上处理所有的任务，对于小数据集，执行时间可以明显被缩短

`hive.exec.mode.local.auto=true`：开启本地 MR，默认 false

`hive.exec.mode.local.auto.inputbytes.max=50000000`：最大输入数据量，小于这个值采用 local MR，默认值：134217728（128M）

`hive.exec.mode.local.auto.input.files.max=10`：最大输入文件个数，默认为 4

### 11.4 表的优化

- 小表 Join 大表（MapJoin）

    将 key 相对分散，并且数据集较小的表放在 join 的左边，可以使用 map join 让小的维度的表先进内存，在 map 端完成 join（当前版本 Hive 已经对 join 进行了优化，放左边右边已经没有区别）

    - MapJoin 参数设置

        ```hql
        -- 设置自动选择 MapJoin，默认为 true
        set hive.auto.convert.join = true
        -- 大表小表的阈值设置（默认 25M 以下认为是小表）
        set hive,mapjoin.smalltable.filesize = 25000000
        ```

    - MapJoin 机制

        <img src="./images/mapjoin.png"/> 

        - Task A，它是一个 Local Task（在客户端本地执行的 Task），负责扫描小表 b 的数据，将其转换成一个 HashTable 的数据结构，并写入本地文件中，之后将该文件加载到 DistributeCache 中
        - Task B，该任务是一个没有 Reduce 的 MR，启动 MapTask 扫描大表 a，在 Map 阶段，根据 a 的每一条记录去和 DistributedCache 中的 b 表对应的 HashTable 关联，并直接输出结果
        - 由于 MapJoin 没有 Reduce，所以由 Map 将直接输出结果文件，有多少个 Map Task，就有多少个结果文件

- 大表 Join 大表

    - 空 key 过滤

        有时 join 超时是因为某些 key 对应的数据太多，而相同的 key 对应的数据都会发送到相同的 reducer 上，从而导致内存不够，很多情况下，这些 key 对应的数据是异常数据，需要在 SQL 中进行过滤，例如 key 对应的字段为空（非 inner join 不需要数据为 null 的）

    - 空 key 转换

        有时虽然某个 key 为空对应的数据很多，但是相应的数据不是异常数据，必须要包含在 join 结果中，此时可以表 a 中 key 为空的字段赋一个随机的值，使得数据随机均匀地分到不同地 reducer 上
        
    - SMB（Sorted Merge Bucket Join）
    
- Group by

    默认情况下，Map 阶段同一 key 数据分发给一个 reduce，当一个 key 数据过大时就会发生数据倾斜

    并不是所有的聚合操作都需要在 reduce 端完成，很多聚合操作都可以现在 Map 端运行部分聚合，最后在 reduce 端得出结果

    - 开启 Map 端集合参数设置

        ```hql
        -- 是否在 Map 端进行聚合 默认为 true
        set hive.map.aggr = true
        -- 在 Map 端进行聚合操作的条目个数
        set hive.groupby.mapaggr.checkinterval = 100000
        -- 有数据倾斜的时候进行负载均衡，默认为 false
        set hive.groupby.skewindata = true
        ```

        当 skewindata 设定为 true 时，生成的查询计划会生成两个 MR Job，第一个 MR Job 中，Map 的输出结果会随机分配到 Reduce 中，每个 Reduce 做部分聚集操作，并输出结果，这样处理的结果是相同的 Group By Key 有可能被分到不同的 Reduce 中，从而达到均衡的目的，第二个 MR Job 再根据预处理的数据结果按照 Group By Key 分到 Reduce 中（这个过程可以保证相同的 Group By Key 被分到同一个 Reduce 中），最后完成聚合操作

- Count（Distinct）去重统计

    数据量大的时候，由于 COUNT 和 DISTINCT 操作需要用一个 Reduce Task 来完成，这一个 Reduce 需要处理的数据量太大，就会导致整个 Job 很难完成，一般 COUNT DISTINCT 使用先 GROUP BY 再 COUNT 的方式替换，但是要注意 GROUP BY 造成的数据倾斜的问题

- 笛卡尔积

    尽量避免笛卡尔积，join 的时候不加 on 条件，或者无效的 on 条件，Hive 只能使用 1 个 reducer 来完成笛卡尔积

- 行列过滤

    列处理：在 SELECT 中，只拿需要的列，如果有分区，尽量使用分区过滤，少用 SELECT *

    行处理：在分区裁剪中，当使用外联时，如果将副表的过滤条件写在 where 后面，那么就会先全表关联，然后再过滤

- 分区 => 9

- 分桶 => 9

