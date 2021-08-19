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

__Shuffle__ 

__输出的数据 OutputFormat__ 

__Join__ 

__ETL__ 

### 4.4 数据压缩

__压缩算法__ 

__特点__ 

__生产环境使用__ 

### 4.5 常见错误与解决方案

## 5. Yarn

