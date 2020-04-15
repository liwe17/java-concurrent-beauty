package com.weiliai.chapter11;

import java.util.concurrent.TimeUnit;

/**
 * @Author: Doug Li
 * @Date 2020/4/15
 * @Describe: 创建线程需要线程名字
 */
public class TestThreadName {

    private static final String THREAD_SAVE_ORDER = "THREAD_SAVE_ORDER";

    private static final String THREAD_SAVE_ADDR = "THREAD_SAVE_ADDR";


    public static void main(String[] args) {
        testThreadName();
//        testThreadNoName();
    }

    public static void testThreadNoName() {
        //订单模块
        Thread threadOne = new Thread(() -> {
            System.out.println("保存订单的线程");
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            throw new NullPointerException();
        });

        //发货模块
        Thread threadTwo = new Thread(() -> {
            System.out.println("保存收获地址的线程");
        });

        //启动线程
        threadOne.start();
        threadTwo.start();

    }

    public static void testThreadName() {
        //订单模块
        Thread threadOne = new Thread(() -> {
            System.out.println("保存订单的线程");
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            throw new NullPointerException();
        },THREAD_SAVE_ORDER);

        //发货模块
        Thread threadTwo = new Thread(() -> {
            System.out.println("保存收获地址的线程");
        },THREAD_SAVE_ADDR);

        //启动线程
        threadOne.start();
        threadTwo.start();

    }


}
