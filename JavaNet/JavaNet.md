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

```java
public static void main(String[] args) throws Exception {
    SAXParserFactory factory = SAXParserFactory.newInstance();
    SAXParser parser = factory.newSAXParser();
    WebHandler handler = new WebHandler();
    parser.parse(Thread.currentThread().getContextClassLoader().getResourceAsStream("web.xml"),handler);
	WebContent content = new WebContent(handler.getEntities(), handler.getMappings());
    String className = content.getClassName("/login");
    if (className == null){
	    System.out.println("404 Not Found");
	}else {
        Class<?> clz = (Class<Servlet>) Class.forName(className);
        Servlet servlet = (Servlet) clz.getDeclaredConstructor().newInstance();
        Method m = clz.getMethod("service");
        m.invoke(servlet);
    }
}
```

xml 解析类 Handler

```java
public class WebHandler extends DefaultHandler {
    private List<Entity> entities;
    private List<Mapping> mappings;
    private Entity entity;
    private Mapping mapping;
    private String tag;
    private boolean isMapping;
    @Override
    public void startDocument() throws SAXException {
        System.out.println("--- start ---");
        entities = new ArrayList<>();
        mappings = new ArrayList<>();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if(null!=qName){
            tag = qName;
            if(tag.equals("servlet")){
                entity = new Entity();
                isMapping = false;
            }else if(tag.equals("servlet-mapping")){
                mapping = new Mapping();
                isMapping = true;
            }
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String content = new String(ch,start,length).trim();
        if(null!=tag){
            if(isMapping){
                if(tag.equals("servlet-name")){
                    mapping.setName(content);
                }else if(tag.equals("url-pattern")){
                    mapping.addPattern(content);
                }
            }else{
                if(tag.equals("servlet-name")){
                    entity.setName(content);
                }else if(tag.equals("servlet-class")){
                    entity.setCls(content);
                }
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if(null!=qName){
            if(qName.equals("servlet")){
                entities.add(entity);
            }else if(qName.equals("servlet-mapping")){
                mappings.add(mapping);
            }
        }
        tag = null;
    }

    @Override
    public void endDocument() throws SAXException {
        System.out.println("--- end ---");
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public List<Mapping> getMappings() {
        return mappings;
    }
}
```

