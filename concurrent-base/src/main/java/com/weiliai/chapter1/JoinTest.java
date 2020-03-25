package com.weiliai.chapter1;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * @Author: Doug Li
 * @Date 2020/3/25
 * @Describe: join方法测试
 */
public class JoinTest {


    @Test
    public void test() throws InterruptedException {

        final Thread thread = new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(1);
                System.err.println("休眠1s完成");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        });
        thread.start();
        thread.join();
        System.out.println("main执行");

    }



    //线程A调用线程B的join方法后会被阻塞,当其他线程调用了线程A的interrupt()方法中断了线程A,线程A会抛出InterruptedException
    @Test
    public void test1(){

        //线程one
        final Thread threadOne = new Thread(()->{
            System.out.println("threadOne begin run!");
            for(;;){

            }
        });

        final Thread mainThread = Thread.currentThread();

        //线程two
        final Thread threadTwo = new Thread(()->{
            //休眠1s
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mainThread.interrupt();
            //threadOne.interrupt();
        });

        //启动子线程
        threadOne.start();

        //延迟1s启动线程
        threadTwo.start();

        try {
            threadOne.join();
        } catch (InterruptedException e) {
            System.out.println("main thread"+e);
        }

    }

}
