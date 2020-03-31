package com.weiliai.chapter6;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.LockSupport;

/**
 * @Author: Doug Li
 * @Date 2020/3/31
 * @Describe: 先进先出的锁
 */
public class FIFOMutex {

    private static final AtomicBoolean locked = new AtomicBoolean(false);

    private static final Queue<Thread> waiters = new ConcurrentLinkedQueue<>();

    public void lock(){
        boolean wasInterrupted = false;
        Thread current = Thread.currentThread(); //获取当前线程
        waiters.add(current); //加入等待队列

        //只有队首的线程可以获取锁
        //1.队首不是本线程,则park自己
        //2.队首是自己,但CAS锁状态失败,继续park自己
        while(waiters.peek() != current||!locked.compareAndSet(false,true)){
            LockSupport.park(this);
            //线程阻塞到这里
            if(Thread.interrupted()){//如果park方法是因为中断返回,则忽略中断,并且重置中断标志
                wasInterrupted = true;
            }
        }

        waiters.remove();
        //虽然我们对中断信号不关注,但是其他线程可能关注,恢复即可
        if(wasInterrupted){
            current.interrupt();
        }
    }

    //释放锁
    public void unlock(){
        locked.set(false);
        //唤醒队首线程
        LockSupport.unpark(waiters.peek());
    }

}
