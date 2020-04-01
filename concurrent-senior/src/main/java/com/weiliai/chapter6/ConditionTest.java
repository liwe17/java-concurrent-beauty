package com.weiliai.chapter6;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: Doug Li
 * @Date 2020/4/1
 * @Describe: 条件变量的实例代码,仅作注释
 */
public class ConditionTest {

    //创建一个独占锁,基于AQS实现的锁
    private static final ReentrantLock lock = new ReentrantLock();

    //创建Lock对象的的条件变量
    private static final Condition condition = lock.newCondition();

    public static void main(String[] args) {
        lock.lock(); //首先获取独占锁
        try {
            System.out.println("begin wait");
            condition.await(); //调用条件变量的await阻塞挂起当前线程
            System.out.println("end wait");
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            lock.unlock(); //释放了锁
        }

        lock.lock(); //
        try {
            System.out.println("begin signal");
            condition.signal();
            System.out.println("end signal");
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }

    }


}
