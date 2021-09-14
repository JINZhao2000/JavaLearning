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

        yarn 添加配置

        ```xml
        	<property>
        		<description>Whether to enable log aggregation. Log aggregation collects
        			each container's logs and moves these logs onto a file-system, for e.g.
        			HDFS, after the application completes. Users can configure the
        			"yarn.nodemanager.remote-app-log-dir" and
        			"yarn.nodemanager.remote-app-log-dir-suffix" properties to determine
        			where these logs are moved to. Users can access the logs via the
        			Application Timeline Server.
        		</description>
        		<name>yarn.log-aggregation-enable</name>
        		<value>true</value>
        	</property>
        	<property>
        		<description>
        			URL for log aggregation server
        		</description>
        		<name>yarn.log.server.url</name>
        		<value>http://hadoop01:19888/jobhistory/logs</value>
        	</property>
        	<property>
        		<description>How long to keep aggregation logs before deleting them.  -1 disables. 
        			Be careful set this too small and you will spam the name node.</description>
        		<name>yarn.log-aggregation.retain-seconds</name>
        		<value>604800</value>
        	</property>
        ```

        然后需要重启所有服务
        
    - 各个组件逐一启动和停止

        ```bash
        hdfs --daemon start/stop namenode/datanode/secondarynamenode
        yarn --daemon start/stop resourcemanager/nodemanager
        ```

    - 启动脚本

        ```bash
        #!/bin/bash
        if [ $# -lt 1 ]
        then
          echo "No Args Input ..."
          exit;
        fi
        
        case $1 in
        "start")
          echo "===== hadoop start ====="
          echo "===== hdfs ====="
          ssh hadoop01 "/apps/modules/hadoop-3.3.1/sbin/start-dfs.sh"
          echo "===== yarn ====="
          ssh hadoop02 "/apps/modules/hadoop-3.3.1/sbin/start-yarn.sh"
          echo "===== history server ====="
          ssh hadoop01 "/apps/modules/hadoop-3.3.1/bin/mapred --daemon start historyserver"
          echo "===== started ====="
        ;;
        "stop")
          echo "===== stop ====="
          echo "===== history server ====="
          ssh hadoop01 "/apps/modules/hadoop-3.3.1/bin/mapred --daemon stop historyserver"
          echo "===== yarn ====="
          ssh hadoop02 "/apps/modules/hadoop-3.3.1/sbin/stop-yarn.sh"
          echo "===== hdfs ====="
          ssh hadoop01 "/apps/modules/hadoop-3.3.1/sbin/stop-dfs.sh"
          echo "===== stoped ====="
        ;;
        *)
          echo "Input Args Error ..."
        esac
        for host in hadoop01 hadoop02 hadoop03
        do
          echo "===== $host ====="
          ssh $host jps
        done
        ```
        
    - 端口号

        | 端口名称                   | Hadoop 2.x | Hadoop 3.x     |
        | -------------------------- | ---------- | -------------- |
        | NameNode 内部通信端口      | 8020/9000  | 8020/9000/9820 |
        | NameNode HTTP UI           | 50070      | 9870           |
        | MapReduce 查看任务执行端口 | 8088       | 8088           |
        | 历史服务器通信端口         | 19888      | 19888          |
        | HTTP UI 下载端口           |            | 9864           |

    - 常用配置文件

        - Hadoop 2.x

            core-site.xml

            hdfs-site.xml

            yarn-site.xml

            mapred-site.xml

            slaves

        - Hadoop 3.x

            core-site.xml

            hdfs-site.xml

            yarn-site.xml

            mapred-site.xml

            workers

    - 集群时间同步

        如果服务器能连接外网，不需要时间同步

        > 检查所有节点 ntpd 服务状态和开机自动启动状态
        >
        > systemctl status ntpd
        >
        > systemctl start ntpd
        >
        > systemctl is-enabled ntpd
        >
        > 修改 master 主机的 /etc/ntp.conf 文件
        >
        > ​	授权某网段上所有机器可以从这台机器上查询和同步时间
        >
        > ​	restrict 192.168.68.0 mask 255.255.255.0 nomodify notrap
        >
        > ​	不适用其它互联网时间
        >
        > ​	将 server xxx iburet 都注释掉
        >
        > ​	当该节点丢失网络连接，依然可以采用本地时间作为时间服务器为集群中的其他节点同步
        >
        > ​	server 127.127.1.0
        >
        > ​	fudge 127.127.1.0 stratum 10
        >
        > 修改 master 主机的 /etc/sysconfig
        >
        > ​	添加 SYNC_HWCLOCK=yes
        >
        > 重新启动 ntpd 服务
        >
        > 设置 ntpd 自启动
        >
        > 关闭 slaves 机器的 ntp 和自启动
        >
        > ​	systemctl stop ntpd
        >
        > ​	systemctl disable ntpd
        >
        > 配置同步
        >
        > ​	crontab -e
        >
        > ​	编写定时任务
        >
        > ​	* /1 * * * * /usr/sbin/ntpdate host

### 2.4 常见错误的解决方案

1. ResourceManager 连接不上

    防火墙没有关

    YARN 没有启动

2. 主机名配置错误

3. IP 地址配置错误

4. SSH 没有配置好

5. ROOT 用户和普通用户启动集群不统一

6. 配置文件出错

7. 不识别主机名称

    主机名称映射错误

8. DataNode 与 NameNode 只能启动一个

    DataNode 与 NameNode 的 VERSION 不匹配

9. jps 进程不存在，但是提示进程以及开启

    /tmp 目录下存在启动的临时文件，删除然后重启

## 3. HDFS

### 3.1 HDFS 概述

1. HDFS 产生背景，定义和使用场景

    随着数据量越来越大，在一个操作系统存不下所有的数据，那么就分配到更多的操作系统管理的磁盘中，但是不方便管理和维护，迫切需要一种系统来管理多台机器上的文件，这就是分布式文件系统

    HDFS 是一个文件系统，用于存储文件，通过目录树来定位文件，其次它是分布式的，由多服务器联合起来实现其功能，集群中的服务器由各自的角色

    适合一次写入，多次读出的场景，一个文件经过创建，写入，和关闭之后就不需要改变

2. HDFS 优缺点

    - 高容错性

        数据自动保存多个副本，它通过增加副本的形式，提高容错性

        某一副本丢失后，可以自动恢复

    - 适合处理大数据

        数据规模：能够处理数据规模达到 GB，TB，甚至 PB 级别的数据

        文件规模：能够处理百万规模以上的文件数量，数量相当之大

    - 可以构建在廉价机器上，通过多副本机制，提高可靠性

    - 不适合低延时数据访问：比如毫秒级的存储数据

    - 无法高效地对大量小文件进行存储

        存储大量小文件地话，它会占用 NameNode 大量内存来存储文件目录和块信息，这样是不可取地，因为 NameNode 的内存总是有限的

        小文件存储的寻址时间会超过读取的时间，它违反了 HDFS 的设计目标

    - 不支持并发写入，不支持文件随机修改

        一个文件只能有一个写，不允许多个线程同时写

        仅支持数据 append，不支持文件的随机修改

3. HDFS 组成

    <img src="./images/hdfs-architecture.png" >

    - NameNode 就是 Master 是一个管理者
        - 管理 HDFS 的名称空间
        - 配置副本策略
        - 管理数据块（Block）映射信息
        - 处理客户端的读写请求
    - DataNode 激素 Slave 执行实际操作
        - 存储实际的数据块
        - 执行数据块的读/写操作
    - Client 客户端
        - 文件切分，文件上传 HDFS 的时候，Client 将文件切分成一个一个的块，然后进行上传
        - 与 NameNode 交互，获取文件的位置信息
        - 与 DataNode 交互，读取或者写入数据
        - Client 提供一些命令来管理 HDFS，比如 NameNode 格式化
        - Client 可以通过一些命令来访问 HDFS，比如对 HDFS 的增删改查操作
    - SecondaryNameNode 并非 NameNode 的热备，当 NameNode 宕机时，无法马上替换 NameNode 并提供服务
        - 辅助 NameNode，分担其工作量，比如定期合并 Fsimage 和 Edits，并推送给 NameNode
        - 在紧急情况下，可辅助恢复 NameNode

4. HDFS 文件块大小

    HDFS 中，文件在物理上是分块存储，块的大小可以通过修改配置参数 `dfs,blocksize` 来规定，在 2.x 和 3.x 默认为 128MB，1.x 为 64MB

    为什么块的大小不能太小也不能太大

    - HDFS 块太小反而会增加寻址时间
    - 如果块太大，从磁盘传输数据的实际会明显大于定位这个块开始位置所需的时间，导致程序在处理这块数据时会非常慢
    - HDFS 块大小取决于磁盘传输速率

### 3.2 HDFS Shell 相关操作

- 基本语法

    `hadoop fs` / `hdfs dfs` 

    ```bash
    hadoop fs -help xx
    hadoop fs -mkdir /xxx
    hadoop fs -moveFromLocal ./xxx /xxx # 从本地剪切文件上传到 hdfs
    hadoop fs -copyFromLocal ./xxx /xxx # 从本地复制文件上传到 hdfs
    hadoop fs -put ./xxx /xxx # 等同 copyFromLocal
    hadoop fs -appendToFile ./xxx /xxx # 从本地文件追加内容到 hdfs
    hadoop fs -copyToLocal /xxx ./xxx # 从 hdfs 拷贝到本地
    hadoop fs -get /xxx ./xxx # 等用 copyToLocal
    hadoop fs -ls
    hadoop fs -cat
    hadoop fs -chgrp
    hadoop fs -chmod
    hadoop fs -chown
    hadoop fs -mkdir
    hadoop fs -mv
    hadoop fs -cp
    hadoop fs -tail
    hadoop fs -rm
    hadoop fs -du
    hadoop fs -setrep xx /xxx # 设置副本数量
    ```

