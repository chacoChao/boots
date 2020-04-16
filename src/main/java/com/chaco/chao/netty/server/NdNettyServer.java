package com.chaco.chao.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * author:zhaopeiyan001
 * Date:2019-04-29 18:09
 */
public class NdNettyServer {

    public static void main(String[] args) {
        //server client
        ServerBootstrap bootstrap = new ServerBootstrap();

        //设置nioSocket factory
//        bootstrap.group(producer, customer);
        bootstrap.channel(NioServerSocketChannel.class);

    }

    public static void service() throws Exception {


    }

}
