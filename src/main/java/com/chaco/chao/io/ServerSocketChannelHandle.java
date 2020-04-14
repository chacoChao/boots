package com.chaco.chao.io;

import lombok.extern.slf4j.Slf4j;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * 这个处理类，专门用来响应ServerSocketChannel的事件
 * ServerSocketChannel只有一种事件：接受客户端链接
 * author:zhaopeiyan001
 * Date:2019-09-27 17:34
 */
public @Slf4j
class ServerSocketChannelHandle implements CompletionHandler<AsynchronousSocketChannel, Void> {

    private AsynchronousServerSocketChannel serverSocketChannel;

    public ServerSocketChannelHandle(AsynchronousServerSocketChannel serverSocket) {
        this.serverSocketChannel = serverSocket;
    }

    /**
     * 我们分别观察this、socketChannel、attachment三个对象的ID
     * 来观察不同客户端链接到达时，这三个对象的变化，以说明ServerSocketChannelHandle的监听模式
     *
     * @param result
     * @param attachment
     */
    @Override
    public void completed(AsynchronousSocketChannel result, Void attachment) {
        log.info("completed(AsynchronousServerSocketChannel result, Void attachment)");
        //每次都要重新注册监听（一次注册，一次响应），由于"文件状态标识符"是独享的，所以不需要担心有'漏掉的'事件
        this.serverSocketChannel.accept(attachment, this);

        //为这个新的socketChannel注册read事件，以便操作系统在收到数据并准备好后，主动通知应用程序
        //我们要将这个客户端多次传输的数据累加起来一起处理，所以我们将一个stringbuffer对象作为一个'附件'依附在这个channel上
        ByteBuffer readBuffer = ByteBuffer.allocate(64);
//        result.read(readBuffer, new StringBuffer(), new SocketChannelReadHandle(result, readBuffer));
    }

    @Override
    public void failed(Throwable exc, Void attachment) {
        log.info("failed(Throwable exc, Void attachment)");
    }
}