### 3.3 HDFS 的客户端 API

### 3.4 HDFS 的读写流程

__HDFS 写数据流程__ 

1. 服务器启动

2. 创建 DistributedFileSystem 客户端

3. 向 NameNode 上请求传输文件

4. 检查是否可以创建文件

    检查权限

    检查目录结构（是否存在）

5. 响应可以上传数据

6. 请求上传第一个 Block，请求返回 DataNode

7. 副本存储节点选择

    本地节点

    其它机架节点

    其它机架的另一个节点

8. 返回多个节点，表示采用这些节点存储数据

9. 创建 FSDataOutputStream

10. 请求建立 Block 传输通道，client 与 master，master 与 slave，slave 与 slave

11. 应答成功

12. 传输数据 Packet（64k）of Chunks（chunk = chunk 512 bytes + chunksum 4 byte）

__网络拓扑 - 节点距离计算__ 

同一节点上的进程距离为 0

同一机架上的不同节点距离为 2

同一数据中心不同机架上的节点距离为 4

不同数据中心节点距离为 6

__副本节点选择__ 

第一个副本在 Client 的节点上

第二个副本在 Remote 上的随机节点

第三个副本在刚在节点机架上的另一个节点

```java
package org.apache.hadoop.hdfs.server.blockmanagement;

@InterfaceAudience.Private
public class BlockPlacementPolicyDefault extends BlockPlacementPolicy {
    protected Node chooseTargetInOrder(int numOfReplicas, 
                                 Node writer,
                                 final Set<Node> excludedNodes,
                                 final long blocksize,
                                 final int maxNodesPerRack,
                                 final List<DatanodeStorageInfo> results,
                                 final boolean avoidStaleNodes,
                                 final boolean newBlock,
                                 EnumMap<StorageType, Integer> storageTypes)
                                 throws NotEnoughReplicasException {
    final int numOfResults = results.size();
    if (numOfResults == 0) {
      DatanodeStorageInfo storageInfo = chooseLocalStorage(writer,
          excludedNodes, blocksize, maxNodesPerRack, results, avoidStaleNodes,
          storageTypes, true);

      writer = (storageInfo != null) ? storageInfo.getDatanodeDescriptor()
                                     : null;

      if (--numOfReplicas == 0) {
        return writer;
      }
    }
    final DatanodeDescriptor dn0 = results.get(0).getDatanodeDescriptor();
    if (numOfResults <= 1) {
      chooseRemoteRack(1, dn0, excludedNodes, blocksize, maxNodesPerRack,
          results, avoidStaleNodes, storageTypes);
      if (--numOfReplicas == 0) {
        return writer;
      }
    }
    if (numOfResults <= 2) {
      final DatanodeDescriptor dn1 = results.get(1).getDatanodeDescriptor();
      if (clusterMap.isOnSameRack(dn0, dn1)) {
        chooseRemoteRack(1, dn0, excludedNodes, blocksize, maxNodesPerRack,
            results, avoidStaleNodes, storageTypes);
      } else if (newBlock){
        chooseLocalRack(dn1, excludedNodes, blocksize, maxNodesPerRack,
            results, avoidStaleNodes, storageTypes);
      } else {
        chooseLocalRack(writer, excludedNodes, blocksize, maxNodesPerRack,
            results, avoidStaleNodes, storageTypes);
      }
      if (--numOfReplicas == 0) {
        return writer;
      }
    }
    chooseRandom(numOfReplicas, NodeBase.ROOT, excludedNodes, blocksize,
        maxNodesPerRack, results, avoidStaleNodes, storageTypes);
    return writer;
  }
}
```

__HDFS 读数据流程__ 

1. 请求下载文件
2. 检查是否存在文件以及是否有权限下载文件
3. 返回目标文件的元数据
4. 创建 `FSDataInputStream` 请求读取数据（负载均衡）
5. 传输数据（串行化）

### 3.5 NN 和 2NN

__NameNode__ 

1. 从 edits_inprogress_xxx 和 fsimage 中加载编辑日志和镜像文件到内存
2. 元数据的增删改
3. 记录操作日志，更新滚动日志
4. 内存数据增删改

__SecondNameNode__ 

1. 请求是否需要 CheckPoint
    - 定时时间到
    - Edits 中数据满了
2. 请求执行 CheckPoint
3. 滚动正在写的 Edits（NN 中）
4. 拷贝到 2NN
5. 加载到内存并合并
6. 生成新的 fsimage
7. 拷贝到 NN
8. 重命名成 fsimage（NN 中）

Fsimage 与 Edits 概念

NameNode 被格式化之后，将在 `${hadoop.tmp.dir}/tmp/dfs/name/current` 目录中产生如下文件

> fsimage_xxx - HDFS 文件系统元数据的一个永久性的检查点，其中包含 HDFS 文件系统的所有目录和文件 inode 的序列化信息
>
> fsimage_xxx.md5
>
> edits_xxx-xxx 存放 HDFS 文件系统的所有更新操作路径，文件系统客户端执行的所有写操作首先会被记录到 Edits 文件中
>
> edits_inprogress_xxx
>
> seen_txid 保存的是一个数字，就是 edits_的数字
>
> VERSION

每次 NameNode 启动的时候，都会将 fsimage 文件读入内存，加载 Edits 里面的更新操作，保证内存中的元数据信息是最新的，同步的，可以看成 NameNode 启动的时候就将 fsimage 和 edits 文件进行了合并

__查看 fsimage 文件__ 

```bash
hdfs oiv -p XML -i fsimage_xxx -o <output file>
```

__查看 edits 文件__ 

```bash
hdfs oev -p XML -i edits_inprogress_xxx -o <output file>
```

__CheckPoint 时间设置__ 

- 通常情况下，SecondaryNameNode 每隔一小时执行一次

    hdfs-default.xml

    ```xml
    <property>
    	<name>dfs.namenode.checkpoint.period</name>
        <value>3600s</value>
    </property>
    ```

- 一分钟检查一次操作次数，当操作次数达到 1m 的时候，SecondaryNameNode 执行一次

    ```xml
    <property>
    	<name>dfs.namenode.checkpoint.txns</name>
        <value>1000000</value>
    </property>
    <property>
    	<name>dfs.namenode.checkpoint.check.period</name>
        <value>60s</value>
    </property>
    ```

### 3.6 DataNode 工作机制

1. DataNode 启动后向 NameNode 注册
2. 注册成功
3. 每周期（默认 6 小时）上报所有块信息

4. 心跳每周期（默认 3 秒），心跳返回结果带有 NameNode 给该 DataNode 的命令
5. 超过周期（默认 10 分钟 + 30 秒）没有收到 DataNode 的心跳，则认为该节点不可用（类似 Eureka - CAP 中的 AP 原则）

DN 向 NN 汇报时间间隔

```xml
<property>
    <name>dfs.blockreport.intervalMsec</name>
   	<value>21600000</value>
</property>
```

DN 扫描自己节点块信息列表的时间

```xml
<property>
	<name>dfs.datanode.directoryscan.interval</name>
    <value>21600s</value>
</property>
```

超时计算

timeout = 2 * dfs.namenode.heartbeat.recheck-interval + 10 * dfs.heartbeat.interval

```xml
<property>
	<name>dfs.namenode.heartbeat.recheck-interval</name>
    <value>300000</value>
</property>
<property>
	<name>dfs.heartbeat.interval</name>
    <value>3</value>
</property>
```

__数据完整性__ 

1. 当 DataNode 读取 Block 的时候，它会计算 CheckSum
2. 如果计算后的 CheckSum 与 Block 创建时的不一样，说明 Block 已经损坏
3. Client 读取其它 DataNode 上的 Block
4. 常见的检验算法 CRC（32），MD5（128），SHA1（160）
5. DataNode 在其文件创建后周期验证 CheckSum

## 4. MapReduce

### 4.1 MapReduce 概述

__MapReduce 定义__ 

- 完全并发对数据进行 K，V 配对，形成分布式的 Map
- 对所有的 Map 进行 Reduce，将结果汇集到一起

__MapReduce 优缺点__ 

- 优点
    1. 易于编程：用户只关心业务逻辑，实现框架的接口
    2. 良好的扩展性：可以动态增加服务器，解决计算资源不够的问题
    3. 高容错性：任何一台机器宕机，可以将任务转移到其它的节点
    4. 适合海量计算：几千台服务器共同计算
- 缺点
    1. 不擅长实时计算 - MySQL
    2. 不擅长流式计算 - Spark Streaming / Flink
    3. 不擅长 DAG 有向无环图计算 - Spark

__MapReduce 进程__ 

- MrAppMaster：负责整个程序的过程调度及状态协调

- MapTask：负责 Map 阶段的整个数据处理流程

- ReduceTask：负责 Reduce 阶段整个处理流程

    MapTask 与 ReduceTask 进程名为 yarn-child

__WordCount (Hello World)__ 

