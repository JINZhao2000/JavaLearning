# Rabbit MQ

## 1. 中间件分类

### 1.1 分布式消息中间件

MQ

- ActiveMQ
- RabbitMQ
- Kafka
- RocketMQ

场景

- 消息中间件监控数据
- 异步数据传输场景
- 削峰填谷场景
- 海量数据同步场景
- 分布式事务场景
- 日记管理场景
- 大数据分析场景

协议与设计

- AMQP
- MQTT
- 持久化设计
- Kafka 协议
- 消息分发协议
- 高可用设计
- 可靠性设计
- 容错设计

### 1.2 负载均衡中间件

- Nginx
- Lvs
- KeepAlive
- CDN

### 1.3 缓存中间件

- MemCache
- Redis

### 1.4 数据库中间件

- ShardingJdbc
- Mycat

### 1.5 案例分析

- 异步数据保存
- 订单数据的消息分发
- 分布式事务
- 消息的容错
- 分布式锁
- 分布式会话
- 分库分表

## 2. 消息分发策略

MQ 中的角色

- 生产者
- 消费者
- 存储消息

消息分发策略对比

|          | ActiveMQ | RabbitMQ | Kafka | RocketMQ |
| -------- | -------- | -------- | ----- | -------- |
| 发布订阅 | 支持     | 支持     | 支持  | 支持     |
| 轮询分发 | 支持     | 支持     | 支持  | /        |
| 公平分发 | /        | 支持     | 支持  | /        |
| 重发     | 支持     | 支持     | /     | 支持     |
| 消息拉取 | /        | 支持     | 支持  | 支持     |

## 3. RabbitMQ 安装

- erlang
- rabbitmq

## 4. RabbitMQ 配置

```shell
rabbitmq-plugins enable rabbitmq_management
```

[RabbitMQ Management](xxx:15672/)

```
username : guest
password : guest
```

添加用户

```shell
rabbitmqctl add_user xxx xxx
```

添加成员

```shell
rabbitmqctl set_user_tags xxx administrator
# administrator 登陆控制台，查看所有信息，对 rabbitmq 管理
# monitoring    登录控制台，查看所有信息
# policymaker   登录控制台，指定策略
# management    登录控制台
```

授权

```shell
rabbitmqctl set_permissions -p / xxx ".*" ".*" ".*"
```

命令

```shell
rabbitmqctl add_user name pwd
rabbitmqctl set_user_tags name role
rabbitmqctl change_password name pwdNew
rabbitmqctl delete_user user
rabbitmqctl list_users
rabbitmqctl set_permissions -p / name ".*" ".*" ".*"
```

## 5. 什么是 AMQP

Advanced Message Queuing Protocol，是应用层协议的开发标准，为面向消息的中间件设计

**AMQP 生产者流转过程**

- 建立连接
    - Producer --- protocol header 0-9-1 ---> Broker
    - Broker --- Connection.Start ---> Producer
    - Producer --- Connection.Start.Ok ---> Broker
    - Broker --- Connection.Tune ---> Producer
    - Producer --- Connection.Tune.Ok ---> Broker
    - Producer --- Connection.Open ---> Broker
    - Broker ---> Connection.Open.Ok ---> Producer
- 开启通道
    - Producer --- Channel.Open ---> Broker
    - Broker --- Channel.Open.Ok ---> Producer
- 发送消息
    - Producer --- Basic.Publish ---> Broker
- 释放资源
    - Producer --- Channel.Close ---> Broker
    - Broker --- Channel.Close.Ok ---> Producer

**AMQP 生产者流转过程**

- 建立连接
    - Consumer --- protocol header 0-9-1 ---> Broker
    - Broker --- Connection.Start ---> Consumer 
    - Consumer --- Connection.Start.Ok ---> Broker
    - Broker --- Connection.Tune ---> Consumer 
    - Consumer --- Connection.Tune.Ok ---> Broker
    - Consumer --- Connection.Open ---> Broker
    - Broker ---> Connection.Open.Ok ---> Consumer 
- 开启通道
    - Consumer --- Channel.Open ---> Broker
    - Broker --- Channel.Open.Ok ---> Consumer 
