package com.weiliai.chapter11;

import java.util.TimerTask;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Doug Li
 * @Date 2020/4/14
 * @Describe: 抛出异常其他任务不受影响
 */
public class TestScheduledThreadPoolExecutor {

    private static ScheduledThreadPoolExecutor scheduledExecutor = new ScheduledThreadPoolExecutor(1);

    public static void main(String[] args) {
        //添加任务1,延迟500ms执行
        scheduledExecutor.schedule(()->{
                System.err.println("----one----");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                throw new RuntimeException("error");
            }, 500,TimeUnit.MILLISECONDS);

        //添加任务2,延迟500ms执行
        scheduledExecutor.schedule(()->{
                for (;;) {
                    System.err.println("----two----");
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        }, 1,TimeUnit.SECONDS);

        scheduledExecutor.shutdown();
    }
}