```java
public class WordCount {
    // <Object, Text, Text, IntWritable>
    // 输入：偏移量，内容；输出：内容，计数
    public static class TokenizerMapper extends Mapper<Object, Text, Text, IntWritable>{
    	private final static IntWritable one = new IntWritable(1);
	    private Text word = new Text();      
	    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
    		StringTokenizer itr = new StringTokenizer(value.toString());
	    	while (itr.hasMoreTokens()) {
        		word.set(itr.nextToken());
	        	context.write(word, one);
	      	}
	    }
  	}
  
    // <Text, IntWritable, Text, IntWritable>
    // Map 输出，也是 Reduce 输入：K，V；Reduce 输出：Map<K, V>
    public static class IntSumReducer extends Reducer<Text,IntWritable,Text,IntWritable> {
        private IntWritable result = new IntWritable();
        public void reduce(Text key, Iterable<IntWritable> values, Context context) 
            throws IOException, InterruptedException {
            int sum = 0;
            for (IntWritable val : values) {
                sum += val.get();
            }
            result.set(sum);
            context.write(key, result);
        }
    }

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
        if (otherArgs.length < 2) {
            System.err.println("Usage: wordcount <in> [<in>...] <out>");
            System.exit(2);
        }
        Job job = Job.getInstance(conf, "word count");
        job.setJarByClass(WordCount.class);
        job.setMapperClass(TokenizerMapper.class);
        job.setCombinerClass(IntSumReducer.class);
        job.setReducerClass(IntSumReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        for (int i = 0; i < otherArgs.length - 1; ++i) {
            FileInputFormat.addInputPath(job, new Path(otherArgs[i]));
        }
        FileOutputFormat.setOutputPath(job, new Path(otherArgs[otherArgs.length - 1]));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
```

__Hadoop 数据类型__ 

| Java 类型 | Hadoop Writable 类型 |
| --------- | -------------------- |
| Boolean   | BooleanWritable      |
| Byte      | ByteWritable         |
| Int       | IntWritable          |
| Float     | FloatWritable        |
| Long      | LongWritable         |
| Double    | DoubleWritable       |
| String    | Text                 |
| Map       | MapWritable          |
| Array     | ArrayWritable        |
| Null      | NullWritable         |

__MapReduce 编程规范__ 

程序分为三部分：Mapper，Reducer 和 Driver

- Mapper

    1. 用户自定义的 Mapper 要继承自己的父类
    2. Mapper 的输入数据是 K，V 对的形式
    3. Mapper 中的业务逻辑写在 map() 方法中
    4. Mapper 的输出数据是 K，V 对的形式
    5. map() 方法（MapTask 进程）对每一个 <K, V> 调用一次

- Reducer

    1. 用户自定义的 Reducer 要继承自己的父类
    2. Reducer 的输入数据类型对应 Mapper 输出的类型，也是 K，V
    3. Reduce 中的业务逻辑写在 reduce() 方法中
    4. ReduceTask 进程对每一组相同 K 的 <K, V> 组调用一次 reduce() 方法

- Driver

    相当于  YARN 集群的客户端，用于提交整个程序到 YARN 集群，提交的是封装了 MapReduce 程序相关运行参数的 Job 对象

__WordCount__ 

- Mapper
    - 将 MapTask 传的文本内容转成 String
    - 根据空格将一行分成单词
    - 将单词输出为 <单词，I>
- Reducer
    - 汇总各个 key 的个数
    - 输出该 key 的总次数
- Driver
    - 获取配置信息，获取 Job 对象实例
    - 指定本程序的 jar 包所在的本地路径
    - 关联 Mapper 和 Reducer 业务类
    - 指定 Mapper 输出数据的 K，V 类型
    - 指定最终输出的数据的 K，V 类型
    - 指定 Job 的输入原始文件所在目录
    - 指定 Job 的输出结果所在目录
    - 提交作业

### 4.2 序列化

__什么是序列化__ 

序列化就是把内存中的对象，转换成字节序列（或者其它数据传输协议）以便于存储到磁盘（持久化）和网络传输

反序列化就是将接收到的字节序列（或者其它传输协议）或是磁盘的持久化数据，转换成内存中的对象

__为什么要序列化__ 

一般来说，对象只生存在内存里，序列化能让对象再进程之间共享以及发送到远程计算机

__为什么不用 Java 的序列化__ 

Java 的序列化是一个重量级序列化框架（Serializable），一个对象被序列化之后，会附带很多额外信息（校验信息，Header，继承体系等），不便于在网络上高效传输

Hadoop 序列化仅仅添加了一个简单校验，数据紧凑，传输快速，有多语言的互交互性

__序列化实现__ 

自定义 bean 对象实现序列化接口（Writable）

- 必须实现 Writable 接口
- 反序列化时们需要反射调用空构造函数，所以必须要有空参构造
- 重写序列化方法
- 重写反序列化方法
- 注意反序列化和序列化的顺序完全一致
- 需要把结果显示在文件中需要重写 `toString()` 
- 如果自定义 bean 放在 key 中传输，则还需要实现 Comparable 接口，因为 MapReduce 框架中的 Shuffle 过程要求对 key 必须能排序

### 4.3 MapReduce 框架原理

MapTask：

Input --- InputFormat --> Mapper

Shuffle：

Mapper --- Shuffle --> Reducer

ReduceTask：

Reducer --- OutputFormat --> Output

__输入的数据 InputFormat__ 

切片与 MapTask 并行决定机制

- 问题引出

    MapTask 的并行度决定 Map 阶段的任务并发处理速度，进而影响到整个 Job 的处理速度

- MapTask 并行度决定机制

    数据块：Block 是 HDFS 物理上把数据分成一块一块，数据块是 HDFS 的存储单位

    数据切片：数据切片只是在逻辑上对输入进行分片，并不会在磁盘上将其切分成片进行存储，数据切片是 MapReduce 程序计算输入单位，一个切片会对应启动一个 MapTask

    - 一个 Job 的 Map 阶段并行度由客户端在提交 Job 时的切片数决定
    - 每一个 Split 切片分配一个 MapTask 并行实例处理
    - 默认情况下，切片大小 = BlockSize
    - 切片时不考虑数据集整体，而是逐个针对每一个文件单独切片

Job 提交流程

Job

```java
public class Job extends JobContextImpl implements JobContext, AutoCloseable {
    public boolean waitForCompletion(boolean verbose) throws IOException, InterruptedException, ClassNotFoundException {
        if (state == JobState.DEFINE) {
            submit(); /////
        }
        if (verbose) {
            monitorAndPrintJob();
        } else {
            // get the completion poll interval from the client.
            int completionPollIntervalMillis = Job.getCompletionPollInterval(cluster.getConf());
            while (!isComplete()) {
                try {
                    Thread.sleep(completionPollIntervalMillis);
                } catch (InterruptedException ie) {
                }
            }
        }
        return isSuccessful();
    }
    
    public void submit() throws IOException, InterruptedException, ClassNotFoundException {
	    ensureState(JobState.DEFINE);
	    setUseNewAPI();
	    connect(); /////
	    final JobSubmitter submitter = getJobSubmitter(cluster.getFileSystem(), cluster.getClient());
    	status = ugi.doAs(new PrivilegedExceptionAction<JobStatus>() {
            public JobStatus run() throws IOException, InterruptedException, ClassNotFoundException {
                return submitter.submitJobInternal(Job.this, cluster);
            }
        });
        state = JobState.RUNNING;
        LOG.info("The url to track the job: " + getTrackingURL());
    }
    
    @Private
    @VisibleForTesting
    synchronized void connect() throws IOException, InterruptedException, ClassNotFoundException {
        if (cluster == null) {
            cluster = ugi.doAs(new PrivilegedExceptionAction<Cluster>() {
                public Cluster run() throws IOException, InterruptedException, ClassNotFoundException {
                    return new Cluster(getConfiguration());
                }
            });
        }
    }
}
```

Cluster

```java
public class Cluster {
	public Cluster(Configuration conf) throws IOException {
		this(null, conf);
	}
    
    public Cluster(InetSocketAddress jobTrackAddr, Configuration conf) throws IOException {
        this.conf = conf;
        this.ugi = UserGroupInformation.getCurrentUser();
        initialize(jobTrackAddr, conf);
    }
    
    private void initialize(InetSocketAddress jobTrackAddr, Configuration conf) throws IOException {
        initProviderList();
        final IOException initEx = new IOException("Cannot initialize Cluster. Please check your configuration for "
            + MRConfig.FRAMEWORK_NAME
            + " and the correspond server addresses.");
        if (jobTrackAddr != null) {
            LOG.info("Initializing cluster for Job Tracker=" + jobTrackAddr.toString());
        }
        for (ClientProtocolProvider provider : providerList) {
            LOG.debug("Trying ClientProtocolProvider : "
                      + provider.getClass().getName());
            ClientProtocol clientProtocol = null;
            try {
                if (jobTrackAddr == null) {
                    clientProtocol = provider.create(conf);
                } else {
                    clientProtocol = provider.create(jobTrackAddr, conf);
                }
                if (clientProtocol != null) {
                    clientProtocolProvider = provider;
                    client = clientProtocol;
                    LOG.debug("Picked " + provider.getClass().getName() + " as the ClientProtocolProvider");
                    break;
                } else {
                    LOG.debug("Cannot pick " + provider.getClass().getName()
                              + " as the ClientProtocolProvider - returned null protocol");
                }
            } catch (Exception e) {
                final String errMsg = "Failed to use " + provider.getClass().getName() + " due to error: ";
                initEx.addSuppressed(new IOException(errMsg, e));
                LOG.info(errMsg, e);
            }
        }
        if (null == clientProtocolProvider || null == client) {
            throw initEx;
        }
    }
}
```

JobSubmitter

