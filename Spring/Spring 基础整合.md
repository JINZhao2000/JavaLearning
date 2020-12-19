# Spring 基础整合

## 1. JDBC Template

- Spring 所提供的用于操作 JDBC 工具类，类似 DBUtils
- 依赖连接池 DataSource （数据源）

### 1.1 环境搭建

#### 1.1.1 创建表

```mysql
CREATE DATABASE springJDBCTemplate;
USE springJDBCTemplate;
CREATE TABLE s_user(
    id int PRIMARY KEY AUTO_INCREMENT,
    s_username varchar(50),
    s_password varchar(32)
);
INSERT INTO s_user(s_username,s_password) VALUES('a','1234');
INSERT INTO s_user(s_username,s_password) VALUES('b','5678');
```

#### 1.1.2 导入 jar 包

```groovy
compile group: 'org.springframework', name: 'spring-jdbc', version: '5.2.6.RELEASE'
compile group: 'org.springframework', name: 'spring-tx', version: '5.2.6.RELEASE'
compile group: 'com.mchange', name: 'c3p0', version: '0.9.5.2'
compile group: 'org.apache.commons', name: 'commons-dbcp2', version: '2.7.0'
compile group: 'org.apache.commons', name: 'commons-pool2', version: '2.7.0'
compile group: 'mysql', name: 'mysql-connector-java', version: '8.0.22'
```

spring-jdbc：jdbc 开发

spring-tx：事务

mysql-connector-java：驱动

c3p0：连接池

commons-dbcp2：dbcp 连接池

commons-pool2：dbcp 依赖

#### 1.1.3 JavaBean 配置

### 1.2 使用 API

APITest.java

```java
public class APITest {
    @Test
    public void test01(){
        // create DataSource dbcp
        BasicDataSource dataSource = new BasicDataSource();
        // 4 elements of DataSource
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://192.168.137.254:3306/springJDBCTemplate");
        dataSource.setUsername("jinzhao");
        dataSource.setPassword("18273645");
        // create Template
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource);
        // use api
        jdbcTemplate.update("INSERT INTO s_user(s_username,s_password) VALUES(?,?);","c","1234");
    }
}
```

### 1.3 配置DBCP

UserDao.java

```java
public class UserDao {
    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate (JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void update(User user){
        String sql = "update s_user set s_username = ?, p_password = ? where id = ?";
        Object[] args = {user.getUsername(),user.getPassword(),user.getId()};
        jdbcTemplate.update(sql,args);
    }
}
```

User.java

```java
public class User {
    private  Integer Id;
    private  String username;
    private  String password;

    public User () {}

    public User (Integer id, String username, String password) {
        Id = id;
        this.username = username;
        this.password = password;
    }

    public Integer getId () {
        return Id;
    }

    public void setId (Integer id) {
        Id = id;
    }

    public String getUsername () {
        return username;
    }

    public void setUsername (String username) {
        this.username = username;
    }

    public String getPassword () {
        return password;
    }

    public void setPassword (String password) {
        this.password = password;
    }

    @Override
    public String toString () {
        return "User{" +
                "Id=" + Id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
```

DBCPTest.java

```java
public class DBCPTest {
    @Test
    public void test01(){
        User user = new User();
        user.setId(1);
        user.setUsername("a");
        user.setPassword("1234");

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("JDBCContext1.xml");
        UserDao userDao = applicationContext.getBean("userDao", UserDao.class);
    }
}
```

JDBCContext1.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
                           http://www.springframework.org/schema/beans/spring-beans.xsd">
    <bean id="dataSource" class="org.apache.commons.dbcp2.BasicDataSource">
        <property name="driverClassName" value="com.mysql.cj.jdbc.Driver"/>
        <property name="url" value="jdbc:mysql://192.168.137.254:3306/springJDBCTemplate"/>
        <property name="username" value="jinzhao"/>
        <property name="password" value="18273645"/>
    </bean>
    <bean id="jdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate">
        <property name="dataSource" ref="dataSource"/>
    </bean>
    <bean id="userDao" class="com.ayy.jdbcTemplate.dbcp.UserDao">
        <property name="jdbcTemplate" ref="jdbcTemplate"/>
    </bean>
