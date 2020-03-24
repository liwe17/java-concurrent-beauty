package com.weiliai.chapter1;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * @Author: Doug Li
 * @Date 2020/3/24
 * @Describe: 虚假唤醒
 */
public class SpuriousWakeupTest {

    static final Queue<Object> queue = new ArrayBlockingQueue<Object>(1);

    static class ProductThread extends Thread{
        @Override
        public void run() {
            synchronized (queue){
                while(queue.size()==1){ //假设队列只存储一个元素,如果while->if就会出现虚假唤醒
                    try {
                        queue.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                queue.add(new Object());
                queue.notifyAll();
            }
        }
    }


    static class ConsumerThead extends Thread{
        @Override
        public void run() {
            synchronized (queue){
                while(queue.isEmpty()){
                    try {
                        queue.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                //检索并移除头部元素
                queue.poll();
                queue.notifyAll();
            }
        }
    }

}
