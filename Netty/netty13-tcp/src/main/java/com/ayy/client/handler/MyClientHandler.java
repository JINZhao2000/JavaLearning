package com.ayy.client.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.StandardCharsets;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 25/07/2021
 * @ Version 1.0
 */

public class MyClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
    private int count = 0;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < 10; i++) {
            ByteBuf buffer = Unpooled.copiedBuffer("client msg num "+i+"|", StandardCharsets.UTF_8);
            ctx.writeAndFlush(buffer);
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        byte[] buffer = new byte[msg.readableBytes()];
        msg.readBytes(buffer);
        String message = new String(buffer, StandardCharsets.UTF_8);

        System.out.println("msg received: " + message);
        System.out.println("num msg received: " + (++count));
    }
}

