# Kafka

## Kafka 概述

### 定义

Kafka 是一个__分布式__的基于__发布/订阅模式__的__消息队列__

使用 ：Spark 与 Kafka 对接

### 消息队列

#### 传统消息队列的应用场景

- 传统消息队列应用场景

MQ 异步处理

​	同步处理

 1. 填写注册信息

 2. 注册信息写入数据库

 3. 调用发送短信接口

 4. 发送短信

 5. 页面响应注册成功

    异步处理

1. 填写初测信息

2. 注册信息写入数据库

3. 发送短信的请求写入消息队列

   ​	MQ -> 发送短信

4. 页面响应注册成功

优点：

1. 解耦
2. 可恢复性
3. 缓冲
4. 灵活性 & 峰值处理能力

#### 消息队列的两种模式

- 点对点模式
- 发布/订阅模式

### Kafka 基础架构

- 生产者生产消息 Producer

  Producer A -> message to A-0 A-1

  Producer B -> message to B-0

- Kafka 集群管理消息 Kafka Cluster -> Broker -> Topic Partition Leader

  Broker 1

  ​	Topic A Partition 0 Leader

  ​	Topic A Partition 1 Follower

  Broker 2

  ​	Topic A Partition 0 Leader

  ​	Topic A Partition 1 Follower

  Replication A/0 A/1

  ​	Broker1 A-0 -> Broker2 A-0

  ​	Broker2 A-1 -> Broker1 A-1

  Broker 3

  ​	Partition 0

  ​		message 0 message 1

- 消费者消费信息

  Group Consumer A & Consumer B

  同一个 Broker 的数据只能被同一个组里唯一一个消费者消费

  或者说 同一个消费组里两个不同消费者不能访问同一个分区 Broker

  ​	Message from A-0 -> Consumer A

  ​	Message from A-1 -> Consumer B

  Message from B-0 -> Consumer C

  消费者组：提高消费能力 -> 一个人消费两个分区的内容

- Zookeeper 注册信息

  Kafka 依赖于 Zookeeper 存储信息 管理集群

  Kafka 同一套集群 -> zk 同一套集群

  消费信息保存在 zk 里

  before 0.9 version offset 储存在 zk

  0.9 and after 0.9 offset 储存在 Kafka 本地 -> 存在磁盘 168H

1. Producer

   消息生产者 向 Kafka Broker 发消息的客户端

2. Consumer

   消息消费者 向 Kafka Broker 取消息的客户端

3. Consumer Group CG

   消费者组，组内每个消费者负责消费不同分区的数据，一个分区只能由一个组内消费者消费，消费者组之间互相不影响，所有的消费者都属于某个消费组，即消费者组是逻辑上的订阅者

4. Broker

   一台 Kafka 服务就是一个 Broker，一个集群由多个 Broker 组成，一个 Broker 可以容纳多个 Topic

5. Topic

   一个队列，生产者和消费者面向的都是一个 Topic

6. Partition

   为了实现扩展性，一个非常大的 Topic 可以分布到多个 Broker 上，一个 Topos 可以分成多个 Partition，每个 Partition 都是一个有序队列

7. Replica

   副本，为保证集群中的某个节点发生故障时，该节点上的 Partition 不丢失，且 Kafka 仍然能正常工作， Kafka 提供了副本机制，一个 Topic 每个分区都有若干个副本，一个 Leader 和若干个 Follower

8. Leader

   每个分区的主副本，生产者发送数据对象，以及消费者消费数据对象的对象都是 Leader

9. Follower

   每个分区的从副本，实时从 Leader 中同步数据，保持和 Leader 数据的同步，Leader 发生故障时，某个 Follower 会成为新的 Leader

## Kafka 入门

### 集群部署

修改 broker

删除 topic 功能

```shell
delete.topic.enable=true
```

设置日志存放路径

```shell
log.dirs=
```

配置 zk 集群地址

```shell
zookeeper.connect=hostname1:2181,hostname2:2181,hostname3:2181
```

配置环境变量

```shell
/etc/profile
```

### Kafka 命令行操作

1. 查看所有 Topic

   ```shell
   kafka-topics.sh --zookeeper hostname:2181 --list
   ```

2. 创建 Topic

   ```shell
   kafka-topics.sh --zookeeper hostname:2181 --create --replication-factor -3 --partitions 1 --topic first
   ```

   - topic 定义 topic 名
   - replication-factor 定义副本数
   - partitions 定义分区数

3. 删除 Topic

   ```shell
   kafka-topics.sh --zookeeper hostname:2181 --delete --topic first
   ```

   需要 server.properties 中设置 delete.topic.enable=true，否则只是标记删除

4. 发送消息

   ```shell
   kafka-topics.sh --broker-list hostname:9092 --topic first
   >message1
   >message2
   ```
   
5. 消费信息

   ```shell
   kafka-console-consumer.sh \ --zookeeper hostname:2181 --topic first
   kafka-console-consumer.sh \ --bootstrap-server hostname:9092 --topic first
   kafka-console-consumer.sh \ --bootstrap-server hostname:9092 --from-beginning --topic first
   ```

   --from-beginnning：会把主题中以往所有的数据都读出来

6. 查看某个 Topic 详情

   ```shell
   kafka-topics.sh --zookeeper hostname:2181 --describe --topic first
   ```

7. 修改分区数

   ```shell
   kafka-topics.sh --zookeeper hostname:2181 --alter --topic first --partitions 6
   ```
## Kafka 架构深入

## Kafka API

## Kafka 监控

## Flume 对接 Kafka

## Kafka 面试题

