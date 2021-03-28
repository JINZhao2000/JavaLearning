# Netty

## 1. Netty 介绍（自己查）

[Netty 官网](https://netty.io/) 

[参考资料](https://www.manning.com/books/netty-in-action?query=netty%20in%20action) 

netty 模块结构

![img](images/components.png)

相对而言，Netty 框架更加偏向底层，而路由转发更需要开发者自己实现

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

## 3. Netty 编写简单的 Socket

ClientInitializer

```java
public class NettyClientSocketInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,0,4,0,4));
        pipeline.addLast(new LengthFieldPrepender(4));
        // 字符串编码和解码
        pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
        pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));
        pipeline.addLast(new NettyClientSocketHandler());
    }
}
```

ClientHandler

```java
public class NettyClientSocketHandler extends SimpleChannelInboundHandler<String> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println(ctx.channel().remoteAddress());
        System.out.println("client output : "+msg);
        ctx.writeAndFlush("from client : " + LocalDateTime.now());
    }

    // 一定要处理异常
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 预先向服务端发送数据
        // 否则不会进行数据传输
        ctx.writeAndFlush("Client Connected");
    }
}
```

ServerInitializer

```java
public class NettyServerSocketInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,0,4,0,4));
        pipeline.addLast(new LengthFieldPrepender(4));
        // 字符串编码解码
        pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
        pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));
        pipeline.addLast(new NettyServerSocketHandler());
    }
}
```

ServerHandler

```java
public class NettyServerSocketHandler extends SimpleChannelInboundHandler<String> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println(ctx.channel().remoteAddress()+" --- "+msg);
        ctx.channel().writeAndFlush("From server : "+ UUID.randomUUID());
    }

    // 一定要处理异常
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
```

__ChatSocket__ 

ServerInitializer

```java
public class ChatServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast(new DelimiterBasedFrameDecoder(4096, Delimiters.lineDelimiter()));
        pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
        pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));
        pipeline.addLast(new ChatServerHandler());
    }
}
```

ServerHandler

```java
public class ChatServerHandler extends SimpleChannelInboundHandler<String> {
    // netty 自己的 Channel 集合本质是 Set
    private static final ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.forEach((ch)->{
            // 判断是否是自己
            if(!ch.equals(channel)){
                ch.writeAndFlush(channel.remoteAddress()+" : "+msg+"\n");
            } else {
                ch.writeAndFlush("[Self] "+msg+"\n");
            }
        });
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();

        channelGroup.writeAndFlush("[Server] "+channel.remoteAddress()+" has connected\n");

        // 将连接上的 Channel 加入组里
        channelGroup.add(channel);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        // 因为执行这个方法时就已经移除了，所以没必要再移除
        // channelGroup.remove(channel);
        channelGroup.writeAndFlush("[Server] "+channel.remoteAddress()+" has disconnected\n");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println(channel.remoteAddress()+" connected");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println(channel.remoteAddress()+" disconnected");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
```

ClientInitializer

```java
public class ChatClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast(new DelimiterBasedFrameDecoder(4096, Delimiters.lineDelimiter()));
        pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
        pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));
        pipeline.addLast(new ChatClientHandler());
    }
}
```

ClientHandler

```java
public class ChatClientHandler extends SimpleChannelInboundHandler<String> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println(msg);
    }
}
```

## 4. Netty 心跳包

Netty 有自己心跳包的类

ServerInitializer

```java
public class HeartServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
		// 空闲状态 Handler : 读空闲时间，写空闲时间，读写空闲时间，时间单位
        pipeline.addLast(new IdleStateHandler(5, 7, 3, TimeUnit.SECONDS));
        pipeline.addLast(new HeartServerHandler());
    }
}
```

ServerHandler

```java
public class HeartServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;

            String eventType = null;
			
            switch (idleStateEvent.state()) {
                // 连接后超过读空闲时间
                case READER_IDLE:
                    eventType = "Read Idle";
                    break;
                // 连接后写过后，不写超过时间
                case WRITER_IDLE:
                    eventType = "Write Idle";
                    break;
                // 连接后不读不写超过时间
                case ALL_IDLE:
                    eventType = "ReadWrite Idle";
                    break;
            }

            System.out.println(ctx.channel().remoteAddress() + " Timeout Event : "+eventType);
            ctx.channel().close();
        }
    }
}
```

## 5. Netty WebSocket

WebSocket 是将 Http 协议升级得到

Initializer

```java
public class WebSocketServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
		// 因为是基于 Http 的所以要 Http 的 Handler
        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new ChunkedWriteHandler());
        // 每次处理的大小
        pipeline.addLast(new HttpObjectAggregator(8192));
        // 对应 ws://~/ws 下的请求
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
        pipeline.addLast(new TextWebSocketServerFrameHandler());
    }
}
```

Handler

```java
public class TextWebSocketServerFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        System.out.println("msg : "+msg.text());

        ctx.channel().writeAndFlush(new TextWebSocketFrame("Server Time : "+ LocalDateTime.now()));
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        // 长 id 是唯一的，但是短 id 是不一定唯一的
        System.out.println("HandlerAdded : "+ctx.channel().id().asLongText());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("HandlerRemoved : "+ctx.channel().id().asLongText());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
```

## 6. Google Protobuf 使用方式分析

Google 的 Protobuf 是用于 RPC 通信的

它基于一个接口协议文件，将两个语言对象的对应的属性信息

```protobuf
message Person {
  // required 必须内容， string 类型， name 名字， 1 排的位置
  required string name = 1;
  required int32 id = 2;
  optional string email = 3;
}
```



## 7. Apache Thrift 使用方式与文件编写方式分析

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



