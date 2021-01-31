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

- bin 启动文件
- config 配置文件
  - log4j2 日志配置文件
  - jvm.options java 虚拟机相关配置
  - elasticsearch.yml es 的配置文件，默认端口 9200
- lib 相关 jar 包
- modules 功能模块
- plugins 插件
  - [ES Head](https://github.com/mobz/elasticsearch-head/)

跨域配置 elasticsearch.yml

```yaml
http.cors.enabled: true
http.cors.allow-origin: "*"
```



