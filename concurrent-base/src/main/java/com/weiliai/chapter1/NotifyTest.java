package com.weiliai.chapter1;

/**
 * @Author: Doug Li
 * @Date 2020/3/24
 * @Describe: 通知测试
 */
public class NotifyTest {

    private static volatile Object resourceA = new Object();

    public static void main(String[] args) throws InterruptedException {

        //创建线程
        final Thread threadA = new Thread(()->{
            //获取resourceA共享资源的监视器锁
            synchronized (resourceA){
                System.out.println("threadA get resourceA lock");
                try{
                    System.out.println("threadA begin wait");
                    resourceA.wait();
                    System.out.println("threadA end wait");
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        });

        //创建线程
        final Thread threadB = new Thread(()->{
            //获取resourceA共享资源的监视器锁
            synchronized (resourceA){
                System.out.println("threadB get resourceA lock");
                try{
                    System.out.println("threadB begin wait");
                    resourceA.wait();
                    System.out.println("threadB end wait");
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        });


        //创建线程
        final Thread threadC = new Thread(()->{
            //获取resourceA共享资源的监视器锁
            synchronized (resourceA){
                //resourceA.notify();
                resourceA.notifyAll();
            }
        });

        //启动线程
        threadA.start();
        threadB.start();

        Thread.sleep(100);
        threadC.start();

        //等待线程结束
        threadA.join();
        threadB.join();
        threadC.join();

        System.out.println("main over");
    }



}
