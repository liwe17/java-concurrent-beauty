package com.weiliai.chapter11;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Doug Li
 * @Date 2020/4/15
 * @Describe: TODO
 */
public class ThreadPoolTest {

    static class LocalVariable{
        private long [] a = new long[1024*1024];
    }

    private static final ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(5,5,
            1, TimeUnit.MINUTES,new LinkedBlockingQueue<>());

    private static final ThreadLocal<LocalVariable> localVariable = new ThreadLocal<>();

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 50; i++) {
            poolExecutor.execute(()->{
                localVariable.set(new LocalVariable());
                System.out.println("use local variable");
//                localVariable.remove();
            });
        }
        TimeUnit.SECONDS.sleep(1);

        System.out.println("pool executor over");

//        poolExecutor.shutdown();
    }
}
