package com.weiliai.chapter1;

/**
 * @Author: Doug Li
 * @Date 2020/3/27
 * @Describe: 守护线程
 */
public class DaemonTest {


    public static void main(String[] args) {
        final Thread thread = new Thread(()->{
            for(;;){

            }
        });

        //设置守护线程
        thread.setDaemon(true);
        thread.start();
    }

}
