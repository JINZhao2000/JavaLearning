# SpringAOP

## 1. AOP 介绍

### 1.1 什么是AOP

------------

Aspect Oriented Programming 面向切面编程

通过对业务逻辑的各个部分进行隔离，降低了耦合度，提高了可重用性

AOP 采用横向抽取机制，取代了纵向继承

应用：事务管理、性能监视、安全检查、缓存、日志等

###  1.2 AOP 实现原理

----------

代理模式实现程序功能统一维护

接口 + 实现类：Spring 采用 jdk 动态代理 Proxy

实现类：Spring 采用 cglib 字节码增强

### 1.3 术语

------------

__Target__：目标类，需要被代理的类

​	例如：UserService

__JoinPoint__：连接点，指可能被拦截到的方法

​	例如：UserService 的所有方法

__PointCut__：切入点，已经被增强的连接点

​	例如：addUser()

__Advice__：通知/增强，拦截到 JoinPoint 后做的事

​	有前置通知，后置通知，异常通知，最终通知，环绕通知

​	例如：after()，before()

__Weaving__：织入/植入，把增强 advice 应用到目标对象 target 来创建新的代理对象 Proxy 的过程

__Proxy__：代理类

__Aspect__：切面，是切入点 PointCut 和通知 advice 的结合

​	一个线是一个特殊的面

​	__->__ 一个切入点和一个通知组合成一个特殊的面

## 2 手动方式

### 2.1 JDK 动态代理

----

JDK 动态代理对 “装饰者” 设计模式简化，使用前提：必须有接口

#### 目标类：接口+实现类

```java
public interface UserService {
	void addUser();
	void updateUser();
	void deleteUser();
}
```

```java
public class UserServiceImpl implements UserService{
	@Override
	public void addUser () {
		System.out.println("JDK Proxy addUser");
	}
	@Override
	public void updateUser () {
		System.out.println("JDK Proxy updateUser");
	}
	@Override
	public void deleteUser () {
		System.out.println("JDK Proxy deleteUser");
	}
}
```

#### 切面类：用于存通知

```java
public class MyAspect {
	public void before(){
		System.out.println("before");
	}
	public void after(){
		System.out.println("after");
	}
}
```

#### 工厂类：编写工厂去生成代理

```java
public class MyBeanFactory {
	public static UserService createService(){
		//target
		final UserService userService = new UserServiceImpl();
		//aspect
		final MyAspect myAspect = new MyAspect();
		//proxy
		UserService proxyService = (UserService) Proxy.newProxyInstance(
				MyBeanFactory.class.getClassLoader(),//ClassLoader
				//1. Factory.class.getClassLoader();
				//2. AImpl.getClass().getClassLoader();
				new Class[]{UserService.class},//interfaces the proxy implement
				//1. AImpl.getClass().getInterfaces(); can only catch its owns interfaces (not parent's interfaces)
				//2. new Class[]{A.class}
				new InvocationHandler() {
					@Override
					public Object invoke (Object proxy, Method method, Object[] args) throws Throwable {
						//final needed
						//before
						myAspect.before();
						//method.getName()
						//method.invoke(Object object,Object[] args) of Impl
						Object obj = method.invoke(userService,args);
						//after
						myAspect.after();
						return obj;
					}
					//invoke(this,addUser,[])
				}
				//invocationHandler -> unnamed inner class
				//when every method runs, will call the method invoke()
		);
		return proxyService;
	}
}
```

#### 测试

```java
public class testJDK {
	@Test
	public void demo01(){
		UserService userService = MyBeanFactory.createService();
		userService.addUser();
		userService.updateUser();
		userService.deleteUser();
	}
}
```

### 2.2 CGLIB 字节码增强

-------

没有接口只有实现类

采用字节码增强框架 CGLIB

​	在运行时，创建目标类的子类，从而对目标类进行增强

#### 导入 jar 包

```groovy
    // https://mvnrepository.com/artifact/cglib/cglib
    compile group: 'cglib', name: 'cglib', version: '3.3.0'
    // https://mvnrepository.com/artifact/org.ow2.asm/asm
    compile group: 'org.ow2.asm', name: 'asm', version: '9.0'
```

spring-core.jar 已经整合以上两个内容

```groovy
	// https://mvnrepository.com/artifact/org.springframework/spring-core
	compile group: 'org.springframework', name: 'spring-core', version: '5.2.6.RELEASE'
```

#### 工厂类

