package com.chaco.chao.io;

import lombok.extern.slf4j.Slf4j;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * author:zhaopeiyan001
 * Date:2019-09-26 16:22
 */
public @Slf4j
class SocketServer3_1 {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(80);
        try {
            while (true) {
                //这里Java通过JNI请求操作系统，并一直等待操作系统返回结果（或者出错）
                Socket socket = serverSocket.accept();

                //下面我们收取信息（这里还是阻塞式的,一直等待，直到有数据可以接受）
                InputStream in = socket.getInputStream();
                OutputStream out = socket.getOutputStream();
                Integer sourcePort = socket.getPort();
                int maxLen = 2048;
                byte[] contextBytes = new byte[maxLen];
                int realLen;
                StringBuffer message = new StringBuffer();
                //read的时候，程序也会被阻塞，直到操作系统把网络传来的数据准备好。
                while ((realLen = in.read(contextBytes, 0, maxLen)) != -1) {
                    message.append(new String(contextBytes, 0, realLen));
                    /**
                     * 假设没读取到over关键字
                     * 表示客户端的所有信息在经过若干次传送后，完成
                     */
                    if (-1 != message.indexOf("over")) {
                        break;
                    }
                }
                log.info("服务器收到来自于端口：" + sourcePort + "的信息：" + message);
                //下面开始发送信息
                out.write("回发响应信息！".getBytes());

                //关闭
                out.close();
                in.close();
                socket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        } finally {
            if (null != serverSocket) {
                serverSocket.close();
            }
        }
    }
}
