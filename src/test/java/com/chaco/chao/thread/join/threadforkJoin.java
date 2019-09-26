package com.chaco.chao.thread.join;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;

/**
 * author:zhaopeiyan001
 * Date:2019-09-24 19:54
 */
@SpringBootTest
public @Slf4j class threadforkJoin {

    @Test
    public void testJoin() throws InterruptedException {
        PrintTask printTask = new PrintTask(0, 300);
        //创建实例，并执行分割任务
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        forkJoinPool.submit(printTask);
        forkJoinPool.awaitTermination(2, TimeUnit.SECONDS);
        forkJoinPool.shutdown();
    }

    @Data
    @AllArgsConstructor
    private class PrintTask extends RecursiveAction {
        private static final int THRESHOLD = 50;
        private int start;
        private int end;

        @Override
        protected void compute() {
            if (end - start < THRESHOLD) {
                for (int i = start; i < end; i++) {
                    System.out.println(Thread.currentThread().getName() + "的i值：" + i);
                }
            } else {
                int middle = (start + end) / 2;
                PrintTask left = new PrintTask(start, middle);
                PrintTask right = new PrintTask(middle, end);
                //并行两个子任务
                left.join();
                right.join();
            }
        }
    }
}
