package com.chaco.chao.io;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;

/**
 * author:zhaopeiyan001
 * Date:2019-09-26 12:07
 */
public @Slf4j
class SocketClientRequestThread implements Runnable {

    private CountDownLatch countDownLatch;

    private Integer clientIndex;

    public SocketClientRequestThread(CountDownLatch countDownLatch, Integer clientIndex) {
        this.countDownLatch = countDownLatch;
        this.clientIndex = clientIndex;
    }


    @Override
    public void run() {
        Socket socket = null;
        OutputStream clientRequest = null;
        InputStream clientResponse = null;

        try {
            socket = new Socket("localhost", 80);
            clientRequest = socket.getOutputStream();
            clientResponse = socket.getInputStream();
            // 等待   直到SocketClientDemo完成所有线程的启动，然后说有线程一起发送请求
            this.countDownLatch.await();
            // 发送请求信息
            clientRequest.write(("这是第" + this.clientIndex + "个客户端的请求。").getBytes());
            clientRequest.flush();
            //等待  直到服务器返回信息
            log.info("第" + this.clientIndex + "个客户端的请求发送完成，等待服务器返回信息");
            int maxLen = 1024;
            byte[] contextByte = new byte[maxLen];
            int realLen;
            String message = "";
            //程序执行到这里，会一直等待服务器返回信息（注意，前提是in和out都不能close，如果close了就收不到服务器的反馈了）
            while ((realLen = clientResponse.read(contextByte, 0, maxLen)) != -1) {
                message += new String(contextByte, 0, realLen);
            }
            log.info("接收到来自服务器的信息" + message);
        } catch (IOException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        } finally {
            if (null != clientRequest) {
                try {
                    clientRequest.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    log.error(e.getMessage());
                }
            }
            if (null != clientResponse) {
                try {
                    clientResponse.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    log.error(e.getMessage());
                }
            }
        }
    }
}
