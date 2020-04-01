package com.weiliai.chapter6;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: Doug Li
 * @Date 2020/4/1
 * @Describe: 简单的线程安全集合
 */
public class ReentrantLockList {

    //线程不安全List
    private ArrayList<String> array = new ArrayList<>();

    //独占锁
    private volatile ReentrantLock lock = new ReentrantLock();

    //添加元素
    public void add(String e){
        lock.lock();
        try{
            array.add(e);
        }finally {
            lock.unlock();
        }
    }

    //删除元素
    public void remove(String e){
        lock.lock();
        try{
            array.remove(e);
        }finally {
            lock.unlock();
        }
    }

    //获取数据
    public void get(int index){
        lock.lock();
        try{
            array.get(index);
        }finally {
            lock.unlock();
        }
    }

}
