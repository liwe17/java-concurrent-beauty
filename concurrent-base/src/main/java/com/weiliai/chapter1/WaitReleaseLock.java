package com.weiliai.chapter1;

import java.util.concurrent.TimeUnit;

/**
 * @Author: Doug Li
 * @Date 2020/3/24
 * @Describe: 线程调用共享变量的wait()方法,只会释放当前共享变量的监视器锁
 */
public class WaitReleaseLock {

    //创建资源
    private static volatile Object resourceA = new Object();
    private static volatile Object resourceB = new Object();

    public static void main(String[] args) throws InterruptedException {

        //创建线程
        final Thread threadA = new Thread(()->{

            //获取resourceA共享资源的监视器锁
            synchronized (resourceA){
                System.out.println("threadA get resourceA lock");

                //获取resourceB共享资源监视器锁
                synchronized (resourceB){
                    System.out.println("threadA get resourceB lock");

                    //线程A阻塞,并释放获取到的resourceA的锁
                    System.out.println("threadA release resourceA lock");
                    try {
                        resourceA.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        });


        //创建线程
        final Thread threadB = new Thread(()->{
            try {
                //休眠1S
                TimeUnit.SECONDS.sleep(1);
                //获取resourceA共享资源的监视器锁
                synchronized (resourceA){
                    System.out.println("threadB get resourceA lock");
                    System.out.println("threadB try get resourceA lock");

                    //获取resourceB共享资源监视器锁
                    synchronized (resourceB){
                        System.out.println("threadB get resourceB lock");

                        //线程A阻塞,并释放获取到的resourceA的锁
                        System.out.println("threadB release resourceA lock");
                        resourceA.wait();
                    }

                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        });


        //启动线程
        threadA.start();
        threadB.start();

        //等待两个线程结束
        threadA.join();
        threadB.join();

        System.out.println("main over");
    }

}
