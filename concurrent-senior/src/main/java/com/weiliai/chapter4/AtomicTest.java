package com.weiliai.chapter4;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author: Doug Li
 * @Date 2020/3/30
 * @Describe: 统计个数
 */
public class AtomicTest {

    //创建Long型原子计数器
    private static AtomicLong atomicLong = new AtomicLong();

    //创建数据源
    private static Integer[] arrayOne = new Integer[]{0,1,2,3,0,5,6,0,56,0};
    private static Integer[] arrayTwo = new Integer[]{10,1,2,3,0,5,6,0,56,0};


    public static void main (String[] args) throws InterruptedException {
        //线程one统计数组one中的个数
        Thread threadOne = new Thread(()->{
            for (int i = 0,length=arrayOne.length; i <length; i++) {
                if(arrayOne[i]==0){
                    atomicLong.incrementAndGet();
                }
            }
        });

        //线程Two统计数组two中的个数
        Thread threadTwo = new Thread(()->{
            for (int i = 0,length=arrayTwo.length; i <length; i++) {
                if(arrayTwo[i]==0){
                    atomicLong.incrementAndGet();
                }
            }
        });

        //开启线程
        threadOne.start();
        threadTwo.start();

        //等待线程执行完毕
        threadOne.join();
        threadTwo.join();

        System.out.println("count-0: "+atomicLong.get());
    }

}
