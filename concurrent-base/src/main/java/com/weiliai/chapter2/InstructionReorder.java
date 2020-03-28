package com.weiliai.chapter2;

/**
 * @Author: Doug Li
 * @Date 2020/3/28
 * @Describe: 指令重排序
 */
public class InstructionReorder {

    private static int num = 0;

    private static boolean ready = false;

    public static class ReadThread extends Thread{

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()){
                if(ready){
                    System.out.println(num+num);
                }
                System.out.println("read thread...");
            }
        }
    }

    public static class WriteThread extends Thread{

        @Override
        public void run() {
            //前两行代码执行顺序不一定,可以用volatile修饰ready,保证可见性和避免重排序
            num = 2;
            ready = true;
            System.out.println("writeThread set over...");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        final ReadThread rt = new ReadThread();
        rt.start();

        final WriteThread wt = new WriteThread();
        wt.start();

        Thread.sleep(10);
        rt.interrupt();
        System.out.println("main exit");
    }

}
