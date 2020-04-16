package com.chaco.chao.algorithms.cyclprint;

import java.util.concurrent.Semaphore;

/**
 * author:zhaopeiyan001
 * Date:2020-04-14 14:05
 */
public class cyclSemaphore {
    //start with A semaphore limit 1
    private static Semaphore A = new Semaphore(1);
    //B\C    default limit 0
    private static Semaphore B = new Semaphore(0);
    private static Semaphore C = new Semaphore(0);

    static class ThreadA extends Thread {
        @Override
        public void run() {
            try {
                for(int i = 0; i < 10; i++) {
                    // A获取信号执行,A信号量减1,当A为0时将无法继续获得该信号量
                    A.acquire();
                    System.out.println("A");
                    // B释放信号，B信号量加1（初始为0），此时可以获取B信号量
                    B.release();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    static class ThreadB extends Thread {
        @Override
        public void run() {
            try {
                for(int i = 0; i < 10; i++) {
                    // A获取信号执行,A信号量减1,当A为0时将无法继续获得该信号量
                    B.acquire();
                    System.out.println("B");
                    // B释放信号，B信号量加1（初始为0），此时可以获取B信号量
                    C.release();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    static class ThreadC extends Thread {
        @Override
        public void run() {
            try {
                for(int i = 0; i < 10; i++) {
                    // A获取信号执行,A信号量减1,当A为0时将无法继续获得该信号量
                    C.acquire();
                    System.out.println("C");
                    // B释放信号，B信号量加1（初始为0），此时可以获取B信号量
                    A.release();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new ThreadA().start();
        new ThreadB().start();
        new ThreadC().start();
    }
}
