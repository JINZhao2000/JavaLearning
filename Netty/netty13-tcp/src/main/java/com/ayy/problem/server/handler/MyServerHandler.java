package com.ayy.problem.server.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 25/07/2021
 * @ Version 1.0
 */

public class MyServerHandler extends SimpleChannelInboundHandler<ByteBuf> {
    private int count = 0;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        byte[] buffer = new byte[msg.readableBytes()];
        msg.readBytes(buffer);

        String message = new String(buffer, StandardCharsets.UTF_8);

        System.out.println("msg received : " + message);
        System.out.println("num msg received : " + (++count));

        ByteBuf response = Unpooled.copiedBuffer(UUID.randomUUID().toString()+"|", StandardCharsets.UTF_8);
        ctx.writeAndFlush(response);
    }
}
