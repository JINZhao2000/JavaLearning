# Spark 调优

## 1. 资源调优

### 1.1 资源规划

#### 1.1.1 资源设定考虑

1. 总体原则

    以 128G RAM，32 Threads 为例

    先设定单个 Executor 核数，根据 Yarn 配置得出每个节点最多的 Executor 数量

    > 每个节点的 yarn 内存 / 每个节点数量 = 单个节点数量
    >
    > 总 executor 数 = 单节点数量 * 节点数

2. 具体提交参数

    > executor-cores
    >
    > 每个 executor 的最大核数（3-6 比较合理）
    >
    > num-executors
    >
    > = 每个节点的 executor 数 * work 节点数
    >
    > 每个 node 的 executor 数- 单节点 yarn 总核数 / 每个 executor 的最大 cpu 核数
    >
    > > 假设
    > >
    > > yarn.nodemanager.resource,cpu-vcores = 28, executor-cores = 4, node = 10
    > >
    > > 那么每个 node 的 executor 个数为 7
    > >
    > > num-executors = 70
    >
    > executor-memory
    >
    > = yarn-nodemanager.resource.memory-mb / 每个节点 executor 数量
    >
    > > 假设 yarn 参数配置为 100G
    > >
    > > 每个 Executor 大概为 100/7 = 14G
    > >
    > > 注意 yarn 配置中每个容器允许的最大内存是否匹配（yarn.scheduler.maximum-allocation-mb）

#### 1.1.2 内存估算

- Other 内存 = 自定义数据结构 * 每个 executor 核数 （用户自定义数据结构， Spark 内部元数据）
- Storage 内存 = 广播变量 + cache/Executor 数量
- Executor 内存 = 每个 Executor 核数 * （数据集大小  /  并行度）（Shuffle, Join, Group）

#### 1.1.3 调整内存资源

spark.memory.fraction = (估算 storage 内存 + 估算 Execution 内存) / (估算 storage 内存 + 估算 Execution 内存 + 估算 Other 内存)

spark.memory.storageFraction = (估算 storage 内存) / (估算 storage 内存 + 估算 Execution 内存)

### 2.1 持久化和序列化

#### 2.1.1 RDD

kryo 序列化

```scala
set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
```

#### 2.1.2 DataFrame 和 DataSet

默认为 MEMORY_AND_DISK，可以通过使用 MEMORY_AND_DISK_SER 来序列化，不过区别不大