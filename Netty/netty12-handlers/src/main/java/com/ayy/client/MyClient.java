package com.ayy.client;

import com.ayy.client.initializer.MyClientInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 21/07/2021
 * @ Version 1.0
 */

public class MyClient {
    public static void main(String[] args) {
        EventLoopGroup eventExecutors = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.handler(new MyClientInitializer());
            ChannelFuture future = bootstrap.group(eventExecutors).
                    channel(NioSocketChannel.class).
                    connect("localhost",10000).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
