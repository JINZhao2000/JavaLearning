# MyBatis

[MyBatis 官网](https://mybatis.org/mybatis-3/zh/index.html)

## 1. 简介

- 什么是 MyBatis

  - 持久层框架
  - 避免几乎所有的 JDBC 代码
  - 用 xml 或注解来配置和映射原生类型，接口和 POJO

- MyBatis 依赖

  github : https://github.com/mybatis/mybatis-3

  ```xml
  <dependencies>
      <dependency>
        <groupId>org.mybatis</groupId>
          <artifactId>mybatis</artifactId>
      </dependency>
  </dependencies>
  ```
  
- 优点

  - 简单易学
  - 灵活
  - sql 和代码的分离，提高了可维护性
  - 提供了映射便签，支持对象与数据库的 orm 字段关系映射
  - 提供对象关系映射标签，支持对象关系组建维护
  - 提供 xml 标签，支持编写动态 sql

## 2. 第一个 MyBatis 程序

- 搭建环境

  ```mysql
  CREATE TABLE mb_user (
    uid INT PRIMARY KEY AUTO_INCREMENT,
    uname VARCHAR(20) DEFAULT NULL,
    pwd VARCHAR(15) DEFAULT NULL
  );
  INSERT INTO mb_user(uname, pwd) VALUES('USER1','123456');
  INSERT INTO mb_user(uname, pwd) VALUES('USER2','123456');
  INSERT INTO mb_user(uname, pwd) VALUES('USER3','123456');
  INSERT INTO mb_user(uname, pwd) VALUES('USER4','123456');
  ```

- 实体类

  ```java
  public class User {
      private int uid;
      private String uname;
      private String pwd;
  
      public User() {}
      ...
  }
  ```

- 工具类

  ```java
  public class MyBatisUtils {
      private static SqlSessionFactory sqlSessionFactory;
  
      static {
          try {
              InputStream is = MyBatisUtils.class.getClassLoader().getResourceAsStream("mybatis-config.xml");
              sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
          } catch (Exception e) {
              e.printStackTrace();
          }
      }
  
      public static SqlSession getSqlSession(){
          return sqlSessionFactory.openSession();
      }
  }
  ```

- Mapper 接口（Dao 层）

  ```java
  public interface UserMapper {
      List<User> getAllUser();
  }
  ```

- Mapper 配置文件

  ```xml
  <?xml version="1.0" encoding="UTF-8" ?>
  <!DOCTYPE mapper
          PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
          "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
  <mapper namespace="com.ayy.dao.UserMapper">
      <select id="getAllUser" resultType="com.ayy.bean.User">
          select * from mb_user
      </select>
  </mapper>
  ```

- MyBatis 配置

  ```xml
  <?xml version="1.0" encoding="UTF-8" ?>
  <!DOCTYPE configuration
          PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
          "http://mybatis.org/dtd/mybatis-3-config.dtd">
  <configuration>
      <environments default="development">
          <environment id="development">
              <transactionManager type="JDBC"/>
              <dataSource type="POOLED">
                  <property name="driver" value="com.mysql.cj.jdbc.Driver"/>
                  <property name="url" value="jdbc:mysql://rm-4xo63l909kdrexz8kio.mysql.germany.rds.aliyuncs.com:3306/jdbc_test?rewriteBatchedStatements=true"/>
                  <property name="username" value="jdbc"/>
                  <property name="password" value="JDBCjdbc000"/>
              </dataSource>
          </environment>
      </environments>
      <mappers>
          <mapper resource="com/ayy/dao/UserMapper.xml"/>
      </mappers>
  </configuration>
  ```

- 测试类

  ```java
  public class UserMapperTest {
      private SqlSession sqlSession;
  
      @Before
      public void setUp(){
          sqlSession = MyBatisUtils.getSqlSession();
      }
  
      @After
      public void tearDown(){
          sqlSession.close();
      }
  
      @Test
      public void testGetAllUser(){
          UserMapper mapper = sqlSession.getMapper(UserMapper.class);
          List<User> allUser = mapper.getAllUser();
          allUser.forEach(System.out::println);
      }
  
      @Test
      public void testGetAllUser2(){
          List<User> users = sqlSession.selectList("com.ayy.dao.UserMapper.getAllUser");
          users.forEach(System.out::println);
      }
  }
  ```

## 3. CRUD

- Mapper 文件

  ```xml
  <mapper namespace="com.ayy.dao.UserMapper">
      <select id="getAllUser" resultType="com.ayy.bean.User">
          select * from mb_user
      </select>
  </mapper>
  ```

  - namespace：对应接口名称

- Select

  ```xml
  <!-- List<User> getAllUser(); -->
  <select id="getAllUser" resultType="com.ayy.bean.User">
      select * from mb_user
  </select>
  ```

  - id：方法名
  - resultType：返回类型
  - parameterType：参数类型

  ```xml
  <!-- User getUserById(int id); -->
  <select id="getUserById" parameterType="int" resultType="com.ayy.bean.User">
      select * from mb_user where uid = #{id}
  </select>
  ```

- Insert（注意事务提交）

  ```xml
  <!-- void addUser(User user); -->
  <insert id="addUser" parameterType="com.ayy.bean.User">
      insert into mb_user(uname, pwd) values (#{uname},#{pwd})
  </insert>
  ```

  - 对象中的属性可以直接拿 #{} 号取

- update

  ```xml
  <!-- void updateUser(User user); -->
  <update id="updateUser" parameterType="com.ayy.bean.User">
      update mb_user set uname = #{uname} , pwd = #{pwd} where uid = #{uid}
  </update>
  ```

- delete

  ```xml
  <!-- void deleteUser(int id); -->
  <delete id="deleteUser" parameterType="int">
      delete from mb_user where uid = #{id}
  </delete>
  ```

- map（非正规做法）

  - Mapper 接口方法

    ```java
    void addUserByMap(Map<String,Object> map);
    ```

  - Mapper 配置文件

    ```xml
    <insert id="addUserByMap" parameterType="map">
        insert into mb_user (uname, pwd) values (#{namexxxxxxx},#{pwdxxxxx})
    </insert>
    ```

  - 测试类

    ```java
    @Test
    public void testAddUserByMap(){
        try {
            HashMap<String, Object> map = new HashMap<>();
            map.put("namexxxxxxx","USER2222");
            map.put("pwdxxxxx","123456");
            mapper.addUserByMap(map);
            sqlSession.commit();
        } catch (Exception e){
            sqlSession.rollback();
        }
    }
    ```

- 模糊查询

  - 接口方法

    ```java
    List<User> getUserLike(String value);
    ```

  - 配置

    ```xml
    <select id="getUserLike" resultType="com.ayy.bean.User">
        select * from mb_user where uname like #{value};
    </select>
    ```

  - 测试

    ```java
    @Test
    public void testGetUserLike(){
        List<User> users = mapper.getUserLike("%USER2%");
        users.forEach(System.out::println);
    }
    ```


## 4. 配置解析

- mybatis-config.xml

  ```xml
  <configuration>
  	<properties></properties> <!-- 属性 -->
      <settings></settings> <!-- 设置 -->
      <typeAliases></typeAliases> <!-- 类型别名 -->
      <typeHandlers></typeHandlers> <!-- 类型处理器 -->
      <objectFactory></objectFactory> <!-- 对象工厂 -->
      <plugins></plugins> <!-- 插件 -->
      <environments> <!-- 环境配置 -->
      	<environment> <!-- 环境变量 -->
          	<transactionManager></transactionManager> <!-- 事务管理器 -->
              <dataSource></dataSource> <!-- 数据源 -->
          </environment>
      </environments>
      <databaseIdProvider></databaseIdProvider> <!-- 数据库厂商标识 -->
      <mappers></mappers> <!-- 映射器 -->
  </configuration>
  ```

  - Properties 属性

    - 通过 properties 来读取配置文件
    - 优先级为外部配置文件

  - Settings 设置

    | 设置名                           | 描述                                                         | 有效值                                                       | 默认值                                                |
    | -------------------------------- | ------------------------------------------------------------ | ------------------------------------------------------------ | ----------------------------------------------------- |
    | __cacheEnabled__                 | 全局地开启或者关闭配置文件中的所有映射器已经配置的任何缓存   | true\|false                                                  | true                                                  |
    | __lazyLoadingEnabled__           | 延迟加载，所有关联对象都会延迟加载，特定关联关系中可通过设置 `fetchType` 属性来覆盖该项的开关 | true\|false                                                  | false                                                 |
    | aggressiveLazyLoading            | 任何方法的调用都会加载改对象的所有属性，否则每个属性按需加载（参考：`lazyLoadTriggerMethods`） | true\|false                                                  | false<br>(@Since 3.4.1)                               |
    | multipleResultSetsEnabled        | 是否允许单个语句返回多结果集（需要数据库驱动支持）           | true\|false                                                  | true                                                  |
    | useColumnLabel                   | 使用列标签代替列名。实际表现依赖于数据库驱动，具体可参考数据库驱动的相关文档，或通过对比测试来观察 | true\|false                                                  | true                                                  |
    | useGeneratedKeys                 | 允许 JDBC 支持自动生成主键，需要数据库驱动支持。如果设置为 true，将强制使用自动生成主键。尽管一些数据库驱动不支持此特性，但仍可正常工作（如 Derby） | true\|false                                                  | false                                                 |
    | autoMappingBehavior              | 指定 MyBatis 应如何自动映射列到字段或属性。 NONE 表示关闭自动映射；PARTIAL 只会自动映射没有定义嵌套结果映射的字段。 FULL 会自动映射任何复杂的结果集（无论是否嵌套） | NONE, PARTIAL, FULL                                          | PARTIAL                                               |
    | autoMappingUnknownColumnBehavior | 指定发现自动映射目标未知列（或未知属性类型）的行为<br>`NONE`: 不做任何反应 <br>`WARNING`: 输出警告日志（`'org.apache.ibatis.session.AutoMappingUnknownColumnBehavior'` 的日志等级必须设置为 `WARN`） <br>`FAILING`: 映射失败 (抛出 `SqlSessionException`) | NONE, WARNING, FAILING                                       | NONE                                                  |
    | defaultExecutorType              | 配置默认的执行器。SIMPLE 就是普通的执行器；REUSE 执行器会重用预处理语句（PreparedStatement）； BATCH 执行器不仅重用语句还会执行批量更新 | SIMPLE, REUSE,  BATCH                                        | SIMPLE                                                |
    | defaultStatementTimeout          | 设置超时时间，它决定数据库驱动等待数据库响应的秒数           | >0                                                           | null                                                  |
    | defaultFetchSize                 | 为驱动的结果集获取数量（fetchSize）设置一个建议值。此参数只可以在查询设置中被覆盖 | >0                                                           | null                                                  |
    | defaultResultSetType             | 指定语句默认的滚动策略（@Since 3.5.2）                       | FORWARD_ONLY \| SCROLL_SENSITIVE \| SCROLL_INSENSITIVE \| DEFAULT | null                                                  |
    | safeRowBoundsEnabled             | 是否不允许在嵌套语句中使用分页（RowBounds）                  | true\|false                                                  | false                                                 |
    | safeResultHandlerEnabled         | 是否不允许在嵌套语句中使用结果处理器（ResultHandler）        | true\|false                                                  | true                                                  |
    | mapUnderscoreToCamelCase         | 是否开启驼峰命名自动映射                                     | true\|false                                                  | false                                                 |
    | localCacheScope                  | MyBatis 利用本地缓存机制（Local Cache）防止循环引用和加速重复的嵌套查询。 默认值为 SESSION，会缓存一个会话中执行的所有查询。 若设置值为 STATEMENT，本地缓存将仅用于执行语句，对相同 SqlSession 的不同查询将不会进行缓存 | SESSION \| STATEMENT                                         | SESSION                                               |
    | jdbcTypeForNull                  | 当没有为参数指定特定的 JDBC 类型时，空值的默认 JDBC 类型     | NULL, VARCHAR, OTHER                                         | OTHER                                                 |
    | lazyLoadTriggerMethods           | 指定对象的哪些方法触发一次延迟加载                           | 用逗号分隔的方法列表                                         | equals,clone,hashCode,toString                        |
    | defaultScriptingLanguage         | 指定动态 SQL 生成使用的默认脚本语言                          | 一个类型别名或全限定类名                                     | org.apache.ibatis.scripting.xmltags.XMLLanguageDriver |
    | defaultEnumTypeHandler           | 指定 Enum 使用的默认 `TypeHandler` （@Since 3.4.5）          | 一个类型别名或全限定类名                                     | org.apache.ibatis.type.EnumTypeHandler                |
    | callSettersOnNulls               | 指定当结果集中值为 null 的时候是否调用映射对象的 setter（map 对象时为 put）方法，这在依赖于 Map.keySet() 或 null 值进行初始化时比较有用。注意基本类型（int、boolean 等）是不能设置成 null 的 | true\|false                                                  | false                                                 |
    | returnInstanceForEmptyRow        | 当返回行的所有列都是空时，MyBatis默认返回 `null`。 当开启这个设置时，MyBatis会返回一个空实例。 请注意，它也适用于嵌套的结果集（如集合或关联）（@Since 3.4.2） | true\|false                                                  | false                                                 |
    | logPrefix                        | 指定 MyBatis 增加到日志名称的前缀                            | 任何字符串                                                   | null                                                  |
    | __logImpl__                      | 指定 MyBatis 所用日志的具体实现，未指定时将自动查找          | SLF4J \| LOG4J \| LOG4J2 \| JDK_LOGGING \| COMMONS_LOGGING \| STDOUT_LOGGING \| NO_LOGGING | null                                                  |
    | proxyFactory                     | 指定 Mybatis 创建可延迟加载对象所用到的代理工具              | CGLIB \| JAVASSIST                                           | JAVASSIST（@Since 3.3）                               |
    | vfsImpl                          | 指定 VFS 的实现                                              | 自定义 VFS 的实现的类全限定名，以逗号分隔                    | null                                                  |
    | useActualParamName               | 允许使用方法签名中的名称作为语句参数名称。 为了使用该特性，你的项目必须采用 Java 8 编译，并且加上 `-parameters` 选项 （@Since 3.4.1） | true\|false                                                  | true                                                  |
    | configurationFactory             | 指定一个提供 `Configuration` 实例的类。 这个被返回的 Configuration 实例用来加载被反序列化对象的延迟加载属性值。 这个类必须包含一个签名为`static Configuration getConfiguration()` 的方法（@Since 3.2.3） | 一个类型别名或完全限定类名                                   | null                                                  |
    | shrinkWhitespacesInSql           | 从SQL中删除多余的空格字符。请注意，这也会影响SQL中的文字字符串（@Since 3.5.5） | true\|false                                                  | false                                                 |
    | defaultSqlProviderType           | 指定一个包含提供程序方法的SQL提供程序类（@Since 3.5.6）。当省略了这些属性时，此类适用于sql provider注释（例如）上的`type`（或`value`）属性`@SelectProvider` | 一个类型别名或完全限定类名                                   | null                                                  |

  - TypeAliases 别名

    优先级 typeAlias > 注解 > package

    - package

      默认别名为这个类类名首字母小写

      ```xml
      <package name="com.ayy.bean"/>
      ```

    - typeAlias

      ```xml
      <typeAlias type="com.ayy.bean.User" alias="User"/>
      ```

    - 在类上用 `@Alias` 注解

    - 默认别名

      | 别名       | 映射类型   |
      | ---------- | ---------- |
      | _byte      | byte       |
      | _long      | long       |
      | _int       | int        |
      | _short     | short      |
      | _integer   | int        |
      | _double    | double     |
      | _float     | float      |
      | _boolean   | boolean    |
      | string     | String     |
      | byte       | Byte       |
      | long       | Long       |
      | short      | Short      |
      | int        | Integer    |
      | integer    | Integer    |
      | double     | Double     |
      | float      | Float      |
      | boolean    | Boolean    |
      | date       | Date       |
      | decimal    | BigDecimal |
      | object     | Object     |
      | map        | Map        |
      | hashmap    | HashMap    |
      | list       | List       |
      | arraylist  | ArrayList  |
      | collection | Collection |
      | iterator   | Iterator   |

  - Environments 环境配置

    - 可以配置多套环境

    - 每一次只能通过 default=id 来选择一套环境

    - transactionManager（Spring + Mybatis 就没必要配置）

      - JDBC

        直接使用 JDBC 的 commit 和 rollback，依赖于数据源得到的连接来管理事务作用域

      - MANAGED

        不提交也不回滚，让容器来管理事务的整个生命周期（EJB）

        默认会关闭连接，但是可以通过将 `closeConnection` 设置为 flase 来阻止它默认关闭的行为

        ```xml
        <transactionManager type="MANAGED">
            <property name="closeConnection" value="false"/>
        </transactionManager>
        ```

    - dataSource

      - type 内建数据源有三种类型

        ```xml
        <dataSource type="[UNPOOLED|POOLED|JNDI]"></dataSource>
        ```

        - poolMaximumActiveConnections 10 任意时间存在的正在使用的连接数量
        - poolMaximumIdleConnections 10 任意时间存在的连接空闲数
        - poolMaximumCheckoutTime  20000ms 在被强制返回前，池中连接被检出时间
        - poolTimeToWait 20000ms 获取不到连接时，连接池打印状态日志并重新获取连接
        - poolManimumLocalBadConnectionTolerence 3 （@Since 3.4.5） 允许在获得一个坏连接后重新申请新连接，次数为 Idle 与 Bad 之和
        - poolPingQuery "NO PING QUERY SET" 数据库侦测查询，返回出错时信息
        - poolPingEnabled false 是否侦测查询，启用时必须设置 `poolPingQuery` 为一个可执行 sql 语句
        - poolPingConnectionsNotUsedFor 0 侦测频率

      - driver

      - url

      - username

      - password

      - defaultTransactionIsolationLeval

      - defaultNetworkTimeout

      - driver.encoding=UTF-8

      - initial_context (JNDI)

      - data_source (JNDI)

      - env.encoding=UTF-8 (JNDI)

  - Plugins 插件

    - mybatis-generator-core
    - mybatis-plus
    - 通用 mapper

  - Mappers 映射器

    - 资源路径

      ```xml
      <mapper resource="org/mybatis/builder/AuthorMapper.xml"/>
      ```

    - 使用接口映射器

      ```xml
      <mapper class="org.mybatis.builder.AuthorMapper"/>
      ```

    - 将包内接口全注册为映射器

      ```xml
      <package name="org.mybatis.builder"/>
      ```

## 5. 作用域和生命周期

SqlSessionFactoryBuilder

- 一旦创建就不用了
- 局部变量

SqlSessionFactory

- 一旦创建运行期间一直存在
- 一般为单例或者静态单例模式

SqlSession

- 每个线程都有自己的一个 SqlSession
- 不是线程安全

## 6. 解决属性名和字段名不一致的问题

- 数据库字段

  ```text
  mb_user
    uid
    uname
    pwd
  ```

- Bean 字段

  ```java
  public class User{
      private int id;
      private String name;
      private String password;
  }
  ```

- 第一种解决方式：select xxx as xxx

- 第二种解决方式：ResultMap 结果集映射

  ```xml
  <resultMap id="UserMap" type="User">
      <result column="uid" property="id"/>
      <result column="uname" property="name"/>
      <result column="pwd" property="password"/>
  </resultMap>
  ```

## 7. 日志

### 7.1 日志工厂

settings -> logimpl

- SLF4J *
- LOG4J
- LOG4J2
- JDK_LOGGING
- COMMONS_LOGGING
- STDOUT_LOGGING *
- NO_LOGGING

STDOUT_LOGGING 标准日志输出

```xml
<setting name="logImpl" value="STDOUT_LOGGING"/>
```

LOG4J - Log for Java

- 导入 log4j

  ```xml
  <dependency>
      <groupId>log4j</groupId>
      <artifactId>log4j</artifactId>
      <version>${log4j}</version>
  </dependency>
  ```

- log4j.properties

  ```properties
  log4j.rootLogger=DEBUG,console
  log4j.additivity.org.apache=true
  # console
  log4j.appender.console=org.apache.log4j.ConsoleAppender
  log4j.appender.console.Threshold=DEBUG
  log4j.appender.console.ImmediateFlush=true
  log4j.appender.console.Target=System.err
  log4j.appender.console.layout=org.apache.log4j.PatternLayout
  log4j.appender.console.layout.ConversionPattern=[%-5p] %d(%r) --> [%t] %l: %m %x %n
  
  log4j.logger.org.mybatis=DEBUG
  log4j.logger.java.sql=DEBUG
  log4j.logger.java.sql.Statement=DEBUG
  log4j.logger.java.sql.ResultSet=DEBUG
  log4j.logger.java.sql.PreparedStatement=DEBUG
  ```

- 配置实现

  ```xml
  <setting name="logImpl" value="LOG4J"/>
  ```

## 8. 分页

- limit 分页

  ```mysql
  select * from t_user limit startIndex, pageSize;
  select * from t_user limit endIndex;
  ```

- RowBounds 分页（不推荐）

  ```java
  @Test
  public void testGetUserByRowBounds(){
      List<User> users = sqlSession.selectList(
          "com.ayy.dao.UserMapper.getUserByRowBounds",
          null, new RowBounds(1,3));
      users.forEach(System.out::println);
  }
  ```

- [分页插件](https://pagehelper.github.io/docs/howtouse/) 

## 9. 注解开发

- 方法上注解

  ```java
  @Select("select * from mb_user")
  List<User> getAllUser();
  ```

- 绑定接口

  ```xml
  <mapper class="com.ayy.dao.UserMapper"/>
  ```

- 本质是利用反射来获取 sql 语句，入参，出参和动态代理

- MyBatis 执行流程

  1. 通过 Resources 获取加载全局配置文件
  2. 实例化 SqlSessionFactoryBuilder 构造器
  3. 解析文件流 XMLConfigBuilder
  4. 返回一个 Configuration 对象
  5. 实例化 SqlSessionFactory 对象
  6. transactional 事务管理器
  7. Executor 执行器
  8. 创建 sqlSession
  9. 实现 CRUD
  10. 查看是否执行成功 -- 失败 --> 回滚至 6
  11. 提交事务 （设置 `openSession(true)`）可以自动提交
  12. 关闭 sqlSession

- 增删改查

  ```java
  public interface UserMapper {
      @Select("select * from mb_user")
      List<User> getAllUser();
  
      @Select("select * from mb_user where uid = #{id}")
      User getUserById(@Param("id") int id);
  
      @Insert("insert into mb_user(uid,uname,pwd) values(${id},${name},#{password})")
      void addUser(User user);
  
      @Update("update mb_user set uname=#{name}, pwd=#{password} where uid=#{id}")
      void updateUser(User user);
  
      @Delete("delete from mb_user where uid=#{id}")
      void deleteUser(@Param("id") int id);
  }
  ```

## 10. Lombok

- 一个可以自动写 getter 和 equals 方法的 Java 库插件

- IDEA 安装 Lombok 插件

- 导入 jar 包

  ```xml
  <dependency>
      <groupId>org.projectlombok</groupId>
      <artifactId>lombok</artifactId>
      <version>1.18.18</version>
      <scope>provided</scope>
  </dependency>
  ```

- 注解

  ```java
  @Getter
  @Setter
  @FieldNameConstants
  @EqualsAndHashCode
  @AllArgsConstructor
  @RequiredArgsConstructor
  @NoArgsConstructor
  @Log
  @Log4j
  @slf4j
  @xslf4j
  @CommonsLog
  @JBossLog
  @Flogger
  @Data
  @Builder
  @Singular
  @Delegate
  @Value
  @Accessors
  @Wither
  @SneakyThrows
  ```

- 示例

  ```java
  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public class User {
      private int id;
      private String name;
      private String password;
  }
  ```

## 11. 多对一处理

- 准备表

  ```mysql
  CREATE TABLE prof(
    pid INT PRIMARY KEY AUTO_INCREMENT,
    pname VARCHAR(30) DEFAULT NULL
  );
  
  CREATE TABLE etu(
    eid INT PRIMARY KEY AUTO_INCREMENT,
    ename VARCHAR(30) DEFAULT NULL,
    pid INT REFERENCES prof(pid)
  );
  ```

- 准备数据

  ```mysql
  INSERT INTO prof(pname) VALUES('prof1');
  INSERT INTO etu(ename,pid) VALUES('etu1',1);
  INSERT INTO etu(ename,pid) VALUES('etu2',1);
  INSERT INTO etu(ename,pid) VALUES('etu3',1);
  INSERT INTO etu(ename,pid) VALUES('etu4',1);
  INSERT INTO etu(ename,pid) VALUES('etu5',1);
  ```

- 方法一：子查询

  ```xml
  <select id="getEtus" resultMap="EtuProf">
      select * from etu;
  </select>
  <resultMap id="EtuProf" type="com.ayy.bean.Etu">
      <result property="eid" column="eid"/>
      <result property="ename" column="ename"/>
      <association property="prof" column="pid" javaType="com.ayy.bean.Prof" select="getProfs"/>
  </resultMap>
  
  <select id="getProfs" resultType="com.ayy.bean.Prof">
      select * from prof where pid = #{pid}
  </select>
  ```

- 方法二：结果嵌套

  ```xml
  <select id="getEtus2" resultMap="EtuProf2">
      select e.eid eid, e.ename ename, p.pname pname
      from etu e, prof p
      where e.pid = p.pid;
  </select>
  <resultMap id="EtuProf2" type="com.ayy.bean.Etu">
      <result property="eid" column="eid"/>
      <result property="ename" column="ename"/>
      <association property="prof" javaType="com.ayy.bean.Prof">
          <result property="pname" column="pname"/>
      </association>
  </resultMap>
  ```

## 12. 一对多处理

- 类

  ```java
  @Data
  public class Prof {
      private int pid;
      private String pname;
      private List<Etu> etus;
  }
  ```

  ```java
  @Data
  public class Etu {
      private int eid;
      private String ename;
      private int pid;
  }
  ```

- 结果嵌套

  ```xml
  <select id="getProf" resultMap="ProfEtu">
      select e.eid eid, e.ename ename, p.pname pname, p.pid pid
      from etu e, prof p
      where e.pid = p.pid and p.pid = #{pid}
  </select>
  <resultMap id="ProfEtu" type="com.ayy.bean.Prof">
      <result property="pid" column="pid"/>
      <result property="pname" column="pname"/>
      <collection property="etus" ofType="com.ayy.bean.Etu">
          <result property="eid" column="eid"/>
          <result property="ename" column="ename"/>
          <result property="pid" column="pid"/>
      </collection>
  </resultMap>
  ```

- 子查询

  ```xml
  <select id="getProf2" resultMap="ProfEtu2">
      select * from prof where pid = #{pid};
  </select>
  <resultMap id="ProfEtu2" type="com.ayy.bean.Prof">
      <collection property="etus" javaType="java.util.ArrayList" ofType="com.ayy.bean.Etu" select="getEtu" column="pid"/>
  </resultMap>
  <select id="getEtu" resultType="com.ayy.bean.Etu">
      select * from etu where pid = #{pid}
  </select>
  ```

## 13. 动态 SQL

动态 SQL 指根据不同条件生成不同的 sql 语句

- 环境

  ```mysql
  CREATE TABLE blog(
    bid varchar(30) NOT NULL PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    author VARCHAR(50) NOT NULL,
    create_time DATETIME NOT NULL,
    views INT NOT NULL
  );
  ```

- IF

  ```xml
  <select id="queryBlogIF" parameterType="map" resultType="com.ayy.bean.Blog">
      select * from blog where 1=1
      <if test="title != null">
          and title = #{title}
      </if>
      <if test="author != null">
          and author = #{author}
      </if>
  </select>
  ```

- CHOOSE WHEN OTHERWISE

  ```xml
  <select id="queryBlogCHOOSE" parameterType="map" resultType="com.ayy.bean.Blog">
      select * from blog
      <where>
          <choose>
              <when test="title != null">
                  title = #{title}
              </when>
              <when test="author != null">
                  and author = #{author}
              </when>
              <otherwise>
                  and views = #{views}
              </otherwise>
          </choose>
      </where>
  </select>
  ```

- TRIM（WHERE, SET）

  WHERE

  ```xml
  <select id="queryBlogIF" parameterType="map" resultType="com.ayy.bean.Blog">
      select * from blog
      <where>
          <if test="title != null">
              and title = #{title}
          </if>
          <if test="author != null">
              and author = #{author}
          </if>
      </where>
  </select>
  ```

  SET

  ```xml
  <update id="updateBlog" parameterType="map">
      update blog
      <set>
          <if test="title != null">
              title = #{title},
          </if>
          <if test="author != null">
              author = #{author},
          </if>
      </set>
      where bid = ${bid}
  </update>
  ```

  TRIM

  ```xml
  <trim prefix="WHERE" prefixOverrides="AND |OR " suffix=""/>
  <trim prefix="SET" prefixOverrides="," suffix=""/>
  ```

- SQL 片段

  将一句 SQL 的公共部分方便复用（尽量不要存在 where 标签）

  ```xml
  <sql id="if-title-author">
      <if test="title != null">
          and title = #{title}
      </if>
      <if test="author != null">
          and author = #{author}
      </if>
  </sql>
  ```

  引用

  ```xml
  <include refid="if-title-author"/>
  ```

- FOREACH

  ```xml
  <select id="queryBlogForEach" parameterType="map" resultType="com.ayy.bean.Blog">
      select * from blog
      <where>
          <foreach collection="ids" item="id" open="and (" close=")" separator=" or ">
              bid=#{id}
          </foreach>
      </where>
  </select>
  ```

  Java 代码

  ```java
  @Test
  public void testQueryBlogForEach(){
      Map<String,Object> map = new HashMap<>();
      List<String> ids = new ArrayList<>();
      ids.add("5654d8b19fd54b1db6296ade40aa4341");
      map.put("ids",ids);
      List<Blog> blogs = mapper.queryBlogForEach(map);
      blogs.forEach(System.out::println);
  }
  ```

## 14. 缓存

### 14.1 简介

- 什么是缓存
  - 存在内存中的临时数据
  - 将用户经常查询的数据存放在缓存（内存）中，用户去查询数据就不用从磁盘上查询，从缓存中查询，提高查询效率
- 为什么使用缓存
  - 减少和数据库交互的次数，减少系统开销，提高系统效率
- 什么样的数据能使用缓存
  - 经常查询并且不经常改变数据

### 14.2 MyBatis 缓存

- MyBatis 默认定义了两级缓存：一级缓存和二级缓存
  - 默认情况下，只有一级缓存开启（SqlSession 级别的缓存，也称为本地缓存）
  - 二级缓存需要手动开启和配置，基于 namespace 级别的缓存
  - 为了提高扩展，MyBatis 定义了缓存接口 Cache，可以通过实现 Cache 接口来定义缓存
- `<cache/>` 中定义缓存
  - 清除策略：
    - LRU：最近最少使用：移除最长时间不被用的对象（默认）
    - FIFO：先进先出：按对象进入缓存的顺序来移除他们
    - SOFT：软引用：基于垃圾回收器状态和软引用规则移除对象
    - WEAK：虚引用：更积极地基于垃圾收集器状态和弱引用规则移除对象
  - flushInterva：刷新间隔（ms）
  - size：引用数目（默认：1024）
  - readOnly：只读（默认：false）
  - 二级缓存是事务性的

### 14.3 一级缓存

一级缓存也叫做本地缓存

- 与数据库同一次会话期间查到的数据会放在本地缓存中
- 如果以后需要获取相同的数据，直接从缓存中拿

测试步骤：

- 开启日志

缓存失效的情况：

- 查询不同的东西

- 增删改操作，可能改变原来的操作，必定刷新缓存

- 查询不同的 Mapper.xml

- 手动清理缓存

  ```java
  sqlSession.clearCache();
  ```

### 14.4 二级缓存

- 二级缓存即全局缓存，为了提高缓存的作用域
- 基于 namespace 级别的缓存，对应一个二级缓存
- 工作机制：
  - 一个会话查询一条数据，这个数据就会被放在一级缓存中
  - 如果当前会话关闭了，一级缓存中的数据被保存到二级缓存中
  - 新的会话查询信息就可以从二级缓存中获取内容
  - 不同的 mapper 查出的数据会放在自己对应的缓存（map）中

步骤：

- 开启全局缓存

  ```xml
  <setting name="cacheEnabled" value="true"/>
  ```

- 在要使用二级缓存的 Mapper 中开启

  ```xml
  <cache
      eviction="FIFO"
      flushInterval="60000"
      size="512"
      readOnly="true"/>
  ```

- 测试

  - 仅 `<cache/>` 标签要将实体类序列化
  - 当一级缓存会话提交或者关闭才会放到二级缓存中

### 14.5 自定义缓存 - ehcache

- 包

  ```xml
  <dependency>
      <groupId>org.mybatis.caches</groupId>
      <artifactId>mybatis-ehcache</artifactId>
      <version>1.2.1</version>
  </dependency>
  ```

- ehcache.xml

  ```xml
  <?xml version="1.0" encoding="UTF-8" ?>
  <ehcache xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xsi:noNamespaceSchemaLocation="http://ehcache.org/ehcache.xsd"
           updateCheck="false">
      <diskStore path="./tmpdir/Tmp_EhCache"/>
      <defaultCache
          eternal="false"
          maxElementsInMemory="10000"
          overflowToDisk="false"
          diskPersistent="false"
          timeToIdleSeconds="1800"
          timeToLiveSeconds="259200"
          memoryStoreEvictionPolicy="LRU"/>
      
      <cache
          name="cloud_user"
          eternal="false"
          maxElementsInMemory="5000"
          overflowToDisk="false"
          diskPersistent="false"
          timeToIdleSeconds="1800"
          timeToLiveSeconds="1800"
          memoryStoreEvictionPolicy="LRU"/>
  </ehcache>
  ```

- defaultCache：默认缓存策略，只能定义一个

- name：缓存名称

- maxElementsInMemory：缓存最大数目

- maxElementsOnDisk：硬盘最大缓存个数

- eternal：是否永久有效（设置后 timeout 失效）

- overflowToDisk：是否保存到硬盘

- timeToIdleSeconds：对象失效前允许的闲置时间

- timeToLiveSeconds：失效前允许存活时间

- diskPersistence：是否缓存虚拟机重启期数据

- diskSpoolBufferSizeMB：磁盘缓存大小（默认 30）

- diskExpiryThreadIntervalSeconds：磁盘失效线程运行时间间隔（默认 120 s）

- memoryStoreEvictionPolicy：清理缓存策略

- clearOnFlush：内存数量最大时是否清除