</beans>
```

### 1.4 配置 C3P0

C3P0Test.java

```java
public class C3P0Test {
    @Test
    public void test01(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("JDBCContext2.xml");
        UserDao userDao = applicationContext.getBean("userDao", UserDao.class);
        List<User> allUser = userDao.findAll();
        allUser.forEach(System.out::println);
    }
}
```

User.java 对于 sql 列名带有 '_' 的，应用大写字母取代

例：sql：user_name -> java field：userName

```java
public class User {
    private  Integer Id;
    private  String sUsername;
    private  String sPassword;

    public User () {}

    public User (Integer Id, String sPassword, String sUsername) {
        this.Id = Id;
        this.sPassword = sPassword;
        this.sUsername = sUsername;
    }

    public Integer getId () {
        return Id;
    }

    public void setId (Integer Id) {
        this.Id = Id;
    }

    public String getsPassword () {
        return sPassword;
    }

    public void setsPassword (String sPassword) {
        this.sPassword = sPassword;
    }

    public String getsUsername () {
        return sUsername;
    }

    public void setsUsername (String sUsername) {
        this.sUsername = sUsername;
    }

    @Override
    public String toString () {
        return "User{" +
                "Id=" + Id +
                ", sPassword='" + sPassword + '\'' +
                ", sUsername='" + sUsername + '\'' +
                '}';
    }
}
```

UserDao.java

```java
public class UserDao {
    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate (JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void update(User user){
        String sql = "update s_user set s_username = ?, p_password = ? where id = ?";
        Object[] args = {user.getsUsername(),user.getsPassword(),user.getId()};
        jdbcTemplate.update(sql,args);
    }

    public List<User> findAll () {
        String sql = "select * from s_user";
        return jdbcTemplate.query(sql,BeanPropertyRowMapper.newInstance(User.class));
    }
}
```

JDBCContext2.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
                           http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
        <property name="driverClass" value="com.mysql.cj.jdbc.Driver"/>
        <property name="jdbcUrl" value="jdbc:mysql://192.168.137.254:3306/springJDBCTemplate"/>
        <property name="user" value="jinzhao"/>
        <property name="password" value="18273645"/>
    </bean>
    <bean id="jdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate">
        <property name="dataSource" ref="dataSource"/>
    </bean>
    <bean id="userDao" class="com.ayy.jdbcTemplate.c3p0.UserDao">
        <property name="jdbcTemplate" ref="jdbcTemplate"/>
    </bean>
</beans>
```

### 1.5 使用 JdbcDaoSupport

- Dao 层继承 JdbcDaoSupport 类，通过 getJdbcTemplate 获取模板

  UserDao.java

  ```java
  public class UserDao extends JdbcDaoSupport {
  
      public void update(User user){
          String sql = "update s_user set s_username = ?, p_password = ? where id = ?";
          Object[] args = {user.getsUsername(),user.getsPassword(),user.getId()};
          this.getJdbcTemplate().update(sql,args);
      }
  
      public List<User> findAll () {
          String sql = "select * from s_user";
          return this.getJdbcTemplate().query(sql,BeanPropertyRowMapper.newInstance(User.class));
      }
  }
  ```

- Spring 配置文件

  JDBCContext3.xml

  给 UserDao 传入连接池，通过父类  JdbcDaoSupport 自动获取 JdbcTemplate

