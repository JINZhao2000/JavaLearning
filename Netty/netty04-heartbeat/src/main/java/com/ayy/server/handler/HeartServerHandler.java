package com.ayy.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 26/03/2021
 * @ Version 1.0
 */

public class HeartServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;

            String eventType = null;

            switch (idleStateEvent.state()) {
                case READER_IDLE:
                    eventType = "Read Idle";
                    break;
                case WRITER_IDLE:
                    eventType = "Write Idle";
                    break;
                case ALL_IDLE:
                    eventType = "ReadWrite Idle";
                    break;
            }

            System.out.println(ctx.channel().remoteAddress() + " Timeout Event : "+eventType);
            ctx.channel().close();
        }
    }
}
