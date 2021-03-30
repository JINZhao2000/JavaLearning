package com.ayy.client.handler;

import com.ayy.protobuf.MyDataInfo;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Random;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 29/03/2021
 * @ Version 1.0
 */

public class ProtoBufMultiClientHandler extends SimpleChannelInboundHandler<MyDataInfo.MyMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyDataInfo.MyMessage msg) throws Exception {

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        int rnd = new Random().nextInt(2);
        MyDataInfo.MyMessage message = null;
        switch (rnd){
            case 0 :
                MyDataInfo.Cat cat = MyDataInfo.Cat.newBuilder().setName("Cat").setAge(2).build();
                message = MyDataInfo.MyMessage.newBuilder()
                        .setAnimalType(MyDataInfo.MyMessage.AnimalType.CAT)
                        .setCat(cat)
                        .build();
                break;
            case 1 :
                MyDataInfo.Dog dog = MyDataInfo.Dog.newBuilder().setName("Dog").setAge(3).setRace("RaceDog").build();
                message = MyDataInfo.MyMessage.newBuilder()
                        .setAnimalType(MyDataInfo.MyMessage.AnimalType.DOG)
                        .setDog(dog)
                        .build();
                break;
        }
        ctx.channel().writeAndFlush(message);
    }
}