  ```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <beans xmlns="http://www.springframework.org/schema/beans"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://www.springframework.org/schema/beans
                             http://www.springframework.org/schema/beans/spring-beans.xsd">
  
      <bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
          <property name="driverClass" value="com.mysql.cj.jdbc.Driver"/>
          <property name="jdbcUrl" value="jdbc:mysql://192.168.137.254:3306/springJDBCTemplate"/>
          <property name="user" value="jinzhao"/>
          <property name="password" value="18273645"/>
      </bean>
      <bean id="userDao" class="com.ayy.jdbcTemplate.jdbcDaoSupport.UserDao">
          <property name="dataSource" ref="dataSource"/>
      </bean>
  </beans>
  ```

### 1.6 配置 properties

jdbc.properties

```properties
jdbc.driverClass=com.mysql.cj.jdbc.Driver
jdbc.jdbcUrl=jdbc:mysql://192.168.137.254:3306/springJDBCTemplate
jdbc.user=jinzhao
jdbc.password=18273645
```

JDBCContext4.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
                           http://www.springframework.org/schema/beans/spring-beans.xsd
                           http://www.springframework.org/schema/context
                           http://www.springframework.org/schema/context/spring-context.xsd">

    <!-- classpath: -> ./src/ -->
    <context:property-placeholder location="jdbc.properties"/>

    <bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
        <property name="driverClass" value="${jdbc.driverClass}"/>
        <property name="jdbcUrl" value="${jdbc.jdbcUrl}"/>
        <property name="user" value="${jdbc.user}"/>
        <property name="password" value="${jdbc.password}"/>
    </bean>
    <bean id="userDao" class="com.ayy.jdbcTemplate.properties.UserDao">
        <property name="dataSource" ref="dataSource"/>
    </bean>
</beans>
```

UserDao.java

```java
public class UserDao extends JdbcDaoSupport {

    public void update(User user){
        String sql = "update s_user set s_username = ?, p_password = ? where id = ?";
        Object[] args = {user.getsUsername(),user.getsPassword(),user.getId()};
        this.getJdbcTemplate().update(sql,args);
    }

    public List<User> findAll () {
        String sql = "select * from s_user";
        return this.getJdbcTemplate().query(sql,BeanPropertyRowMapper.newInstance(User.class));
    }

