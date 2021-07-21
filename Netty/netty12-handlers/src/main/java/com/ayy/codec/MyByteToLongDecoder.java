package com.ayy.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 21/07/2021
 * @ Version 1.0
 */

public class MyByteToLongDecoder extends ByteToMessageDecoder{
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes()>0) {
            out.add(in.readLong());
        }
    }
}
