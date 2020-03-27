package com.weiliai.chapter1;

/**
 * @Author: Doug Li
 * @Date 2020/3/28
 * @Describe: ThreadLocal不支持继承
 */
public class TestThreadLocal {

    //创建线程变量
//    public static ThreadLocal<String> threadLocal = new ThreadLocal<>();
    public static ThreadLocal<String> threadLocal = new InheritableThreadLocal<>();

    public static void main(String[] args) {
        //设置线程变量
        threadLocal.set("hello world");
        //启动子线程
        new Thread(()->{
            //子线程输出线程变量的值
            System.out.println("thread:"+threadLocal.get());
        }).start();

        //主线程输出线程变量的值
        System.out.println("main:"+threadLocal.get());
    }

}
