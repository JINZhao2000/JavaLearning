# Netty

## 1. Netty 介绍（自己查）

[Netty 官网](https://netty.io/) 

[参考资料](https://www.manning.com/books/netty-in-action?query=netty%20in%20action) 

netty 模块结构

![img](images/components.png)

## 2. Netty Hello World 分析

HelloServer.java

```java
public class HelloServer {
    public static void main(String[] args) {
        // netty 建议用两个 LoopGroup 处理，虽然一个也可以
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            // 启动服务器
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            // 绑定 LoopGroup，channel，服务器初始化类
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new HelloServerInitializer());
			// 同步打开监听端口
            ChannelFuture channelFuture = serverBootstrap.bind(9000).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 静默关闭
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
```

HelloServerInitializer.java

```java
public class HelloServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        // 获取管道
        ChannelPipeline pipeline = ch.pipeline();
		// 添加 Http Encoder 和 Decoder
        pipeline.addLast("httpServerCodec", new HttpServerCodec());
        // 添加 HttpServerHandler
        pipeline.addLast("helloHttpServerHandler", new HelloHttpServerHandler());
    }
}
```

HelloHttpServerHandler.java

```java
public class HelloHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        if(msg instanceof HttpRequest) {
            // 获取请求 URI
            URI uri = new URI(((HttpRequest) msg).uri());
            // 获取请求来源地址
            System.out.println(ctx.channel().remoteAddress());
            Thread.sleep(3000);
            // 获取 msg 类
            System.out.println(msg.getClass());
            // 获取请求方法 GET POST PUT DELETE
            System.out.println(((HttpRequest) msg).method().name());
            
            // 是否是 favicon.ico 请求
            if("/favicon.ico".equals(uri.getPath())){
                System.out.println("/favicon.ico");
                return;
            }
            
            // 设置返回体
            ByteBuf content = Unpooled.copiedBuffer("Hello World !", CharsetUtil.UTF_8);
            FullHttpResponse response = new DefaultFullHttpResponse(
                // Http 协议版本
                    HttpVersion.HTTP_1_1,
                // Http 响应状态
                    HttpResponseStatus.OK,
                    content);
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());
            ctx.writeAndFlush(response);
            System.out.println("-- content --");
            ctx.close();
        }
    }

    
    // netty 的生命周期
    /* Add
     * Registered
     * Active
     * Inactive
     * Unregistered
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Channel Active");
        super.channelActive(ctx);
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Channel Registered");
        super.channelRegistered(ctx);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println();
        System.out.println("Handler Added");
        super.handlerAdded(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Channel Inactive");
        super.channelInactive(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Channel Unregistered");
        super.channelUnregistered(ctx);
    }
}
```



## Netty 对 Socket 实现

## Netty 压缩与解压缩

## Netty 对于 RPC 的支援

## WebSocket 实现与原理分析

## WebSocket 连接建立方式与生命周期分解

## WebSocket 服务端与客户端开发

## RPC 框架分析

## Google Protobuf 使用方式分析

## Apache Thrift 使用方式与文件编写方式分析

## Netty 大文件传送支持

## 可扩展事件模型

## Netty 统一通信 API

## 零拷贝在 Netty 中的实现与支持

## TCP 粘包与拆包解析

## NIO 模型在 Netty 中的实现

## Netty 编解码开发技术

## Netty 重要类与接口源代码解析

## Channel 分析

## 序列化讲解



