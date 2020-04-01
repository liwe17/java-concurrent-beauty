package com.weiliai.chapter6;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Condition;

/**
 * @Author: Doug Li
 * @Date 2020/4/1
 * @Describe: 自定义不可重入锁测试
 * 生产-消费模型
 */
public class NonReentrantLockTest {

    static final NonReentrantLock lock = new NonReentrantLock();

    static final Condition notFull = lock.newCondition();

    static final Condition notEmpty = lock.newCondition();

    static final Queue<String> queue = new LinkedBlockingQueue<>();

    static final int QUEUE_SIZE = 10;

    public static void main(String[] args) {

        //生产者
        Thread producer = new Thread(()->{
            //获取独占锁
            lock.lock();
            try{
                //如果队列满了,则等待
                while(QUEUE_SIZE == queue.size()){
                    notEmpty.await();
                }
                //添加元素到队列
                queue.add("ele");
                //唤醒消费线程
                notFull.signalAll();
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                //释放锁
                lock.unlock();
            }
        });

        //消费者
        Thread consumer = new Thread(()->{
            //获取独占锁
            lock.lock();
            try{
                //队列为空,则等待
                while(0==queue.size()){
                    notFull.await();
                }
                //消费一个元素
                String ele = queue.poll();
                //唤醒生成线程
                notEmpty.signalAll();
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        });

        //启动线程
        producer.start();
        consumer.start();
    }

}