    public User getById (int id) {
        String sql = "select * from s_user where id = ?";
        return this.getJdbcTemplate().queryForObject(sql,BeanPropertyRowMapper.newInstance(User.class),id);
    }
}

```

PropertiesTest.java

```java
public class PropertiesTest {
    @Test
    public void test01(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("JDBCContext4.xml");
        UserDao userDao = applicationContext.getBean("userDao", UserDao.class);
        User user = userDao.getById(1);
        System.out.println(user);
    }
}
```

## 2. 事务管理

### 2.1 回顾事务

- 事务：一组业务逻辑 / 操作 -> 原子性 要么全部成功，要么全部不成功

- 特性：ACID

  - 原子性：整体
  - 一致性：完成
  - 隔离性：并发
  - 持久性：结果

- 隔离问题：

  - 脏读：一个事务读到另一个事务没有提交的数据
  - 不可重复读：一个事务读到另一个事务已提交的数据（update）
  - 虚读 / 幻读：一个事务读到另一个事务已提交的数据（insert）

- 隔离级别

  - default：默认隔离级别
  - read uncommitted：读未提交 -> 存在3个问题
  - read committed：读已提交 -> 存在2个问题（解决了脏读问题）
  - repeatable read：可重复度 -> 存在1个问题（虚读 / 幻读问题）行锁
  - serializable：串行化 -> 都解决，单事务 -> Blocking 表锁

- 传播行为：在两个业务之间如何共享事务

  - __required__（默认值）：必须，支持当前事务，A 如果有事务，B 将使用该事务，如果 A 没有事务，B 将创建一个新事务
  - supports：支持，支持当前事务，A 如果有事务，B 将使用该事务，如果 A 没有事务，B 将以非事务执行
  - mandatory：强制，支持当前事务，A 如果有事务，B 将使用该事务，如果 A 没有事务，B 将抛出异常
  - __requires_new__：必须新的，如果 A 有事务，将 A 的事务挂起，B 创建一个新的事务，如果 A 没有事务，B 创建一个新的事务
  - not_supported：不支持，如果 A 有事务，将 A 的事务挂起，B 将以非事务执行，如果 A 没有事务，B 以非事务执行
  - never：不，如果 A 有事务，B 将抛出异常，如果 A 没有事务，B 以非事务执行
  - __nested__：嵌套，A 和 B 底层采用保存点机制，形成嵌套事务

- mysql 事务操作

  ```java
  // ABCD 一个事务
  try(Connection connection = null;){
    // 获得链接
      connection = ...;
      // 开启事务
      connection.setAutoCommit(false);
      A;
      B;
      C;
      D;
      // 提交事务
      connection.commit();
  }catch(){
      // 回滚事务
      connection.rollback();
  }
  ```
  
  Savepoint
  
  ```java
  // AB 整体 CD 可选
  // 保存点，记录操作的当前位置，之后可以回滚到指定位置
  Savepoint savepoint = null;
  // 获取链接
  try(Connection connection = ...;){
      // 开启事务
      connection.setAutoCommit(false);
      A;
      B;
      savepoint = connection.setSavepoint();
      C;
      D;
      // 提交事务
      connection.commit();
  }catch(){
      // CD 异常
      if(null!=savepoint){
          // 回滚到 CD 之前
          connection.rollback(savepoint);
  		connection.commit;
      }else{
  	    // 回滚事务 AB
  	    connection.rollback();
      }
  }
  ```

### 2.2 事务管理介绍

#### 2.2.1 导入 jar

```groovy
compile group: 'org.springframework', name: 'spring-tx', version: '5.2.6.RELEASE'
```

transaction -> tx

#### 2.2.2三个顶级接口

- PlatformTransactionManager：平台事务管理器
  - Spring 管理事务必须使用事务管理器
  - 进行事务配置时，必须配置事务管理器
- TransactionDefinition：事务详情（事务定义，事务属性）
  - Spring 用于确定事务具体详情 
    - 隔离级别
    - 是否只读c
    - 超时时间
    - ...
  - 进行事务配置时，必须配置详情
  - Spring 将配置封装到对象实例
- TransactionStatus：事务状态
  - Spring c用于记录当前事务运行状态
    - 保存点
    - 事务是否完成
  - Spring 底层根据状态进行相应操作

#### 2.2.3 PlatformTransactionManager

- 导入 jar 包，需要平台事务管理器的实现类

  ```groovy
  compile group: 'org.springframework', name: 'spring-jdbc', version: '5.2.6.RELEASE'
  compile group: 'org.springframework', name: 'spring-orm', version: '5.2.6.RELEASE'
  ```

- 常见的事务管理器

  - DataSourceTransactionManager：JDBC 开发时事务管理器，采用 JdbcTemplate
  - HibernateTransactionManager：HIbernate 开发时事务管理器，采用 Hibernate

- API 详解

  ```java
  // 事务管理器 通过”事务详情“，获得”事务状态“，从而管理事务
  TransactionStatus getTransaction(@Nullable TransactionDefinition definition) throws TransactionException;
  // 根据状态提交
  void commit(TransactionStatus status) throws TransactionException;
  // 根据状态回滚
  void rollback(TransactionStatus status) throws TransactionException;
  ```

#### 2.2.4 TransactionStatus

- API

  ```java
  // 是否有保存点
  boolean hasSavepoint();
  // 刷新
  void flush();
  ```

- 父类 API TransactionExecution

  ```java
  // 是否新事物
  boolean isNewTransaction();
  // 设置回滚
  void setRollbackOnly();
  // 是否回滚
  boolean isRollbackOnly();
  // 是否完成
  boolean isCompleted();
  ```

#### 2.2.5 TransactionDefinition

- API

  ```java
  // 配置事务详情名称，一般为方法名称，例如：save，add* 等
  String getName();
  // 是否只读
  boolean isReadOnly();
  // 获得超时时间
  int getTimeout();
  // 隔离级别
  int getIsolationLevel();
  // 传播行为
  int getPropagationBehavior();
  // 默认超时时间 -1 -> 使用数据库底层的超时时间
  int TIMEOUT_DEFAULT = -1;
  // 事务的隔离级别
  int ISOLATION_DEFAULT = -1;
  int ISOLATION_READ_UNCOMMITTED = 1;
  int ISOLATION_READ_COMMITTED = 2; // oracle default
  int ISOLATION_REPEATABLE_READ = 4; // mysql default
  int ISOLATION_SERIALIZABLE = 8;
  // 
  int PROPAGATION_REQUIRED = 0;
  int PROPAGATION_SUPPORTS = 1;
  int PROPAGATION_REQUIRES_NEW = 3;
  int PROPAGATION_NOT_SUPPORTED = 4;
  int PROPAGATION_NEVER = 5;
  int PROPAGATION_NESTED = 6;
  ```

## 2.3 案例：转账

#### 2.3.1 搭建环境

- 创建表

  ```mysql
  CREATE DATABASE spring_transaction;
  USE spring_transaction;
  CREATE TABLE account(
     id INT PRIMARY KEY AUTO_INCREMENT,
     username VARCHAR(50),
     money INT
  );
  INSERT INTO account(username,money) VALUES('a','1000');
  INSERT INTO account(username,money) VALUES('b','1000');
  ```

- 导入 jar 包

  - 核心 4+1 : spring-core, spring-beans, spring-context, spring-expression, common-logging
  - aop 4 : aopalliance, spring-aop, aspectj-weaver, spring-aspects
  - 数据库 2 : spring-jdbc, spring-tx
  - 数据库驱动 : mysql-driver
  - 数据库连接池 : c3p0

- dao 层

  ```java
  public class AccountDaoImpl extends JdbcDaoSupport implements AccountDao {
      @Override
      public void out (String payer, Integer money) {
          String sql = "update account set money = money - ? where username = ?";
          this.getJdbcTemplate().update(sql,money,payer);
      }
  
