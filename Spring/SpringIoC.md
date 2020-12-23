# SpringIoC

## 1. Spring 核心容器

4 核心 + 1 依赖

Beans Core Context Expression + Commons-logging

```groovy
    compile group: 'org.springframework', name: 'spring-core', version: '5.2.6.RELEASE'
    // https://mvnrepository.com/artifact/org.springframework/spring-context
    compile group: 'org.springframework', name: 'spring-context', version: '5.2.6.RELEASE'
    // https://mvnrepository.com/artifact/org.springframework/spring-expression
    compile group: 'org.springframework', name: 'spring-expression', version: '5.2.6.RELEASE'
    // https://mvnrepository.com/artifact/org.springframework/spring-beans
    compile group: 'org.springframework', name: 'spring-beans', version: '5.2.6.RELEASE'
    // https://mvnrepository.com/artifact/commons-logging/commons-logging
    compile group: 'commons-logging', name: 'commons-logging', version: '1.2'
```

## 2. IoC

目标类

​	提供 UserService 接口+实现类

​	获得 UserService 实现类实例

​		Spring 创建对象实例 -> IoC 控制反转 Inverse of Control

```java
// 普通创建对象方式
Object o = new Object;

// Spring 解耦
Object 0 = ...Factory.getBeans();
```

​		实例对象从 Spring 工厂（容器）中获得

配置文件

​	位置 任意，开发时在 src 下

​	名称 任意，开发时用 ApplicationContext.xml

​	内容 添加 schema 约束

​		Schema 命名空间

​			  1、命名空间声明

​				默认： xmlns=""        \<标签名\> -> \<bean\>

​				显示： xmlns:别名=""   \<别名:标签名\> -> \<context:...\>

 			 2、确定 schema xsd 文件位置

​				xsi:schemaLocation="名称1 位置1 名称2 位置2 ..."

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
<beans>
```

## 3. DI

Dependency Injection 依赖注入

​	is a：是一个 -> extends

​	has a：有一个 成员变量

```java
        class B{
            private A a;//B 类依赖于 A 类
        }
```

依赖：一个对象使用另一个对象

注入：通过 setter 方法进行另一个对象的实例设置

```java
    class BookServiceImpl{
        //spring 前 耦合
        //private BookDao bookDao = new BookDaoImp();
        //spring 后 解耦
        //setter
        private BookDao bookDao;
        
        BookService bookService = new BookServiceImpl();//-> IoC <bean>
        BookDao bookDao = new BookDaoImpl();            //-> IoC <bean>
        bookService.setBookDao(bookDao);              //-> DI <property>
    }
