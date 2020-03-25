package com.weiliai.chapter1;

import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: Doug Li
 * @Date 2020/3/25
 * @Describe: 睡眠测试
 */
public class SleepTest {

    //创建一个独占锁
    private static final Lock lock = new ReentrantLock();

    public static void main(String[] args) {

        //创建线程A
        final Thread threadA = new Thread(()->{
            //获取独占锁
            lock.lock();
            try{
                System.out.println("child threadA is in sleep");
                TimeUnit.SECONDS.sleep(10);
                System.out.println("child threadA is in waked");
            }catch (InterruptedException e){
                e.printStackTrace();
            }finally {
                //释放锁
                lock.unlock();
            }
        });


        //创建线程B
        final Thread threadB = new Thread(()->{
            //获取独占锁
            lock.lock();
            try{
                System.out.println("child threadB is in sleep");
                TimeUnit.SECONDS.sleep(10);
                System.out.println("child threadB is in waked");
            }catch (InterruptedException e){
                e.printStackTrace();
            }finally {
                //释放锁
                lock.unlock();
            }
        });

        //启动线程
        threadA.start();
        threadB.start();
    }

}