      @Override
      public void in (String payee, Integer money) {
          String sql = "update account set money = money + ? where username = ?";
          this.getJdbcTemplate().update(sql,money,payee);
      }
  }
  ```

- service 层

  ```java
  public class AccountServiceImpl implements AccountService {
      private AccountDao accountDao;
  
      public void setAccountDao (AccountDao accountDao) {
          this.accountDao = accountDao;
      }
  
  
      @Override
      public void transfer (String payer, String payee, Integer money) {
          accountDao.out(payer, money);
          // simulation of power off
          int i= 1/0;
          accountDao.in(payee, money);
      }
  }
  ```

- 配置文件

  ```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <beans xmlns="http://www.springframework.org/schema/beans"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://www.springframework.org/schema/beans
                             http://www.springframework.org/schema/beans/spring-beans.xsd">
  
      <bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
          <property name="driverClass" value="com.mysql.cj.jdbc.Driver"/>
          <property name="jdbcUrl" value="jdbc:mysql://192.168.137.254:3306/spring_transaction"/>
          <property name="user" value="jinzhao"/>
          <property name="password" value="18273645"/>
      </bean>
      <bean id="accountDao" class="com.ayy.transaction.transfer.manuel.dao.impl.AccountDaoImpl">
          <property name="dataSource" ref="dataSource"/>
      </bean>
      <bean id="accountService" class="com.ayy.transaction.transfer.manuel.service.impl.AccountServiceImpl">
          <property name="accountDao" ref="accountDao"/>
      </bean>
  </beans>
  ```

- Test Unit

  ```java
  public class AccountServiceTest {
      @Test
      public void demo01(){
          ApplicationContext applicationContext = new ClassPathXmlApplicationContext("ApplicationContext1.xml");
          AccountService accountService = applicationContext.getBean("accountService", AccountService.class);
          accountService.transfer("a","b",100);
      }
  }
  ```

#### 2.3.2 手动管理事务

- Spring 底层使用 TransactionTemplate 事务模板进行操作

- 操作

  - Service 需要获得 TransactionTemplate
  - Spring 配置模板，并注入给 service
  - 模板需要注入事务管理器
  - 配置事务管理器：DataSourceTransactionManager

- 修改 Service

  ```java
  public class AccountServiceImpl implements AccountService {
      private AccountDao accountDao;
      private TransactionTemplate transactionTemplate;
  
