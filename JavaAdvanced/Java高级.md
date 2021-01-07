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
    
  - 堆，栈，方法区
  
    ex : 
  
    ```java
    public class Demo1 {
        public static void main(String[] args) {
            A a = new A();
        }
    }
    
    class A{
        static int i = 100;
        static {
            i = 200;
            System.out.println("Initialized A");
        }
        public A(){
            System.out.println("Constructor A");
        }
    }
    ```
  
    - 方法区（Demo1 和 A）
      - 静态变量
      - 静态方法
      - 常量池（类名，方法名等）
      - 类的代码
    - 堆
      - java.lang.Class 对象代表方法区的类信息对应的类（Demo1 和 A 的 Class 对象）
      - 调用 new A(); 时产生一个 A 的对象实例，并返回给被指的变量名称
    - 栈
      - main 方法的栈帧
      - 调用 new A(); 时将构造方法压入栈帧
      - 完成后构造方法出栈，然后变量名得到引用对象地址
  
  - 类加载主动引用（一定会发生类的初始化）
  
    - new 一个类的对象
    - 调用类的静态成员（除了 final 常量）和静态方法
    - 使用 java.lang.reflect 包的方法对类进行反射调用
    - 当虚拟机启动，则一定会初始化 main 方法所在的类
    - 当初始化一个类的时候，如果其父类没有被初始化，则初始化其父类
  
  - 类加载被动引用（不会发生类的初始化）
  
    - 当访问一个静态域时，只有真正声明这个域的类才会被初始化
      - 通过子类引用父类静态变量不会导致子类初始化
    - 通过数组定义引用，不会触发此类的初始化
    - 引用常量不会触发此类的初始化（常量在预编译阶段就存入了常量池中了）