- 准备接收消息
    - Consumer --- Basic.Consume---> Broker
    - Broker --- Basic.Consume.Ok ---> Consumer
- broker 发送消息
    - Broker --- Basic.Deliver ---> Consumer
- 发送确认
    - Consumer --- Basic.Ack ---> Broker
- 释放资源
    - Consumer --- Channel.Close ---> Broker
    - Broker --- Channel.Close.Ok ---> Consumer 
    - Consumer --- Connection.Close ---> Broker
    - Broker --- Connection.Close.Ok ---> Consumer

## 6. RabbitMQ 核心组成

Producer / Consumer

- Connection 应用程序与 Broker 的网络连接 TCP/IP
    - Channel 信息读写通道，每个 Channel 代表一个会话服务

Broker (Server) 接收客户端连接，实现 AMQP 实体服务

- Virtual Host 用于逻辑隔离最上层的消息路由，同一个虚拟主机不能用相同名字的 Exchange
    - Exchange 接收消息，根据 Routing Key 发送消息到绑定的队列
    - Routing key 路由规则，虚拟机由它确定如何路由一个消息
    - Bindings Exchange 与 Queue 之间的虚拟连接， Binding 中可以维护多个 Routing Key
    - Queue 消息队列，保存消息并转发给消费者
        - Message 服务与应用程序直接传送的数据，由 Properties 与 Body 组成， Properties 可以对消息进行修饰（消息的优先级，延迟等），Body 是消息内容

## 7. RabbitMQ 运行流程

- Producer
    - 业务数据 --- 序列化 ---> 序列化后的数据 --- 指定 Exchange 和 Routing Key 等 ---> 消息
- Broker
    - --- 发送至 Broker 中 ---> Broker --- Consumer 订阅并接收消息 --->
- Consumer
    - 消息 --- 反序列化 ---> 反序列化后的数据 ---> 接收的业务方数据 ---> 业务处理

## 8. RabbitMQ 支持的消息模式

- simple 简单模式
- work 工作模式
- publish / subscribe (fanout) 发布订阅模式
- routing key (direct) 路由模式
- topic 主题模式
- 参数模式

## 9. RabbitMQ 的使用场景

解耦，削峰，异步

## 10. RabbitMQ 的应用

## 11. RabbitMQ 内存磁盘监控

### 11.1 内存警告

当内存使用超过配置的阈值或者磁盘空间剩余空间时，RabbitMQ 会暂时阻塞客户端的连接（blocking，bloked），并且阻止接收从客户端发送的消息，以此避免服务器的崩溃，客户端与服务端的心跳检测机制也会失效

默认内存是最大内存的 0.4

### 11.2 内存调整

- 命令

    ```shell
    rabbitmqctl set_vm_memory_high_watermark <fraction> # 建议 0.4 - 0.6
    rabbitmqctl set_vm_memory_high_watermark absolute xxxMB
    ```

- 配置文件（/etc/rabbitmq/rabbitmq.conf）

    ```conf
    vm_memory_high_watermark.relative = 
    vm_memory_high_watermark.absolute = 
    ```

### 11.3 内存换页

在某个 Broker 节点及内存阻塞生产者之前，它会尝试将消息队列中的消息换页到磁盘以释放内存空间，持久化和非持久化的消息都会写到磁盘中，其中持久化的消息本身在磁盘中有个副本，所以在转移的过程中，持久化的消息会先从内存中清除掉（默认为 50%*允许内存）

```conf
vm_memory_high_watermark_paging_ratio = 0.7
```

### 11.4 磁盘预警

当磁盘剩余空间低于确定的阈值时，RabbitMQ 同样会阻塞生产者，避免因非持久化的消息持续换页而耗尽磁盘空间导致服务崩溃（默认为 50 MB）

- 命令方式

    ```shell
    rabbitmqctl set_disk_free_limit <disk_limit>
    rabbitmqctl set_disk_free_limit memory_limit <fraction> # 相对于内存的百分比
    ```

- 配置文件

    ```conf
    disk_free_limit.relative = 
    disk_free_limit.absolute = 
    ```

    