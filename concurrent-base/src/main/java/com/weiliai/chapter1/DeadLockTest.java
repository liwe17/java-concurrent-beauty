package com.weiliai.chapter1;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * @Author: Doug Li
 * @Date 2020/3/27
 * @Describe: 死锁测试
 */
public class DeadLockTest {

    private static final Object resourceA = new Object();

    private static final Object resourceB = new Object();


    public static void test(){
        final Thread threadA = new Thread(()->{

            synchronized (resourceA){
                System.out.println(Thread.currentThread()+" get resourceA");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread()+" waiting get resourceB");
                synchronized (resourceB){
                    System.out.println(Thread.currentThread()+" get resourceB");
                }
            }
        });

        final Thread threadB = new Thread(()->{

            synchronized (resourceB){
                System.out.println(Thread.currentThread()+" get resourceB");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread()+" waiting get resourceA");
                synchronized (resourceA){
                    System.out.println(Thread.currentThread()+" get resourceA");
                }
            }
        });

        //启动线程
        threadA.start();
        threadB.start();

    }

    //资源的有序性,破坏了请求并持有条件和环路等待条件
    public static void test1(){
        final Thread threadA = new Thread(()->{

            synchronized (resourceA){
                System.out.println(Thread.currentThread()+" get resourceA");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread()+" waiting get resourceB");
                synchronized (resourceB){
                    System.out.println(Thread.currentThread()+" get resourceB");
                }
            }
        });

        final Thread threadB = new Thread(()->{

            synchronized (resourceA){
                System.out.println(Thread.currentThread()+" get resourceA");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread()+" waiting get resourceB");
                synchronized (resourceB){
                    System.out.println(Thread.currentThread()+" get resourceB");
                }
            }
        });

        //启动线程
        threadA.start();
        threadB.start();

    }

    public static void main(String[] args) {
        //test();
        test1();
    }


}
