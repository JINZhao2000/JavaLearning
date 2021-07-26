package com.ayy.solution.codec;

import com.ayy.solution.protocol.MessageProtocol;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 26/07/2021
 * @ Version 1.0
 */

public class MessageProtocolDecoder extends ReplayingDecoder<Void> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        int length = in.readInt();
        byte[] content = new byte[length];
        in.readBytes(content);
        out.add(new MessageProtocol().setLength(length).setContent(content));
    }
}
