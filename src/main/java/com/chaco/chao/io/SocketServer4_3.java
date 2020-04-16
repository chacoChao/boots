package com.chaco.chao.io;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 多路复用IO的优缺点
 * 不用再使用多线程来进行IO处理了（包括操作系统内核IO管理模块和应用程序进程而言）。当然实际业务的处理中，应用程序进程还是可以引入线程池技术的
 * <p>
 * 同一个端口可以处理多种协议，例如，使用ServerSocketChannel测测的服务器端口监听，既可以处理TCP协议又可以处理UDP协议。
 * <p>
 * 操作系统级别的优化：多路复用IO技术可以是操作系统级别在一个端口上能够同时接受多个客户端的IO事件。同时具有之前我们讲到的阻塞式同步IO和非阻塞式同步IO的所有特点。Selector的一部分作用更相当于“轮询代理器”。
 * <p>
 * 都是同步IO：目前我们介绍的 阻塞式IO、非阻塞式IO甚至包括多路复用IO，这些都是基于操作系统级别对“同步IO”的实现。我们一直在说“同步IO”，一直都没有详细说，什么叫做“同步IO”。实际上一句话就可以说清楚：只有上层（包括上层的某种代理机制）系统询问我是否有某个事件发生了，否则我不会主动告诉上层系统事件发生了：
 * <p>
 * 这个关键概念，在这篇文章之前的几张“原理说明图”中实际上就可以清晰的提现了，但是为了让大家更清楚的总结同步IO、异步IO、阻塞IO、非阻塞IO的概念，下篇文章在讲解异步IO后我会梳理了一张对比表格。
 * <p>
 *     多路复用就是不断在轮训监听"感兴趣的事件"，不断的进行写入操作
 * author:zhaopeiyan001
 * Date:2019-09-27 11:02
 */
public @Slf4j
class SocketServer4_3 {
    /**
     * 改进的java nio server的代码中，由于buffer的大小设置的比较小。
     * 我们不再把一个client通过socket channel多次传给服务器的信息保存在beff中了（因为根本存不下）<br>
     * 我们使用socketchanel的hashcode作为key（当然您也可以自己确定一个id），信息的stringbuffer作为value，存储到服务器端的一个内存区域MESSAGEHASHCONTEXT。
     * <p>
     * 如果您不清楚ConcurrentHashMap的作用和工作原理，请自行百度/Google
     */
    private static final ConcurrentMap<Integer, StringBuffer> MESSAGEHASHCONTEXT = new ConcurrentHashMap<Integer, StringBuffer>();

    public static void main(String[] args) throws Exception {
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.configureBlocking(false);
        ServerSocket serverSocket = serverChannel.socket();
        serverSocket.setReuseAddress(true);
        serverSocket.bind(new InetSocketAddress(83));

        Selector selector = Selector.open();
        //注意、服务器通道只能注册SelectionKey.OP_ACCEPT事件
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);