```java
class JobSubmitter {
    JobStatus submitJobInternal(Job job, Cluster cluster) throws ClassNotFoundException, InterruptedException, IOException {
        //validate the jobs output specs 
        checkSpecs(job); ///// 
        Configuration conf = job.getConfiguration();
        addMRFrameworkToDistributedCache(conf);
        Path jobStagingArea = JobSubmissionFiles.getStagingDir(cluster, conf);
        //configure the command line options correctly on the submitting dfs
        InetAddress ip = InetAddress.getLocalHost();
        if (ip != null) {
            submitHostAddress = ip.getHostAddress();
            submitHostName = ip.getHostName();
            conf.set(MRJobConfig.JOB_SUBMITHOST,submitHostName);
            conf.set(MRJobConfig.JOB_SUBMITHOSTADDR,submitHostAddress);
        }
        JobID jobId = submitClient.getNewJobID();
        job.setJobID(jobId);
        Path submitJobDir = new Path(jobStagingArea, jobId.toString());
        JobStatus status = null;
        try {
            conf.set(MRJobConfig.USER_NAME, UserGroupInformation.getCurrentUser().getShortUserName());
            conf.set("hadoop.http.filter.initializers", 
                     "org.apache.hadoop.yarn.server.webproxy.amfilter.AmFilterInitializer");
            conf.set(MRJobConfig.MAPREDUCE_JOB_DIR, submitJobDir.toString());
            LOG.debug("Configuring job " + jobId + " with " + submitJobDir + " as the submit dir");
            // get delegation token for the dir
            TokenCache.obtainTokensForNamenodes(job.getCredentials(), new Path[] { submitJobDir }, conf);
            populateTokenCache(conf, job.getCredentials());
            // generate a secret to authenticate shuffle transfers
            if (TokenCache.getShuffleSecretKey(job.getCredentials()) == null) {
                KeyGenerator keyGen;
                try {
                    keyGen = KeyGenerator.getInstance(SHUFFLE_KEYGEN_ALGORITHM);
                    keyGen.init(SHUFFLE_KEY_LENGTH);
                } catch (NoSuchAlgorithmException e) {
                    throw new IOException("Error generating shuffle secret key", e);
                }
                SecretKey shuffleKey = keyGen.generateKey();
                TokenCache.setShuffleSecretKey(shuffleKey.getEncoded(), job.getCredentials());
            }
            if (CryptoUtils.isEncryptedSpillEnabled(conf)) {
                conf.setInt(MRJobConfig.MR_AM_MAX_ATTEMPTS, 1);
                LOG.warn("Max job attempts set to 1 since encrypted intermediate" + "data spill is enabled");
            }
            copyAndConfigureFiles(job, submitJobDir); /////
            Path submitJobFile = JobSubmissionFiles.getJobConfPath(submitJobDir);
            // Create the splits for the job
            LOG.debug("Creating splits at " + jtFs.makeQualified(submitJobDir));
            
            
            
            
            
            // 切片 ！！！
            int maps = writeSplits(job, submitJobDir);  
            conf.setInt(MRJobConfig.NUM_MAPS, maps);
            LOG.info("number of splits:" + maps);
            
            int maxMaps = conf.getInt(MRJobConfig.JOB_MAX_MAP, MRJobConfig.DEFAULT_JOB_MAX_MAP);
            if (maxMaps >= 0 && maxMaps < maps) {
                throw new IllegalArgumentException("The number of map tasks " + maps +
                                                   " exceeded limit " + maxMaps);
            }
            // write "queue admins of the queue to which job is being submitted"
            // to job file.
            String queue = conf.get(MRJobConfig.QUEUE_NAME, JobConf.DEFAULT_QUEUE_NAME);
            AccessControlList acl = submitClient.getQueueAdmins(queue);
            conf.set(toFullPropertyName(queue, QueueACL.ADMINISTER_JOBS.getAclName()), acl.getAclString());
            // removing jobtoken referrals before copying the jobconf to HDFS
            // as the tasks don't need this setting, actually they may break
            // because of it if present as the referral will point to a
            // different job.
            TokenCache.cleanUpTokenReferral(conf);
            if (conf.getBoolean(
                MRJobConfig.JOB_TOKEN_TRACKING_IDS_ENABLED,
                MRJobConfig.DEFAULT_JOB_TOKEN_TRACKING_IDS_ENABLED)) {
                // AddHDFS tracking ids
                ArrayList<String> trackingIds = new ArrayList<String>();
                for (Token<? extends TokenIdentifier> t : job.getCredentials().getAllTokens()) {
                    trackingIds.add(t.decodeIdentifier().getTrackingId());
                }
                conf.setStrings(MRJobConfig.JOB_TOKEN_TRACKING_IDS, trackingIds.toArray(new String[trackingIds.size()]));
            }
            // Set reservation info if it exists
            ReservationId reservationId = job.getReservationId();
            if (reservationId != null) {
                conf.set(MRJobConfig.RESERVATION_ID, reservationId.toString());
            }
            // Write job file to submit dir
            writeConf(conf, submitJobFile);
            // Now, actually submit the job (using the submit name)
            printTokens(jobId, job.getCredentials());
            status = submitClient.submitJob(jobId, submitJobDir.toString(), job.getCredentials());
            if (status != null) {
                return status;
            } else {
                throw new IOException("Could not launch job");
            }
        } finally {
            if (status == null) {
                LOG.info("Cleaning up the staging area " + submitJobDir);
                if (jtFs != null && submitJobDir != null)
                    jtFs.delete(submitJobDir, true);
            }
        }
    }
    
    private void checkSpecs(Job job) throws ClassNotFoundException, InterruptedException, IOException {
        JobConf jConf = (JobConf)job.getConfiguration();
        // Check the output specification
        if (jConf.getNumReduceTasks() == 0 ? 
            jConf.getUseNewMapper() : jConf.getUseNewReducer()) {
            org.apache.hadoop.mapreduce.OutputFormat<?, ?> output =
                ReflectionUtils.newInstance(job.getOutputFormatClass(), job.getConfiguration());
            output.checkOutputSpecs(job);
        } else {
            jConf.getOutputFormat().checkOutputSpecs(jtFs, jConf);
        }
    }
    
    // FileOutputFormat imple;ents
    public void checkOutputSpecs(JobContext job) throws FileAlreadyExistsException, IOException{
        // Ensure that the output directory is set and not already there
        Path outDir = getOutputPath(job);
        if (outDir == null) {
            throw new InvalidJobConfException("Output directory not set.");
        }
        // get delegation token for outDir's file system
        TokenCache.obtainTokensForNamenodes(job.getCredentials(),
                                            new Path[] { outDir }, job.getConfiguration());        
        if (outDir.getFileSystem(job.getConfiguration()).exists(outDir)) {
            throw new FileAlreadyExistsException("Output directory " + outDir + " already exists");
        }
    }
    
    private void copyAndConfigureFiles(Job job, Path jobSubmitDir) throws IOException {
        Configuration conf = job.getConfiguration();
        boolean useWildcards = conf.getBoolean(Job.USE_WILDCARD_FOR_LIBJARS,
                                               Job.DEFAULT_USE_WILDCARD_FOR_LIBJARS);
        JobResourceUploader rUploader = new JobResourceUploader(jtFs, useWildcards);
        rUploader.uploadResources(job, jobSubmitDir); /////

        // Get the working directory. If not set, sets it to filesystem working dir
        // This code has been added so that working directory reset before running
        // the job. This is necessary for backward compatibility as other systems
        // might use the public API JobConf#setWorkingDirectory to reset the working
        // directory.
        job.getWorkingDirectory();
    }
}
```

JobResourceUploader

```java
class JobResourceUploader {
    public void uploadResources(Job job, Path submitJobDir) throws IOException {
        try {
            initSharedCache(job.getJobID(), job.getConfiguration());
            uploadResourcesInternal(job, submitJobDir);
        } finally {
            stopSharedCache();
        }
    }
    
    private void uploadResourcesInternal(Job job, Path submitJobDir) throws IOException {
        Configuration conf = job.getConfiguration();
        short replication = (short) conf.getInt(Job.SUBMIT_REPLICATION, Job.DEFAULT_SUBMIT_REPLICATION);
        
        if (!(conf.getBoolean(Job.USED_GENERIC_PARSER, false))) {
            LOG.warn("Hadoop command-line option parsing not performed. "
                     + "Implement the Tool interface and execute your application "
                     + "with ToolRunner to remedy this.");
        }
        // Figure out what fs the JobTracker is using. Copy the
        // job to it, under a temporary name. This allows DFS to work,
        // and under the local fs also provides UNIX-like object loading
        // semantics. (that is, if the job file is deleted right after
        // submission, we can still run the submission to completion)

        // Create a number of filenames in the JobTracker's fs namespace
        LOG.debug("default FileSystem: " + jtFs.getUri());
        if (jtFs.exists(submitJobDir)) {
            throw new IOException("Not submitting job. Job directory " + submitJobDir
                                  + " already exists!! This is unexpected.Please check what's there in"
                                  + " that directory");
        }
        // Create the submission directory for the MapReduce job.
        submitJobDir = jtFs.makeQualified(submitJobDir);
        submitJobDir = new Path(submitJobDir.toUri().getPath());
        FsPermission mapredSysPerms = new FsPermission(JobSubmissionFiles.JOB_DIR_PERMISSION);
        mkdirs(jtFs, submitJobDir, mapredSysPerms);

        if (!conf.getBoolean(MRJobConfig.MR_AM_STAGING_DIR_ERASURECODING_ENABLED,
                             MRJobConfig.DEFAULT_MR_AM_STAGING_ERASURECODING_ENABLED)) {
            disableErasureCodingForPath(submitJobDir);
        }

        // Get the resources that have been added via command line arguments in the
        // GenericOptionsParser (i.e. files, libjars, archives).
        Collection<String> files = conf.getStringCollection("tmpfiles");
        Collection<String> libjars = conf.getStringCollection("tmpjars");
        Collection<String> archives = conf.getStringCollection("tmparchives");
        String jobJar = job.getJar();
        
        // Merge resources that have been programmatically specified for the shared
        // cache via the Job API.
        files.addAll(conf.getStringCollection(MRJobConfig.FILES_FOR_SHARED_CACHE));
        libjars.addAll(conf.getStringCollection(MRJobConfig.FILES_FOR_CLASSPATH_AND_SHARED_CACHE));
        archives.addAll(conf.getStringCollection(MRJobConfig.ARCHIVES_FOR_SHARED_CACHE));

        Map<URI, FileStatus> statCache = new HashMap<URI, FileStatus>();
        checkLocalizationLimits(conf, files, libjars, archives, jobJar, statCache);

        Map<String, Boolean> fileSCUploadPolicies = new LinkedHashMap<String, Boolean>();
        Map<String, Boolean> archiveSCUploadPolicies = new LinkedHashMap<String, Boolean>();

        uploadFiles(job, files, submitJobDir, mapredSysPerms, replication, fileSCUploadPolicies, statCache);
        uploadLibJars(job, libjars, submitJobDir, mapredSysPerms, replication, fileSCUploadPolicies, statCache);
        uploadArchives(job, archives, submitJobDir, mapredSysPerms, replication, archiveSCUploadPolicies, statCache);
        uploadJobJar(job, jobJar, submitJobDir, replication, statCache);
        addLog4jToDistributedCache(job, submitJobDir);

        // Note, we do not consider resources in the distributed cache for the
        // shared cache at this time. Only resources specified via the
        // GenericOptionsParser or the jobjar.
        Job.setFileSharedCacheUploadPolicies(conf, fileSCUploadPolicies);
        Job.setArchiveSharedCacheUploadPolicies(conf, archiveSCUploadPolicies);

        // set the timestamps of the archives and files
        // set the public/private visibility of the archives and files
        ClientDistributedCacheManager.determineTimestampsAndCacheVisibilities(conf, statCache);
        // get DelegationToken for cached file
        ClientDistributedCacheManager.getDelegationTokens(conf, job.getCredentials());
    }
}
```

