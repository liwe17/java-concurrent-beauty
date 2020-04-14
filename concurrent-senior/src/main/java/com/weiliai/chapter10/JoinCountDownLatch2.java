package com.weiliai.chapter10;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Doug Li
 * @Date 2020/4/13
 * @Describe: CountDownLatch测试1
 */
public class JoinCountDownLatch2 {

    //创建一个CountDownLatch实例
    private static volatile CountDownLatch countDownLatch = new CountDownLatch(2);

    public static void main(String[] args) throws InterruptedException{

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.submit(()->{
            try {
                TimeUnit.SECONDS.sleep(1);
                System.out.println("child threadOne over!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                countDownLatch.countDown();
            }
        });

        executorService.submit(()->{
            try {
                TimeUnit.SECONDS.sleep(1);
                System.out.println("child threadTwo over!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                countDownLatch.countDown();
            }
        });

        System.out.println("wait all child thread over!");

        //等待子线程执行完毕,返回
        countDownLatch.await();
        System.out.println("all child thread over!");
        executorService.shutdown();
    }
}