      public void setAccountDao (AccountDao accountDao) {
          this.accountDao = accountDao;
      }
  
      public void setTransactionTemplate (TransactionTemplate transactionTemplate) {
          this.transactionTemplate = transactionTemplate;
      }
  
      @Override
      public void transfer (String payer, String payee, Integer money) {
          transactionTemplate.execute(new TransactionCallbackWithoutResult() {
              @Override
              protected void doInTransactionWithoutResult (TransactionStatus status) {
                  accountDao.out(payer, money);
                  // simulation of power off
                  // int i= 1/0;
                  accountDao.in(payee, money);
              }
          });
      }
  }
  ```

- 修改 Spring 配置

  ```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <beans xmlns="http://www.springframework.org/schema/beans"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://www.springframework.org/schema/beans
                             http://www.springframework.org/schema/beans/spring-beans.xsd">
  
      <bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
          <property name="driverClass" value="com.mysql.cj.jdbc.Driver"/>
          <property name="jdbcUrl" value="jdbc:mysql://192.168.137.254:3306/spring_transaction"/>
          <property name="user" value="jinzhao"/>
          <property name="password" value="18273645"/>
      </bean>
      <bean id="accountDao" class="com.ayy.transaction.transfer.manuel.dao.impl.AccountDaoImpl">
          <property name="dataSource" ref="dataSource"/>
      </bean>
      <bean id="accountService" class="com.ayy.transaction.transfer.manuel.service.impl.AccountServiceImpl">
          <property name="accountDao" ref="accountDao"/>
          <property name="transactionTemplate" ref="transactionTemplate"/>
      </bean>
      <bean id="transactionTemplate" class="org.springframework.transaction.support.TransactionTemplate">
          <property name="transactionManager" ref="txManager"/>
      </bean>
      <bean id="txManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
          <property name="dataSource" ref="dataSource"/>
      </bean>
  </beans>
  ```

#### 2.3.3 半自动工厂 Bean 生成代理

- Spring 提供管理事务的代理工厂 Bean TransactionProxyFactoryBean

  - getBean() 获得代理对象
  - Spring 配置一个代理

- Service

  ```java
  public class AccountServiceImpl implements AccountService {
      private AccountDao accountDao;
  
      public void setAccountDao (AccountDao accountDao) {
          this.accountDao = accountDao;
      }
  
      @Override
      public void transfer (String payer, String payee, Integer money) {
          accountDao.out(payer, money);
          // simulation of power off
          // int i= 1/0;
          accountDao.in(payee, money);
  
      }
  }
  ```

- Spring 配置

  ```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <beans xmlns="http://www.springframework.org/schema/beans"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://www.springframework.org/schema/beans
                             http://www.springframework.org/schema/beans/spring-beans.xsd">
  
