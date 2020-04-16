package com.chaco.chao.algorithms.cyclprint;

import lombok.extern.java.Log;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * author:zhaopeiyan001
 * Date:2020-04-07 17:45
 */
@Log
public class CyclPrint {
    private ReentrantLock lock = new ReentrantLock();

    private Condition condition = lock.newCondition();
    private Condition condition1 = lock.newCondition();

    private boolean hasValue = false;

    public void set() {
        try {
            lock.lock();
            while (hasValue) {
                condition.await();
            }
            log.info("打印A");
            hasValue = true;
            condition1.signal();
        } catch (Exception e) {
            e.printStackTrace();
            log.info("error:" + e.getMessage());
        } finally {
            lock.unlock();
        }
    }

    public void get() {
        try {
            lock.lock();
            while (!hasValue) {
                condition1.await();
            }
            log.info("打印B");
            hasValue = false;
            condition.signal();
        } catch (Exception e) {
            log.info("error:" + e.getMessage());
        } finally {
            lock.unlock();
        }
    }


    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                CyclPrint cyclPrint = new CyclPrint();
            }
        }).start();
    }
}
