# Java Net

## 1. 常用类操作 IP Port URL

- IP

  ```java
  InetAddress addr = InetAddress.getLocalHost();
  addr.getHostName();
  addr.getHostAddress();
  ```

- Port

  ```java
  InetSocketAddress socketAddress = new InetSocketAddress(hostname,port);
  InetSocketAddress socketAddress = new InetSocketAddress("127.0.0.1",80);
  socketAddress.getHostName();
  socketAddress.getAddress();
  socketAddress.getPort();
  ```

- URL

  ```java
  URL url = new URL("url");
  url.getProtocol();
  url.getHost(); // ip
  url.getPort(); // http https 为 -1
  url.getDefaultPort(); // http https 为 80 443
  url.getFile(); // 路径 + 查询语句
  url.getPath(); // 路径
  url.getQuery(); // 查询语句
  url.getRef(); // 锚点
  ```

  爬虫
  - URL
  - 下载
  - 分析
  - 抽取

## 2. 传输协议 UDP TCP

### 2.1 UDP

操作字节数组

- Server

  1. 使用 DatagramSocket 指定端口，创建接收端

  2. 准备容器，封装成 DatagramSocket 包裹

  3. 阻塞式接收包裹 receive(DatagramPacket p)

  4. 分析数据

     byte[]  getData(); getLength();

  5. 释放资源

- Client

  1. 使用 DatagramSocket 指定端口，创建发送端
  2. 准备数据，一定要转换为字节数组
  3. 封装成 DatagramSocket 包裹，需要指定目的地
  4. 发送包裹 send(DatagramPacket p)
  5. 释放资源

### 2.2 TCP

需要登录操作，操作 IO 流

- Server
  1. 指定端口，使用 ServerSocket 创建服务器
  2. 阻塞式等待连接 accept
  3. 操作，输入输出流操作
  4. 释放资源

- Client
  1. 建立连接，使用 Socket 创建客户端 +服务器地址和端口
  2. 操作，输入输出流操作
  3. 释放资源

## 3. WebServer

使用 request & response 式的交流

### 3.1 XML 解析 Extensible Markup Language

作为数据的一种存储格式或用于存储软件的参数，程序解析此配置文件，就可以达到不修改代码就能更改程序的目的