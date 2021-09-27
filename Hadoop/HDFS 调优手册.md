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

### 3.1 NameNode 多目录配置

- NameNode 的本地目录可以配置成多个，且每个目录存放的内容相同，增加了可靠性

- 配置

    hdfs-site.xml

    ```xml
    <property>
    	<name>dfs.namenode.name.dir</name>
        <value>file://${hadoop.tmp.dir}/dfs/name1, file://${hadoop.tmp.dir}/dfs/name2</value>
    </property>
    ```

    然后停止集群，删除每个节点的 data 和 logs 中的数据

    格式化集群并启动

### 3.2 DataNode 多目录配置

- DataNode 可以配置成多个目录，每个目录存储的数据不一样

- 配置

    hdfs-site.xml

    ```xml
    <property>
    	<name>dfs.datanode.data.dir</name>
        <value>file://${hadoop.tmp.dir}/dfs/data1,file://${hadoop.tmp.dir}/dfs/data2</value>
    </property>
    ```

    然后重启集群

### 3.3 集群数据均衡 - 磁盘间数据均衡

- 生成均衡计划

    ```bash
    hdfs diskbalancer -plan <name>
    ```

- 执行均衡计划

    ```bash
    hdfs diskbalancer -execute <name>.plan.json
    ```

- 查看均衡任务执行情况

    ```bash
    hdfs diskbalancer -query <name>
    ```

- 取消均衡任务

    ```bash
    hdfs diskbalancer -cancel <name>.plan.json
    ```

## 4. HDFS 集群扩容及缩容

### 4.1 添加白名单

白名单：表示在白名单的主机 IP 地址可以访问集群，用来存储数据

企业中：配置白名单，可以尽量防止黑客恶意访问攻击

配置白名单步骤：

- 在 NameNode 节点的 `$HADOOP_HOME/etc/hadoop/` 下创建 `whitelist` 和 `blacklist`  

- 在 `hdfs-site.xml` 中增加 `dfs.hosts` 参数

    ```xml
    <property>
    	<name>dfs.hosts</name>
        <value>$HADOOP_HOME/etc/hadoop/whitelist</value>
    </property>
    <property>
    	<name>dfs.hosts.exclude</name>
        <value>$HADOOP_HOME/etc/hadoop/blacklist</value>
    </property>
    ```

- 第一次添加白名单需要重启集群，不是第一次只要刷新 NameNode 即可

    ```bash
    hdfs dfsadmin -refreshNodes
    ```

### 4.2 节点间数据均衡

- 数据均衡命令

    ```bash
    # 开启数据均衡
    # 最大相差磁盘空间利用率不超过 10%
    $HADOOP_HOME/sbin/start-balancer.sh threshold 10
    # 关闭数据均衡
    $HADOOP_HOME/sbin/stop-balancer.sh
    ```

### 4.3 退役旧节点

- 黑名单

## 5. HDFS 存储优化

### 5.1 纠删码

- 纠删码原理

    HDFS 默认情况下，一个文件有 3 个副本，提高了数据可靠性，但是带来了 2 倍的冗余开销

    Hadoop 3.x 引入纠删码，采用计算的方式，可以节省约 50% 左右的空间

- 纠删码命令

    ```bash
    hdfs ec
    # 纠删码策略
    hdfs ec -listPolicies
    # 添加纠删码
    hdfs ec -addPolicies -policyFile <file>
    # 获取某一路径的纠删码策略
    hdfs ec -getPolicy -path <path>
    # 删除策略
    hdfs ec -removePolicy -policy <policy>
    # 设置某一路径的纠删策略
    hdfs ec -setPolicy -path <path> -policy <policy>
    # 取消某一路径策略
    hdfs ec -unsetPolicy -path <path>
    # 开启或者关闭某个纠删策略
    hdfs ec -enablePolicy -policy <policy>
    hdfs ec -disablePolicy -policy <policy>
    ```

- 纠删码策略解释

    RS-3-2-1024k：使用 RS 编码，每 3 个数据单元，生成 2 个校验单元，共 5 个单元，只要有任意 3 个单元存在，就可以得到原始数据，每个单元是 1024k

### 5.2 异构存储（冷热数据分离）