```java
public class MyBeanFactory {
	public static UserServiceImpl createService(){
		//target
		final UserServiceImpl userServiceImpl = new UserServiceImpl();
		//aspect
		final MyAspect myAspect = new MyAspect();
		//proxy
		//1. Enhancer
		Enhancer enhancer = new Enhancer();
		//2. choose the parent's class
		enhancer.setSuperclass(userServiceImpl.getClass());
		//3 set callback === invocationHandler
		enhancer.setCallback(new MethodInterceptor() {
			@Override
			// intercept === invoke
			//proxy method args methodProxy
			public Object intercept (Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
				//before
				myAspect.before();
				//execute target method
				Object obj = method.invoke(userServiceImpl,objects);
				//execute the parent of proxy
				//methodProxy.invokeSuper(o,objects);
				//after
				myAspect.after();
				return obj;
			}
		});
		//4. create proxy
		UserServiceImpl proxyService = (UserServiceImpl) enhancer.create();
		return proxyService;
	}
}
```

## 3 AOP 联盟通知类型

__org.aopalliance.aop.Advice__

### 3.1 前置通知

​	org.springframework.aop.MethodBeforeAdvice

​	目标方法前执行的通知

### 3.2 后置通知

​	org.springframework.aop.AfterReturningAdvice

​	目标方法后执行的通知

### 3.3 环绕通知

​	org.aopalliance.intercept.MethodInterceptor

​	目标方法前后执行的通知

​	必须手动执行目标方法

### 3.4 异常抛出通知

​	org.springframework.aop.ThrowsAdvice

​	抛出异常的方法后执行的通知 catch 块中

### 3.5 引介通知

​	org.springframework.aop.IntroductionInterceptor

​	在目标类中添加方法和属性

## 4 Spring 代理，半自动

​	Spring 创建代对象，从 Spring 容器中手动获取代理对象

​	导 jar 包 4 core + 1 dependency + aop alliance (规范) + Spring AOP

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
    // https://mvnrepository.com/artifact/aopalliance/aopalliance
    compile group: 'aopalliance', name: 'aopalliance', version: '1.0'
    // https://mvnrepository.com/artifact/org.springframework/spring-aop
    compile group: 'org.springframework', name: 'spring-aop', version: '5.2.6.RELEASE'
```

Spring 配置

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="userService" class="com.ayy.springaop.semi_automatic.UserServiceImpl"/>
    <bean id="myAspect" class="com.ayy.springaop.semi_automatic.MyAspect"/>

    <bean id="proxyService" class="org.springframework.aop.framework.ProxyFactoryBean">
        <property name="interfaces" value="com.ayy.springaop.semi_automatic.UserService"/>
        <property name="target" ref="userService"/>
        <property name="interceptorNames" value="myAspect"/>
        <property name="optimize" value="true"/><!--强制使用cglib-->
    </bean>
</beans>
```

测试

```java
public class testSpringSemiAuto {
	@Test
	public void demo01(){
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("ApplicationContext1.xml");
		UserService userService = (UserService) applicationContext.getBean("proxyService");
		userService.addUser();
		userService.updateUser();
		userService.deleteUser();
	}
}
```

## 5 Spring AOP 自动代理，全自动

从 Spring 容器获得目标类，如果配置了 aop Srping 将自动生成代理

要确定目标类，用 AspectJ 的切入点表达式，导入 jar 包

```groovy
    // https://mvnrepository.com/artifact/org.aspectj/aspectjweaver
    runtime group: 'org.aspectj', name: 'aspectjweaver', version: '1.9.5'
```

导入命名空间

```xml
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
                           http://www.springframework.org/schema/beans/spring-beans.xsd
                           http://www.springframework.org/schema/aop
                           http://www.springframework.org/schema/aop/spring-aop.xsd">
```

advisor：一个通知和一个切入点

​	advice-ref：通知引用

​	pointcut-ref：切入点引用

execution(\* package.\*.\*(..))

return package.class.method(args)

Spring 配置

```xml
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
                           http://www.springframework.org/schema/beans/spring-beans.xsd
                           http://www.springframework.org/schema/aop
                           http://www.springframework.org/schema/aop/spring-aop.xsd">
    <bean id="userService" class="com.ayy.springaop.automatic.UserServiceImpl"/>
    <bean id="myAspect" class="com.ayy.springaop.automatic.MyAspect"/>
    <aop:config proxy-target-class="true"> <!--cjlib-->
        <aop:pointcut id="myPointCut" expression="execution(* com.ayy.springaop.automatic.*.*(..))"/>
        <aop:advisor advice-ref="myAspect" pointcut-ref="myPointCut"/>
    </aop:config>
</beans>
```

## 6 AspectJ

### 6.1 什么是 AspectJ

----

基于 Java 语言的 AOP 框架

Spring 2.0 对 AspectJ 切入点表达式支持

@AspectJ (since AspectJ 1.5) -> @Aspect

主要用途：自定义开发

