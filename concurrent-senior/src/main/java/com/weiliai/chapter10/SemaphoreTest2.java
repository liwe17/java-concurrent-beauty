package com.weiliai.chapter10;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @Author: Doug Li
 * @Date 2020/4/14
 * @Describe: 信号量测试2
 */
public class SemaphoreTest2 {

    //创建一个Semaphore实例
    private static Semaphore semaphore = new Semaphore(0);

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        //将线程A添加到线程池
        executorService.submit(()->{
            System.out.println(Thread.currentThread() + " A task over");
            //每当调用了一次release方法后，许可就会累加1
            semaphore.release();
        });

        //将线程B添加到线程池
        executorService.submit(()->{
            System.out.println(Thread.currentThread() + " A task over");
            //每当调用了一次release方法后，许可就会累加1
            semaphore.release();
        });

        //等待子线程执行完毕,返回
        semaphore.acquire(2);

        //将线程C添加到线程池
        executorService.submit(()->{
            System.out.println(Thread.currentThread() + " B task over");
            //每当调用了一次release方法后，许可就会累加1
            semaphore.release();
        });

        //将线程D添加到线程池
        executorService.submit(()->{
            System.out.println(Thread.currentThread() + " B task over");
            //每当调用了一次release方法后，许可就会累加1
            semaphore.release();
        });

        //等待子线程执行完毕,返回
        semaphore.acquire(2);
        System.out.println("all child thread over!");

        //关闭线程池
        executorService.shutdown();
    }
}
