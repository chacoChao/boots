package com.chaco.chao.io;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * author:zhaopeiyan001
 * Date:2019-09-27 18:00
 */
public @Slf4j
class SocketChannelReadHandle implements CompletionHandler<Integer, StringBuilder> {

    private AsynchronousSocketChannel socketChannel;

    /**
     * 专门用于进行这个通道数据缓存操作的ByteBuffer
     * 也可也用作为CompletionHandler的attachment形式传入
     * attachment用来记录所有传送过来的StringBuffer了
     * */
    private ByteBuffer byteBuffer;

    public SocketChannelReadHandle(AsynchronousSocketChannel socketChannel, ByteBuffer readBuffer) {
        this.byteBuffer = readBuffer;
        this.socketChannel = socketChannel;
    }

    @Override
    public void completed(Integer result, StringBuilder attachment) {
        //如果条件成立，说明客户端主动终止了TCP套接字，这是服务端终止就可以了
        if (result == -1) {
            try {
                this.socketChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
                log.error(e.getMessage());
            }
            return;
        }

        log.info("completed(Integer result, Void attachment) : 然后我们来取出通道中准备好的值");

        /**
         * 实际上，由于我们从Integer result直到了本次channel从操作系统获取数据总长度
         * 我们不需要切换成'读模式'的，但是为了保证编码的规范性，还是建议切换
         * 无论是Java AIO框架还是Java NIO框架，都会出现'buffer'的总容量'小于'当前从系统获取到的总数据量
         * 但区别是，Java nio框架中，我们不需要专门考虑处理这样的情况，因为Java nio框架已经帮我们做了处理（做成了多次通知）
         */
        this.byteBuffer.flip();
        byte[] contexts = new byte[1024];
        this.byteBuffer.get(contexts, 0, result);
        this.byteBuffer.clear();
        try {
            String nowContent = new String(contexts, 0, result, "UTF-8");
            attachment.append(nowContent);
            log.info("===================目前的传输结果：" + attachment);
        } catch (IOException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }

        //如果条件成立，说明还没有接收到'结束标记'
        if (attachment.indexOf("over") == -1) {
            return;
        }

        //=========================================================================
        //          和上篇文章的代码相同，我们以“over”符号作为客户端完整信息的标记
        //=========================================================================
        log.info("=======收到完整信息，开始处理业务=========");
        attachment = new StringBuilder();

        //还要继续监听（一次监听一次通知）
        this.socketChannel.read(this.byteBuffer, attachment, this);
    }

    @Override
    public void failed(Throwable exc, StringBuilder attachment) {
        log.info("=====发现客户端异常关闭，服务器将关闭TCP通道");
        try {
            this.socketChannel.close();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
    }
}