### 6.2 切入点表达式

----

#### execution() 用于描述方法

语法：execution(修饰符，返回值，包.类.方法(参数) throws 异常)

​	__修饰符__，一般省略

​		public 公共方法

​		\* 任意

​	__返回值__，不能省略

​		void 没有返回值

​		String 返回字符串

​		\* 任意

​	__包__，[省略]

​		com.ayy	固定包

​		com.ayy.*.service	ayy 下任意子包的 service

​		com.ayy..	ayy 包下面自己和所有子包

​		com.ayy.*.service..	ayy 下任意子包的 service 下自己和所有子包

​	__类__，[省略]

​		UserService	指定类

​		*Impl	以 Impl 结尾

​		User*	以 User 开头

​		\*	任意

​	__方法__，不能省略

​		addUser	固定方法

​		add*	以 add 开头

​		*Do	以 Do 结尾

​		\*	任意

​	__参数__

​		()	无参

​		(int)	一个 int

​		(int, int)	两个 int

​		(..)	可变参数

​	__throws__，可省略，一般不写

__综合 1__

​	execution(* com.ayy.\*.service..\*.\*(..))

__综合 2__

```xml
<aop:pointcut expression="execution(* com.ayy.*Do.*WithCommit(..)) ||
                          execution(* com.ayy.*Service.*(..))" id="myPointCut"/>
```

#### within() 匹配包或子包中的方法

​	within(com.ayy.aop.*)

#### this() 匹配实现接口代理对象中的方法

​	this(com.ayy.aop.user.UserDao)

#### target() 匹配实现接口的目标对象中的方法

​	target(com.ayy.aop.user.UserDao)

#### args() 匹配参数格式符合标准的方法

​	args(int, int)

#### bean() 对指定的 bean 中的所有方法

​	bean("userService")

### 6.3 AspectJ 通知类型

----

aop 联盟定义的通知类型具有特性接口，必须实现，从而确定方法名称

AspectJ 通知类型只定义类型名称，以及方法的格式

个数：6种

#### before 前置通知 - 校验

​	在方法执行前执行，如果抛出异常，阻止方法运行

#### afterReturning 后置通知 - 常规数据处理

​	方法正常返回后执行，如果方法中抛出异常，通知无法执行

​	必须在方法执行后才执行，所以可以获得方法的返回值

#### around 环绕通知 - 十分强大，可以做任何事

​	方法执行前后分别执行，可以阻止方法的执行

​	必须手动执行目标方法

#### afterThrowing 抛出异常通知 - 包装异常信息

​	方法抛出异常后执行，如果方法没有抛出异常，无法执行

#### after 最终通知 - 清理现场

​	方法执行完毕后执行，无论方法是否出现异常

```java
try{
    //before
    //method
    //afterReturning
}catch{
    //afterThrowing
}finally{
    //after
}
```

### 6.4 导入 jar 包

----

​	aopalliance

​	spring-aop

​	aspect weaver

​	spring aspect

```groovy
	compile group: 'aopalliance', name: 'aopalliance', version: '1.0'
    // https://mvnrepository.com/artifact/org.springframework/spring-aop
    compile group: 'org.springframework', name: 'spring-aop', version: '5.2.6.RELEASE'
    // https://mvnrepository.com/artifact/org.aspectj/aspectjweaver
    runtime group: 'org.aspectj', name: 'aspectjweaver', version: '1.9.5'
    // https://mvnrepository.com/artifact/org.springframework/spring-aspects
    compile group: 'org.springframework', name: 'spring-aspects', version: '5.2.6.RELEASE'
```

--AspectJMethodBeforeAdvice

--AspectJAfterReturningAdvice

--AspectJAroundAdvice

--AspectJAfterThrowingAdvice

```java
	@Override
	public Object invoke(MethodInvocation mi) throws Throwable {
		try {
			return mi.proceed();
		}
		catch (Throwable ex) {
			if (shouldInvokeOnThrowing(ex)) {
				invokeAdviceMethod(getJoinPointMatch(), null, ex);
			}
			throw ex;
		}
	}
```

--AspectJAfterAdvice

```java
@Override
	public Object invoke(MethodInvocation mi) throws Throwable {
		try {
			return mi.proceed();//手动执行
		}
		finally {
			invokeAdviceMethod(getJoinPointMatch(), null, null);
		}
	}

```

### 6.5 基于 XML

----

目标类：接口+实现

切面类：编写多个通知，采用 AspectJ 通知名称任意（方法名任意）