异构存储主要解决：不同的数据，存储在不同类型的硬盘中，达到最佳性能的问题

存储类型和存储策略

- 存储类型

    - RAM_DISK（内存镜像文件系统）
    - SSD（SSD 固态硬盘）
    - DISK（普通磁盘，在 HDFS 中，如果没有主动声明数据目录存储类型，默认都是 DISK）
    - ARCHIVE（没有特指哪种存储介质，主要的指的是计算能力比较弱而存储密度比较高的存储介质，用来解决数据的容量扩增的问题，一般用于归档）

- 存储策略

    | 策略 ID | 策略名称      | 副本分布             |
    | ------- | ------------- | -------------------- |
    | 15      | Lazy_Persist  | RAM_DISK:1, DISK:n-1 |
    | 12      | All_SSD       | SSD:n                |
    | 10      | One_SSD       | SSD:1, DISK:n-1      |
    | 7       | Hot (default) | DISK:n               |
    | 5       | Warm          | DISK:1, ARCHIVE:n-1  |
    | 2       | Cold          | ARCHIVE:n            |

    从 Lazy_Persist 到 Cold，分别代表了设备的访问速度从快到慢

- Shell

    ```bash
    # 查看可用的存储策略
    hdfs storagepolicies -listPolicies
    # 为指定路径设定存储策略
    hdfs storagepolicies -setStoragePolicy -path <path> -policy <policy>
    # 获取指定路径的存储策略
    hdfs storagepolicies -getStoragePolicy -path <path>
    # 取消存储策略，以上级目录为准，根目录是 HOT
    hdfs storagepolicies -unsetStoragePolicy -path <path>
    # 查看文件块分布
    hdfs fsck xxx -files -blocks -locations
    # 查看集群节点
    hadoop dfsadmin -report
    # 根据存储策略自动移动文件夹
    hdfs mover <path>
    ```

