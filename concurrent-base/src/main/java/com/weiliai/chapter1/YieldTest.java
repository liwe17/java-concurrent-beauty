package com.weiliai.chapter1;

/**
 * @Author: Doug Li
 * @Date 2020/3/25
 * @Describe: 让出CPU执行权的yield方法
 */
public class YieldTest implements Runnable{

    public YieldTest() {
        //创建并启动线程
        new Thread(this).start();
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            //当i=0时让出CPU执行权,放弃时间片,进行下一轮调度
            if(i%5==0){
                System.out.println(Thread.currentThread()+"yield cpu......");

                //当前线程让出CPU执行权,放弃时间片,进行下一轮调度
                Thread.yield();
            }
        }
        System.out.println(Thread.currentThread()+"is over");
    }

    public static void main(String[] args) {
        new YieldTest();
        new YieldTest();
        new YieldTest();
    }

}
