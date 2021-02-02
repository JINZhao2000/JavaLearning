# ElasticSearch 7.X

## 1. 了解ES

6.X 与 7.X 的区别

- 原生 API
- RestFul

ElasticSearch，Solr：搜索（百度，github）

- 高扩展的分布式全文搜索引擎
- 实时的存储，检索数据
- Lucene - 一套信息检索包（索引结构，读写索引的工具，排序，搜索规则... 工具类）
- ES 是基于 Lucene 工具包进行封装和增强

使用

- 百科，全文检索，高亮
- 新闻网站，行为日志
- SOF
- Github
- 电商网站，检索商品
- ELK 日志数据分析
- 商品价格监控
- BI （Business Intelligence）系统

## 2. ES 与 Solr 差别

ES

- 全文搜索，结构化搜索，分析
- 实时搜索，搜索纠错
- 用简单的 RESTful API 来封装 Lucene 的复杂性

Solr

- 可配置，可扩展，对搜索引擎和性能进行了优化
- 可以独立运行在 Netty，Tomcat 等 Servlet 容器中
- XML，JSON，补提供 UI 功能，但是可以通过管理界面配置
- 提供类似于 Web-serivce API 接口

Lucene

- Java 信息检索库

ES 与 Solr 比较

- 对单纯已有的数据搜索，Solr 更快
- 建立索引时，Solr 会产生 IO 阻塞，查询性能较差
- 随着数据量增加，Solr 效率会变低，ES 则没有变化
- ES 开箱即用，Solr 需要安装
- Solr 用 Zookeeper 进行分布式管理，而 ES 自带有分布式协调管理功能
- Solr 支持 JSON，XML，CSV，ES 只支持 JSON
- Solr 提供更多功能，ES 更注重核心功能，高级功能由第三方插件提供，比如图形界面用 Kibana
- Solr 查询快，但是更新索引慢（插入慢），ES 查询交 Solr 慢，建立索引快，适用于实时查询
- Solr 比较成熟，有一个更大，更成熟的用户，开发，贡献者社区，而 ES 更新快，学习成本高

## 3. ES 安装（jdk8+）

- bin 启动文件 默认端口 9200
- config 配置文件
  - log4j2.properties - 日志配置文件
  - jvm.options - java 虚拟机相关配置
  - elasticsearch.yml - es 的配置文件，默认端口 9200
