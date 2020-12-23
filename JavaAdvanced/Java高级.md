# Java 高级

## 1. 注解

- 注解入门 @since jdk1.5

  - 注解的作用
    - 不是程序本身，对程序做出某种解释
    - 可以被其他程序读取
  - 注解的格式
    - @注释名(参数值)
  - 注解的位置
    - package
    - class
    - method
    - field

- 内置注解

  - @Override

    java.lang.Override

    重写超类方法声明

  - @Deprecated

    java.lang.Deprecated

    不鼓励使用的方法，属性，类，它存在更好的选择

  - @SuppressWarnings

    java.lang.SuppressWarnings

    抑制编译时的警告信息

    - 参数

      deprecation：过时的类或方法的警告

      unchecked：未检查的转换警告 - 比如集合未使用泛型

      fallthrough：在 switch 语句发生 case 穿透

      path：在类路径，源文件路径等中不存在路径警告

      serial：当在可序列化的类上缺少 serialVersionUID 定义时的警告

      finally：任何 finally 子句不能完成时的警告

      all：所有警告

    - 使用

      @SuppressWarnings("all")

      @SuppressWarnings(value={"deprecation", "unchecked"})

- 自定义注解

  @interface 来声明一个注解

  ​	public @interface name{body}

  每一个方法实际上是一个配置参数

  - 方法的名称就是参数名称
  - 返回值类型就是参数的类型（返回值类型只能是基本类型，Class，String，Enum）
  - 可以通过 default 来声明参数的默认值 -1 代表不存在
  - 如果只有一个参数成员，一般参数名为 value

- 元注解

  - @Target

    描述作用范围

    - PACKAGE：包
    - TYPE：类，接口，枚举，Anno 类型
    - CONSTRUCTOR：构造器
    - FIELD：域
    - METHOD：方法
    - LOCAL_VARIABLE：局部变量
    - PARAMETER：参数

    @Target(value=ElementType.PACKAGE)

  - @Retention

    注解的生命周期

    - SOURCE：源文件中有效
    - CLASS：在 class 文件有效
    - RUNTIME：在运行时youxiao

    @Retention(RetentionPolicy.RUNTIME)

  - @Documented

  - @Inherited

- 反射获取注解 ORM

## 2. 反射

- 反射机制

  - 可以于运行时加载，探知，使用编译期间完全未知的类

  - 程序运行中，可以动态加载一个只有名称的类，对于任何一个已加载的类，都能够知道这个类的所有属性和方法，对于任何一个对象，都可以调用它的任意一个属性

    Class c = Class.forName("....");

  - 加载完类后，在堆的内存中，就产生了一个 Class 类型的对象，包含了类的完整结构的类型，称为反射

  - Class 类 表示 java 中类型本身 class/interface/enum/annotation/primitive type/void

    - Class 类的对象包含某个被加载类的结构，一个被加载类对应一个 Class 对象
    - 当一个 class 被加载时，或当加载器 classloader 的 defineClass() 被 JVM 调用，JVM 便自动产生一个 Class 对象

  - 反射操作泛型

    - ParameterizedType：表示一种参数化类型，比如 Collection\<String\>
    - GenericArrayType：表示一种元素类型是参数化类型或者类型变量的数组类型
    - TypeVariable：是各种类型变量的公共父接口
    - WildcardType：代表一种通配符表达式，比如 ?, ? extends Number, ? super Integer

  - 反射操作注解（Runtime）

    - c.getAnnotations();
    - c.getAnnotation(AnnoClass.class);
    - f.getAnnotation(FieldClass.class);

  - setAccessible

    - 启用和禁用访问的安全检查开关，禁止安全检查可以提高反射的运行速度

- 动态编译

  - 场景

    - 可以做一个浏览器端写 java 代码，上传服务器编译和运行的在线测评系统

    - 服务器动态加载某些类文件进行编译

  - 动态编译的两种做法

    - 通过 Runtime 调用 javac，启动新的线程去操作

      ```java
      Runtime run = Runtime.getRuntime();
      Process process = run.exec("javac -cp /filepath/ file.java");
      ```

    - 通过 JavaCompiler 动态编译

      ```java
      JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
      int result = compiler.run(null,null,null,sourceFile);
      ```

      - 第一个参数：为 java 编译器提供的参数
      - 第二个参数：得到 java 编译器的输出信息
      - 第三个参数：接收编译器的错误信息
      - 第四个参数：可变参数 string[] 能传入一个或多个源文件
      - 返回值：0 表示编译成功，非 0 表示编译失败

  - 动态执行编译好的文件

    - Runtime
    - 反射

- 动态执行 javascript 代码

- 动态字节码操作

