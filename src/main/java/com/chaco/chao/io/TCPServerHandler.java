package com.chaco.chao.io;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;

/**
 * author:zhaopeiyan001
 * Date:2019-09-29 17:24
 */
public @Slf4j
class TCPServerHandler extends ChannelInboundHandlerAdapter {

    private static AttributeKey<StringBuffer> content = AttributeKey.valueOf("content");

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        log.info("super.channelRegistered(ctx)");
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        log.info("super.channelUnregistered(ctx)");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("super.channelActive(ctx)");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("super.channelInactive(ctx)");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("channelReadChannelHandler Context ctx, Object msg");
        /**
         * 我们使用IDE工具模拟长链接中的数据缓慢提交
         * 由read方法负责接收数据，但只是进行数据累加，不进行任何处理
         */
        ByteBuf byteBuf = (ByteBuf) msg;
        try {
            StringBuffer contextBuffer = new StringBuffer();
            while (byteBuf.isReadable()) {
                contextBuffer.append((char) byteBuf.readByte());
            }

            //加入临时区域
            StringBuffer content = ctx.attr(TCPServerHandler.content).get();
            if (null == content) {
                content = new StringBuffer();
                ctx.attr(TCPServerHandler.content).set(content);
            }
            content.append(contextBuffer);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        } finally {
            byteBuf.release();
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        log.info("channelReadComplete(ctx)");

        /**
         * 由readComplete方法负责检查数据是否接收完了
         * 我们检查整个内容中是否由over关键字
         */
        StringBuffer content = ctx.attr(TCPServerHandler.content).get();
        //如果条件成立说明还没有接收到完整客户端信息
        if (content.indexOf("over") == -1) {
            return;
        }

        //当接收到信息后，首先要做的是清空原来的历史信息
        ctx.attr(TCPServerHandler.content).set(new StringBuffer());

        //准备像客户端发送响应
        ByteBuf buffer = ctx.alloc().buffer(1024);
        buffer.writeBytes("发回响应信息！".getBytes());
        ctx.writeAndFlush(buffer);

        /**
         * 关闭  正常种植这个通道上下文，就可以关闭通道了
         * 如果不关闭，这个通道的回话会一直存在，只要网络是稳定的，服务器就可以随时通过这个回话向客户端发送信息
         * 关闭通道意味着TCP将正常断开，其中所有的handler、channelHandlerContext、ChannelPipeline、Attribute等信息都将注销
         */
        ctx.close();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        log.info("userEventTriggered(ctx, evt)");
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        log.info("channelWritabilityChanged(ctx)");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.info("exceptionCaught(ctx, cause)");
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        log.info("handlerAdded(ctx)");
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        log.info("handlerRemoved(ctx)");
    }
}
