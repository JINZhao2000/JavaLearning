package com.ayy.solution.server.handler;

import com.ayy.solution.protocol.MessageProtocol;
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

public class MyServerHandler extends SimpleChannelInboundHandler<MessageProtocol> {
    private int count = 0;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
        int length = msg.getLength();
        byte[] content = msg.getContent();
        System.out.println("Server received : ");
        System.out.println("Length : "+length);
        System.out.println("Content : "+new String(content, StandardCharsets.UTF_8));
        System.out.println("Num msg : "+(++count));

        String resp = UUID.randomUUID().toString()+"|";
        byte[] respCont = resp.getBytes(StandardCharsets.UTF_8);
        int respLen = respCont.length;

        ctx.writeAndFlush(new MessageProtocol().setLength(length).setContent(content));
    }
}