Job 流程：

1. Job.submit()
2. JobSubmitter：Cluster 成员 Proxy
3. YarnRunner -> YARN 或者本地程序 LocalJobRunner
4. 通过 Cluster 进行
    1. StagingDir
    2. JobID
    3. 调用 FileInputFormat.getSplits() 获取切片信息并序列化成文件 Job.split
    4. 将 Job 相关参数写到文件 Job.xml
    5. 如果是 yarnRunner，还需要获取 Job 的 jar 包 xxx.jar

切片

JobSubmitter

```java
class JobSubmitter {
    private int writeSplits(org.apache.hadoop.mapreduce.JobContext job, Path jobSubmitDir) 
        throws IOException, InterruptedException, ClassNotFoundException {
        JobConf jConf = (JobConf)job.getConfiguration();
        int maps;
        if (jConf.getUseNewMapper()) {
            maps = writeNewSplits(job, jobSubmitDir);  /////
        } else {
            maps = writeOldSplits(jConf, jobSubmitDir);
        }
        return maps;
    }

    private <T extends InputSplit> int writeNewSplits(JobContext job, Path jobSubmitDir) 
        throws IOException, InterruptedException, ClassNotFoundException {
        Configuration conf = job.getConfiguration();
        InputFormat<?, ?> input =
            ReflectionUtils.newInstance(job.getInputFormatClass(), conf);

        List<InputSplit> splits = input.getSplits(job); /////
        T[] array = (T[]) splits.toArray(new InputSplit[splits.size()]);

        // sort the splits into order based on size, so that the biggest
        // go first
        Arrays.sort(array, new SplitComparator());
        JobSplitWriter.createSplitFiles(jobSubmitDir, conf, jobSubmitDir.getFileSystem(conf), array); ///// 生成切片文件
        return array.length;
    }
}
```

FileInputFormat

```java
public abstract class FileInputFormat<K, V> extends InputFormat<K, V> {
    public List<InputSplit> getSplits(JobContext job) throws IOException {
        StopWatch sw = new StopWatch().start();
        long minSize = Math.max(getFormatMinSplitSize(), getMinSplitSize(job)); // Max(1 or SPLIT_MINIZE)
        // SPLIT_MINIZE => mapreduce.input.fileinputformat.split.minsize: default = 0
        long maxSize = getMaxSplitSize(job);  // SPLIT_MAXSIZE or Long.MAX_VALUE
        // SPLITE_MAXSIZE => mapreduce.input.fileinputformat.split.maxsize

        // generate splits
        List<InputSplit> splits = new ArrayList<InputSplit>();
        List<FileStatus> files = listStatus(job);

        boolean ignoreDirs = !getInputDirRecursive(job)
            && job.getConfiguration().getBoolean(INPUT_DIR_NONRECURSIVE_IGNORE_SUBDIRS, false);
        for (FileStatus file: files) {
            if (ignoreDirs && file.isDirectory()) {
                continue;
            }
            Path path = file.getPath();
            long length = file.getLen();
            if (length != 0) {
                BlockLocation[] blkLocations;
                if (file instanceof LocatedFileStatus) {
                    blkLocations = ((LocatedFileStatus) file).getBlockLocations();
                } else {
                    FileSystem fs = path.getFileSystem(job.getConfiguration());
                    blkLocations = fs.getFileBlockLocations(file, 0, length);
                }
                // 并不是所有文件都支持切片，如果不支持切片的压缩文件
                if (isSplitable(job, path)) {
                    // 本地模式为 32MB 33554432
                    long blockSize = file.getBlockSize();
                    // Math.max(minSize, Math.min(maxSize, blockSize));
                    long splitSize = computeSplitSize(blockSize, minSize, maxSize);

                    long bytesRemaining = length;
                    while (((double) bytesRemaining)/splitSize > SPLIT_SLOP) { // SPLIT_SLOP = 1.1
                        int blkIndex = getBlockIndex(blkLocations, length-bytesRemaining);
                        splits.add(makeSplit(path, length-bytesRemaining, splitSize,
                                             blkLocations[blkIndex].getHosts(),
                                             blkLocations[blkIndex].getCachedHosts()));
                        bytesRemaining -= splitSize;
                    }

                    if (bytesRemaining != 0) {
                        int blkIndex = getBlockIndex(blkLocations, length-bytesRemaining);
                        splits.add(makeSplit(path, length-bytesRemaining, bytesRemaining,
                                             blkLocations[blkIndex].getHosts(),
                                             blkLocations[blkIndex].getCachedHosts()));
                    }
                } else { // not splitable
                    if (LOG.isDebugEnabled()) {
                        // Log only if the file is big enough to be splitted
                        if (length > Math.min(file.getBlockSize(), minSize)) {
                            LOG.debug("File is not splittable so no parallelization "
                                      + "is possible: " + file.getPath());
                        }
                    }
                    splits.add(makeSplit(path, 0, length, blkLocations[0].getHosts(),
                                         blkLocations[0].getCachedHosts()));
                }
            } else { 
                //Create empty hosts array for zero length files
                splits.add(makeSplit(path, 0, length, new String[0]));
            }
        }
    }
```

JobSubmitWriter

```java
public class JobSplitWriter {
    public static <T extends InputSplit> void createSplitFiles(
        Path jobSubmitDir, Configuration conf, FileSystem fs, T[] splits) 
        throws IOException, InterruptedException {
        FSDataOutputStream out = createFile(fs, JobSubmissionFiles.getJobSplitFile(jobSubmitDir), conf);
        SplitMetaInfo[] info = writeNewSplits(conf, splits, out);
        out.close();
        writeJobSplitMetaInfo(fs,JobSubmissionFiles.getJobSplitMetaFile(jobSubmitDir), 
                              new FsPermission(JobSubmissionFiles.JOB_FILE_PERMISSION), splitVersion,info);
    }
}
```

FileInputFormat 切片流程

1. 先找到数据存储目录

2. 开始遍历处理（规划切片）目录下的每一个文件

3. 遍历第一个文件 xxx

    1. 获取文件大小 `fs.sizeOf(xxx)` 

    2. 计算切片大小

        `computeSplitSize(Math.max(minSize, Math.min(maxSize, blockSize))) = blockSize = 128M` 

    3. 默认情况下，切片大小 = blockSize

    4. 开始切，形成第 1 个切片：每次切片时，都要判断剩下的块是否大于块的 1.1 倍，不大于 1.1 倍就划分一块切片

    5. 将切片信息写到一个切片规划中

    6. 整个切片的核心过程在 `getSplit()` 方法中完成

    7. `InputSplit` 只记录了切片的原数据信息，比如起始位置，长度以及所在的节点列表等

4. 提交切片规划文件到 YARN 上，YARN 上的 MRAppMaster 就可以根据切片规划文件计算开启 MapTask 个数

FileInputFormat 切片机制

1. 简单地按照文件的内容长度进行切片
2. 切片大小，默认等于 Block 大小
3. 切片时不考虑数据集整体，而是逐个针对每一个文件单独切片

FileInputFormat 参数配置

- `Math.max(minSize, Math.min(maxSize, blockSize));` 
- `mapreduce.input.fileinputformat.split.minsize = 1`
- `mapreduce.input.fileinputformat.split.maxsize = Long.MAX_VALUE` 
- API
    - `(FileSplit) context.getInputSplit();` 
    - `inputSplit.getPath().getName()` 

FileInputFormat 常见的接口实现类包括：TextInputFormat，KeyValueTextInputFormat，NLineInputFormat，CombineTextInputFormat 和自定义 InputFormat

TextInputFormat

> TextInputFormat 是默认的 FileInputFormat 实现类，按行读取每条记录，Key 是存储改行在整个文件的其实字节偏移量，LongWritable 类型，Value 是这行的内容，不包括任何终止符（换行符或者回车符），Text 类型

CombineTextInputFormat 切片机制

> 默认的切片机制是对任务按文件规划切片，不管文件多小，都会是一个单独的切片，都会给一个 MapTask，这样如果由大量小文件，就会产生大量的 MapTask，处理效率极其低下

1. 应用场景

    CombineTextInputFormat 用于小文件过多的场景，它可以将多个小文件从逻辑上规划到一个切片中，这样多个小文件就可以交给一个 MapTask 处理

