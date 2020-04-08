package com.weiliai.chapter7;

import java.util.Random;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * @Author: Doug Li
 * @Date 2020/4/8
 * @Describe: 测试优先级队列
 */
public class TestPriorityBlockingQueue {

    static class Task implements Comparable<Task>{

        private int priority = 0;

        private String taskName;

        public int getPriority() {
            return priority;
        }

        public void setPriority(int priority) {
            this.priority = priority;
        }

        public String getTaskName() {
            return taskName;
        }

        public void setTaskName(String taskName) {
            this.taskName = taskName;
        }

        @Override
        public int compareTo(Task o) {
            return  this.priority>=o.getPriority()?1:-1;
        }

        public void doSomeThing(){
            System.out.println(taskName+":"+priority);
        }
    }

    public static void main(String[] args) {
        //创建任务,并添加队列
        final PriorityBlockingQueue<Task> queue = new PriorityBlockingQueue<>();
        final Random random = new Random();
        Task task;
        for(int i =0 ;i<10;i++){
            task = new Task();
            task.setPriority(random.nextInt(10));
            task.setTaskName("taskName"+i);
            queue.offer(task);
        }
        //取出执行任务
        Task poll;
        while(!queue.isEmpty()){
            poll = queue.poll();
            if(null!=poll) {
                poll.doSomeThing();
            }
        }
    }

}
