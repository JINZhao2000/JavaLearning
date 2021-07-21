package com.ayy.server.initializer;

import com.ayy.codec.MyByteToLongDecoder;
import com.ayy.codec.MyMessageToByteEncoder;
import com.ayy.server.handler.MyHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 21/07/2021
 * @ Version 1.0
 */

public class MyInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new MyMessageToByteEncoder());
        pipeline.addLast(new MyByteToLongDecoder());
        pipeline.addLast(new MyHandler());
    }
}
