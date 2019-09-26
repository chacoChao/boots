package com.chaco.chao.io;

import lombok.extern.slf4j.Slf4j;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * author:zhaopeiyan001
 * Date:2019-09-26 14:19
 */
public @Slf4j
class SocketServer1 {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(80);

        try {
            while (true) {
                Socket socket = serverSocket.accept();
                //收取信息
                InputStream in = socket.getInputStream();
                OutputStream out = socket.getOutputStream();
                Integer socketPort = socket.getPort();
                int maxLen = 2048;
                byte[] bytes = new byte[maxLen];
                //这里也会被阻塞，直到有数据准备好
                int realLen = in.read(bytes, 0, maxLen);
                //读取信息
                String message = new String(bytes, 0, realLen);

                log.info("服务器收到来自于端口：" + socketPort + "的信息：" + message);

                //下面开始发送信息
                out.write("回发响应信息！".getBytes());

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
