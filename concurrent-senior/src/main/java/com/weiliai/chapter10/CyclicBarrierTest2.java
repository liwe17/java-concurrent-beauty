package com.weiliai.chapter10;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: Doug Li
 * @Date 2020/4/13
 * @Describe: CyclicBarrier测试2
 */
public class CyclicBarrierTest2 {

    //创建一个CyclicBarrier实例,添加一个所有子线程全部达到屏障后执行的任务
    private static CyclicBarrier cyclicBarrier = new CyclicBarrier(2);

    public static void main(String[] args){
        //创建一个线程个数固定为2的线程池
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        //将线程A添加到线程池
        executorService.submit(() -> {
            try {
                System.out.println(Thread.currentThread() + " step1");
                cyclicBarrier.await();
                System.out.println(Thread.currentThread() + " step2");
                cyclicBarrier.await();
                System.out.println(Thread.currentThread() + " step3");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });


        //将线程B添加到线程池
        executorService.submit(() -> {
            try {
                System.out.println(Thread.currentThread() + " step1");
                cyclicBarrier.await();
                System.out.println(Thread.currentThread() + " step2");
                cyclicBarrier.await();
                System.out.println(Thread.currentThread() + " step3");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        //关闭线程池
        executorService.shutdown();
    }

}
