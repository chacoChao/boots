//package com.chaco.chao.io;
//
//import lombok.extern.slf4j.Slf4j;
//
//import java.io.IOException;
//import java.net.InetSocketAddress;
//import java.net.ServerSocket;
//import java.net.URLDecoder;
//import java.net.URLEncoder;
//import java.nio.ByteBuffer;
//import java.nio.channels.*;
//import java.util.Iterator;
//
///**
// * author:zhaopeiyan001
// * Date:2019-09-26 20:25
// */
//public @Slf4j
//class SocketServer4_2 {
//    public static void main(String[] args) throws IOException {
//        ServerSocketChannel serverChannel = ServerSocketChannel.open();
//        serverChannel.configureBlocking(false);
//        ServerSocket serverSocket = serverChannel.socket();
//        serverSocket.setReuseAddress(true);
//        serverSocket.bind(new InetSocketAddress(80));
//
//        Selector selector = Selector.open();
//        //注意  服务器通道只能注册SelectionKey.OP_ACCEPT事件
//        serverChannel.register(selector, SelectionKey.OP_ACCEPT);
//
//        try {
//            while (true) {
//                //如果条件成立，说明本次询问selector并没有获取到任何准备好的、感兴趣的事件
//                //Java程序对多路复用IO的支持也包括了阻塞模式和非阻塞模式两种
//                continue;
//            }
//            //这里就是本次询问操作系统，所获取到的（所关心的事件）的事件类型  （每一个管道是独立的）
//            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
//            while (iterator.hasNext()) {
//                SelectionKey readyKey = iterator.next();
//                //已处理的readyKey一定要移除
//                iterator.remove();
//                SelectableChannel selectableChannel = readyKey.channel();
//                if (readyKey.isValid() && readyKey.isAcceptable()) {
//                    log.info("================channel通道已经准备好=================");
//                    /**
//                     * 当server socket channel通道已经准备好了，就可以从server socket channel中获取socketchannel了
//                     * 拿到socket channel后，要做的事情就是马上到selector注册这个socket channel
//                     * 否则无法监听到这个socket channel到达到数据
//                     */
//                    ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectableChannel;
//                    SocketChannel socketChannel = serverSocketChannel.accept();
//                    registerSocketChannel(socketChannel, selector);
//                } else if (readyKey.isValid() && readyKey.isConnectable()) {
//                    log.info("=================socket channel 建立连接====================");
//                } else if (readyKey.isValid() && readyKey.isReadable()) {
//                    log.info("=================socket channel数据准备完成，可以进行读取==============");
//                    readSocketChannel(readyKey);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            log.error(e.getMessage());
//        } finally {
//            serverSocket.close();
//        }
//    }
//
//    /**
//     * 这个方法用于读取从客户端传来的信息
//     * 并且观察从客户端过来的socket channel在经过多次传输后，是否完成传输
//     * 如果传输完成，则返回一个true的标记
//     *
//     * @param readyKey
//     * @throws IOException
//     */
//    private static void readSocketChannel(SelectionKey readyKey) throws IOException {
//        SocketChannel clientSocketChannel = (SocketChannel) readyKey.channel();
//        //获取客户端使用的端口
//        InetSocketAddress sourceSocketAddress = (InetSocketAddress) clientSocketChannel.getRemoteAddress();
//        Integer resourcePort = sourceSocketAddress.getPort();
//
//        //拿到这个socket channel使用的缓存区，准备读取数据
//        //缓存区用法：capacity、position、limit
//        ByteBuffer contextBytes = (ByteBuffer) readyKey.attachment();
//        //将通道的数据写入到缓存区！
//        //由于之前设置了byteBuffer的大小为2048byte，所以可以存在写入不完的情况
//        int readLen = -1;
//        try {
//            readLen = clientSocketChannel.read(contextBytes);
//        } catch (Exception e) {
//            //关闭channel
//            e.printStackTrace();
//            log.error(e.getMessage());
//            clientSocketChannel.close();
//            return;
//        }
//
//        //如果缓存区中没有任何数据（但实际上这个不太可能，否则就不会触发OP_READ事件了）
//        if (readLen == -1) {
//            log.warn("===============缓存区没有数据=================");
//            return;
//        }
//
//        //将缓存区从写状态切换为读状态（读写模式相互切换）
//        //这时Java nio框架中的这个socket channel的写请求将全部等待
//        contextBytes.flip();
//        //注意中文乱码的问题，我个人喜好是使用URLDecoder/URLEncoder，进行解编码。
//        //当然java nio框架本身也提供编解码方式
//        byte[] messageBytes = contextBytes.array();
//        String messageEncode = new String(messageBytes, "UTF-8");
//        String message = URLDecoder.decode(messageEncode, "UTF-8");
//
//        //如果收到了"over"关键字，才会清空buffer，并回发数据
//        //否则不清空缓存，还要还原buffer的'写状态'
//        if (message.indexOf("over") != -1) {
//            //清空已经读取的缓存，并从新切换为写状态（clear() 与  capacity()  方法的区别）
//            contextBytes.clear();
//            log.info("端口：" + resourcePort + "客户端发来的信息======message : " + message);
//
//            //======================================================
//            //          当然接受完成后，可以在这里正式处理业务了
//            //======================================================
//
//            //回发数据，并关闭channel
//            ByteBuffer sendBuffer = ByteBuffer.wrap(URLEncoder.encode(" 回发处理结果", "UTF-8").getBytes());
//            clientSocketChannel.write(sendBuffer);
//            clientSocketChannel.close();
//        } else {
//            log.info("端口：" + resourcePort + "客户端信息还未接收完，继续接收========message：" + message);
//            //limit和capacity的值一致，position的位置是readLen的位置
//            contextBytes.position(readLen);
//            contextBytes.limit(contextBytes.capacity());
//        }
//    }
//
//    /**
//     * 在server socket channel接收到/准备好 一个新的 TCP连接后。
//     * 就会向程序返回一个新的socketChannel。<br>
//     * 但是这个新的socket channel并没有在selector“选择器/代理器”中注册，
//     * 所以程序还没法通过selector通知这个socket channel的事件。
//     * 于是我们拿到新的socket channel后，要做的第一个事情就是到selector“选择器/代理器”中注册这个
//     * socket channel感兴趣的事件
//     *
//     * @param socketChannel 新的socket channel
//     * @param selector      selector“选择器/代理器”
//     * @throws Exception
//     */
//    private static void registerSocketChannel(SocketChannel socketChannel, Selector selector) throws Exception {
//        socketChannel.configureBlocking(false);
//
//        socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(2048));
//    }
//}
