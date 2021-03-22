package com.ayy.initializer;

import com.ayy.handler.HelloHttpServerHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 22/03/2021
 * @ Version 1.0
 */

public class HelloServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast("httpServerCodec", new HttpServerCodec());
        pipeline.addLast("helloHttpServerHandler", new HelloHttpServerHandler());
    }
}