- lib 相关 jar 包
- modules 功能模块
- plugins 插件
  - [ES Head](https://github.com/mobz/elasticsearch-head/)

跨域配置 elasticsearch.yml

```yaml
http.cors.enabled: true
http.cors.allow-origin: "*"
```

- ELK

  收集清洗数据  -> 搜索，储存 -> Kibana

  Kibana 版本要和 ES 一致

  ELK 都是拆箱即用

- Kibana 启动测试

  默认端口 5601

  开发工具 post curl head 谷歌浏览器插件

  ./config/kibana.yml 修改 语言

  ```yaml
  i18n.locale: "zh-CN"
  ```

## 4. ES 核心概念

集群，节点，索引，类型，文档，分片，映射

- ES 是面向文档

  ES 和关系型数据库的对比

  | RDB          | ES         |
  | ------------ | ---------- |
  | 数据库 DB    | 索引 Index |
  | 表 Table     | Types      |
  | 行 Row       | Docs       |
  | 字段 Columns | Fields     |

  ES 集群中可以包含多个索引，每个索引可以包含多个类型，每个类型下又包含多个文档，每个文档又包含多个字段

- 物理设计

  ES 后台把每个索引划分成多个分片，每份分片可以在集群中的不同服务器间迁移

  一个就是一个集群

  ```json
  {
  	"name": "LAPTOP-KUJHF1L7",
  	"cluster_name": "elasticsearch",
  	"cluster_uuid": "J1wFzhAWSA63fcd5mSgP-A",
  	"version": {
  		"number": "7.10.2",
  		"build_flavor": "default",
  		"build_type": "zip",
  		"build_hash": "747e1cc71def077253878a59143c1f785afa92b9",
  		"build_date": "2021-01-13T00:42:12.435326Z",
  		"build_snapshot": false,
  		"lucene_version": "8.7.0",
  		"minimum_wire_compatibility_version": "6.8.0",
  		"minimum_index_compatibility_version": "6.0.0-beta1"
  	},
  	"tagline": "You Know, for Search"
  }
  ```

- 逻辑设计

  一个索引类型中，包含多个文档，比如说文档1，文档2，当我们索引一篇文档时，可以通过这样的一个顺序找到它：索引 -> 类型 -> 文档 ID，通过这个组合我们就能索引到某个具体的文档，ID 不必是整数，实际上它是个字符串

  - 文档

    ES 最小单位是文档，有几个重要属性：

    - 自我包含，一篇文档同时包含字段和对应的值，也就是同时包含 key:value 
    - 可以是层次型的，一个文档中包含文档
    - 灵活的结构，文档不依赖预先定义的模式，在 ES 可以忽略字段或者动态添加一个新字段

    尽管可以动态添加和删除字段，但是每个字段类型非常重要，可以是字符串也可以是整型，而且 ES 会保存字段和类型之间的映射和其他的设置，这种映射具体到每个映射的每种类型，类型被称为映射类型

  - 类型

    类型是文档的逻辑容器，文档是无模式的，他们不需要拥有映射中所定义的所有字段

    新增字段时， ES 会自动将新增字段加入映射，但是会推测它的类型，最安全的方式是提前定义做好映射

  - 索引

    索引是映射类型的容器，ES 中，索引是一个文档的集合，存储了映射类型的字段和其它设置，然后储存到分片上

- 节点和分片 工作方式

  一个集群至少有一个节点，一个节点就是一个 ES 进程，节点可以有多个索引，默认为 5 个，每个主分片会有一个副本

  主分片和副本不会在同一个节点内，实际上一个分片是一个 Lucene 索引，一个包含倒排索引的文件目录，倒排索引的结构使得 ES 在不扫描全部文档的情况下，就能告诉你哪些文档包含特定关键字

  - 倒排索引

    倒排索引适用于快速的全文搜索，一个索引有文档中所有不重复的列表构成，对于每个词都有包含它的文档列表

    Ex : 文档内

    ```shell
    Have a good day
    I have a pen
    ```

    为了创建索引，首先将每个文档拆分成独立的词（tokens），然后创建一个包含所有不重复的词条的排序列表，然后列出每个词在哪个文档

    | term | doc1 | doc2 |
    | ---- | ---- | ---- |
    | Have | 1    |      |
    | I    |      | 1    |
    | a    | 1    | 1    |
    | have |      | 1    |
    | good | 1    |      |
    | pen  |      | 1    |
    | day  | 1    |      |

    如果两个文档都匹配，但是一个文档比另一个文档权重（score）更高，如果没有别的条件，现在这两个包含文档都将返回

    倒排索引数据查询会快很多，完全过滤掉无关的所有数据

## 5. IK 分词器

分词：即把一段中文或者别的词划分成一个个的关键字，在搜索的时候把自己的信息进行分词，把数据库中或者索引库中的数据进行分词，然后进行匹配操作，默认的中文分词是将每个中文看作一个词，然后中文分词器 IK 可以解决这个问题

IK 提供了两个分词算法：ik_smart 和 ik_max_word，其中 ik_smart 为最少切分，ik_max_word 为最细粒度切分

[IK 分词器](https://github.com/medcl/elasticsearch-analysis-ik) 

命令行检查

```shell
elasticsearch-plugin list
```

使用

ik_max_word

```json
GET _analyze
{
  "analyzer": "ik_max_word",
  "text": "生日快乐"
}
```

结果

```json
{
  "tokens" : [
    {
      "token" : "生日快乐",
      "start_offset" : 0,
      "end_offset" : 4,
      "type" : "CN_WORD",
      "position" : 0
    },
    {
      "token" : "生日",
      "start_offset" : 0,
      "end_offset" : 2,
      "type" : "CN_WORD",
      "position" : 1
    },
    {
      "token" : "快乐",
      "start_offset" : 2,
      "end_offset" : 4,
      "type" : "CN_WORD",
      "position" : 2
    }
  ]
}
```

ik_smart

```json
GET _analyze
{
  "analyzer": "ik_smart",
  "text": "生日快乐"
}
```

结果

```json
{
  "tokens" : [
    {
      "token" : "生日快乐",
      "start_offset" : 0,
      "end_offset" : 4,
      "type" : "CN_WORD",
      "position" : 0
    }
  ]
}
```

自己需要的词需要自己加到分词器字典中

```shell
~\elasticsearch-7.10.2\plugins\elasticsearch-analysis-ik\config\IKAnalyser.cfg.xml
```

编写自己的 xxx.dic 文件并配置到配置文件中（UTF-8）

```xml
<entry key="ext_dict">xxx.dic</entry>
```

