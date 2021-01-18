# Hibernate

## 1. Hibernate 初识

### 1.1 什么是 Hibernate

Hibernate 是一个持久化 ORM 框架，解决持久化操作，使得程序员可以专注于业务开发，提高开发效率，并具有可靠的移植性，降低了系统的耦合度

__持久化（persistence）__

- 持久，即把数据保存到可以永久保存的设备中（比如磁盘）
- 持久化的主要应用是将内存中的数据储存在关系型数据库中，当然也可以储存在磁盘，XML 数据文件中等，Hibernate 中叫做加载
  - 狭义概念：数据存储在物理介质中不会丢失
  - 广义概念：对数据的 CRUD 操作都叫做持久化

__ORM__ 

Object Relation Mapping：对象（JavaBean）关系（关系型数据库）映射，它的作用是在关系型数据库和对象之间做一个映射，这样我们在具体操作数据库的时候，就不需要再去和复杂的 sql 语句打交道，只要像平时操作对象一样操作它就可以了

### 1.2 为什么要做持久化和 ORM 设计

- 代码复杂

  ​		用 JDBC 的 API 访问数据库，代码量较大，特别是访问字段较多表的时候，代码显得繁琐，累赘，容易出错，程序员需要消耗大量的时间精力去编写具体数据库访问的 sql 语句，还要十分小心其中大量重复的源代码是否有疏漏，并不能集中精力于业务逻辑的开发上面

  ​		ORM 则建立了 Java 对象与数据库对象之间的映射关系，程序员不需要编写复杂的 sql 语句，直接操作 Java 对象即可，大大降低了代码量，也使程序员更加专注于业务逻辑的实现

- 数据库连接的问题

  ​		采用 JDBC 编程，必须保证各种关系之间不能出错

  ​		ORM 建立 Java 对象与数据库对象关系映射同时，也自动根据数据库对象之间的关系创建 Java 对象的关系，并且提供了维持这些关系完整，有效的机制

- 耦合度

  ​		JDBC 属于数据访问层，但使用 JDBC 时，程序员必须知道后台用的是什么数据库，有哪些表，各个表有哪些字段，各个字段的类型是什么，表与表之间什么关系，创建了什么索引等等与后台数据库相关的信息，相当于软件程序员兼职数据库 DBA

  ​		使用 ORM 技术，可以将数据库层完全屏蔽，呈现给程序员的只有 Java 对象，程序员只要根据业务逻辑的需要，调用 Java 对象的 Getter 和 Setter 方法，即可实现对后台数据库的操作，程序员不必知道后台采用什么样的数据库，有哪些表，有什么字段，表与表之间有什么关系

  ​		于是，系统设计人员把 ORM 搭建好以后，把 Java 对象交给程序员去实现业务逻辑，使持久层与数据库层清晰分界

## 2. Hibernate 入门

### 2.1 Hibernate 基本配置

hibernate.cfg.xml 配置文件

```xml-dtd
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE hibernate-configuration PUBLIC
        "-//Hibernate/Hibernate Configuration DTD 3.0//EN"
        "http://www.hibernate.org/dtd/hibernate-configuration-3.0.dtd">
<hibernate-configuration xmlns="http://www.hibernate.org/xsd/hibernate-configuration">
    <session-factory>
        <property name="connection.driver_class">className</property>
        <property name="connection.url">url</property>
        <property name="connection.username">username</property>
        <property name="connection.password">password</property>
		<property name="dialect">org.hibernate.dialect.MySQL8Dialect</property>
    </session-factory>
</hibernate-configuration>
```

maven 配置读取子配置文件

```xml-dtd
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">

<!-- .... -->

    <build>
        <finalName>hibernate-01</finalName>

        <resources>
            <resource>
                <directory>src/main/resources</directory>
            </resource>
            <resource>
                <directory>src/main/java</directory>
                <includes>
                    <include>**/*.xml</include>
                </includes>
                <filtering>false</filtering>
            </resource>
        </resources>
    </build>
</project>
```

