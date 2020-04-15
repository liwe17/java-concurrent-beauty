#并发编程实践
##ArrayBlockingQueue的使用
logback异步日志打印中的ArrayBlockingQueue的使用

###异步日志打印模型概述
在高并发,高流量并且响应时间要求比较小的系统中同步打印日志在性能上已经满足不了了,这是以因为打印本身是需要写磁盘的,写磁盘操作会暂时阻
塞调用打印日志的业务系统,这会造成调用线程的响应时间增加.

异步日志打印,是将打印日志任务放入一个队列后就返回,然后使用一个线程专门从队列中获取日志任务,并将其写入磁盘.


##Tomcat的NioEndPoint中的ConcurrentLinkedQueue


##并发组件ConcurrentHashMap使用注意事项
涉及的代码:com.weiliai.chapter11.TestMap


##SimpleDateFormat是线程不安全的
SimpleDateFormat是Java提供的一个格式化和解析日期的工具类,在日常开发中常用到,但是由于它是线程不安全的,所以多线程共用一个
SimpleDateFormat实例对日期进行解析或者格式化会导致程序出错.

涉及的代码:
com.weiliai.chapter11.TestSimpleDateFormat
com.weiliai.chapter11.TestSimpleDateFormat2

##使用Timer时需要注意的事情
当一个Timer运行多个TimerTask时,只要其中一个TimerTask在执行中向run方法外抛出了异常,则其他任务也会自动终止.

Timer固定的多生产者单消费者模型
ScheduledThreadPoolExecutor是可配置的.

所以开发中优先选用ScheduledThreadPoolExecutor.

涉及的代码:
com.weiliai.chapter11.TestTimer
com.weiliai.chapter11.TestScheduledThreadPoolExecutor


##对需要复用但是会被下游修改的参数要进行深复制
引用元素进行复制需要进行深复制


##创建线程和线程池时要指定与业务相关的名称
在日常开发中,当一个应用中需要创建多个线程或者线程池时最好给每个线程或者线程池根据业务类型设置具体的名称,以便在出现问题时方便进行定位

创建线程需要有线程名,涉及的代码:
com.weiliai.chapter11.TestThreadName

创建线程池也需要指定线程池的名称,涉及的代码:
com.weiliai.chapter11.TestThreadPoolName

##使用线程池的情况下当程序结束时,记得调用shutdown关闭线程池

##线程池使用FutureTask时需要注意的事情
线程池使用FutureTask时如果把拒绝策略设置为DiscardPolicy和DiscardOldestPolicy,并且在被拒绝的任务的Future对象上调用了无惨get方法,
那么调用线程会被一致阻塞.

涉及的代码:com.weiliai.chapter11.TestFutureTaskGet

##使用ThreadLocal不当可能会导致内存泄漏


##参考资料
https://www.cnblogs.com/nxzblogs/p/11355528.html