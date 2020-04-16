package com.chaco.chao.algorithms.cyclprint;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * author:zhaopeiyan001
 * Date:2020-04-14 13:42
 */
public class cucl_Synch {
    @Data
    @AllArgsConstructor
    public static class ThreadPrinter implements Runnable {

        private String name;
        private Object prev;
        private Object self;

        @Override
        public void run() {
            int count = 10;
            while (count > 0) {
                //多线程并发使用while
                synchronized (prev) {
                    synchronized (self) {
                        System.out.println(name);
                        count--;
                        self.notifyAll();
                    }
                    try {
                        if (0 == count) {
                            prev.notifyAll();
                        } else {
                            prev.wait();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Object a = new Object();
        Object b = new Object();
        Object c = new Object();
        ThreadPrinter pa = new ThreadPrinter("A", c, a);
        ThreadPrinter pb = new ThreadPrinter("B", a, b);
        ThreadPrinter pc = new ThreadPrinter("C", b, c);

        new Thread(pa).start();
        Thread.sleep(10);
        new Thread(pb).start();
        Thread.sleep(10);
        new Thread(pc).start();
        Thread.sleep(10);
    }
}