- 深入类加载器

  - 类加载器原理

    - 类加载器作用

      将 class 文件字节码内容加载到内存中，并将这些静态数据转换成方法区中的运行时数据结构，在堆中生成一个代表这个类的 java.lang.Class 对象，作为方法区类数据的访问接口

    - 类缓存

      标准的 Java SE 类加载器可以按要求查找类，但一旦某一个类被加载到类加载器中，它将维持（缓存）一段时间，JVM 垃圾回收器可以回收这些对象

  - java.lang.ClassLoader 类介绍

    - 作用

      - java.lang.ClassLoader 类的基本职责就是根据一个指定类的名称，找到或者生成其对应的字节码，然后从这些字节代码中定义出一个 Java 类，即 java.lang.Class 类的一个实例
      - 除此之外，ClassLoader 还负责加载 Java 应用所需的资源，如图像文件和配置文件等

    - 相关方法

      ```java
      ClassLoader getParent(); // 获取父类加载器
      Class<?> loadClass(String name); // 加载名为 name 的类
      Class<?> findClass(String name); // 查找名为 name 的类
      Class<?> findLoadedClass(String name); // 查找名为 name 的已经被加载过的类
      Class<?> defineClass(String name,byte[] b, int off, int len); // 把字节数组 b 中的内容转换为 Java 类
      void resolveClass(Class<?> c); // 链接指定的 Java 类
      // 类的名称 name 参数的值是类的全限定名，内部类用$表示
      ```

  - 类加载器树状结构（组合模式实现）

    - 引导类加载器 bootstrap class loader
      - 它用来加载 Java 核心库（JAVA_HOME/jre/lib/rt.jar，或 sun.boot.class.path 路径下的内容），是用原生代码实现的，并不继承自 java.lang.ClassLoader
      - 加载扩展类和应用程序类加载器，并指定他们的父类加载器
    - 扩展类加载器 extensions class loader
      - 用来加载 Java 的扩展库 （JAVA_HOME/jre/ext/*.jar，或 java.ext.dirs 路径下的内容），Java 虚拟机的实现会提供一个扩展库的目录，该类加载器在此目录里面查找并加载 Java 类
      - 由 sun.misc.Launcher$ExtClassLoader 实现
    - 应用程序类加载器 application class loader
      - 它根据 Java 应用的类路径（classpath，java.class.path 路径））加载类，一般来说，Java 应用的类都是由它来完成加载的
      - 由 sun.misc.Launcher$AppClassLoader 实现
    - 自定义类加载器
      - 开发人员可以通过继承 java.lang.ClassLoader 类的方式实现自己的类加载器，以满足一些特殊的要求
      - 父类是 AppClassLoader

  - 类加载器的代理模式

    - 代理模式

      - 交给其他加载器来加载指定的类

    - 双亲委托机制

      - 就是某个特定的类加载器在接到类加载的请求时，首先将加载任务委托给父类加载器，依次追溯，直到最父类的加载器，如果父类加载器可以完成类加载任务时，就成功返回，只有父类加载器无法完成此加载任务时，才自己去加载

      - 双亲委托机制是为了保证 Java 核心库的类型安全

        这种机制就保证不会出现用户自己能定义 java.lang.Object 类的情况

      - 类加载器除了用于加载类，也是安全的最基本的保障

    - 双亲委托机制是代理模式的一种

      - 并不是所有的类加载器都采用双亲委托机制
      - tomcat 服务器类加载器也使用代理模式，所不同的是它是首先尝试去加载某个类，如果找不到再代理给父类加载器，这与一般的加载器的顺序是相反的

  - 自定义类加载器（文件，网络，加密）

    - 自定义类加载器的流程
      - 首先检查请求类型是否已经被这和个类装载器装在到命名空间中了，如果已经装载，直接返回
      - 委派类加载请求给父类加载器，如果父类加载器能够完成，则返回父类加载器加载的 Class 实例
      - 调用本类加载器的 findClass(...) 方法，试图获取对应的字节码，如果获取到，则调用 defineClass(...) 导入类型到方法区，如果获取不到对应的字节码或者其他原因失败，返回异常给 loadClass(...)，loadClass(...) 转抛异常，终止加载过程
      - 被两个类加载器加载的同一个类，JVM 不认为是同一个类
    - 文件类加载器
    - 网络类加载器
    - 加密解密类加载器（取反，DES 对称加密解密）

  - 线程上下文类加载器

    - 双亲委托机制以及类加载器问题

      - 一般情况下，保证同一个类中所关联的其他类都是由当前类的类加载器所加载的

        比如，Class A 本身早 Ext 下找到，那么他里面 new 出来的一些类也就只能用 Ext 去找了（不会低一个级别），所以有些命名 App 可以找到的，却找不到了

      - JDBC API 他有实现 Driven 的部分，我们的 JDBC API 都是由 Root 或者 Ext 来载入的，但是 JDBC  driver 却是由 Ext 或者 App 来载入的，那么就有可能找不到 Driver 了。在 Java 领域中，其实只要分成这种 API + SPI（Service Provide Interface，特定厂商提供）的，都会遇到此问题

      - 常见的 SPI 有 JDBC，JCE，JNDI，JAXP 和 JBI 等，这些 SPI 的接口由 Java 的核心库来提供，如 JAXP 的 SPI 接口定义包含在 javax.xml.parsers 包中，SPI 的接口是 Java 核心库的一部分，是由引导类加载器来加载的，SPI 实现的 Java 类一般是由系统类加载器来加载的，引导类加载器是无法找到 SPI 实现类的，因为它只加载 Java 的核心库

    - 通常动态加载资源时，有三个 ClassLoader 可以选择

      - system class loader / application class loader
      - 当前类加载器
      - 当前线程类加载器

    - 线程加载类是为了抛弃双亲委托加载链模式

      每个线程都有一个关联上下文的类加载器，如果使用 `new Thread()` 方式生成一个新线程，新线程将继承其父线程的上下文加载器，如果程序对线程上下文类加载器没有任何改动的话，程序中所有的线程将都使用系统类加载器作为上下文加载器

    - ```java
      Thread.currentThread().getContextClassLoader(); // default appclassloader
      Thread.currentThread().setContextClassLoader();
      ```

  - 服务器类加载原理和 OSGi 介绍

    - Open Service Gateway Initative 面向 Java 的动态模块系统，它为开发人员提供了面向服务和基于组件的运行环境，并提供标准的方式用来管理软件的生命周期

    - Eclispe 就是基于 OSGi 构建的

    - 原理

      OSGi 中每个模块（bundle）都包含 Java 包和类，模块可以声明它所依赖的需要导入（import）的其他模块的 Java 包和类（通过 Import-Package），也可以声明导出（export）自己的包和类，供其他模块使用（通过 Export-Package），也就是说需要能够隐藏和共享一个模块中的某些 Java 包和类。这是通过 OSGi 特有的类加载器机制来实现的，OSGi 中的每个模块都有对应的一个类加载器，它负责加载模块自己包含的 Java 包和类，当它需要加载 Java 核心库的类时（以 java 开头的包和类），它会代理给父类加载器（通常是启动类加载器）来完成，当它需要加载所导入的 Java 类时，它会代理给导出此 Java 类的模块来完成加载。模块也可以显式声明某些 Java 包和类，必须由父类加载器来加载，只需要设置系统属性 org.osgi.framework.bootdelegation 的值即可

    - Equinox：OSGI 的一个实现

## 4. 正则表达式 Regex

- 基本语法

  - 普通字符

    字母，数字，汉字，下划线，以及没有特殊定义的标点符号，都是 “普通字符”，表达式中的普通字符，在匹配一个字符串时，匹配与之相同的一个字符

  - 简单的转义字符

    | 符号                                                       | 意义             |
    | ---------------------------------------------------------- | ---------------- |
    | \n                                                         | 换行符           |
    | \t                                                         | 制表符           |
    | \\\                                                        | 代表 \ 本身      |
  | \\^, \\$, \\(, \\), \\{, \\}, \\?, \\+, \\*, \\|, \\[, \\] | 匹配这些字符本身 |
    
  - 标准字符集合

    - 能够与 ‘多种字符’ 匹配的表达式

    - 注意区分大小写，大写是相反的意思

      | 符号 | 意义                                                         |
      | ---- | ------------------------------------------------------------ |
      | \\d  | 任意数字 0~9 任意一个                                        |
      | \\w  | 任意一个字母，数字或者下划线，即 A~Z, a~z, 0~9 和 _          |
      | \\s  | 包括空格，制表符，换行符，等空白符其中的一个                 |
      | .    | 小数点可以匹配任意一个字符，除了换行符<br />如果要匹配包括 '\\n' 在内的所有字符，一般用 [\\s\\S] |

  - 自定义字符集合

    - 用 [] 的匹配方式，能够匹配方括号中任意一个字符

      | 符号      | 意义                             |
      | --------- | -------------------------------- |
      | [ab5@]    | 匹配 a 或 b 或 5 或 @            |
      | [^ abc]   | 匹配 a, b, c  以外的任意一个字符 |
      | [f-k]     | 匹配 f-k 之间的字母              |
      | [^A-F0-3] | 匹配 A-F 和 0-3 之外的字符       |

    - 正则表达式的特殊符号，被包含到括号中，则失去特殊意义，除了 ^ 和 - 之外

    - 标准字符集合，除小数点外，如果被包含于中括号，自定义字符集合将包含该集合

      [\d.\\-+] : 匹配数字，小数点，+，-

  - 量词

    - 修饰匹配次数的特殊符号

      | 符号   | 意义                                       |
      | ------ | ------------------------------------------ |
      | {n}    | 表达式重复 n 次                            |
      | {m, n} | 表达式至少重复 m 次，最多重复 n 次         |
      | {m, }  | 表达式至少重复 n 次                        |
      | ?      | 匹配表达式 0 次或者 1 次，相当于 {0, 1}    |
      | +      | 表达式至少出现 1 次，相当于 {1, }          |
      | *      | 表达式不出现，或者出现任意次，相当于 {0, } |

    - 匹配次数中的贪婪模式（匹配字符越多越好，默认）

    - 匹配次数中的非贪婪模式（匹配字符越少越好，修饰匹配次数的特殊符号后再加上一个 "?" 号）

  - 字符边界

    - 本组标记的不是字符而是位置，符合某种条件的位置

      | 符号 | 含义                   |
      | ---- | ---------------------- |
      | ^    | 与字符串开始的地方匹配 |
      | $    | 与字符串结束的地方匹配 |
      | \b   | 匹配一个单词边界       |

    - \b 匹配这样一个位置：前面的字符和后面的字符不全是 \w

  - 匹配模式

    - IGNORECASE 忽略大小写模式
      - 匹配时忽略大小写
    - SINGLELINE 单行模式
      - 整个文本看作一个字符串，只有一个开头和一个结尾
      - 使小数点 "." 可以匹配包含换行符（\n）在内的任意字符
    - MULTILINE 多行模式
      - 每行都是一个字符串，都有开头和结尾
      - 如果需要仅匹配字符串开始和结尾位置，可以使用 \A 和 \Z

  - 选择符和分组

    | 表达式                  | 作用                                                         |
    | ----------------------- | ------------------------------------------------------------ |
    | \| 分支结构             | 左右两边表达式之间 “或” 关系，匹配左边或者右边               |
    | () 捕获组               | 1）在被修饰匹配次数的时候，括号中的表达式可以作为整体被修饰<br />2）取匹配结果的时候，括号中的表达式匹配到的内容可以被单独得到<br />3）每一对括号会分配一个编号，使用 () 的捕获根据左括号的顺序从 1 开始自动编号，捕获元素编号为 0 的第一个捕获是由整合正则表达式模式匹配的文本 |
    | (?:Expression) 非捕获组 | 一些表达式中，不得不使用 () 但又不需要保存 () 中子表达式匹配的内容，这时可以用非捕获组来抵消使用 () 带来的副作用 |

  - 反向引用 (\nnn)

    - 每一对 () 会分配一个编号，使用 () 的捕获根据左括号位置的顺序从 1 开始编号
    - 通过反向引用，可以对分组已捕获的字符串进行引用
    
  - 预搜索（零宽断言）

    - 只进行子表达式的匹配，匹配内容不计入最终的匹配结果，是零宽度

    - 这个位置应该符合某个条件，判断当前的前后字符，是否符合指定的条件，但不匹配前后的字符，是对位置的匹配

    - 正则表达式的匹配过程中，如果子表达式匹配到的是字符内容，而非位置，并被保存到最终的匹配结果中，那么就认为这个子表达式是占有字符的，如果子表达式匹配的仅仅是位置，或者匹配的内容并不保存到最终的匹配结果中，那么就认为这个子表达式是零宽度的，占有字符还是零宽度的，是针对匹配的内容是否保存到最终的匹配结果中而言的

      | 表达式   | 作用                                     |
      | -------- | ---------------------------------------- |
      | (?=exp)  | 断言自身出现的位置的后面能匹配表达式 exp |
      | (?<=exp) | 断言自身出现的位置的前面能匹配表达式 exp |
      | (?!exp)  | 断言此位置的后面不能匹配表达式 exp       |
      | (?<!exp) | 断言此位置的前面不能匹配便打算 exp       |

- 练习

  - 电话号码验证

    - 电话数字号码由数字和 “-” 构成
    - 电话号码为 7-8 位
    - 如果电话号码中包含有区号，那么区号为三位或者四位，首位是0
    - 区号用 - 和其他部分隔开
    - 移动电话号码为 11 位
    - 11 位移动电话号码的第一位和第二位为 “13“，”15“，”18“

    ```regex
    ((0\d{2,3}-){0,1}\d{7,8})|(1[358]\d{9})
    ```

  - 邮箱验证

    - 用户名：字母，数字，下划线，中划线
    - @
    - 网址：字母，数字
    - 小数点 .
    - 组织域名：2-4 位字母组成
    - 不区分大小写

    ```regex
    [\w\-]+@[a-z0-9A-Z]+(\.[A-Za-z]{2,3}){1,2}
    ```

  - 常用正则表达式表

    | 功能         | 表达式                                        |
    | ------------ | --------------------------------------------- |
    | 中文字符     | [\u4e00-\u9fa5]                               |
    | 空白行       | \n\s*\r                                       |
    | HTML 标记    | <(\S\*?)[\^>]\*>.\*?<^1>\|<.*?/>              |
    | 首尾空白字符 | ^\s\*\|\s\*$                                  |
    | Email 地址   | \w+([-+.]\w+)\*@\w+([-.]\w+)\*\.\w+([-.]\w+)* |
    | URL          | [a-zA-Z]+://[\^\s]*                           |
    | 国内电话号码 | (\d{3}-\d{8})\|(\d{4}-\d{7})                  |
    | 腾讯 QQ 号   | \[1-9][0-9]{4,}                               |
    | 中国邮政编码 | [1-9]\d{5}(?!\d)                              |
    | 身份证       | \d{15}\|\d{18}                                |
    | ip 地址      | \d+\\.\d+\\.\d+\\.\d+                         |

- 开发环境中和文本编辑器中的正则

  - eclipse

    Search -> File Search -> Regular expression

  - Notepad++

  - Editplus

  - UltraEdit

- 数据库中的正则

  - Mysql 5.5+

  - Oracle 12g+

    ```sql
    select ename
    from emp
    where esalary REGEXP '.000' // 1000 2000 3000 ...
    ```

- Java复杂文本操作

  - 相关类位于 java.util.regex 包下面

  - 类 Pattern

    - 正则表达式的编译表示形式

    - ```java
      Pattern p = Pattern.compile(r,int); // 建立正则表达式，并启用相应模式
      ```

  - 类 Matcher

    - 通过解释 Pattern 对 character sequence 执行匹配操作的引擎

    - ```java
      Matcher m = p.matcher(str); // 匹配 str 字符串
      ```