      <bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
          <property name="driverClass" value="com.mysql.cj.jdbc.Driver"/>
          <property name="jdbcUrl" value="jdbc:mysql://192.168.137.254:3306/spring_transaction"/>
          <property name="user" value="jinzhao"/>
          <property name="password" value="18273645"/>
      </bean>
      <bean id="accountDao" class="com.ayy.transaction.transfer.semi_auto.dao.impl.AccountDaoImpl">
          <property name="dataSource" ref="dataSource"/>
      </bean>
      <bean id="accountService" class="com.ayy.transaction.transfer.semi_auto.service.impl.AccountServiceImpl">
          <property name="accountDao" ref="accountDao"/>
      </bean>
      <bean id="proxyAccountService" class="org.springframework.transaction.interceptor.TransactionProxyFactoryBean">
          <property name="transactionManager" ref="txManager"/>
          <property name="target" ref="accountService"/>
          <property name="proxyInterfaces" value="com.ayy.transaction.transfer.semi_auto.service.AccountService"/>
          <property name="transactionAttributes">
              <props>
                  <!--
                      key : which methods use the transaction
                      text : detail of the transactions
                          PROPAGATION, ISOLATION, readOnly, -Exception(rollback), +Exception(commit)
                          ex : PROPAGATION_REQUIRED, ISOLATION_DEFAULT[, readOnly][, +java.lang.ArithmeticException][, -java.lang.ArithmeticException]
                   -->
                  <prop key="transfer">PROPAGATION_REQUIRED, ISOLATION_DEFAULT</prop>
              </props>
          </property>
      </bean>
      <bean id="txManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
          <property name="dataSource" ref="dataSource"/>
      </bean>
  </beans>
  ```

- Test Unit

  ```java
  public class AccountServiceTest {
      @Test
      public void demo01(){
          ApplicationContext applicationContext = new ClassPathXmlApplicationContext("ApplicationContext3.xml");
          AccountService accountService = applicationContext.getBean("proxyAccountService", AccountService.class);
          accountService.transfer("a","b",100);
      }
  }
  ```

#### 2.3.4 AOP 配置基于 Xml

- 在 Spring xml 配置 aop 自动生成代理，进行事务的管理

  - 配置管理器
  - 配置事务详情
  - 配置 aop

- Spring 配置

  ```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <beans xmlns="http://www.springframework.org/schema/beans"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xmlns:aop="http://www.springframework.org/schema/aop"
         xmlns:tx="http://www.springframework.org/schema/tx"
         xsi:schemaLocation="http://www.springframework.org/schema/beans
                             http://www.springframework.org/schema/beans/spring-beans.xsd
                             http://www.springframework.org/schema/aop
                             http://www.springframework.org/schema/aop/spring-aop.xsd
                             http://www.springframework.org/schema/tx
                             http://www.springframework.org/schema/tx/spring-tx.xsd">
  
      <bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
          <property name="driverClass" value="com.mysql.cj.jdbc.Driver"/>
          <property name="jdbcUrl" value="jdbc:mysql://192.168.137.254:3306/spring_transaction"/>
          <property name="user" value="jinzhao"/>
          <property name="password" value="18273645"/>
      </bean>
      <bean id="accountDao" class="com.ayy.transaction.transfer.aopxml.dao.impl.AccountDaoImpl">
          <property name="dataSource" ref="dataSource"/>
      </bean>
      <bean id="accountService" class="com.ayy.transaction.transfer.aopxml.service.impl.AccountServiceImpl">
          <property name="accountDao" ref="accountDao"/>
      </bean>
      <bean id="txManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
          <property name="dataSource" ref="dataSource"/>
      </bean>
      <tx:advice id="txAdvice" transaction-manager="txManager">
          <tx:attributes>
              <tx:method name="transfer" propagation="REQUIRED" isolation="DEFAULT"/>
          </tx:attributes>
      </tx:advice>
      <aop:config>
          <aop:advisor advice-ref="txAdvice" pointcut="execution(* com.ayy.transaction.transfer.aopxml.service..*.*(..))"/>
      </aop:config>
  </beans>
  ```

#### 2.3.5 AOP 配置基于注解

- 配置事务管理器，并将事务管理器交予 Spring

- 在目标类或目标方法添加注解即可 @Transactional

- Spring 配置

  ```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <beans xmlns="http://www.springframework.org/schema/beans"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xmlns:context="http://www.springframework.org/schema/context"
         xmlns:tx="http://www.springframework.org/schema/tx"
         xsi:schemaLocation="http://www.springframework.org/schema/beans
                             http://www.springframework.org/schema/beans/spring-beans.xsd
                             http://www.springframework.org/schema/tx
                             http://www.springframework.org/schema/tx/spring-tx.xsd
                             http://www.springframework.org/schema/context
                             http://www.springframework.org/schema/context/spring-context.xsd">
  
