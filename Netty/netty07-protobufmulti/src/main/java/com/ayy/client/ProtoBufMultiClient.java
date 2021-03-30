package com.ayy.client;

import com.ayy.client.initializer.ProtoBufMultiClientInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 29/03/2021
 * @ Version 1.0
 */

public class ProtoBufMultiClient {
    public static void main(String[] args) {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap()
                    .group(eventLoopGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ProtoBufMultiClientInitializer());

            ChannelFuture channelFuture = bootstrap.connect("localhost",9000).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
