package com.weiliai.chapter6;

import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * @Author: Doug Li
 * @Date 2020/3/31
 * @Describe: LockSupport类
 */
public class LockSupportTest {

    public static void main(String[] args) {

    }

    @Test
    public void testPark() throws InterruptedException {
        final Thread mainThread = Thread.currentThread();
        System.out.println("begin park");
        Thread thread = new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mainThread.interrupt();
        });
        thread.start();
        LockSupport.park();

        thread.join();
        System.out.println("end park");
    }


    @Test
    public void testUnpark(){
        System.out.println("begin park");
        LockSupport.unpark(Thread.currentThread());
        LockSupport.park();
        System.out.println("end park");
    }

    @Test
    public void testParkNanos(){
        System.out.println("begin park");
        LockSupport.parkNanos(100);
        System.out.println("end park");
    }

}
