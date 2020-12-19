# SSH

## 1. jar 整合

- maven properties

  ```xml
  <properties>
  	<maven.compiler.source>1.11</maven.compiler.source>
      <maven.compiler.target>1.11</maven.compiler.target>
  	<junit>4.12</junit>
      <struts2>2.5.25</struts2>
      <asm>3.3</asm>
      <spring>5.2.6.RELEASE</spring>
      <mysql>8.0.22</mysql>
  	<c3p0>0.9.5.5</c3p0>
      <hibernate>5.4.23.Final</hibernate>
  </properties>
  ```

- Hibernate

  slf4j -> 日志框架 -> 过滤jar -> 具体日志 log4j slf4j-log4j\*.\*.jar

  jta -> Java transaction api 规范

  dom4j -> 解析 xml

  commons-collections -> Java 集合增强包

  antlr -> 解析HQL 语句

  jpa 规范 -> 注解开发

  ehcache -> 二级缓存

  ```xml
  <dependency>
  	<groupId>org.hibernate</groupId>
  	<artifactId>hibernate-entitymanager</artifactId>
  	<version>${hibernate}</version>
  </dependency>
  <dependency>
      <groupId>org.hibernate</groupId>
      <artifactId>hibernate-ehcache</artifactId>
      <version>${hibernate}</version>
  </dependency>
  ```

- Spring

  spring 4+1 + aop 2+2

  ```xml
  <dependency>
  	<groupId>org.springframework</groupId>
  	<artifactId>spring-context</artifactId>
  	<version>5.2.6.RELEASE</version>
  </dependency>
  <!-- context = core + beans + context + expression + aop-->
  ```

  db jdbc+tx

  ```xml
  <dependency>
  	<groupId>org.springframework</groupId>
  	<artifactId>spring-jdbc</artifactId>
  	<version>${spring}</version>
  </dependency>
  <dependency>
  	<groupId>org.springframework</groupId>
  	<artifactId>spring-tx</artifactId>
  	<version>${spring}</version>
  </dependency>
  ```

  web + test

  ```xml
  <dependency>
  	<groupId>org.springframework</groupId>
  	<artifactId>spring-web</artifactId>
  	<version>${spring}</version>
  </dependency>
  <dependency>
  	<groupId>org.springframework</groupId>
  	<artifactId>spring-test</artifactId>
  	<version>${spring}</version>
  	<scope>test</scope>
  </dependency>
  ```

  mysql c3p0

  ```xml
  <dependency>
  	<groupId>mysql</groupId>
  	<artifactId>mysql-connector-java</artifactId>
  	<version>${mysql}</version>
  </dependency>
  <dependency>
  	<groupId>com.mchange</groupId>
  	<artifactId>c3p0</artifactId>
  	<version>${c3p0}</version>
  </dependency>
  ```

  hibernate

  ```xml
  <dependency>
  	<groupId>org.springframework</groupId>
  	<artifactId>spring-orm</artifactId>
  	<version>${spring}</version>
  </dependency>
  ```

- Struts2 - Webwork

  asm -> 字节码增强框架

  commons-* -> Apache 工具类

  - logging 简洁日志
  - lang3 java.lang 增强包
  - io java.io 增强包
  - fileupload 文件上传

  freemarker -> 模板技术 扩展名：*.ftl

  javassist -> 字节码增强器

  log4j -> Apache 日志文件 /src/log4j.properties

  值栈 -> ognl 表达式

  Webwork -> xwork-core （当前版本已整合）

  ```xml
  <dependency>
  	<groupId>org.apache.struts</groupId>
  	<artifactId>struts2-core</artifactId>
  	<version>${struts2}</version>
  </dependency>
  <dependency>
  	<groupId>asm</groupId>
  	<artifactId>asm-commons</artifactId>
  	<version>${asm}</version>
  </dependency>
  <dependency>
  	<groupId>org.apache.struts</groupId>
  	<artifactId>struts2-spring-plugin</artifactId>
  	<version>${struts2}</version>
  	<exclusions>
  		<exclusion>
  			<groupId>org.springframework</groupId>
  			<artifactId>spring-core</artifactId>
  		</exclusion>
  		<exclusion>
  			<groupId>org.springframework</groupId>
  			<artifactId>spring-beans</artifactId>
  		</exclusion>
  	</exclusions>
  </dependency>
  ```
  
  - 模板技术：页面静态化
    - freemarker 扩展名：*.ftl
    - velocity 扩展名：*.vm
  
- 整合包

  - spring-hibernate : spring-orm
  - struts2-spring : struts-spring-plugin

## 2. Spring 整合 Hibernate - hibernate.cfg.xml