创建 pojo User

```java
public class User {
    private Integer uid;
    private String uname;
    private Integer balance;

    public User() {}

    public User(Integer uid, String uname, Integer balance) {
        this.uid = uid;
        this.uname = uname;
        this.balance = balance;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "User{"+this.uname+","+this.balance+"}";
    }
}
```

在同级下创建 User.hbm.xml

```xml-dtd
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE hibernate-mapping PUBLIC
        "-//hibernate/Hibernate Mapping DTD 3.0//EN"
        "http://www.hibernate.org/xsd/hibernate-mapping-3.0.dtd">
<hibernate-mapping>
    <class name="com.ayy.pojo.User" table="user_table">
        <id name="uid">
			<generator class="native"></generator> <!-- 主键自增策略 -->
        </id>
        <property name="uname"/>
        <property name="balance"/>
    </class>
</hibernate-mapping>
```

如果表的字段名和对象不符，在 property 中加入 column 属性

然后再原来 hibernate.cfg.xml 中配置 mapping

```xml
<hibernate-configuration xmlns="http://www.hibernate.org/xsd/hibernate-configuration">
    <session-factory>
        <!-- ... -->
        <mapping resource="com/ayy/pojo/User.hbm.xml"/>
    </session-factory>
</hibernate-configuration>
```

测试用例

```java
@Test
public void HibernateConfigTest(){
	StandardServiceRegistry registry = null;
    SessionFactory sessionFactory = null;
	Session session = null;
    Transaction tx = null;
	try {
        registry = new StandardServiceRegistryBuilder()
				.configure()
			    .build();
		sessionFactory = new MetadataSources(registry)
	            .buildMetadata()
		        .buildSessionFactory();
	    session = sessionFactory.openSession();
        tx = session.beginTransaction();
        User user = new User();
		user.setUname("Hibernate1");
	    user.setBalance(1000);
        session.save(user);
		tx.commit();
	}catch (Exception e){
        tx.rollback();
    }finally {
        if (session!=null && session.isOpen()) {
            session.close();
        }
    }
}
```

Hibernate 3 写法

```java
Configuration cfg = new Configuration().configure();
SessionFactory sf = cfg.buildSessionFactory();
```

Hibernate 4 写法

```java
Configuration cfg = new Configuration().configure();
StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
    .applySettings(cfg.getProperties()).build();
SessionFactory sessionFactory = cfg.buildSessionFactory(registry);
```

数据库更新方式

```xml
<property name="hibernate.hbm2ddl.auto">update</property>
```

工作中一般用 update

- validate 没有表就会报错

- create-drop 为每次执行前删除表并重新创建
- create 每次重新建表
- update 如果有表就插入，没有就创建表

## 3. 5 大关键接口

### 3.1 Configuration (Hibernate 3/4)

负责 Hibernate 的配置工作，创建 SessionFactory 对象，在 Hibernate 启动过程中，Configuration 类的实例首先定位在映射文件位置，读取配置，然后创建 SessionFactory 对象

```java
@Test
public void testConfig(){
//    Configuration cfg = new Configuration();
    Configuration cfg = new Configuration().configure();
//    Configuration cfg = new Configuration().configure("others.cfg.xml");
//    cfg.addProperties();
//    cfg.addResource();
}
```

### 3.2 SessionFactory

SessionFactory 接口负责初始化 Hibernate，它充当数据存储源的代理，使用工厂模式创建 Session 对象，需要注意的是 SessionFactory 并不是轻量级的，一般情况下，一个项目通常只需要一个 SessionFactory 就够，当需要操作多个数据库时，可以为每个数据库指定一个 SessionFactory

```java
@Test
public void testSessionFactory(){
    StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
			.configure()
	        .build();
    SessionFactory sessionFactory = new MetadataSources(registry)
			.buildMetadata()
	        .buildSessionFactory();
}
```

### 3.3 Session

Session 接口

