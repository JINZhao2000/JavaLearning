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

### 4.1 核心配置文件

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

      