```java
public class MyAspect {

	public void myBefore(JoinPoint joinPoint){
		System.out.println("AspectJ XML Before "+joinPoint.getSignature().getName());
	}

	public void myAfterReturning(JoinPoint joinPoint,Object object){
		System.out.println("AspectJ XML AfterReturning "
                           +joinPoint.getSignature().getName()+",-> "+object);
	}

	public Object myAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
		System.out.println("--before--");
		//execute manually
		Object obj = proceedingJoinPoint.proceed();
		System.out.println("--after--");
		return obj;
	}
    
	public void myAfterThrowing(JoinPoint joinPoint,Throwable throwable){
		System.out.println("AspectJ XML AfterThrowing "
                           +joinPoint.getSignature().getName());
		System.out.println(throwable.getMessage());
	}
    
	public void myAfter(JoinPoint joinPoint){
		System.out.println("AspectJ XML After "+joinPoint.getSignature().getName());
	}
}
```

aop 编程，将通知应用到目标类

```xml
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
                           http://www.springframework.org/schema/beans/spring-beans.xsd
                           http://www.springframework.org/schema/aop
                           http://www.springframework.org/schema/aop/spring-aop.xsd">
    <bean id="userService" class="com.ayy.springaop.aspectjXml.UserServiceImpl"/>
    <bean id="myAspect" class="com.ayy.springaop.aspectjXml.MyAspect"/>
    <aop:config>
        <aop:aspect ref="myAspect">
            <aop:pointcut id="myPointCut" 
                          expression=
            "execution(* com.ayy.springaop.aspectjxml.UserServiceImpl.*(..))"/>
            <aop:before method="myBefore" pointcut-ref="myPointCut"/>
            <aop:after-returning method="myAfterReturning" pointcut-ref="myPointCut" returning="object"/>
            <!--arg1 pointcut arg2 Object -> returning-->
            <aop:around method="myAround" pointcut-ref="myPointCut"/>
            <aop:after-throwing method="myAfterThrowing" pointcut-ref="myPointCut" throwing="throwable"/>
            <!--arg1 pointcut arg2 Throwable throwable-->
            <aop:after method="myAfter" pointcut-ref="myPointCut"/>
        </aop:aspect>
    </aop:config>
</beans>
```

测试

### 6.6 基于注解

----

替换 bean

```java
@Service("userService")
public class UserServiceImpl implements UserService {
	@Override
	public void addUser () {
		System.out.println("AspectJ Anno addUser");
	}

	@Override
	public String updateUser () {
		System.out.println("AspectJ Anno updateUser");
		return "testReturning";
	}

	@Override
	public void deleteUser () {
		System.out.println("AspectJ Anno deleteUser");
	}
}
```

扫描注解类 bean 注解+aop 注解

```xml
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
                           http://www.springframework.org/schema/beans/spring-beans.xsd
                           http://www.springframework.org/schema/aop
                           http://www.springframework.org/schema/aop/spring-aop.xsd
                           http://www.springframework.org/schema/context
                           http://www.springframework.org/schema/context/spring-context.xsd">
    <context:component-scan base-package="com.ayy.springaop.aspectjAnno"/>
    <aop:aspectj-autoproxy/>
</beans>
```

替换 aop

​	替换切面类

​	替换公共切入点

​	替换通知

```java
@Component
@Aspect
public class MyAspect {
	@Pointcut("execution(* com.ayy.springaop.aspectjAnno.UserServiceImpl.*(..))")
	private void myPointCut(){}

	@Before(value = "myPointCut()")
	public void myBefore(JoinPoint joinPoint){
		System.out.println("AspectJ Anno Before "+joinPoint.getSignature().getName());
	}

	@AfterReturning(value = "myPointCut()",returning = "object")
	public void myAfterReturning(JoinPoint joinPoint,Object object){
		System.out.println("AspectJ Anno AfterReturning "+joinPoint.getSignature().getName()+",-> "+object);
	}

	@Around(value = "myPointCut()")
	public Object myAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
		System.out.println("--before--");
		//execute manually
		Object obj = proceedingJoinPoint.proceed();
		System.out.println("--after--");
		return obj;
	}

	@AfterThrowing(value = "myPointCut()",throwing = "throwable")
	public void myAfterThrowing(JoinPoint joinPoint,Throwable throwable){
		System.out.println("AspectJ Anno AfterThrowing "+joinPoint.getSignature().getName());
		System.out.println(throwable.getMessage());
	}

	@After(value = "myPointCut()")
	public void myAfter(JoinPoint joinPoint){
		System.out.println("AspectJ Anno After "+joinPoint.getSignature().getName());
	}
}
```

总结

@Aspect 声明切面，修饰切面类，从而获得通知

通知

​	@Before

​	@AfterReturning

​	@Around

​	@AfterThrowing

​	@After

切入点

​	@PointCut -> private void name(){}