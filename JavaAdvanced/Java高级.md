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

- 动态执行 javascript 代码 - Narshorn

  - 定义引擎

    ```java
    ScriptEngineManager manager = new ScriptEngineManager();
    ScriptEngine engine = manager.getEngineByName("javascript");
    ```

  - 定义和调用共用变量

    ```java
    engine.put("msg","hello javascript");
    System.out.println(engine.get("msg"));
    ```

  - 定义内部变量

    ```java
    String str = "var user = {name:'abc',age:18};";
    str+="print(user.name)";
    engine.eval(str);
    ```

  - 定义和调用函数

    ```java
    engine.eval("function add(a,b){return (a+b);}");
    Invocable jsInvoke = (Invocable)engine;
    Object res = jsInvoke.invokeFunction("add",new Object[]{10,20});
    System.out.println(res);
    ```

  - 导入 Java 类

    ```java
    String jsCode = "var clazz = Java.type(\"java.util.Arrays\"); var list = clazz.asList([\"a\",\"b\",\"c\"]);";
    engine.eval(jsCode);
    List<String> list= (List<String>)engine.get("list");
    list.forEach(System.out::println);
    ```

  - 执行 JavaScript 脚本文件

    ```java
    String path = DemoJS.class.getClassLoader().getResource("").getPath();
    FileReader reader = new FileReader(new File(path+"test.js"));
    engine.eval(reader);
    reader.close();
    ```

- 动态字节码操作

  - Java 动态性实现方式
    - 字节码操作
    - 反射
  - 运行时操作字节码可以实现以下功能
    - 动态生成新的类
    - 动态改变某个类的结构
  - 优势
    - 比反射性能高
    - javassist 性能比 asm 低
  - 库
    - BCEL
    - ASM
    - CGLIB
    - Javassist

## 3. JVM

- 类加载全过程

  - 类加载机制

    JVM 把 class 文件加载到内存，并对数据进行校验，解析，初始化，最终形成 JVM 可以直接使用的 Java 类型的过程

    - 加载

      将 class 文件字节码内容加载到内存中，并将这些静态数据转换成方法区中的运行时数据结构，在一个堆中生成一个代表这个类 java.lang.Class 对象，作为方法去类数据访问的接口，这个过程需要类加载器的参与

    - 链接

      - 验证

        确保加载的类信息符合 JVM 规范，没有安全方面的问题

      - 准备

        正式为类变量 (static 变量) 分配内存并设置类变量初始值的阶段，这些内存都将在方法区中分配

      - 解析

        虚拟机常量池内存的符号引用替换为直接引用过程

    - 初始化

      - 初始化阶段是执行类构造器 `<clinit>()` 方法的过程，类构造器 `<clinit()>` 方法是由编译器自动收集类中的所有类变量的赋值动作和静态语句块中的语句合并产生的
      - 当初始化一个类的时候，如果发现其父类还没有进行过初始化，则需要先触发其父类的初始化
      - 虚拟机会保证一个类的`<clinit>()` 方法在多线程环境中被正确加锁和同步
      - 当访问一个 Java 类的静态域时，只有其真正声明这个域的类才会被初始化

    - 使用

    - 卸载

