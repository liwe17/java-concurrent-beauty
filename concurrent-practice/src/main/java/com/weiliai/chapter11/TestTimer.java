package com.weiliai.chapter11;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Doug Li
 * @Date 2020/4/14
 * @Describe: Timer问题复现
 */
public class TestTimer {

    //创建定时器对象
    static Timer timer = new Timer();

    public static void main(String[] args) {
        //添加任务1,延迟500ms执行
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.err.println("----one----");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                throw new RuntimeException("error");
            }
        }, 500);

        //添加任务2,延迟500ms执行
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                for (;;) {
                    System.err.println("----two----");
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, 1000);
    }

}
