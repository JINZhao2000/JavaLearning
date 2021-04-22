# SpringCloud

## 先前知识

- JavaSE
- MySQL
- 前端
- Servlet
- Http
- Mybatis
- Spring
- SpringMVC
- SpringBoot
- Dubbo + Zookeeper
- Maven，Git
- Ajax，Json

## 学习内容

解决 4 个核心问题

- 客户端的访问

    负载均衡，轮询

- 服务之间通信

    HTTP，RPC

- 服务治理

    服务发现与注册中心

- 服务容错机制

    熔断与降级

## 解决方案

SpringCloud NetFlix - 一站式解决方案

- API 网关 Zuul 组件
- 服务通信 Feign（基于 HttpClient）
- 服务注册与发现 Eureka
- 熔断机制 Hystrix

Apache Dubbo Zookeeper - 半自动，需要整合第三方

- API 网关 无
- 服务通信 Dubbo（不完善）
- 服务注册与发现 Zookeeper
- 熔断机制 无

SpringCloud Alibaba - 最新一站式解决方案

- API 网关
- 服务通信
- 服务注册与发现
- 熔断机制

## 新概念

服务网格 Service Mesh

- Istio

## 技术栈

| 内容             | 技术                                                         |
| ---------------- | ------------------------------------------------------------ |
| 服务开发         | Springboot，Spring，SpringMVC                                |
| 服务配置与管理   | Archaius（Netflix），Diamond（Alibaba）                      |
| 服务注册与发现   | Eureka，Consul，Zookeeper，Nacos                             |
| 服务调用         | Rest，RPC，gRPC                                              |
| 服务熔断器       | Hystrix，Envoy，Resilience4J，Sentienl                       |
| 负载均衡         | Ribbon，Nginx，LoadBalancer                                  |
| 服务接口调用     | Feign，openFeign                                             |
| 消息队列         | Kafka，RabbitMQ，ActiveMQ                                    |
| 服务配置中心管理 | SpringCloudConfig，Chef                                      |
| 服务路由         | Zuul，Gateway                                                |
| 服务监控         | Zabbix，Nagios，Matrics，Specatator                          |
| 全链路追踪       | Zipkin，Brave，Dapper                                        |
| 服务部署         | Docker，OpenStack，Kubernetes                                |
| 数据流操作开发包 | SpringCloud Stream（封装与 Redis，Rabbit，Kafka 等发下哦那个接受消息） |
| 事件消息总线     | SpringCloud Bus                                              |

## Eureka

CAP 原则（Eureka 是 AP 原则）

- C Consistency 强一致性
- A Availability 可用性
- P Partition tolerance 分区容错性

基于 REST 服务

用于定位服务，实现云端中间层服务发现和故障转移

__Eureka 自我保护机制__ 

在某一时刻微服务突然不可用时，eureka 不会立即清理，依旧会对该微服务的信息进行保存

- 默认情况下。如果 EurekaServer 在一定时间内没有接收到某个微服务实例的心跳，EurekaServer 将会注销该实例（默认 90 s），但是当网络分区故障发生时，微服务与 Eureka 之间无法正常通信，此时不应该注销该服务，因为微服务是健康的。Eureka 通过自我保护机制来解决这个问题：当 EurekaServer 节点在短时间丢失过多客户端时，那么这个节点就会进入自我保护模式，一旦进入该模式，EurekaServer 就会保护服务注册表中的数据（不会注销微服务），当网络故障恢复后，该 EurekaServer 节点会自动退出保护模式
- 在自我保护模式中，EurekaServer 会保护服务注册表中的信息，不在注销任何服务实例，当它收到的心跳数重新恢复到阈值以上时，该 EurekaServer 节点就会自动退出保护模式，它的设计哲学就是宁可保留错误的服务注册信息，也不盲目注销任何可能健康的服务实例
- `eureka.server.enable-self-preservation` 自我保护机制的 property

__与 Zookeeper 区别__ 

- Zookeeper 保证的是 CP 而 Eureka 保证的是 AP

- Zookeeper 当 master 节点失去联系时，然后子节点会选举一个节点作为 master，选举时间比较长，期间不能注册服务

## Ribbon

基于 Netflix Ribbon 实现的客户端负载均衡（高可用）的工具

在配置文件中列出 LoadBalancer 后面所有的机器，通过轮询或者哈希去选择性连接

常见的负载均衡软件：Nginx，Lvs

负载均衡分类

- 集中式 LB
    - 在服务的消费方和提供方之间使用独立的 LB 设施，如 Nginx
- 进程式 LB
    - 将 LB 逻辑集成到消费方，消费方从服务注册中心获知哪些地址可用，然后从地址中选出合适的服务器
    - Ribbon 是一个类库，集成于消费方进程，消费方通过它来获取到服务提供方的地址

## Feign

Feign 是声明式的 web service 客户端，提供负载均衡的 http 客户端（面向接口）

## Hystrix

用于处理分布式系统的延迟和容错的开源库

- 服务熔断
- 服务降级
- 服务限流
- 实时监控

## Zuul

路由网关 API Gateway

包含了对请求的路由和过滤两个功能

- 实现外部访问统一入口基础
- 对请求过程干预，实现请求校验，服务聚合能功能基础
- Zuul 最后会被注册进 eureka