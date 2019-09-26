package com.chaco.chao.io;

import java.util.concurrent.CountDownLatch;

/**
 * author:zhaopeiyan001
 * Date:2019-09-25 16:48
 */
public class SocketClientDemo {
    public static void main(String[] args) throws InterruptedException {
        Integer clientNumber = 20;
        CountDownLatch countDownLatch = new CountDownLatch(clientNumber);

        //分别启动20个客户端
        for (int index = 0; index < clientNumber; index++, countDownLatch.countDown()) {
            SocketClientRequestThread client = new SocketClientRequestThread(countDownLatch, index);
            new Thread(client).start();
        }

        synchronized (SocketClientDemo.class) {
            SocketClientDemo.class.wait();
        }
    }
}
