package com.chaco.chao.netty.server.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * author:zhaopeiyan001
 * Date:2019-04-30 11:11
 */
public class DiscardServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//      super.channelRead(ctx, msg);
//        ((ByteBuf) msg).release();
        ByteBuf in = (ByteBuf) msg;
        try {
            ctx.write(msg);
            ctx.flush();
            //do something with msg
            System.out.println(in.readByte());
            System.out.println((char) in.readByte());
            System.out.flush();
        } finally {
//            ReferenceCountUtil.release(msg);
            in.release();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        super.exceptionCaught(ctx, cause);
        cause.printStackTrace();
        ctx.close();
    }
}
