package com.chaco.chao.io;

import lombok.AllArgsConstructor;
import lombok.Data;
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
class SocketServer2 {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(80);

        try {
            while (true) {
                Socket socket = serverSocket.accept();
                //当然业务处理过程可以交给一个线程（这里可以使用线程池）,并且线程的创建是很耗资源的。
                //最终改变不了.accept()只能一个一个接受socket的情况,并且被阻塞的情况
                SocketServerThread socketServerThread = new SocketServerThread(socket);
                new Thread(socketServerThread).start();
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

@Data
@Slf4j
@AllArgsConstructor
class SocketServerThread implements Runnable {

    private Socket socket;

    @Override
    public void run() {
        InputStream in = null;
        OutputStream out = null;
        try {
            in = socket.getInputStream();
            out = socket.getOutputStream();
            Integer socketPort = socket.getPort();
            int maxLen = 1024;
            byte[] contextBytes = new byte[maxLen];
            //使用线程，同样无法解决read方法的阻塞问题，
            //也就是说read方法处同样会被阻塞，直到操作系统有数据准备好
            int realLen = in.read(contextBytes, 0, maxLen);
            //读取信息
            String message = new String(contextBytes, 0, realLen);

            log.info("服务器收到来自于端口：" + socketPort + "的信息：" + message);

            out.write("回发响应信息！".getBytes());
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        } finally {
            try {
                if (null != in) {
                    in.close();
                }
                if (null != out) {
                    out.close();
                }
                if (null != socket) {
                    socket.close();
                }
            } catch (Exception r) {
                r.printStackTrace();
                log.error(r.getMessage());
            }
        }
    }
}
