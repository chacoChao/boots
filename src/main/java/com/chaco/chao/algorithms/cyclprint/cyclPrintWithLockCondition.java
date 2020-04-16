package com.chaco.chao.algorithms.cyclprint;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * author:zhaopeiyan001
 * Date:2020-04-14 11:51
 */
public class cyclPrintWithLockCondition {
    private static Lock lock = new ReentrantLock();

    private static Condition A = lock.newCondition();
    private static Condition B = lock.newCondition();
    private static Condition C = lock.newCondition();

    private static int count = 0;

    static class ThreadA extends Thread {
        @Override
        public void run() {
            try {
                lock.lock();
                for (int i = 0; i < 10; i++) {
                    while (count % 3 != 0) {
                        //A释放lock锁
                        A.await();
                    }
                    System.out.println("A");
                    count++;
                    //A执行完唤醒B线程
                    B.signal();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    static class ThreadB extends Thread {
        @Override
        public void run() {
            try {
                lock.lock();
                for (int i = 0; i < 10; i++) {
                    while (count % 3 != 1) {
                        //A释放lock锁
                        B.await();
                    }
                    System.out.println("B");
                    count++;
                    //A执行完唤醒B线程
                    C.signal();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    static class ThreadC extends Thread {
        @Override
        public void run() {
            try {
                lock.lock();
                for (int i = 0; i < 10; i++) {
                    while (count % 3 != 2) {
                        //A释放lock锁
                        C.await();
                    }
                    System.out.println("C");
                    count++;
                    //A执行完唤醒B线程
                    A.signal();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        new ThreadA().start();
        new ThreadB().start();
        new ThreadC().start();
    }
}
