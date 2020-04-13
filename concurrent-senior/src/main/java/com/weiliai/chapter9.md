#Java并发包中ScheduledThreadPoolExecutor原理探究
ScheduledThreadPoolExecutor:在指定一定延迟时间后或者定时进行任务调度执行的线程池.

ScheduledTheadPoolExecutor继承了ThreadPoolExecutor并实现了ScheduledExecutorService接口,线程池队列为DelayedWorkQueue(类似队列
DelayedQueue)是延迟队列.

ScheduledFutureTask具有返回值的任务,继承自FutureTask,内部有一个变量state用来表示任务状态,一开始状态为NEW

##重要方法
schedule(Runnable command,long delay,TimeUnit unit)
提交一个延迟执行的队伍,任务从提交时间算起延迟单位unit的delay时间后开始执行,提交的任务不是周期性任务,任务只会执行一次

当任务执行完毕后,让其延迟固定时间后再次运行(fixed-delay任务),initialDelay表示提交任务后延迟多少时间开始执行任务,delay表示当任务执
行完毕后延长多少时间再次运行该任务,unit是delay和initialDelay时间单位,任务会一直重复运行直到任务运行中抛出了异常,被取消了,或者关闭
了线程池<=>例如:每天固定4:00执行任务
scheduleWithFixedDelay(Runnable command,long initialDelay,long delay,TimeUnit unit)

相对起始时间以固定频率调用指定的任务(fixed-rate任务),当任务提交到线程池并延迟initialDelay时间(单位unit)后开始执行任务,然后从
initialDelay+period时间点再次执行,而后从initialDelay+2*period时间点再次执行,循环往复,直到抛出了异常或者调用任务cancel方法取消,
或者关闭了线程池<=>例如每2秒执行一次
scheduleWithFixedRate(Runnable command,long initialDelay,long period,TimeUnit unit)

##总结
ScheduledThreadPoolExecutor内部使用了DelayQueue来存放具体任务.任务类型使用period区分
任务分为三种:
1.一次性任务,period=0
2.fixed-delay保证同一个任务多次执行之间间隔固定时间,period<0
3.fixed-rate任务保证按照固定的频率执行,period>0

