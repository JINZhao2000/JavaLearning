# Hadoop

## 1. 大数据概论

### 1.1 大数据概念

大数据：指无法在一定时间范围内用常规软件进行捕捉，管理和处理的数据集合，是需要新处理模式才能具有更强的决策力，洞察发现力和流程化能力的海量，高增长率和多样化的信息资产

大数据主要解决海量数据的采集，存储和分析计算问题

### 1.2 大数据的特点（4V）

1. Volume 大量

    截至目前，人类生产所有的印刷材料的数据量是 200 PB，而历史上人类总共说过的话的数据量大约是 5 EB，当前，典型个人计算机硬盘的容量为 TB 量级，而一些大企业的数据量已经接近 EB 量级

2. Velocity 高速

3. Variety 多样

    这种类型的多样性也让数据被分为结构化数据和非结构化数据，相对于以往便于存储的以数据库、文本为主的结构化数据，非结构化数据越来越多，包括网络日志，音频，视频，图片，地理位置信息等，这些多类型的数据对数据的处理能力提出了更高的要求

4. Value 低价值密度

    价值密度的高低与数据总量的大小成反比

    如何对有价值数据 “提纯” 成为目前大数据背景下待解决的难题

### 1.3 大数据的应用场景

- 内容推荐
- 商品搭配
- 物流仓储
- 精算预测
- AI 5G IoT VR AR

### 1.4 大数据部门内部的组织结构

- 平台组

    Hadoop，Flume，Kafka，HBase，Spark 等框架平台搭建

    集群性能监控

    集群性能调优

- 数据仓库组

    ETL 清洗工程师（数据清洗）

    数据分析，数据仓库建模

- 实时组

    实时指标分析与性能调优

- 数据挖掘组

    算法工程师

    推荐系统工程师

    用户画像工程师

- 报表开发组

    JavaEE 工程师

    前端工程师

## 2. Hadoop 入门

### 2.1 Hadoop 概念

#### 2.1.1 Hadoop 是什么

1. Hadoop 是一个由 Apache 基金会所开发的分布式系统基础架构
2. 主要解决，海量数据的存储和海量数据分析计算问题
3. 广义上来说，Hadoop 通常是指一个更广泛的概念 —— Hadoop 生态圈
    - Hadoop
    - HBase
    - Hive
    - Zookeeper
    - HAMA
    - Oozie
    - Thrift
    - Nutch
    - Solr
    - ......

#### 2.1.2 Hadoop 发展