2. 虚拟存储切片最大值设置

    `CombineTextInputFormat.setMaxInputSplitSize(job, 4194304); // 4 MB ` 

    注意：虚拟存储切片最大值设置最好根据实际的小文件大小情况来设置具体的值

3. 切片机制

    生成切片过程包括：虚拟存储过程和切片过程二部分

    虚拟存储过程

    > 判断文件大小是否大于 setMaxInputSplitSize 值
    >
    > 如果不大于则单独生成一个虚拟存储文件
    >
    > 如果大于则使最后两个切片的大小平均，之前的切片大小为 Max 大小

    切片过程

    >判断虚拟存储的文件大小是否大于 setMaxInputSplitSize 值，大于等于则单独形成一个切片
    >
    >如果不大于则跟下一个虚拟存储文件进行合并，共同形成一个切片，合并的最终大小与切片大小无关

__MapReduce 工作流程__ 

1. 获取待处理文本

2. 客户端 `submit()` 前，获取待处理数据的信息，然后根据参数配置，形成一个任务分配的规划

3. 提交信息

    - 本地：job.split，wc.jar，job.xml
    - yarn：RM

4. yarn 计算出 MapTask 数量：MRAppMaster，NodeManager

5. 默认 TextInputFormat

6. Mapper 逻辑运算输出到 OutputCollector（环形缓冲区）

7. 向环形缓冲区写入 <K, V> 数据，左半侧是索引 kvmata，kvindex，右半侧是数据 <K, V> bufindex

    - meta：index，partition，keystart，valstart
    - records：key，value，unused

    写到 80% 时反向写数据

8. 分区，排序（快排）

9. 溢出到文件（分区且区内有序）

10. Merge 归并排序（磁盘上）

11. Combiner 合并

12. 所有 MapTask 任务完成后，启动相应数量的 ReduceTask，并告知 ReduceTask 处理数据范围（数据分区）

13. 下载到 ReduceTask 本地磁盘 合并文件 归并排序

14. 一次读取一组

15. 分组 GroupingComparator

16. 默认 TextOutputFormat -> Reduce(k, v), Context.write(k, v) -> OutputFormat -> RecordWriter -> Write(k, v)

__Shuffle（Map 方法之后，Reduce 方法之前）__ 

Shuffle 机制

1. Map 方法进图 getPartition 方法，获取分区，然后进入环形缓冲区，默认 100M
2. 环形缓冲区到达 80% 时进行逆写
3. 数据溢出后，对数据进行排序（快排），通过重新排序 key 的 index，优先按照字典顺序排序
4. 产生两个文件 split.index 和 split.out
5. 对这些数据进行归并排序
6. Combiner（可选）
7. 压缩数据
8. 数据写入磁盘，等待 Reduce 来拉取数据
9. Reduce 拉取数据放入内存缓冲，不够时溢出到磁盘，最后归并排序
10. 按照相同的 key 分组，进入到 Reduce 方法

Partition 分区

- 问题引出

    要求将统计结果按照条件输出到不同文件中（分区）

- 默认 Partition 分区

    Job 中设置

    ```java
    job.setNumReduceTasks(int num);
    ```

    HashPartitioner

    ```java
    public class HashPartitioner<K2, V2> implements Partitioner<K2, V2> {
        public void configure(JobConf job) {}
        /** Use {@link Object#hashCode()} to partition. */
        public int getPartition(K2 key, V2 value, int numReduceTasks) {
            return (key.hashCode() & Integer.MAX_VALUE) % numReduceTasks;
        }
    }
    ```

    MapTask

    ```java
    public class MapTask extends Task {
        @Override
        public void write(K key, V value) throws IOException, InterruptedException {
          collector.collect(key, value, partitioner.getPartition(key, value, partitions));
        }
    }
    ```

    如果没有设置 NumReduceTasks 则不会通过 HashPartitioner

    MapTask

    ```java
    private class NewOutputCollector<K,V>
        extends org.apache.hadoop.mapreduce.RecordWriter<K,V> {
        private final MapOutputCollector<K,V> collector;
        private final org.apache.hadoop.mapreduce.Partitioner<K,V> partitioner;
        private final int partitions;
        NewOutputCollector(org.apache.hadoop.mapreduce.JobContext jobContext,
                           JobConf job,
                           TaskUmbilicalProtocol umbilical,
                           TaskReporter reporter
                          ) throws IOException, ClassNotFoundException {
            collector = createSortingCollector(job, reporter);
            partitions = jobContext.getNumReduceTasks();
            if (partitions > 1) {
                partitioner = (org.apache.hadoop.mapreduce.Partitioner<K,V>)
                    ReflectionUtils.newInstance(jobContext.getPartitionerClass(), job);
            } else {
                partitioner = new org.apache.hadoop.mapreduce.Partitioner<K,V>() {
                    @Override
                    public int getPartition(K key, V value, int numPartitions) {
                        return partitions - 1;
                    }
                };
            }
        }
    }
    ```

- 自定义 Partitioner

    - 自定义类继承 Partitioner，重写 getPartition() 方法

    - 在 Job 驱动中，设定自定义 Partitioner

        ```java
        job.setPartitionerClass(MyPartitioner.class);
        ```

    - 自定义 Partition 后，要根据自定义 Partitioner 的逻辑设置相应数量的 ReduceTask

        ```java
        job.setNumReduceTasks(2);
        ```

    - 如果设置数量小于 Partition 数量，则会抛出 `IOException` 

    - 如果设置数量等于 1，则不会走自己的 Partitioner

    - 如果设置数量大于 Partition 数量，则会多产生空文件

    - 分区号必须从 0 开始，并且连续

WritableComparable 排序

- 排序概述

    MapTask 和 ReduceTask 均会对数据按照 key 进行排序，该操作属于 Hadoop 的默认行为，任何用用程序中的数据均会被排序，而不管逻辑上是否需要

    默认排序是用快排按照字典顺序排序

    对于 MapTask，它会将处理结果暂时放到环形缓冲区中，当环形缓冲区使用率达到一定阈值后，再对缓冲区中的数据进行一次快速排序，并将这些有序数据溢写到磁盘上，当数据处理完后，它会对磁盘上所有文件进行归并排序

    对于 ReduceTask，它从每个MapTask 上远程拷贝相应的数据文件，如果文件大小超过一定阈值，则溢写到磁盘上，否则存储到内存中，如果磁盘文件数目达到一定阈值，则进行一次归并排序，以生成一个更大的文件，如果内存中文件大小或者数目超过一定阈值，则进行一次合并后将数据溢写到磁盘上，当所有数据拷贝完毕后，ReduceTask 统一对内存和磁盘上的所有数据进行一次归并排序

- 排序分类

    - 部分排序

        MapReduce 根据输入记录的键值对数据集排序，保证输出的每个文件内部有序

    - 全排序

        最终输出结果只有一个文件，且文件内部有序，实现方式是只设置一个 ReduceTask，但该方法在处理大型文件时效率极低，因为一台机器处理了所有的文件，完全丧失了 MapReduce 所提供的并行架构

    - 辅助排序（GroupingComparator 分组）

        在 Reduce 端对 key 进行分组，应用于：在接收 key 为 bean 对象时，想让一个或几个字段相同（全部字段比较不同）的 key 进入到同一个 reduce 方法时，可以采用分组排序

    - 二次排序

        在自定义排序过程中，如果 compareTo 中判断条件为两个即为二次排序

Combiner 

- Combiner 合并
    - Combiner 是 MR 程序中 Mapper 和 Reducer 之外的一种组件

    - Combiner 组件的父类就是 Reducer

    - Combiner 和 Reducer 的区别在于运行的位置

        Combiner 是在每一个 MapTask 所在的节点运行

        Reducer 是接收全局所有 Mapper 的输出结果

    - Combiner 的意义就是对每一个 MapTask 的输出进行局部汇总，以减小网络传输量

    - Combiner 能够应用的前提是不能影响最终的业务逻辑（算平均值），而且，Combiner 的输出 <K, V> 应该跟 Reducer 的输入 <K, V> 类型要对应起来

- Combiner 实现步骤

    ```java
    // 其实这个代码和 Reducer d
    public class WordCountCombiner extends Reducer<Text, IntWritable, Text, IntWritable> {
        private IntWritable outV = new IntWritable();
        
        @Override
        protected void reduce(Text key, Iterable<IntWritable> values, Context context)
            throws IOException, InterruptedException {
            int sum = 0;
            for (IntWritable value : values) {
                sum += value.get();
            }
            outV.set(sum);
            context.write(key, outV);
        }
    }
    ```

    Job 添加 Combiner

    ```java
    job.setCombinerClass(WordCountCombiner.class);
    ```

__输出的数据 OutputFormat__ 

OutputFormat 接口实现类

- NullOutputFormat
- FileOutputFormat
    - MapFileOutputFormat
    - SequenceFileOutputFormat
        - SequenceFileAsBinaryOutputFormat
    - TextOutputFormat
- FilterOutputFormat
    - LazyOutputFormat
- DBOutputFormat
    - DBOutputFormat

自定义 OutputFormat

RecordWriter

```java
public class LogRecordWriter extends RecordWriter<Text, NullWritable> {
    private FSDataOutputStream log1Out;
    private FSDataOutputStream log2Out;
    public LogRecordWriter (TaskAttemptContext job) {
        try {
            FileSystem fs = FileSystem.get(job.getConfiguration());   
            log1Out = fs.create(new Path("/var/log/log1.log"));
            log2Out = fs.create(new Path("/var/log/log2.log"));
        } catch(IOException e){
            e.printStackTrace();
        }
    }
    
    @Override
    public void write (K key, V value) throws IOException, InterruptedException {
        if(....) {
            log1Out.writeBytes(key+"\n");
        } else {
            log2Out.writeBytes(key+"\n");
        }
    }
    
    @Override
    public void close (TaskAttemptContext context) throws IOException, InterruptedException {
 		IOUtils.closeStream(log1Out);
        IOUtils.closeStream(log2Out);
    }
}
```

