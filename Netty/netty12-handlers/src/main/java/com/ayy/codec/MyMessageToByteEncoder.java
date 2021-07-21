package com.ayy.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 21/07/2021
 * @ Version 1.0
 */

public class MyMessageToByteEncoder extends MessageToByteEncoder<Long> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Long msg, ByteBuf out) throws Exception {
        out.writeLong(msg);
    }
}
