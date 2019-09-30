package com.chaco.chao.io;

import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.channels.Selector;

/**
 * author:zhaopeiyan001
 * Date:2019-09-26 18:13
 */
public @Slf4j
class SocketServer3_2 {
    private static Object xWait = new Object();

    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(80);
            serverSocket.setSoTimeout(100);
            while (true) {
                Socket socket = null;
                try {
                    socket = serverSocket.accept();
                } catch (SocketTimeoutException ste) {
                    //===========================================================
                    //      执行到这里，说明本次accept没有接收到任何数据报文
                    //      主线程在这里就可以做一些事情，记为X
                    //===========================================================
                    synchronized (SocketServer3_2.xWait) {
                        log.info("这次没有从底层接收到任务数据报文，等待10毫秒，模拟事件X的处理时间");
                        SocketServer3_2.xWait.wait(10);
                    }
                    continue;
                }
                SocketServerThread socketServerThread = new SocketServerThread(socket);
                new Thread(socketServerThread);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            if (null != serverSocket) {
                serverSocket.close();
            }
        }
    }
}
