package com.ayy.client.handler;

import com.ayy.protobuf.DataInfo;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 29/03/2021
 * @ Version 1.0
 */

public class ProtoBufClientHandler extends SimpleChannelInboundHandler<DataInfo.Student> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DataInfo.Student msg) throws Exception {

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        DataInfo.Student student = DataInfo.Student.newBuilder()
                .setName("Student1")
                .setAge(18)
                .setAddress("1 Rue A")
                .build();
        ctx.writeAndFlush(student);
    }
}