```

目标类

​	dao

​		BookDao 接口，实现类

​	service

​		BookService 接口实现类

配置文件

​	\<property\>

​		name -> 变量名

​		ref -> 类名

测试

​	只需要创建 BookService，Spring 会自己去找另一个 bean BookDao

## 4. 核心 API

BeanFactory

​	工厂 -> 生产任意 bean

​	采用延迟加载，第一次 getBean() 才会初始化 Bean

ApplicationContext

​	BeanFactory 子接口

​	国际化处理

​	事件传递

​	Bean 自动装配

​	不同应用层 Context 实现

​	配置文件加载进行对象实例化

​	-ClassPathXmlApplicationContext 用于加载类路径 (src) 下指定 xml

​		加载 xml 运行时位置 -> WEB-INF/classes/...xml
​    -FileSystemXmlApplicationContext 用于加载指定盘符下的 xml

​		加载 xml 运行时位置 -> WEB-INF/...xml

​		JavaWeb 的 ServletContext.getRealPath()

## 4. 装配 Bean 基于 XML

​	实例化方式

1. 默认构造

```xml
<bean id="" class="">
```

2. 静态工厂

​	常用于整合其他框架（工具）

​	静态工厂：生产实例对象，所有方法都是 static

```xml
<bean id="" class="工厂实现类全限定类名" factory-method="静态方法"></bean>
```

3. 实例工厂

​	实例工厂：必须先有工厂实例对象，才能创建对象，所有都是非静态方法

## 5. Bean 的种类
​        普通 bean：以上全书普通 bean <bean id="" class="A"></bean> spring 直接创建实例 A 并返回

​		FactoryBean：是一个特殊的 Bean，具有工厂生产对象的能力，只能生产特定的对像

​			bean 必须实现 FactoryBean 接口，此接口提供了 getObject() 方法用于获得特定 bean
​            \<bean id="" class="FB"\> 先创建FB实例，然后反调用 getObject() 方法并返回方法的返回值

​				- FB fb = new FB();

​				- return fb.getObject();

BeanFactory 和 FactoryBean 对比

​	BeanFactory：工厂，用于生成任意 bean

​	FactoryBean：特殊 bean，用于生成另一个特定的 bean ex：ProxyFactoryBean，此工厂 bean 用于生产代理

​		\<bean id="" class="....ProxyFactoryBean"\> 获得代理对象实例 -> AOP使用

## 6. 作用域

​	用于确定 spring 创建 bean 实例的个数的

​	__singleton__	在 Spring IoC 容器中仅存在一个 Bean 实例，Bean 以单例方式存在，默认值

​	__prototype__	每次调用 getBean() 时都返回一个新的实例 (new xxxBean()) -> struts 整合 spring 配置 action 多例

​	__request__	每次 HTTP 请求都会创建一个新的 Bean，仅适用于 WebApplicationContext

​	__session__	同一个 HTTP Session 共享一个 Bean，不同 Session 使用不同 Bean，仅适用于 WebApplicationContext

​	__globalSession__	一般用于 Portlet 应用环境，仅适用于 WebApplicationContext

## 7. Bean 的生命周期

1. Instantiate Bean 对象实例化

2. Populate properties 封装属性

3. 如果 Bean 实现 BeanNameAware 执行 setBeanName

4. 如果 Bean 实现 BeanFactoryAware 或者 ApplicationContextAware 设置工厂

   ​	setBeanFactory 或者上下文对象 setApplicationContext

5. 如果存在类实现 BeanPostProcessor （后处理 Bean），执行 postProcessBeforeInitialization

6. 如果 Bean 实现 InitializingBean 执行 afterPropertiesSet

7. 调用 \<bean init-method="init"\> 指定初始化方法 init

8. 如果存在类实现 BeanPostProcessor （处理 Bean），执行 postProcessAfterInitialization

9. 执行业务处理

   1. Bean is ready to use
   2. Container is shutdown

10. 如果 Bean 实现 DisposableBean 执行 destroy

11. 调用 \<bean destroy-method="customerDestroy"\> 指定销毁方法 customerDestroy

### 1. 初始化和销毁

----

目标方法执行前，执行后，将进行初始化或销毁

```xml
<bean id="" class="" init-method="初始化方法名" destroy-method="销毁方法名称">
```

目标类

​	Spring 配置

​	测试

​		close()

​		单例  

### 2. BeanPostProcessor 后处理 Bean

----

Spring 的一种机制，只要实现这个接口 BeanPostProcessor，并将实现类提供给 Spring 容器，Spring 容器将自动执行，在初始化之前执行 before()，初始化方法之后执行 after()

​	Factory hook that allows for custom modification of new bean instances,

​		e.g. checking for master interfaces of wrapping them with proxies

​	Spring 提供工厂钩子，用于修改实例对象，可以生成代理对象，是 AOP 底层

```xml
<bean class="">
```

```java
A a = new A();
a = B.before(a);	//-> 将 a 的实例对象传递给后处理 Bean，可以生成代理对象并返回
a.init();
a = B.after(a);
a.addUser();		//生成代理对象，目的在目标方法前后执行（例如：开启事务，提交事务）
a.destroy();
```

1、编写实现类 -> 动态代理

 2、配置

​		Q1：作用于某一个还是所有？

​		-- 所有

​		Q2：如何只作用于一个

​		-- 通过参数2 beanName 进行控制

## 8. 属性依赖注入

### 1. 注入依赖对象装配方式

​	手动装配

​		-使用 xml 配置

​			构造函数注入

​			属性 setter 方法注入

​			接口注入（Spring 不支持）

​		-使用注解

​	自动装配：strusts 和 Spring 整合可以自动装配

​		byType 按类型装配

​		byName 按名称装配

​		constructor 构造装配

​		auto 不确定装配

#### 构造方法

​	constructor-arg

#### setter 方法

​	property

#### P 命令空间

​	对 setter 方法简化，替换 \<property name=""\>

​	在 \<bean p:propertyname="value" p:propertyname-ref="beanname"\>

​	使用前提：必须添加命名空间

#### SpEL

​	对 \<property\>进行统一编程，所有内容都是用value

```xml
<property name="" value="#{}">
```

​	#{常量数}、#{'String'} 要用单引号、#{1e3} -> 1*10^3 科学计数法

​	#{beanId} 另一个 Bean 引用

​	#{beanId.propertyName} 操作属性

​	#{beanId.toString} 执行方法

​	#{T(java.lang.Arrays).toString()} 静态方法

​	运算符

​	正则表达式

​	集合 

### 2. 集合注入

```xml
<property>
Array       <array>
List        <list>
Set         <set>
Map         <map> <entry key="" value="">
Properties  <props> <prop key="String">String</prop>
base data type -> value
referenced data type -> ref
```

## 9. 装备 Bean 基于注解
​	注解：一个类，使用 @注解名

​	开发中：使用注解取代 XML 配置文件

​	前提：添加命名空间，让 Spring 扫描含有注解的类

1. 代替bean

   @Component 取代 <bean class=">

   @Component("id") 取代 <bean id="id" class="">

2. web 开发，提供3个 @Component 注解的衍生注解

   @Repository dao 层

   @Service    service 层

   @Controller web 层

3. 依赖注入，给私有字段或 setter 方法

   普通值：@Value("")

   引用值：

   1. 按照类型注入

      @Autowired

   2. 按照名称注入1

      @Autowired()

      @Qualifer("name")

   3. 按照名称注入2

      @Resource("name")

4. 生命周期

   初始化：@PostConstruct

   销毁：@PreDestroy

5. 作用域

   @Scope("prototype") 多例



## 10. 注解和 XML 混合使用
​	1. 将所有的 bean 配置到 XML 中

​	2. 将所有依赖都使用注解

​		@Autowired

​		默认不生效：要配置 \<context:annotation-config\>

​		\<context:annotation-config\> 和 \<context:component-scan base-package="">

​		一般不一起使用

​		scan 扫描含有注解类 @Compoent 和注入注解自动生效

​		config 只在 xml 和注入注解混合使用时生效  