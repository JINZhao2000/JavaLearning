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

## 6. REST 风格

Rest (Representational state transfer) 是一种软件架构风格，提供了一组设计原则和约束条件，它主要用于客户端和服务器交互类的软件，基于这个风格设计的软件可以更简洁，更有层次，更易于实现缓存等机制

基本 Rest 命令

| method | url 地址                                         | 描述                    |
| ------ | ------------------------------------------------ | ----------------------- |
| PUT    | localhost:9200/索引名称/类型名称/文档 id         | 创建文档（指定文档 id） |
| POST   | localhost:9200/索引名称/类型名称                 | 创建文档（随机文档 id） |
| POST   | localhost:9200/索引名称/类型名称/文档 id/_update | 修改文档                |
| DELETE | localhost:9200/索引名称/类型名称/文档 id         | 删除文档                |
| GET    | localhost:9200/索引名称/类型名称/文档 id         | 查询文档通过文档 id     |
| POST   | localhost:9200/索引名称/类型名称/_search         | 查询所有数据            |

### 6.1 索引操作

__创建索引__

PUT /文档名/\~类型名~/文档 id

{请求体}

```json
PUT /test1/type1/1
{
  "name": "name1",
  "age": 18
}
# 结果
{
  "_index" : "test1",
  "_type" : "type1",
  "_id" : "1",
  "_version" : 1,
  "result" : "created",
  "_shards" : {
    "total" : 2,
    "successful" : 1,
    "failed" : 0
  },
  "_seq_no" : 0,
  "_primary_term" : 1
}
```

不指定类型时会映射的类型

- 字符串类型

  text，keyword

- 数值类型

  long，integer，short，byte，double，float，half_float，scaled_float

- 日期类型

  date

- 布尔类型

  boolean

- 二进制类型

  binary

__指定字段类型__

```json
PUT /test2
{
  "mappings": {
    "properties": {
      "name":{
        "type": "text"
      },
      "age":{
        "type": "integer"
      },
      "birth":{
        "type": "date"
      }
    }
  }
}
```

__获取信息__

```json
GET test2
# 结果
{
  "test2" : {
    "aliases" : { },
    "mappings" : {
      "properties" : {
        "age" : {
          "type" : "integer"
        },
        "birth" : {
          "type" : "date"
        },
        "name" : {
          "type" : "text"
        }
      }
    },
    "settings" : {
      "index" : {
        "routing" : {
          "allocation" : {
            "include" : {
              "_tier_preference" : "data_content"
            }
          }
        },
        "number_of_shards" : "1",
        "provided_name" : "test2",
        "creation_date" : "1612385709947",
        "number_of_replicas" : "1",
        "uuid" : "MNe5X_BSTa-GaQgk27RALg",
        "version" : {
          "created" : "7100299"
        }
      }
    }
  }
}
```

__查看默认信息__

```json
PUT /test3/_doc/1
{
  "name": "name2",
  "age": 20,
  "birth": "2000-01-01"
}
# 结果
{
  "_index" : "test3",
  "_type" : "_doc",
  "_id" : "1",
  "_version" : 1,
  "result" : "created",
  "_shards" : {
    "total" : 2,
    "successful" : 1,
    "failed" : 0
  },
  "_seq_no" : 0,
  "_primary_term" : 1
}
###
GET test3
# 结果
{
  "test3" : {
    "aliases" : { },
    "mappings" : {
      "properties" : {
        "age" : {
          "type" : "long"
        },
        "birth" : {
          "type" : "date"
        },
        "name" : {
          "type" : "text",
          "fields" : {
            "keyword" : {
              "type" : "keyword",
              "ignore_above" : 256
            }
          }
        }
      }
    },
    "settings" : {
      "index" : {
        "routing" : {
          "allocation" : {
            "include" : {
              "_tier_preference" : "data_content"
            }
          }
        },
        "number_of_shards" : "1",
        "provided_name" : "test3",
        "creation_date" : "1612385935292",
        "number_of_replicas" : "1",
        "uuid" : "MwaIIm5fT1qyKIMHT8CUZw",
        "version" : {
          "created" : "7100299"
        }
      }
    }
  }
}
```

扩展：

通过命令 elasticsearch 索引情况

```json
# 命令
GET _cat/health
# 结果
1612386147 21:02:27 elasticsearch yellow 1 1 9 9 0 0 3 0 - 75.0%

# 命令
GET _cat/indices?v
# 结果
health status index                           uuid                   pri rep docs.count docs.deleted store.size pri.store.size
yellow open   test2                           MNe5X_BSTa-GaQgk27RALg   1   1          0            0       208b           208b
yellow open   test3                           MwaIIm5fT1qyKIMHT8CUZw   1   1          1            0      4.2kb          4.2kb
green  open   .apm-custom-link                R68L5WZZQ5iJr9qjYqBZCA   1   0          0            0       208b           208b
green  open   .kibana_task_manager_1          j9MEpfM2QrGYlNohyvla4w   1   0          5           64    211.4kb        211.4kb
green  open   .apm-agent-configuration        rbRe84GzQm-Trsp44kPxiw   1   0          0            0       208b           208b
yellow open   test1                           oDlAUv2XTvm6SkA1Emwj4A   1   1          1            0        4kb            4kb
green  open   .kibana_1                       YldWiq36RverYoXTectA9A   1   0         38            1      4.2mb          4.2mb
green  open   .kibana-event-log-7.10.2-000001 DJzm-j6AS5KBk3viGcY36g   1   0          5            0     27.2kb         27.2kb
```

__修改索引__

```json
# 用 PUT 覆盖
PUT test3/_doc/1
{
  "name": "name_update",
  "age": 14,
  "birth": "1994-01-01"
}

# 结果
# 修改后版本号增加
{
  "_index" : "test3",
  "_type" : "_doc",
  "_id" : "1",
  "_version" : 2,
  "result" : "updated",
  "_shards" : {
    "total" : 2,
    "successful" : 1,
    "failed" : 0
  },
  "_seq_no" : 1,
  "_primary_term" : 1
}

# 用 POST - 推荐
POST test3/_doc/1/_update
{
  "doc":{
    "name": "name_post_update"
  }
}

# 结果
{
  "_index" : "test3",
  "_type" : "_doc",
  "_id" : "1",
  "_version" : 3,
  "result" : "updated",
  "_shards" : {
    "total" : 2,
    "successful" : 1,
    "failed" : 0
  },
  "_seq_no" : 2,
  "_primary_term" : 1
}
```

__删除__

根据请求来判断是删除索引还是文档记录

```json
DELETE test3

# 结果
{
  "acknowledged" : true
}
```

RESTFUL 是 ES 推荐的文档风格

### 6.2 文档操作

#### 基本操作

__添加数据__

```json
PUT /ayy/user/1
{
  "name": "name1",
  "age": 18,
  "desc": "desc1",
  "tags": ["tag1","tag2"]
}
# ...
```

__查询数据__

```json
GET /ayy/user/1
```

__更新数据__

```json
PUT /ayy/user/1
{
  "name": "name1++",
  "age": 18,
  "desc": "desc1",
  "tags": ["tag1","tag2"]
}
# 不能忘了 _update 不传递值会覆盖
POST /ayy/user/1/_update
{
  "doc":{
    "name":"name1--"
  }
}
```

__搜索__

根据默认规则产生基本查询

```json
# 通过 id 查询
GET /ayy/user/1
# 条件查询
GET /ayy/user/_search?q=name:name2

```

#### 复杂操作（排序，分页，高亮，模糊查询，精准查询）

