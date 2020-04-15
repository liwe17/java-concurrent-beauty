package com.weiliai.chapter11;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: Doug Li
 * @Date 2020/4/15
 * @Describe: 测试线程池的名称
 */
public class TestThreadPoolName {

    //不定义线程池名称
    private static final ThreadPoolExecutor executorOne = new ThreadPoolExecutor(5, 5,
            1, TimeUnit.MINUTES, new LinkedBlockingQueue<>());
    private static final ThreadPoolExecutor executorTwo = new ThreadPoolExecutor(5, 5,
            1, TimeUnit.MINUTES, new LinkedBlockingQueue<>());

    //定义线程池名称的工厂
    private static final ThreadPoolExecutor executorThree = new ThreadPoolExecutor(5, 5,
            1, TimeUnit.MINUTES, new LinkedBlockingQueue<>(), new NamedThreadFactory("ASYNC-ACCEPT-POOL"));
    private static final ThreadPoolExecutor executorFour = new ThreadPoolExecutor(5, 5,
            1, TimeUnit.MINUTES, new LinkedBlockingQueue<>(), new NamedThreadFactory("ASYNC-PROCESS-POOL"));

    public static void main(String[] args) {
        testThreadPoolName();
//        testThreadPoolNoName();
    }

    public static void testThreadPoolNoName() {
        //接受用户链接模块
        executorOne.execute(() -> {
            System.out.println("接受用户链接线程!");
            throw new NullPointerException();
        });
        //具体处理用户请求模块
        executorOne.execute(() -> {
            System.out.println("具体处理用户请求线程!");
        });

        executorOne.shutdown();
        executorTwo.shutdown();
    }


    public static void testThreadPoolName() {
        //接受用户链接模块
        executorThree.execute(() -> {
            System.out.println("接受用户链接线程!");
            throw new NullPointerException();
        });
        //具体处理用户请求模块
        executorFour.execute(() -> {
            System.out.println("具体处理用户请求线程!");
        });

        executorThree.shutdown();
        executorFour.shutdown();
    }


    static class NamedThreadFactory implements ThreadFactory {
        private static final AtomicInteger poolNumber = new AtomicInteger(1);
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

        NamedThreadFactory(String name) {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() :
                    Thread.currentThread().getThreadGroup();
            if (null == name || name.isEmpty()) {
                name = "pool-";
            }
            namePrefix = name + "-" +
                    poolNumber.getAndIncrement() +
                    "-thread-";
        }

        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r,
                    namePrefix + threadNumber.getAndIncrement(),
                    0);
            if (t.isDaemon())
                t.setDaemon(false);
            if (t.getPriority() != Thread.NORM_PRIORITY)
                t.setPriority(Thread.NORM_PRIORITY);
            return t;
        }
    }
}
