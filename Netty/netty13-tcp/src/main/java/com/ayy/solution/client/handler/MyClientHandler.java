package com.ayy.solution.client.handler;

import com.ayy.solution.protocol.MessageProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.StandardCharsets;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 25/07/2021
 * @ Version 1.0
 */

public class MyClientHandler extends SimpleChannelInboundHandler<MessageProtocol> {
    private int count = 0;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < 10; i++) {
            String msg = "Client Msg "+i+"|";
            byte[] buffer = msg.getBytes(StandardCharsets.UTF_8);
            ctx.writeAndFlush(new MessageProtocol().setContent(buffer).setLength(buffer.length));
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
        int length = msg.getLength();
        byte[] content = msg.getContent();
        System.out.println("Client received : ");
        System.out.println("Length : "+length);
        System.out.println("Content : "+new String(content, StandardCharsets.UTF_8));
        System.out.println("Num msg : "+(++count));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}

