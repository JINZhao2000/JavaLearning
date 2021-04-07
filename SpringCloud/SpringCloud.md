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

