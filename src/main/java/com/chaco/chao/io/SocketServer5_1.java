package com.chaco.chao.io;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * author:zhaopeiyan001
 * Date:2019-09-27 16:53
 */
public @Slf4j
class SocketServer5_1 {
    private static final Object object = new Object();

    public static void main(String[] args) throws IOException, InterruptedException {
        /**
         * 对于使用线程池技术：
         * 1、Executors是线程池生成工具，通过这个工具我们可以很轻松的生成'固定大小的线程池'、'调度池'、'可伸缩线程数量的池'等
         * 2、也可以通过ThreadPoolExecutor直接生成线程池
         * 3、这个线程池是用来得到操作系统的'IO事件通知'的，不是用来进行'得到IO数据后的业务处理的'。要进行后者的操作，可以再定义一个线程池  （最好不要混用）
         * 4、也可以不使用线程池（不推荐），如果决定不使用线程池，直接AsynchronousServerSocketChannel.open()就行了。
         */
        ExecutorService threadPool = Executors.newFixedThreadPool(20);
        AsynchronousChannelGroup group = AsynchronousChannelGroup.withThreadPool(threadPool);
        AsynchronousServerSocketChannel serverSocket = AsynchronousServerSocketChannel.open(group);

        //设置要监听的端口 “0.0.0.0”代表本机所有IP设备
        serverSocket.bind(new InetSocketAddress("0.0.0.0", 80));
        //为AsynchronousServerSocketChannel注册监听，注意只是为AsynchronousServerSocketChannel通道注册监听
        //并不包括为随客户端和服务器socketChannel通道注册监听
        serverSocket.accept(null, new ServerSocketChannelHandle(serverSocket));

        //等待   以便观察现象（保证守护线程不会推出）
        synchronized (object) {
            object.wait();
        }
    }
}