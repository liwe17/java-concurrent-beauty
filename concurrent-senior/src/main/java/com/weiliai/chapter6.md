#Java并发包中锁原理剖析
##LockSupport工具类
LockSupport类的主要作用就是挂起和唤醒线程,该工具类是创建锁和其他工具类的基础.
LockSupport类与每个使用他的线程都关联一个许可证,在默认情况下调用LockSupport类的方法的线程是不持有许可证的.

void park()方法:如果调用park方法的线程已经拿到了与LockSupport关联的许可证,则调用LockSupport.park()时会马上返回,否则调用线程会被
禁止参与线程的调度,也就是阻塞挂起.

涉及的代码:com.weiliai.chapter6.LockSupportTest.testPark()

void unpark(Thread thread)方法:如果参数thread线程没有持有thread与LockSupport类关联的许可证,则让thread线程持有
如果thread之前因调用park()而挂起,则调用unpark后,该线程会被唤醒.
如果thread之前没有调用park,则调用unpark方法后,在调用park方法,会立刻返回.

涉及的代码:com.weiliai.chapter6.LockSupportTest.testUnpark()

void park(Object blocker)方法:一般使用的是这个方法而不是无参的park方法,原因是这个方法输出日志时会输出阻塞的类的信息,而park方法不
会输出.

void parkNanos(long nanos)方法:该方法和park方法类似,只不过是在指定时间后自动返回
void parkNanos(Object blocker,long nanos)方法:nanos当前时间后多长时间返回
void parkUntil(Object blocker,long deadline)方法:deadline从1970年到现在某个时间点的毫秒值后返回
涉及的代码:
