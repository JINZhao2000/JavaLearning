package com.ayy.server.handler;

import com.ayy.protobuf.MyDataInfo;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 29/03/2021
 * @ Version 1.0
 */

public class ProtoBufMultiServerHandler extends SimpleChannelInboundHandler<MyDataInfo.MyMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyDataInfo.MyMessage msg) throws Exception {
        MyDataInfo.MyMessage.AnimalType type = msg.getAnimalType();
        if (type == MyDataInfo.MyMessage.AnimalType.CAT){
            System.out.println(msg.getCat());
        } else if(type == MyDataInfo.MyMessage.AnimalType.DOG){
            System.out.println(msg.getDog());
        }
    }
}
