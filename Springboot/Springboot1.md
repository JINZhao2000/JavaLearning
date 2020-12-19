# Spring Boot 入门

## Spring Boot 简介

- 简化 Spring 应用开发的框架

- 基于整合 Spring 技术栈的一个大整合

- JEE 开发的一站式解决方式

## 微服务 - Martin Fowler

微服务是一种架构风格，它是一系列小服务的组合，每个服务运行在各自的进程内，通过 HTTP 的方式进行互通

单体应用（All In One）开发，测试，部署，水平扩展简单

微服务，每一个功能元素最终都是一个可独立替换，独立升级的软件单元

[微服务文档](https://martinfowler.com/articles/microservices.html)

## HelloWorld

 <kbd>@SpringBootApplication</kbd>来标注一个主程序类，说明这是一个 SpringBoot 主配置类，SpringBoot 就应该运行这个类的 main 方法来启动 SpringBoot 应用

```java
@SpringBootApplication
public class HelloWorld {
    public static void main (String[] args) {
        SpringApplication.run(HelloWorld.class,args);
    }
}
```

<kbd>@SpringBootApplication</kbd>

```java
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan(excludeFilters = { @Filter(type = FilterType.CUSTOM, classes = TypeExcludeFilter.class),
		@Filter(type = FilterType.CUSTOM, classes = AutoConfigurationExcludeFilter.class) })
public @interface SpringBootApplication {...}
```

<kbd>@SpringBootConfiguration</kbd> SpringBoot 配置类

- 标注在某个类上，表示这是一个 SpringBoot 的配置类
- <kbd>@Configuration</kbd> 配置类上来标注这个注解
  - 配置类 <-> 配置文件
  - <kbd>Component</kbd> 

<kbd> @EnableAutoConfiguration</kbd> 开启自动配置功能

- 以前我们需要配置东西，SpringBoot 帮我们自动配置，该注解告诉 SpringBoot 开启自动配置功能，这样自动配置才能生效

- ```java
  @AutoConfigurationPackage
  @Import(AutoConfigurationImportSelector.class)
  public @interface EnableAutoConfiguration {...}
  ```

  - <kbd>@AutoConfigurationPackage</kbd> 自动配置包

    - ```java
      @Import(AutoConfigurationPackages.Registrar.class)
      ```

      Spring 底层注解<kbd>@Import</kbd>，给容器中导入一个组件，导入的组件由 __AutoConfigurationPackages.Registrar.class__ 

      ```java
      static class Registrar implements ImportBeanDefinitionRegistrar, DeterminableImports {
      
      	@Override
      	public void registerBeanDefinitions(AnnotationMetadata metadata, 	BeanDefinitionRegistry registry) {
      	register(registry, new PackageImports(metadata).getPackageNames().toArray(new String[0]));
      	}
          ...
      }
      ```

      将主配置类 <kbd>@SpringBootApplication</kbd> 标注的类的==所在包及下面所有子包==里面的所有组件扫描到 Spring 容器

    - ```java
      @Import(AutoConfigurationImportSelector.class)
      ```

      给容器中导入组件

      ```java
      @Override
      public String[] selectImports(AnnotationMetadata annotationMetadata) {
      	if (!isEnabled(annotationMetadata)) {
      		return NO_IMPORTS;
      	}
      	AutoConfigurationEntry autoConfigurationEntry = getAutoConfigurationEntry(annotationMetadata);
      	return StringUtils.toStringArray(autoConfigurationEntry.getConfigurations());
      }
      ```

      将所有需要导入的组件以全类名的方式返回，这些组件就会被添加到容器中
      
      会给容器中导入非常多的自动配置类（xxxAutoConfiguration）
      
      就是给容器中导入这个场所需要的所有组件，并配置好这些组件
      
      免去手动编写，配置注入功能组件等功能
      getCandidateConfigurations(..)
      
      ```java
      SpringFactoriesLoader.loadFactoryNames(getSpringFactoriesLoaderFactoryClass(),getBeanClassLoader());
      ```
      
      SpringFactoriesLoader.loadFactoryNames(..)
      
      ```java
      public final class SpringFactoriesLoader {
      	public static List<String> loadFactoryNames(Class<?> factoryType, @Nullable ClassLoader classLoader) {
      		String factoryTypeName = factoryType.getName();
      		return loadSpringFactories(classLoader).getOrDefault(factoryTypeName, Collections.emptyList());
      	}
      }
      ```
      
      SpringBoot 启动的时候从类路径下的 META-INF/spring.factories 中获取 EnableAutoConfiguration 指定的值，将这些值作为自动配置类导入到容器中，自动配置类生效，帮我们进行自动配置工作
      
      ```java
      public static final String FACTORIES_RESOURCE_LOCATION = "META-INF/spring.factories";
      ```
      
      getSpringFactoriesLoaderFactoryClass()
      
      ```java
      protected Class<?> getSpringFactoriesLoaderFactoryClass() {
      	return EnableAutoConfiguration.class;
      }
      ```
      
      getBeanClassLoader()
      
      ```java
      protected ClassLoader getBeanClassLoader() {
      	return this.beanClassLoader;
      }
      ```
      
      

__Controller__

```java
@Controller
public class HelloController {

    @ResponseBody
    @RequestMapping("/hello")
    public String hello(){
        return "Hello World!";
    }
}
```

__运行主程序测试__

Settings->Build->Build Tools->Maven->Runner->Delegated IDE build/run actions to Maven

__打成 jar 包__

```xml
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-maven-plugin</artifactId>
</dependency>
```

添加META-INF 和 lib

```xml
<build>
        <finalName>${project.artifactId}</finalName><!--修改编译出来的jar包名，仅为{artifactId}.jar-->
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <fork>true</fork><!-- true:会将项目依赖的jar添加到打包的lib目录 false:是一个不包含依赖jar的包 -->
                    <mainClass>com.ayy.s01helloworld.HelloWorld</mainClass>
                </configuration>
                <executions>
                    <execution>
                        <goals>
                            <goal>repackage</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
```

__pom.xml__ 项目顶级父类依赖

```xml
<parent>
	<artifactId>spring-boot-starter-parent</artifactId>
	<groupId>org.springframework.boot</groupId>
	<version>2.3.3.RELEASE</version>
</parent>
```

__SpringBoot 版本依赖__

```xml
<parent>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-dependencies</artifactId>
	<version>2.3.3.RELEASE</version>
</parent>
```

__jar 来源__

```xml
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

spring-boot-starter-__web__

- spring-boot-starter：SpringBoot 场景启动器，帮助我们导入了 web 模块正常运行所依赖的组件
- SpringBoot 将所有的功能场景抽取出来，做成一个个的 starter，只需要在项目里面引入这些 starter 相关场景的所有依赖都会导入进来，要用什么功能就导入什么启动器

