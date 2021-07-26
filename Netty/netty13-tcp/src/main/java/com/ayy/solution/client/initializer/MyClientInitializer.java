package com.ayy.solution.client.initializer;

import com.ayy.solution.client.handler.MyClientHandler;
import com.ayy.solution.codec.MessageProtocolDecoder;
import com.ayy.solution.codec.MessageProtocolEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 25/07/2021
 * @ Version 1.0
 */

public class MyClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new MessageProtocolDecoder());
        pipeline.addLast(new MessageProtocolEncoder());
        pipeline.addLast(new MyClientHandler());
    }
}
