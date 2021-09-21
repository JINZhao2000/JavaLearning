# HDFS 调优手册

## 1. HDFS 核心参数

### 1.1 NameNode 内存生产配置

- NameNode 存储计算

    每个文件块大概占用 150 byte，一台服务器 128 G 为例，能存储 128\*1024\*1024\*1024 / 150 ≈ 9.1 亿

- Hadoop 2.x 配置 NameNode 内存

    NameNode 内存默认 2000M，如果服务器内存 4G，NameNode 内存可以配置 3G，在 `hadoop-env.sh` 文件配置如下

    ```bash
    HADOOP_NAMENODE_OPTS=-Xmx3072m
    ```

- Hadoop 3.x 配置 NameNode 内存

    - `hadoop-env.sh` 中描述 Hadoop 内存是动态分配的

    - 自动分配的 NameNode 和 DataNode 的内存是一样大的，这样是不合理的

        [配置参考](https://docs.cloudera.com/documentation/enterprise/6/release-notes/topics/rg_hardware_requirements.html#concept_fzz_dq4_gbb) 

        - NameNode 最小 1G，每增加 1 millions 个 block，增加 1G 内存
        - DataNode 最小 4G，block 数或者副本数升高，都应该调大 DataNode 的值
        - 一个 DataNode 上的副本总数低于 4 millions 调为 4G，超过 4 millions，每增加 1 millions，增加 1G

    - 修改 `hadoop-env.sh` 

        ```bash
        export HDFS_NAMENODE_OPTS="-Dhadoop.security.logger=INFO,RFAS -Xmx1024m"
        export HDFS_DATANODE_OPTS="-Dhadoop.security.logger=ERROR,RFAS -Xmx1024m"
        ```

### 1.2 NameNode 心跳并发配置

- hdfs-site.xml

    ```xml
    <property>
        <!-- NameNode 有一个工作线程池，用来处理不同的 DataNode 的并发心跳以及客户端并发的元数据操作，默认是 10 -->
    	<name>dfs.namenode.handler.count</name>
        <value>21</value>
    </property>
    ```

    企业里：dfs.namenode.handler.count=20 x ln(cluster x size)，比如集群规模（DataNode 台数）为 3 台时，参数为 21

### 1.3 开启回收站配置

开启回收站功能，可以将删除的文件在不超时的情况下，恢复原数据，起到防止误删除、备份等作用

- 回收站工作机制

    被删除的文件移到回收站，设置文件存活时间，检查回收站的间隔时间

- 开启回收站功能参数说明

    - 默认值 `fs.trash.interval = 0`，0 表示禁用回收站，其它的值表示设置文件的存活时间
    - 默认值 `fs.trash.checkpoint.interval = 0`，检查回收站的间隔时间，如果该值为 0，则该值设置和 `fs.trash.interval` 的参数值相等
    - 要求 `fs.trash.checkpoint.interval <= fs.trash.interval` 

- 启用回收站

    修改 core-site.xml

    ```xml
    <property>
    	<name>fs.trash.interval</name>
        <value>1</value> <!-- 分钟 -->
    </property>
    ```

- 查看回收站

    回收站目录在 `/user/<username>/.Trash/` 下

- __网页上直接删除的文件不会经过回收站__ 

- __通过程序删除的文件不会经过回收站，需要调用 moveToTrash() 才进入回收站__  

    ```java
    Trash trash = new Trash(conf);
    trash.moveToTrash(path);
    ```

- __只有在命令行利用 hadoop fs -rm 命令删除的文件才会走回收站__ 

- 恢复回收站数据：mv

## 2. HDFS 集群压测

### 2.1 测试 HDFS 写性能

- 写测试底层原理

    - 测试文件个数 = 集群 CPU 总核数 - 1
    - 记录每个 Map 的写时间和平均速度
    - 汇总每个 MapTask 向 HDFS 写入时间和平均速度

    Throughput = 所有数据量累加/总时间

    Average IO Rate = （map1 的平均速度 + ...... + mapN 的平均速度）/ 文件数量

    IO Rate std deviation 方差

- 命令

    ```bash
    hadoop jar $HADOOP_HOME/share/hadoop/mapreduce/hadoop-mapreduce-client-jobclient-3.3.1-tests.jar TestDFSIO -write -nrFiles 10 -fileSize 128MB
    # arg1：jar 运行 jar包
    # arg2：jar 包名称
    # arg3：TestDFSIO 测试 HDFS 读写性能
    # arg4：-write 写性能
    # arg5：-nrFiles 10 准备文件个数
    # arg6：-fileSize 128MB 准备文件大小
    ```

### 2.2 测试 HDFS 读性能

- 命令

    ```bash
    hadoop jar $HADOOP_HOME/share/hadoop/mapreduce/hadoop-mapreduce-client-jobclient-3.3.1-tests.jar TestDFSIO -read -nrFiles 10 -fileSize 128MB
    ```

## 3. HDFS 多目录

## 4. HDFS 集群扩容及缩容

## 5. HDFS 存储优化

