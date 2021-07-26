package com.ayy.solution.server.initializer;

import com.ayy.solution.codec.MessageProtocolDecoder;
import com.ayy.solution.codec.MessageProtocolEncoder;
import com.ayy.solution.server.handler.MyServerHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;


/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 25/07/2021
 * @ Version 1.0
 */

public class MyServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new MessageProtocolDecoder());
        pipeline.addLast(new MessageProtocolEncoder());
        pipeline.addLast(new MyServerHandler());
    }
}
