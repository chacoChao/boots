package com.chaco.chao.io;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import io.netty.util.concurrent.DefaultThreadFactory;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.nio.channels.spi.SelectorProvider;
import java.util.concurrent.ThreadFactory;

/**
 * author:zhaopeiyan001
 * Date:2019-09-29 16:47
 */
public @Slf4j
class TestTCPNetty {
    public static void main(String[] args) {
        // netty服务启动器
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        // 设置线程池， BOSS线程池
        NioEventLoopGroup bossLoopGroup = new NioEventLoopGroup(1);
        // WORK线程，
        ThreadFactory threadFactory = new DefaultThreadFactory("work thread pool");
        // CPU number
        int processorsNumber = Runtime.getRuntime().availableProcessors();
        EventLoopGroup workLoopGroup = new NioEventLoopGroup(processorsNumber * 2, threadFactory, SelectorProvider.provider());
        //指定netty的boss线程和work线程
        serverBootstrap.group(bossLoopGroup, workLoopGroup);
        //设置服务的通道类型，只能是实现了ServerChannel接口的'服务器'通道类
        serverBootstrap.channel(NioServerSocketChannel.class);
//        serverBootstrap.channelFactory(new ChannelFactory<ServerChannel>() {
//            @Override
//            public ServerChannel newChannel() {
//                return new NioServerSocketChannel(SelectorProvider.provider());
//            }
//        });

        //设置处理器
        //为了演示，这里我们设置了一组简单的ByteArrayDecoder和ByteArrayEncoder
        //Netty的特色就在这一连串“通道水管”中的“处理器”
        serverBootstrap.childHandler(new ChannelInitializer<NioSocketChannel>() {

            @Override
            protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                nioSocketChannel.pipeline().addLast(new ByteArrayEncoder());
                nioSocketChannel.pipeline().addLast(new TCPServerHandler());
                nioSocketChannel.pipeline().addLast(new ByteArrayDecoder());
            }
        });

        //设置netty服务器绑定的IP和端口
        serverBootstrap.option(ChannelOption.SO_BACKLOG, 128);
        serverBootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
        serverBootstrap.bind(new InetSocketAddress("0.0.0.0", 80));
        //还可以监听多个端口
        //serverBootstrap.bind(new InetSocketAddress("0.0.0.0", 84));
    }
}
