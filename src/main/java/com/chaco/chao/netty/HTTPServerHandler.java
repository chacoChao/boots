package com.chaco.chao.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.AttributeKey;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * author:zhaopeiyan001
 * Date:2019-09-30 18:22
 */
public @Slf4j
class HTTPServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 由于一次httpcontent可能没有传输完全部的请求信息。所以这里要做一个连续的记录
     * 然后在channelReadComplete方法中（执行了这个方法说明这次所有的http内容都传输完了）进行处理
     */
    private static AttributeKey<StringBuffer> CONNTENT = AttributeKey.valueOf("content");

    public HTTPServerHandler() {
        super();
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        super.channelUnregistered(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        /**
         * 在测试中，我们首先取出客户端传来的参数，URL信息，并且返回给一个确认信息
         * 要使用HTTP服务，我们首先要了解Netty中http的格式如下：
         * ----------------------------------------------
         * | http request | http content | http content |
         * ----------------------------------------------
         * 所以通过HttpRequestDecoder channel handler解码后的msg可能是两种类型：
         * HttpRquest：里面包含了请求head、请求的url等信息
         * HttpContent：请求的主体内容
         */
        if (msg instanceof HttpRequest) {
            HttpRequest request = (HttpRequest) msg;
            HttpMethod method = request.getMethod();

            String methodName = method.name();
            String url = request.getUri();
            log.info("methodName = " + methodName + " && url = " + url);
        }

        //如果条件成立，则在这个代码段实现http请求内容的累加
        if (msg instanceof HttpContent) {
            StringBuffer content = ctx.attr(HTTPServerHandler.CONNTENT).get();
            if (null == content) {
                content = new StringBuffer();
                ctx.attr(HTTPServerHandler.CONNTENT).set(content);
            }

            HttpContent httpContent = (HttpContent) msg;
            ByteBuf contentBuf = httpContent.content();
            String preContent = contentBuf.toString(CharsetUtil.UTF_8);
            content.append(preContent);
        }

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        log.info("super.channelReadComplete(ctx)");

        /**
         * 一旦本次http请求传输完成，则可以进行业务处理
         * 并且返回响应
         */
        StringBuffer content = ctx.attr(HTTPServerHandler.CONNTENT).get();
        log.info("http客户端传来的消息为：" + content);

        //开始返回信息
        String returnValue = "return response";
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
        HttpHeaders httpHeaders = response.headers();
        httpHeaders.add("param", "value");
        response.headers().set(HttpHeaders.Names.CONTENT_TYPE, "text/plain");
        //一定要设置铲毒，否则http客户端就会一直等待（因为返回的信息长度客户端不知道）
        response.headers().set(HttpHeaders.Names.CONTENT_LENGTH, returnValue.length());

        ByteBuf responseContent = response.content();
        responseContent.writeBytes(returnValue.getBytes("UTF-8"));

        //开始返回
        ctx.writeAndFlush(response);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        super.channelWritabilityChanged(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