- 配置文件 `hdfs-site.xml` 

    ```xml
    <property>
    	<name>dfs.datanode.data.dir</name>
        <value>[SSD]file:///xxx,[RAM_DISK]file:///,[DISK]file:///xxx</value>
    </property>

- 关于 Lazy_Persist `hdfs-site.xml` 

    ```xml
    <property>
    	<name>dfs.datanode.max.locked.memory</name>
        <value>0</value>
       	<!-- 当这个值小于 dfs.block.size 时，会写入客户端所在的 DISK 目录，其余会写入其它节点的 DISK 目录 -->
        <!-- 通过 ulimit -a 查看机器最大可以配置的值 -->
    </property>
    <property>
    	<name>dfs.block.size</name>
        <value></value>
    </property>
    ```

- 关于 Cold

    当目录为 Cold 并且未配置 ARCHIVE 目录情况下会抛出异常

## 6. HDFS 故障排除

### 6.1 NameNode 故障排除

可以将 `secondarynamenode` 中的数据拷贝到 `namenode` 中

### 6.2 集群安全模式 & 磁盘修复

- 安全模式

    文件只接受读数据，不接受删除和修改数据

- 进入安全模式场景

    - NameNode 在加载镜像文件和编辑日志期间处于安全模式
    - NameNode 再接收 DataNode 注册时处于安全模式

- 退出安全模式

    - `dfs.namenode.safemode.min.datanodes` 最小可用 DataNode 数量必须大于的数量，默认 0
    - `dfs.namenode.safemode.threshold-pct` 副本数达到最小要求的 block 占系统总 block 数的百分比，默认 0.999f（只允许丢一个块）
    - `dfs.namenode.safemode.extension` 稳定时间，默认 30000 毫秒

- Shell

    ```bash
    hdfs dfsadmin -safemode get
    hdfs dfsadmin -safemode enter
    hdfs dfsadmin -safemode leave
    hdfs dfsadmin -safemode wait
    ```

### 6.3 慢磁盘监控

慢磁盘指写入数据非常慢的磁盘，产生数据延时的问题

- 通过心跳未联系时间检测

    一般出现慢磁盘现象，会影响 DataNode 与 NameNode 之间的心跳，正常情况下心跳间隔时间是 3s

- `fio` 命令测试磁盘读写性能

    ```bash
    # 顺序读测试
    fio -filename=<file> -direct=1 -iodepth 1 -thread -rw=read -ioengine=psync -bs=16k -size=2G -numjobs=10 -runtime=60 -group_reporting -name=<name>
    # 顺序写测试
    fio -filename=<file> -direct=1 -iodepth 1 -thread -rw=write -ioengine=psync -bs=16k -size=2G -numjobs=10 -runtime=60 -group_reporting -name=<name>
    # 随机写测试
    fio -filename=<file> -direct=1 -iodepth 1 -thread -rw=randwrite -ioengine=psync -bs=16k -size=2G -numjobs=10 -runtime=60 -group_reporting -name=<name>
    # 混合随机读写
    fio -filename=<file> -direct=1 -iodepth 1 -thread -rw=randrw -ioengine=psync -bs=16k -size=2G -numjobs=10 -runtime=60 -group_reporting -name=<name>
    ```

### 6.4 小文件归档

- HDFS 存小文件的弊端

    每个文件按块存储，每个块的元数据存储再 NameNode 的内存中，因此 HDFS 存储小文件会非常地低效，因为大量小文件会耗尽 NameNode 中的大部分内存，存储小文件所需要的磁盘容量和数据块大小无关

- 解决存储小文件办法

    > HDFS 存档文件或 HAR 文件，它将文件存入 HDFS 块，再减少 NameNode 内存使用同时，允许对文件进行透明的访问，即 HDFS 存档文件对内是一个个独立的文件，对 NameNode 是一个整体，减少了 NameNode 的内存消耗

- 解决方式

    ```bash
    # 启动 yarn
    start-yarn.sh
    hadoop archive -archiveName xxx.har -p /input /output
    # 查看归档
    hadoop fs -ls har:///outp
    ```


## 7. HDFS 数据迁移

### 7.1 Apache 和 Apache 集群间数据拷贝

- scp 实现两个远程主机之间的文件复制

    ```bash
    # push
    scp -r <local absolute path> user@hostname:<absolute path>
    # pull
    scp -r user@hostname:<absolute path> <local absolute path>
    ```

- 采用 `distcp` 命令实现两个 Hadoop 之间的递归数据复制

    ```bash
    hadoop distcp hdfs://hostsrc:port/<path> hdfs://hostdest:port/<path>
    ```

###  7.2 Apache 和 CDH 之间的拷贝

> CDH 官网

## 8. MapReduce 生产经验

### 8.1 MapReduce 慢

- 计算机性能

    CPU，内存，磁盘，网络

- I/O 操作优化

    - 数据倾斜
    - Map 时间太长，Reduce 等待过久
    - 小文件过多

### 8.2 MapReduce 调优参数

- 自定义分区，减少数据倾斜

    继承 Partition 接口，重写 `getPartition()` 方法

- 减少溢写次数

    `mapreduce.task.io.sort.mb` Shuffle 环形缓冲区大小，默认 100m

    `mapreduce.map.sort.spill.percent` 环形缓冲区溢出的阈值，默认 80%

- 增加每次 merge 的合并次数

    `mapreduce.task.io.sort.factor` 默认 10（根据内存调整）

- 在不影响业务结果的情况下，可以提前采用 Combiner

    `job.setCombinerClass(xxxReducer.class);` 

- 为了减少磁盘 I/O，可以采用 Snappy 或者 LZO 压缩

    `conf.setBoolean("mapreduce.map.output.compress", true);` 

    `conf.setClass("mapreduce.map.output.compress.codec", SnappyCodec.class/CompressionCodec.class);` 

- 调整 MapTask 内存大小

    `mapreduce.map.memory.mb` 默认MapTask 内存上限 1024 MB，可以根据 128m 数据对应 1G 内存

- 控制 MapTask 堆大小

    `mapreduce.map.java.opts` （如果内存不够会报 OOM）

- 调整 Task 的 CPU 核数

    `mapreduce.map.cpu.vcores` 默认为 1，计算密集型任务可以增加 CPU 核数

- 异常充实

    `mapreduce.map.maxattempts` 每个 MapTask 最大重试次数，一旦重试超过该次数，则认为 MapTask 运行失败，默认值 4