package com.ayy.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URI;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 22/03/2021
 * @ Version 1.0
 */

public class HelloHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        if(msg instanceof HttpRequest) {
            URI uri = new URI(((HttpRequest) msg).uri());
            System.out.println();
            System.out.println(ctx.channel().remoteAddress());
            Thread.sleep(3000);
            // System.out.println(msg.getClass());
            // System.out.println(((HttpRequest) msg).method().name());
            if("/favicon.ico".equals(uri.getPath())){
                // System.out.println("/favicon.ico");
                return;
            }
            ByteBuf content = Unpooled.copiedBuffer("Hello World !", CharsetUtil.UTF_8);
            FullHttpResponse response = new DefaultFullHttpResponse(
                    HttpVersion.HTTP_1_1,
                    HttpResponseStatus.OK,
                    content);
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());

            ctx.writeAndFlush(response);
            System.out.println("-- content --");
            // ctx.close();
        }
    }

//    @Override
//    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        System.out.println("Channel Active");
//        super.channelActive(ctx);
//    }
//
//    @Override
//    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
//        System.out.println("Channel Registered");
//        super.channelRegistered(ctx);
//    }
//
//    @Override
//    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
//        System.out.println();
//        System.out.println("Handler Added");
//        super.handlerAdded(ctx);
//    }
//
//    @Override
//    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
//        System.out.println("Channel Inactive");
//        super.channelInactive(ctx);
//    }
//
//    @Override
//    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
//        System.out.println("Channel Unregistered");
//        super.channelUnregistered(ctx);
//    }
}