      <bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
          <property name="driverClass" value="com.mysql.cj.jdbc.Driver"/>
          <property name="jdbcUrl" value="jdbc:mysql://192.168.137.254:3306/spring_transaction"/>
          <property name="user" value="jinzhao"/>
          <property name="password" value="18273645"/>
      </bean>
      <bean id="accountDao" class="com.ayy.transaction.transfer.original.dao.impl.AccountDaoImpl">
          <property name="dataSource" ref="dataSource"/>
      </bean>
      <context:annotation-config/> <!-- service -->
      <bean id="txManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
          <property name="dataSource" ref="dataSource"/>
      </bean>
      <!-- proxy-target-class : cglib-->
      <tx:annotation-driven transaction-manager="txManager"/>
  </beans>
  ```

- Service 层

  ```java
  // @Transactional
  @Service
  public class AccountServiceImpl implements AccountService {
      private AccountDao accountDao;
  
      @Autowired
      @Qualifier("accountDao")
      public void setAccountDao (AccountDao accountDao) {
          this.accountDao = accountDao;
      }
  
      @Override
      @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT)
      public void transfer (String payer, String payee, Integer money) {
          accountDao.out(payer, money);
          // simulation of power off
          // int i= 1/0;
          accountDao.in(payee, money);
      }
  }
  ```

## 3. 整合 Junit

- 导入 jar 包

  - 4 + 1

  - spring-test

    ```groovy
    testCompile group: 'org.springframework', name: 'spring-test', version: '5.2.6.RELEASE'
    ```

- 让 Junit 通知 Spring 加载配置文件

- 让 Spring 容器自动注入

- 修改测试类

  ```java
  @RunWith(SpringJUnit4ClassRunner.class)
  @ContextConfiguration(locations = "classpath:JunitContext1.xml")
  public class AccountServiceTest {
      // The scan needn't be configured in xml
      @Autowired
      private AccountService accountService;
  
      @Test
      public void demo01(){
          accountService.transfer("a","b",100);
      }
  }
  ```

## 4. 整合 WEB

- 导入 jar 包

  ```groovy
  compile group: 'org.springframework', name: 'spring-web', version: '5.2.6.RELEASE'
  ```

- Tomcat 启动加载配置文件

  - Servlet --> init(Servlet-Config) --> \<load-on-startup\>2
  - Filter --> init(FilterConfig) --> web.xml 注册过滤器自动调用初始化
  - Listener --> ServletContextListener --> servletContext 对象监听
  - Spring 提供监听器 ContextLoaderListener --> 

  ```xml
  <listener>
      <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
  </listener>
  ```

- 修改配置文件位置 通过系统初始化参数 ServletContext 初始化参数 web.xml

  - \<context-param\>
    - \<param-name\> contextConfigLocation
    - \<param-value\>classpath:applicationContext.xml

  ```xml
  <context-param>
  	<param-name>contextConfigLocation</param-name>
      <param-value>classpath:WebContext1.xml</param-value>
  </context-param>
  ```

- 从 servletContext 作用域获得 spring 容器s

  ```java
  public class HelloServlet extends HttpServlet {
      @Override
      protected void doGet (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
          // method 1
          // ApplicationContext applicationContext =(ApplicationContext)this.getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
          // method 2
          ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
          AccountService accountService = applicationContext.getBean("accountService",AccountService.class);
          accountService.transfer("a","b",1000);
      }
  
      @Override
      protected void doPost (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
          this.doGet(req,resp);
      }
  }
  ```

## 5. SSH 整合