OutputFormat

```java
public class LogOutputFormat extends FileOutputFormat<Text, NullWritable> {
    @Override
    public RecordWriter<Text, NullWritable> getRecordWriter(TaskAttemptContext job)
        throws IOException, InterruptedException {
        return new LogRecordWriter(job);
    }
}
```

Job 绑定

```java
job.setOutputFormatClass(LogOutputFormat.class);
```

__MapReduce 源码分析__ 

MapTask 工作机制

1. Read 阶段
    - 待处理文本
    - 配置参数，任务规划
    - 提交信息
    - 计算出 MapTask 数量
2. Map 阶段
    - InputFormat（默认 TextInputFormat）- RecorderReader
    - Map 逻辑运算
3. Collect 阶段
    - 分区，排序
4. 溢写阶段
    - 溢出到文件（分区且区内有序）
5. Merge 阶段
    - Merge 归并排序

ReduceTask 工作机制

1. Copy 阶段

    ReduceTask 从各个 MapTask 上远程拷贝一片数据，并针对某一数据，如果其大小超过一定阈值，则写到磁盘上，否则直接放到内存中

    - 从 MapTask 拉取处理好的数据到 ReduceTask 上

2. Sort 阶段

    在远程拷贝数据的同时，ReduceTask 启动了两个后台线程对内存和磁盘上的文件进行合并，以防止内存使用过多或者磁盘上文件过多。按照 MapReduce 语义，用户编写 reduce() 函数输入数据是按 key 进行聚集的一组数据，为了将 key 相同的数据聚在一起，Hadoop 采用了基于排序的策略，由于各个 MapTask 实现已经对自己的处理结果进行了局部排序，因此，ReduceTask 只需要对所有数据进行一次归并排序即可

    - 合并文件，归并排序
    - 分组

3. Reduce 阶段

    reduce() 函数将计算结果写到 HDFS 上

    - OutputFormat 输出（默认 TextOutputFormat）

ReduceTask 并行度决定机制

- 设置 ReduceTask 并行度（个数）

    ```java
    job.setNumReduceTasks(int num);
    ```

- 注意事项

    - ReduceTask = 0，表示没有 Reduce 阶段，输出文件和 Map 个数一致
    - ReduceTask 默认值为 1，所以输出文件就一个
    - 如果数据分布不均匀，就有可能在 Reduce 阶段产生数据倾斜
    - ReduceTask 数量并不是任意设置，还要考虑业务逻辑需求，有些情况下，需要计算全局汇总结果，就只能有一个 ReduceTask
    - 具体多少个 ReduceTask，需要根据集群性能而定
    - 如果分区不是 1，但是 ReduceTask 为 1，是否执行分区过程：不执行分区过程，因为 MapTask 源码中，执行分区的前提是先判断 ReduceNum 个数书否大于 1，不大于 1 肯定不执行

MapTask 与 ReduceTask 源码分析

MapTask 解析流程

> context.write(key, value);
>
>  -> collector.collect(key, value, partitioner.getPartition(key, value, partitions));
>
> ​      collect();
>
> ​      -> close();
>
> ​           -> collector.flush();
>
> ​				-> sortAndSpill();
>
> ​					-> sorter.sort();
>
> ​			    <- mergeParts();
>
> ​			<- collector.close();

ReduceTask 解析流程

> if(isMapOrReduce())
>
> initialize();
>
> init(shuffleContext);
>
> -> totalMaps = job.getNumMapTasks();
>
> ​	merger = createMergeManager(context);
>
> ​	-> this.inMemoryMerger = createInMemoryMerger();
>
> ​		this.ondiskMerger = new OnDiskMerger(this);
>
> rIter = shuffleConsumerPlugin.run();
>
> -> eventFetcher.start();
>
> ​	eventFetcher.shutDown();
>
> ​	copyPhase.complete();
>
> ​	taskStatus.setPhase(TaskStatus.Phase.SORT);
>
> ​	sortPhase.complete();
>
> reduce();
>
> cleanup(context);

__Join__ 

Map 端的主要工作：为来自不同或文件的 key / value 对，打标签以区别不同来源的记录，然后用连接字段作为 key，其余部分和新加的标志作为 value，最后进行输出

Reduce 端的主要工作：在 Reduce 端以连接字段作为 key 的分组已经完成，我们只需要在每一个分组当中将那些来源于不同文件的记录（在 Map 阶段已经打标志）分开，最后进行合并就行了

- Reduce Join

    缺点：这种方式中，合并操作是在 Reduce 阶段完成，Reduce 端的处理压力太大，Map 节点的运算负载很低，资源利用率不高，且在 Reduce 阶段极易产生数据倾斜 

- Map Join

    - 使用场景

        一张小表，一张大表

    - 具体方法

        将文件读取到缓存集合中

        ```java
        job.addCacheFile(new URI("file:///"));
        job.addCacheFile(new URI("hdfs://"));
        ```

        Map 的 join 阶段不需要 Reduce 阶段

        ```java
        job.setNumReduceTasks(0);
        ```

        读取缓存的文件数据（setup 方法中）

        - 获取缓存文件
        - 循环读取缓存文件一行
        - 切割
        - 缓存数据到集合
        - 关流

        map

        - 获取一行
        - 截取
        - 获取 id
        - 获取集合中的 value

__ETL__ （数据清洗）

ETL （Extract-Transform-Load）用来描述将数据从来源端经过抽取（Extract），转换（Transform），加载（Load）至目的端的过程

MapReduce 通过设置 MapTask 以及取消 ReduceTask，在 MapTask 内的 map() 方法决定是否写入 context 来实现是否需要该数据

__MapReduce 总结__ 

1. 输入数据接口：InputFormat

    - 默认使用的实现类是：TextInputFormat
    - TextInputFormat 的功能逻辑是：一次读一行文本，然后将起始的偏移量作为 key，行作为内容返回
    - CombineTextInputFormat 可以把多个小文件合并成一个切片处理，提高处理效率

2. 逻辑处理接口：Mapper

    用户根据业务需求实现其中的三个方法：map() setup() cleanup()

3. Partitioner 分区

    - 有默认实现 HashPartitioner，逻辑是根据 key 的哈希值和 numReduces 来返回一个分区号：`key.hashCode()&Integer.MAX_VALUE % numReduces`
    - 如果业务上有特别需求，可以自定义分区

4. Comparable 排序

    - 当我们用自定义对象作为 key 来输出时，就必须要实现 WritableComparable 接口，重写其中的 compareTo() 方法
    - 部分排序：对最终输出的每一个文件进行内部排序
    - 全排序：对所有数据进行排序，通常只有一个 Reduce
    - 二次排序：排序条件有两个

5. Join

    - 前提：不影响最终业务逻辑
    - 提前聚合解决数据倾斜

6. Reducer

    用户根据业务需求实现其中的三个方法：map() setup() cleanup()

7. OutputFormat

    - 默认是 TextOutputFormat 按行输出到文件
    - 自定义 OutputFormat

### 4.4 数据压缩

__概述__ 

优缺点

- 压缩的优点：减少磁盘 IO，减少磁盘存储空间
- 压缩的缺点：增加 CPU 开销

压缩原则

- 运算密集型的 Job，少用压缩
- IO 密集型的 Job，多用压缩

__MR 支持的压缩编码__ 

压缩算法对比

| 压缩格式 | Hadoop 自带？ | 算法    | 文件扩展名 | 是否可切片 | 原来程序是否需要修改           |
| -------- | ------------- | ------- | ---------- | ---------- | ------------------------------ |
| DEFLATE  | 是            | DEFLATE | .deflate   | 否         | 不需要，和文本处理一样         |
| Gzip     | 是            | DEFLATE | .gz        | 否         | 不需要，和文本处理一样         |
| bzip     | 是            | bzip2   | .bz2       | 是         | 不需要，和文本处理一样         |
| LZO      | 否，需要安装  | LZO     | .lzo       | 是         | 需要建索引，还需要指定输入格式 |
| Snappy   | 是            | Snappy  | .snappy    | 否         | 不需要，和文本处理一样         |

__压缩性能比较__ 

| 压缩算法 | 原始文件大小 | 压缩文件大小 | 压缩速度  | 解压速度  |
| -------- | ------------ | ------------ | --------- | --------- |
| gzip     | 8.3 GB       | 1.8 GB       | 17.5 MB/s | 58 MB/s   |
| bzip2    | 8.3 GB       | 1.1 GB       | 2.4 MB/s  | 9.5 MB/s  |
| LZO      | 8.3 GB       | 2.9 GB       | 49.3 MB/s | 74.6 MB/s |

__压缩方式选择__ 

压缩方法选择时重点考虑：压缩/解压缩速度，压缩率（压缩后存储大小），压缩后是否可以支持切片

- Gzip 压缩

    优点：压缩率比较高

    缺点：不支持 split，压缩/解压速度一般

- Bzip2 压缩

    优点：压缩率高，支持 split

    缺点：压缩/解压速度慢

- Lzo 压缩

    优点：压缩/解压速度比较快，支持 split

    缺点：压缩率一般，想支持切片需要创建额外索引

- Snappy 压缩

    优点：压缩和解压缩速度快

    缺点：不支持 split，压缩率一般

