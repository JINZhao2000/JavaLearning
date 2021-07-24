package com.ayy.client.initializer;

import com.ayy.client.handler.MyClientHandler;
import com.ayy.codec.MyByteToLongReplayingDecoder;
import com.ayy.codec.MyMessageToByteEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 21/07/2021
 * @ Version 1.0
 */

public class MyClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new MyMessageToByteEncoder());
        // pipeline.addLast(new MyByteToLongDecoder());
        pipeline.addLast(new MyByteToLongReplayingDecoder());
        pipeline.addLast(new MyClientHandler());
    }
}