        try {
            while (true) {
                //如果条件成立，说明本次询问selector，并没有获取到任何准备好的、感兴趣的事件
                //java程序对多路复用IO的支持也包括了阻塞模式 和非阻塞模式两种。
                if (selector.select(100) == 0) {
                    //================================================
                    //      这里视业务情况，可以做一些然并卵的事情
                    //================================================
                    continue;
                }
                //这里就是本次询问操作系统，所获取到的“所关心的事件”的事件类型（每一个通道都是独立的）
                Iterator<SelectionKey> selecionKeys = selector.selectedKeys().iterator();

                while (selecionKeys.hasNext()) {
                    SelectionKey readyKey = selecionKeys.next();
                    //这个已经处理的readyKey一定要移除。如果不移除，就会一直存在在selector.selectedKeys集合中
                    //待到下一次selector.select() > 0时，这个readyKey又会被处理一次
                    selecionKeys.remove();

                    SelectableChannel selectableChannel = readyKey.channel();
                    if (readyKey.isValid() && readyKey.isAcceptable()) {
                        log.info("======channel通道已经准备好=======");
                        /*
                         * 当server socket channel通道已经准备好，就可以从server socket channel中获取socketchannel了
                         * 拿到socket channel后，要做的事情就是马上到selector注册这个socket channel感兴趣的事情。
                         * 否则无法监听到这个socket channel到达的数据
                         * */
                        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectableChannel;
                        SocketChannel socketChannel = serverSocketChannel.accept();
                        registerSocketChannel(socketChannel, selector);

                    } else if (readyKey.isValid() && readyKey.isConnectable()) {
                        log.info("======socket channel 建立连接=======");
                    } else if (readyKey.isValid() && readyKey.isReadable()) {
                        log.info("======socket channel 数据准备完成，可以去读==读取=======");
                        readSocketChannel(readyKey);
                    }
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            serverSocket.close();
        }
    }

    /**
     * 这个方法用于读取从客户端传来的信息
     * 并且观察从客户端过来的socket channel在经过多次传输后，是否完成传输
     * 如果完成，返回一个true标记
     *
     * @param readyKey
     * @throws IOException
     */
    private static void readSocketChannel(SelectionKey readyKey) throws IOException {
        SocketChannel clientSocketChannel = (SocketChannel) readyKey.channel();
        //获取客户端使用的端口
        InetSocketAddress sourceSocketAddress = (InetSocketAddress) clientSocketChannel.getRemoteAddress();
        Integer resourcePort = sourceSocketAddress.getPort();

        //拿到这个socket channel使用的缓存区，准备读取数据
        //capacity  position   limit
        ByteBuffer contextBytes = (ByteBuffer) readyKey.attachment();
        //将通道的数据写入到缓存区
        //缩小buffer容量大小50byte
        int readLen = 0;
        StringBuffer message = new StringBuffer();
        //将目前通道中的数据写入到缓存区
        while ((readLen = clientSocketChannel.read(contextBytes)) != 0) {
            //一定要把buffer切换成“读”模式，否则由于limit = capacity
            //在read没有写满的情况下，就会导致多读
            contextBytes.flip();
            int position = contextBytes.position();
            int capacity = contextBytes.capacity();
            byte[] messageBytes = new byte[capacity];
            contextBytes.get(messageBytes, position, readLen);

            //这种方式也是可以读取数据的，而且不用关心position的位置。
            //因为是目前contextBytes所有的数据全部转出为一个byte数组。
            //使用这种方式时，一定要自己控制好读取的最终位置（realLen很重要）
            //byte[] messageBytes = contextBytes.array();

            //注意中文乱码的问题，我个人喜好是使用URLDecoder/URLEncoder，进行解编码。
            //当然java nio框架本身也提供编解码方式，看个人咯
            String messageEncode = new String(messageBytes, 0, readLen, "UTF-8");
            message.append(messageEncode);

            //再切换成“写”模式，直接情况缓存的方式，最快捷
            contextBytes.clear();
        }

        //如果发现本次接收的信息中有over关键字，说明信息接收完了
        if (URLDecoder.decode(message.toString(), "UTF-8").indexOf("over") != -1) {
            //则从messageHashContext中，取出之前已经收到的信息，组合成完整的信息
            Integer chnnelUUID = clientSocketChannel.hashCode();
            log.info("端口:" + resourcePort + "客户端发来的信息======message : " + message);
            StringBuffer completeMessage;
            //清空MESSAGEHASHCONTEXT中的历史记录
            StringBuffer historyMessage = MESSAGEHASHCONTEXT.remove(chnnelUUID);
            if (null == historyMessage) {
                completeMessage = message;
            } else {
                completeMessage = historyMessage.append(message);
            }
            log.info("端口:" + resourcePort + "客户端发来的信息======completeMessage : " + URLDecoder.decode(completeMessage.toString(), "UTF-8"));

            //======================================================
            //          当然接受完成后，可以在这里正式处理业务了
            //======================================================

            //回发数据，并关闭channel
            ByteBuffer sendBuffer = ByteBuffer.wrap(URLEncoder.encode("回发处理结果", "UTF-8").getBytes());
            clientSocketChannel.write(sendBuffer);
            clientSocketChannel.close();
        } else {
            //如果没有发现有"over"关键字，说明还没有接收完，则本次接收到到信息存入messageHashContext
            log.info("端口：" + resourcePort + "客户端信息还未接收完，继续接收==========message：" + URLEncoder.encode(message.toString(), "UTF-8"));
            //每一个channel对象都是独立的，所以可以使用对象的hash值，作为唯一标识
            Integer channelUUID = clientSocketChannel.hashCode();

            //然后获取这个channel下以前已经达到的message信息
            StringBuffer historyMessage = MESSAGEHASHCONTEXT.get(channelUUID);
            if (null == historyMessage) {
                historyMessage = new StringBuffer();
                MESSAGEHASHCONTEXT.putIfAbsent(channelUUID, historyMessage.append(message));
            }
        }
    }

    /**
     * 在server socket channel接收到/准备好一个新的TCP连接后
     * 就会向程序返回一个新的socket channel
     * 但是这个新的socket channel并没有在selector"选择器/代理器"中注册
     * 所以程序还没法通过selector通知这个socket channel事件
     * 于是我们拿到新的socket channel后，要做的第一个事情就是到selector中注册这个
     * socket channel感兴趣的事件
     *
     * @param socketChannel socket channel新的socket channel
     * @param selector      selector "选择器/代理器"
     * @throws IOException
     */
    private static void registerSocketChannel(SocketChannel socketChannel, Selector selector) throws IOException {
        socketChannel.configureBlocking(false);
        //socket通道可以且只可以注册三种事件SelectionKey.OP_READ | SelectionKey.OP_WRITE | SelectionKey.OP_CONNECT
        //最后一个参数视为 为这个socketchanne分配的缓存区
        socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(50));
    }
}