1. Hadoop 创始人 Doug Cutting，为了实现与 Google 类似的全文搜索功能，他在 Lucene 框架基础上进行优化升级，查询引擎和搜索引擎
2. 2001 年年底，Lucene 成为 Apache 基金会的一个子项目
3. 对于海量数据的场景，Lucene 框架面对与 Google 同样的困难，存储海量数据难，检索海量速度慢
4. 学习和模仿 Google 解决这些问题的办法：微型版 Nutch
5. 可以说 Google 是 Hadoop 的思想之源
    - [GFS - HDFS](https://static.googleusercontent.com/media/research.google.com/zh-CN//archive/gfs-sosp2003.pdf) 
    - [Map-Reduce - MR](https://static.googleusercontent.com/media/research.google.com/zh-CN//archive/mapreduce-osdi04.pdf) 
    - [Bigtable - HBase](https://static.googleusercontent.com/media/research.google.com/zh-CN//archive/bigtable-osdi06.pdf) 
6. 2003 - 2004 年，Google 公开了部分 GFS 和 MapReduce 思想细节，以此为基础 Doug Cutting 等人用了 2 年业余时间实现了 DFS 和 MapReduce 机制，使 Nutch 性能提升
7. 2005 年 Hadoop 作为 Lucene 的子项目 Nutch 的一部分正式引入 Apache 基金会
8. 2006 年 3 月份，Map-Reduce 和 Nutch Distributed File System（NDFS）分别被纳入到 Hadoop 项目中，Hadoop 就此正式诞生，标志着大数据时代的来临
9. 名字来源于 Doug Cutting 儿子的玩具大象

#### 2.1.3 Hadoop 3 大发行版本

Apache，Cloudera，Hortonworks

Apache 版本最原始最基础的版本，对于入门学习最好 2006

Cloudera 内部集成了很多大数据框架，对应产品 CDH 2008

Hortonworks 文档较好，对应产品 HDP

Hortonworks 现在已经被 Cloudera 公司收购，推出新的品牌 CDP

#### 2.1.4 Hadoop 优势

1. 高可靠性：Hadoop 底层维护多个数据版本，所以即使 Hadoop 某个计算元素或存储出现故障，也不会导致数据的丢失
2. 高扩展性：在集群间分配任务数据，可方便地动态扩展数以千计地节点
3. 高效性：在 MapReduce 的思想下，Hadoop 是并行工作的，以加快任务处理速度
4. 高容错性：能够自动将失败的任务重新分配

#### 2.1.5 Hadoop 组成

- Hadoop 1.x
    - MapReduce 计算+资源调度
    - HDFS 数据存储
    - Common 辅助工具
    
- Hadoop 2.x
    - MapReduce 计算
    - Yarn 资源调度
    - HDFS 数据存储
    - Common 辅助工具
    
- Hadoop 3.x
    - MapReduce 计算
    - Yarn 资源调度
    - HDFS 数据存储
    - Common 辅助工具

- HDFS

    Hadoop Distributed File System 简称 HDFS，是一个分布式文件系统

    - NameNode（NN）：存储文件的元数据：如文件名，文件目录结构，文件属性（生成时间，副本数，文件权限），以及每个文件的块列表和块所在的 DataNode 等
    - DataNode（DN）：在本地文件系统存储文件块数据，以及块数据的校验和
    - Secondary NameNode（2NN）：每隔一段时间对 NameNode 元数据进行备份

- Yarn

    Yet Another Resource Nagotiator 简称 YARN，是一种资源协调者，是 Hadoop 的资源管理器

    - ResourceManager（RM）：整个集群资源（内存，CPU 等）的 master
    - NodeManager（NM）：单个节点资源的 master
    - ApplicationMaster（AM）：单个任务的 master
    - Container：容器，相当于一台独立的服务器，里面封装了任务运行所需要的资源，如内存，CPU，磁盘，网络
    
- MapReduce

    将计算过程分为两个阶段，Map 和 Reduce

    - Map 阶段并行处理输入数据
    - Reduce 阶段对 Map 结果进行汇总

#### 2.1.6 大数据生态体系

数据来源层

- 数据库（结构化数据）
- 文件日志（半结构化数据）
- 视频和 ppt 等（非结构化数据）

数据传输层

- Sqoop 数据传递
- Flume 日志收集
- Kafka 消息队列

数据存储层

- HDFS 文件存储
- HDFS 文件存储或 HBase 非关系型数据库
- Kafka 消息队列

资源管理层

- YARN 资源管理

数据计算层

- MapReduce 离线计算 - Hive 数据查询
- Spark Core 内存计算 - Spark Mlib 数据挖掘 / Spark SQL 数据查询 / Spark Streaming 实时计算 / Flink

任务调度层

- Oozie 任务调度
- Azkaban 任务调度

以上整体：Zookeeper 数据平台配置和调度

业务模型层

- 业务模型，数据可视化，业务应用

### 2.2 环境准备

#### 2.2.1 模板虚拟机的准备

#### 2.2.2 克隆

#### 2.2.3 JDK 与 Hadoop

### 2.3 生产集群搭建

#### 2.3.1 本地模式

#### 2.3.2 生产环境集群（分布式）

- 安全拷贝

    scp -r user@hostname:/path user@hostname:/path

- 同步

    rsync -av user@hostname:/path user@hostname:/path

- xsync 分发脚本

    ```bash
    #!/bin/bash
    
    if [ $# -lt 1 ]
    then
            echo Not Enough Argument!
            exit;
    fi
    
    for host in hadoop02 hadoop03 hadoop04
    do
            echo ===== $host =====
    
            for file in $@
            do
                    if [ -e $file ]
                            then
                                    pdir=$(cd -P $(dirname $file); pwd)
                                    fname=$(basename $file)
                                    ssh $host "mkdir -p $pdir"
                                    rsync -av $pdir/$fname $host:$pdir
                            else
                                    echo $file does not exist!
                    fi
            done
    done
    ```

- ssh 免密登录

    ```bash
    ssh-keygen -t -rsa
    ssh-copy-id hostname
    ```

- 集群配置

    - 集群部署计划

        > NameNode 和 SecondaryNameNode 不要安装在一台服务器
        >
        > ResourceManager 也很消耗内存，不要和 NameNode 和 SecondaryNameNode 配置在一台机器上

        |      | hadoop01              | hadoop02                        | hadoop03                       |
        | ---- | --------------------- | ------------------------------- | ------------------------------ |
        | HDFS | NameNode<br/>DataNode | DataNode                        | SecondaryNameNode<br/>DataNode |
        | YARN | NodeManager           | ResourceManager<br/>NodeManager | NodeManager                    |

    - 配置文件说明

        Hadoop 配置文件分两类：默认配置文件和自定义配置文件，只有用户想修改某一默认配置值时，才需要修改自定义配置文件，更改相应属性值

        - 默认配置文件

            | 文件               | 位置                                                      |
            | ------------------ | --------------------------------------------------------- |
            | core-default.xml   | hadoop-common-3.x.x.jar/core-default.xml                  |
            | hdfs-default.xml   | hadoop-hdfs-3.x.x.jar/hdfs-default.xml                    |
            | yarn-default.xml   | hadoop-yarn-common-3.x.x.jar/yarn-default.xml             |
            | mapred-default.xml | hadoop-mapreduce-client-core-3.x.x.jar/mapred-default.xml |

        - 自定义配置文件

            > core-site.xml
            >
            > hdfs-site.xml
            >
            > yarn-site.xml
            >
            > mapred-site.xml

            文件存放在 $HADOOP_HOME/etc/hadoop/ 下

    - 配置集群

        core-site.xml

        ```xml
        <configuration>
        	<!-- NameNode -->
        	<property>
        		<name>fs.defaultFS</name>
        		<value>hdfs://hadoop01:8020</value>
        		<description>The name of the default file system.  A URI whose
        		scheme and authority determine the FileSystem implementation.  The
        		uri's scheme determines the config property (fs.SCHEME.impl) naming
        		the FileSystem implementation class.  The uri's authority is used to
        		determine the host, port, etc. for a filesystem.</description>
        	</property>
        	<!-- Data Path -->
        	<property>
        		<name>hadoop.tmp.dir</name>
        		<value>/apps/modules/hadoop-3.3.1/data</value>
        		<description>A base for other temporary directories.</description>
        	</property>
        	<!-- Static User -->
        	<property>
        		<name>hadoop.http.staticuser.user</name>
        		<value>root</value>
        		<description>
        		The user name to filter as, on static web filters
        		while rendering content. An example use is the HDFS
        		web UI (user to be used for browsing files).
        		</description>
        	</property>
        </configuration>
        ```

        hdfs-site.xml

        ```xml
        <configuration>
        	<property>
        		<name>dfs.namenode.http-address</name>
        		<value>hadoop01:9870</value>
        		<description>
        			The address and the base port where the dfs namenode web ui will listen on.
        		</description>
        	</property>
        	<property>
        		<name>dfs.namenode.secondary.http-address</name>
        		<value>hadoop03:9868</value>
        		<description>
        			The secondary namenode http server address and port.
        		</description>
        	</property>
        </configuration>
        ```

        yarn-site.xml

        ```xml
        <configuration>
        
        <!-- Site specific YARN configuration properties -->
        	<property>
        		<description>A comma separated list of services where service name should only
        			contain a-zA-Z0-9_ and can not start with numbers</description>
        		<name>yarn.nodemanager.aux-services</name>
        		<value>mapreduce_shuffle</value>
        	</property>
        	<property>
        		<description>The hostname of the RM.</description>
        		<name>yarn.resourcemanager.hostname</name>
        		<value>hadoop02</value>
        	</property>
        	<property>
        		<description>Environment variables that containers may override rather than use NodeManager's default.</description>
        		<name>yarn.nodemanager.env-whitelist</name>
        		<!-- <value>JAVA_HOME,HADOOP_COMMON_HOME,HADOOP_HDFS_HOME,HADOOP_CONF_DIR,CLASSPATH_PREPEND_DISTCACHE,HADOOP_YARN_HOME,HADOOP_HOME,PATH,LANG,TZ</value> -->
        		<value>JAVA_HOME,HADOOP_COMMON_HOME,HADOOP_HDFS_HOME,HADOOP_CONF_DIR,CLASSPATH_PREPEND_DISTCACHE,HADOOP_YARN_HOME,HADOOP_MAPRED_HOME</value>
        	</property>
        </configuration>
        ```

        mapred-site.xml

        ```xml
        <configuration>
        	<property>
        		<name>mapreduce.framework.name</name>
        		<value>yarn</value>
        		<description>The runtime framework for executing MapReduce jobs.
        			Can be one of local, classic or yarn.
        		</description>
        	</property>
            <!-- history job -->
        	<property>
        		<name>mapreduce.jobhistory.address</name>
        		<value>hadoop01:10020</value>
        		<description>MapReduce JobHistory Server IPC host:port</description>
        	</property>
        	<property>
        		<name>mapreduce.jobhistory.webapp.address</name>
        		<value>hadoop01:19888</value>
        		<description>MapReduce JobHistory Server Web UI host:port</description>
        	</property>
        </configuration>
        ```

    - 配置 workers

        ```bash
        hadoop01
        hadoop02
        hadoop03
        ```

    - 启动集群

        1. 仅第一次启动需要初始化

            ```bash
            hdfs namenode -format
            ```

        2. 启动 HDFS

            ```bash
            sbin/start-dfs.sh
            ```

            脚本内配置

            ```bash
            HDFS_DATANODE_USER=root
            HADOOP_DATANODE_SECURE_USER=hdfs
            HDFS_NAMENODE_USER=root
            HDFS_SECONDARYNAMENODE_USER=root
            ```

            此外在 $HADOOP_HOME/etc/hadoop/hadoop-env.sh 中再 export 一遍 JAVA_HOME

        3. 在 ResourceManager 节点启动 YARN

            ```bash
            sbin/start-yarn.sh
            ```

            脚本内配置

            ```ba
            YARN_RESOURCEMANAGER_USER=root
            HADOOP_SECURE_DN_USER=yarn
            YARN_NODEMANAGER_USER=root
            ```

        4. Web 端查看 HDFS 的 NameNode

            - http://hadoop01:9870
            - 查看数据信息

        5. Web 端查看 YARN 的 ResourceManager

            - http://hadoop02:8088
            - 查看 Job 信息

    - 集群测试

        ```bash
        # 创建文件夹
        hadoop fs -mkdir /input
        # 上传文件
        hadoop fs -put input/word.txt /input
        # word count
        hadoop jar share/hadoop/mapreduce/hadoop-mapreduce-examples-3.3.1.jar wordcount /input/word.txt /output
        ```

    - 配置历史服务器

        ```bash
        mapred --daemon start historyserver
        ```

    - 配置日志聚集

### 2.4 常见错误的解决方案

## 3. HDFS

## 4. MapReduce

## 5. Yarn

