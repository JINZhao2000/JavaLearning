package com.ayy.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.security.SecureRandom;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 21/07/2021
 * @ Version 1.0
 */

public class MyHandler extends SimpleChannelInboundHandler<Long> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {
        System.out.println(msg);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        ctx.writeAndFlush(new SecureRandom().nextLong());
    }
}
