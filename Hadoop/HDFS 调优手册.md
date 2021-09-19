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

## 2. HDFS 集群压测

## 3. HDFS 多目录

## 4. HDFS 集群扩容及缩容

## 5. HDFS 存储优化

