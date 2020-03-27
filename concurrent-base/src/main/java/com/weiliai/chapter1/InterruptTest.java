package com.weiliai.chapter1;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * @Author: Doug Li
 * @Date 2020/3/27
 * @Describe: 线程中断测试
 */
public class InterruptTest {


    //interrupt()测试
    @Test
    public void test() throws InterruptedException {

        final Thread thread = new Thread(()->{
            //如果当前线程被中断,则退出线程
            while(!Thread.currentThread().isInterrupted()){
                System.out.println(Thread.currentThread()+" hello");
            }
        });

        //启动子线程
        thread.start();
        //主线程休眠1S,以便中断前让子线程输出
        TimeUnit.SECONDS.sleep(1);

        //中断子线程
        System.out.println("main thread interrupt child thread");
        thread.interrupt();

        //等待子线程执行完毕
        thread.join();
        System.out.println("main is over");
    }


    //打断线程,恢复到激活状态
    @Test
    public void test1() throws InterruptedException {
        final Thread threadOne = new Thread(()->{
            try {
                System.out.println("threadOne begin sleep for 2000 seconds");
                TimeUnit.SECONDS.sleep(2000);
                System.out.println("threadOne awaking");
            } catch (InterruptedException e) {
                System.out.println("threadOne is interrupted while sleeping");
            }
            System.out.println("threadOne-leaving normally");
        });

        //启动线程
        threadOne.start();

        //确保子线程进行休眠状态,主线程休眠1S
        TimeUnit.SECONDS.sleep(1);

        //打断子线程休眠,让子线程sleep函数返回
        threadOne.interrupt();
        //等待子线程执行完毕
        threadOne.join();

        System.out.println("main thread is over");

    }


    //获取线程打断状态
    @Test
    public void test2(){

        final Thread thread = new Thread(()->{
            for(;;){

            }
        });

        //启动线程
        thread.start();
        //设置中断标志
        thread.interrupt();

        //获取中断标识
        System.out.println("isInterrupted: " +thread.isInterrupted()); //true

        //获取中断标志,并重置
        System.out.println("interrupted: " +thread.interrupted()); //false,主线程依然激活状态

        //获取中断标识,并重置
        System.out.println("interrupted: " +Thread.interrupted()); //false,主线程依然是激活状态

        //获取中断标志
        System.out.println("isInterrupted: " +thread.isInterrupted()); //true
    }













}
