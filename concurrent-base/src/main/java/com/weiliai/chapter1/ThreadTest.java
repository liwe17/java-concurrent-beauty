package com.weiliai.chapter1;

import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * @Author: Doug Li
 * @Date 2020/3/21
 * @Describe: 线程的创建和运行
 */
public class ThreadTest {

    //继承Thread类,并重写run方法
    public static class MyThread extends Thread{

        @Override
        public void run() {
            System.out.println("I am a child thread");
        }
    }

    //实现Runnable接口,实现run方法
    public static class RunnableTask implements Runnable{

        public void run() {
            System.out.println("I am a child thread");
        }
    }


    //创建任务类
    public static class CallerTask implements Callable<String> {
        public String call() throws Exception {
            return "hello";
        }
    }


    @Test
    public void testThread(){
        //创建线程
        MyThread thread = new MyThread();
        //启动线程
        thread.start();
    }

    @Test
    public void testRunnable(){
        RunnableTask task = new RunnableTask();
        Thread thread = new Thread(task);
        Thread thread2 = new Thread(task);
        thread.start();
        thread2.start();
    }

    @Test
    public void testCallable() throws Exception {
        //创建异步任务
        FutureTask<String> futureTask = new FutureTask<>(new CallerTask());
        //启动线程
        new Thread(futureTask).start();
        System.out.println(futureTask.get());
    }


}
