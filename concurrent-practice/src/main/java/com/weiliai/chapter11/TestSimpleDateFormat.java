package com.weiliai.chapter11;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: Doug Li
 * @Date 2020/4/14
 * @Describe: 测试SimpleDateFormat线程不安全
 */
public class TestSimpleDateFormat {

    //创建单例实例
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) {
        final ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            executorService.execute(()->{
                try {
                    System.err.println(Thread.currentThread()+" : "+sdf.parse("2020-04-14 23:07:00"));
                } catch (Exception e) {
//                    e.printStackTrace();
                    System.err.println(Thread.currentThread());
                }
            });
        }

        executorService.shutdown();
    }

}
