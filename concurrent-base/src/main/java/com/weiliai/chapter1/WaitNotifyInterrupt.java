package com.weiliai.chapter1;

import org.junit.Test;

/**
 * @Author: Doug Li
 * @Date 2020/3/23
 * @Describe: 当一个线程调用共享对象wait()方法被阻塞挂起后,如果其他线程中断了线程,则该线程会抛出InterruptException异常返回
 */
public class WaitNotifyInterrupt {

    static Object obj = new Object();

    public static void main(String[] args) throws InterruptedException {

        //创建线程A
        final Thread threadA = new Thread(()->{
            try {
                System.out.println("----begin----");
                synchronized (obj){
                    obj.wait();
                }
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        });

        threadA.start();
        Thread.sleep(1000);

        System.out.println("----begin interrupt threadA----");
        threadA.interrupt();
        System.out.println("----end interrupt threadA----");
    }

}