- 压缩位置选择

    压缩可以在 MapReduce 任意阶段使用

    - 输入端采用压缩

        无须显示指定使用的编解码方式，Hadoop 自动检查文件扩展名，如果扩展名能够匹配，就会用恰当的编解码方式对文件进行压缩和解压

        企业开发考虑因素

        - 数据量小于块大小，重点考虑压缩和解压缩速度比较快的 LZO/Snappy
        - 数据量非常大，重点考虑支持切片的 Bzip2 和 LZO

    - Mapper 输出采用压缩

        企业开发中选择

        - 为了减少 MapTask 和 ReduceTask 直接的网络 IO，重点考虑压缩和解压缩快的 LZO 和 Snappy

    - 输出端采用压缩

        看需求：

        - 如果数据永久保存，考虑压缩率比较高的 Bzip2 和 Gzip
        - 如果作为下一个 MapReduce 输入，需要考虑数据量和是否支持切片

__压缩参数设置__ 

编解码器

| 压缩格式 | 编解码器                                   |
| -------- | ------------------------------------------ |
| DEFLATE  | org.apache.hadoop.io.compress.DefaultCodec |
| gzip     | org.apache.hadoop.io.compress.GzipCodec    |
| bzip2    | org.apache.hadoop.io.compress.BZip2Codec   |
| LZO      | com.hadoop.compression.lzo.LzopCodec       |
| Snappy   | org.apache.hadoop.io.compress.SnappyCodec  |

参数配置

| 参数                                                         | 默认值                                     | 阶段         | 建议                                              |
| ------------------------------------------------------------ | ------------------------------------------ | ------------ | ------------------------------------------------- |
| io.compression.codecs (core-site.xml)                        | 无，用 hadoop checknative 查看             | 输入压缩     | Hadoop 通过文件扩展名判断是否支持某种编解码器     |
| mapreduce.map.output.compress（mapred-site.xml）             | false                                      | mapper 输出  | true 为启用压缩                                   |
| mapreduce.map.output.compress.codec（mapred-site.xml）       | org.apache.hadoop.io.compress.DefaultCodec | mapper 输出  | 企业多使用 LZO 或 Snappy 编解码器在此阶段压缩数据 |
| mapreduce.output.fileoutputformat.compress（mapred-site.xml） | false                                      | reducer 输出 | true 为启用压缩                                   |
| mapreduce.output.fileoutputformat.compress.codec（mapred-site.xml） | org.apache.hadoop.io.compress.DefaultCodec | reducer 输出 | 使用标准工具或者编解码器，如 gzip 和 bzip2        |

Map 阶段

```java
Configuration conf = new Configuration();
conf.setBoolean("mapreduce.map.output.compress", true);
conf.setClass("mapreduce.map.output.compress.codec", BZip2Codec.class, CompressionCodec.class);
```

Reduce 阶段

```java
FileOutputFormat.setCompressOutput(job, true);
FileOutputFormat.setOutputCompressorClass(job, BZip2Codec.class);
```

## 5. Yarn

### 5.1 Yarn 基础架构

YARN 由 ResourceManager，NodeManager，ApplicationMaster 和 COntainer 组成

- ResourceManager
    - 处理客户请求
    - 监控 NodeManager
    - 启动或监控 ApplicationMaster
    - 资源的分配与调度
- NodeManager
    - 管理单个节点上的资源
    - 处理来自 ResourceManager 的命令
    - 处理来自 ApplicationMaster 的命令
- ApplicationMaster
    - 为应用程序申请资源并分配给内部任务
    - 任务的监控与容错
- Container
    - Container 是 YARN 中资源的抽象，封装了某个节点上的多维度资源，比如内存，CPU，磁盘，网络等

### 5.2 Yarn 工作机制

YARN 工作机制

1. 申请一个 Application
2. Application 资源提交路径 hdfs:// 以及 `application_id` 
3. 提交 Job 运行所需资源
4. 资源提交完毕，申请运行 MRAppMaster
5. 将用户的请求初始化成一个 Task（FIFO 队列）
6. （NodeManager）领取 Task 任务
7. 创建容器 Container
8. 下载 Job 资源到本地
9. 申请运行 MapTask 容器
10. （NodeManager）领取任务，创建容器
11. （MRAppMaster）发送程序启动脚本（YarnChild）
12. 向 ResourceManager 申请 2 个容器，运行 ReduceTask 程序
13. Reduce 向 Map 获取相应分区的数据
14. 程序运行完后，MR 会向 ResourceManager 注销自己

### 5.3 Yarn 调度器和调度算法

调度器

- FIFO 调度器

    单队列，根据提交任务的顺序，先到先服务

    缺点：单个 job 会卡死整个任务

- 容量调度器（Capacity Scheduler）- Apache Hadoop 默认

    ```xml
    <property>
    	<name>yarn.resourcemanager.scheduler.class</name>
        <value>org.apache.hadoop.yarn.server.resourcemanager.scheduler.capacity.CapacityScheduler</value>
    </property>
    ```

    Yahoo 开发的多用户调度器

    特点：

    - 多队列：每个队列可配置一定的资源量，每个队列采用 FIFO 调度策略
    - 容量保证：管理员可为每个队列设置资源最低保证和资源使用上限
    - 灵活性：如果一个队列中的队列有剩余，可以暂时共享给那些需要资源的队列，而一旦该队列有新的应用程序提交，则其它队列借调的资源会归还给该队列
    - 多租户：
        - 支持多用户共享集群和多应用程序同时运行
        - 防止同一个用户的作业队列独占队列中的资源，该调度器会对同一用户提交的作业所占的资源量进行限定

    算法：

    - 队列资源分配

        从 root 开始，使用深度优先算法，优先选择资源占用率最低的队列分配资源

    - 作业资源分配

        默认按照提交作业的优先级和提交时间顺序分配资源

    - 容器资源分配

        按照容器的优先级分配资源

        如果优先级相同，按照数据本地性原则：

        - 任务和数据在同一节点
        - 任务和数据在同一机架
        - 任务和数据不在同一节点也不在同一机架

- 公平调度器（Fair Scheduler）- CDH 默认

    Faire Scheduler 是 Facebook 开发的多用户调度器

    - 与容量调度器的相同点

        - 多队列：支持多队列作业
        - 容量保证：管理员可为每个队列设置资源最低保证和资源使用上限
        - 灵活性：如果一个队列中资源有剩余，可以暂时共享给那些需要资源的队列，而一旦该队列有新的应用程序提交，则其它队列借调的资源会归还给该队列
        - 多租户：支持多用户共享集群和多应用程序同时执行，为了防止同一个用户的作业独占队列中的资源，该调度器会对同一用户提交的作业所占的资源量进行限定

    - 与容量调度器不同点

        - 核心调度策略不同

            容量调度器：优先选择资源利用率低的队列

            公平调度器：优先选择对资源的缺额比例大的

        - 每个队列可以单独设置资源分配的方式

            容量调度器：FIFO，DRF

            公平调度器：FIFO，FAIR，DRF

    - 资源分配方式

        - FIFO
        
            公平调度器每个队列资源分配策略如果选择 FIFO 的话，公平调度器 = 容量调度器
        
        - Fair（默认）
        
            一种基于最大最小公平算法实现的资源多路复用方式，默认情况下，每个队列内部采用该方式分配资源，这意味着如果一个队列中有两个应用程序同时运行，则每个应用程序可得到 1/2 的资源，如果三个应用程序同时运行，则每个应用程序可得到 1/3 的资源
        
            具体资源分配流程和容量调度器一致
        
            - 选择队列
            - 选择作业
            - 选择容器
        
            以上三步，每一步都是按照公平分配策略分配资源
        
            > 实际最小资源份额：mindshare = Min （资源需求量，配置的最小资源）
            >
            > 是否饥饿：isNeedy = 资源使用量 < mindshare（实际最小资源份额）
            >
            > 资源分配比：minShareRatio = 资源使用量 / Max（mindshare，1）
            >
            > 资源使用权重比：useToWeightRatio = 资源使用量 / 权重
        
            分别计算比较对象的（实际最小资源份额，是否饥饿，资源分配比，资源使用权重比）
        
            判断两种比较对象饥饿状态 === 其中一个饥饿 ==> 饥饿优先
        
            都饥饿 ===> 资源分配比小者优先，相同则按照提交时间顺序
        
            都不饥饿 ===> 资源使用权值比小者优先，相同则按照提交时间顺序
        
            - 作业资源分配方式
                - 加权（关注的是 Job 的个数）
                - 不加权（关注的是 Job 的权重）
        
        - DRF 策略
        
            Dominant Resource Fairness
        
            考虑 CPU 和内存的需求判断主要 Job 的因素来分配

### 5.4 Yarn 生产环境配置参数

### 5.5 Yarn 常用命令

以 WordCount 为例

- yarn application 查看任务

    ```bash
    # 列出所有 Application
    yarn application -list
    # 根据 Application 状态过滤：yarn application -list -appStates
    yarn application -list -appStates xxx
    # 所有状态
    # ALL
    # NEW
    # NEW_SAVING
    # SUBMITTED
    # ACCEPTED
    # RUNNING
    # FINISHED
    # FAILED
    # KILLED
    # kill 掉 Application
    yarn application -kill <appid>
    ```

- yarn logs 查看日志

    ```bash
    # 查看 Application 日志
    yarn logs -applicationId <appid>
    # 查看你 Container 日志
    yarn logs -applicationId <appid> -containerId <containerid>
    ```

- yarn applicationattempt 查看尝试运行的任务

    ```bash
    # 列出所有 Application 尝试列表
    yarn applicationattempt -list <appid>
    # 打印 Application Attempt 状态
    yarn applicationattempt -status <attemptid>
    ```

- yarn container 查看容器

    ```bash
    # 列出所有 Container
    yarn container -list <appid?attemptid?>
    # 打印 container 状态
    yarn container -status <containerid>
    ```

- yarn node 查看节点状态

    ```bash
    # 列出所有节点
    yarn node -list -all
    ```

- yarn rmadmin 更新配置

    ```bash
    # 加载队列配置
    yarn readmin -refreshQueues
    ```

- yarn queue 查看队列

    ```bash
    yarn queue -status <queueName>
    ```
