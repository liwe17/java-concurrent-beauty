package com.weiliai.chapter11;

import java.util.concurrent.*;

/**
 * @Author: Doug Li
 * @Date 2020/4/15
 * @Describe: 使用FutureTask注意事项
 *  当Future的状态>COMPLETING时才会返回,而DiscardPolicy策略为new
 */
public class TestFutureTaskGet {

//    private static final ThreadPoolExecutor executorService = new ThreadPoolExecutor(1,1,
//            1L, TimeUnit.MINUTES,new ArrayBlockingQueue<>(1),new ThreadPoolExecutor.DiscardPolicy());

    private static final ThreadPoolExecutor executorService = new ThreadPoolExecutor(1,1,
            1L, TimeUnit.MINUTES,new ArrayBlockingQueue<>(1),new MyRejectedExecutionHandler());

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        //添加任务one
        Future<?> futureOne = executorService.submit(() -> {
            System.out.println("start runnable one");
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        //添加任务who
        Future<?> futureTwo = executorService.submit(() -> {
            System.out.println("start runnable two");
        });

        //添加任务three
        Future<?> futureThree=null;
        try{
            futureThree = executorService.submit(()->{
                System.out.println("start runnable three");
            });
        }catch (Exception e){
            System.out.println(e.getLocalizedMessage());
        }


        System.out.println("task one "+futureOne.get());
        System.out.println("task two "+futureTwo.get());
        System.out.println("task three "+ (futureThree==null?null:futureThree.get()));
        try{
            System.out.println("task three "+ (futureThree==null?null:futureThree.get()));
        }catch (Exception e){
            System.out.println(e.getLocalizedMessage());
        }
        System.err.println("开始结束线程池!!!!");
        executorService.shutdown();
    }

    public static class MyRejectedExecutionHandler implements RejectedExecutionHandler{
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            if(!executor.isShutdown()){
                if(r!=null &&r instanceof FutureTask){
                    ((FutureTask) r).cancel(true);
                }
            }
        }
    }

}
