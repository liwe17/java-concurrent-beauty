#并发编程基础线程基础
##什么是线程
线程是进程中的一个实体,不能独立存在.
线程则是进程的一个执行路径,一个进程至少有一个线程,进程中的多个线程共享进程的资源.
进程是系统进行资源分配和调度的基本单位.
线程是CPU分配的基本单位.

一个进程中有多个线程,多个线程共享进程的堆和方法区资源,但是每个线程有自己的程序计数器和栈区域.
程序计数器:记录当前要执行的指令地址.
栈区域:用于存储该线程的局部变量,是线程私有的,还存放这栈帧.
堆:进程中最大的一块区域,进程中所有线程共享,是进程创建时候分配,主要存放new创建的对象实例.
方法区:存放JVM加载的类,常量以及静态变量等信息,线程共享

##线程的创建与运行
线程创建的三种方式:
1.继承Thread,继承方式的好处是方便传参,可以在子类添加成员变量,通过set方法设置参数或者构造函数传参.
2.实现Runnable,则只能使用主线程里面被声明为final的变量.
3.使用FutureTask,可以拿到任务的返回结果.

涉及代码:com.weiliai.chapter1.ThreadTest

##线程的通知与等待
wait()函数:当一个线程调用一个共享变量的wait()方法时,该调用线程就会被阻塞挂起,直到发生如下情况,才能被唤醒:
1.其他线程调用该共享对象的notify()或者notifyAll()方法.
2.其他线程调用了该线程的interrupt()方法,该线程抛出InterruptException异常返回.

备注:如果调用wait()方法的线程没有预先获取该对象的监视器锁,则调用wait()会抛出IllegalMonitorStateException异常.
获取方式:
1.执行synchronized同步代码块时,使用共享变量作为参数:
synchronized(共享变量){
    //do something
}
2.调用该共享变量的方法,并且该方法使用synchronized修饰:
synchronized void methodName(){
    //do something
}

虚假唤醒:一个线程可以从挂起状态变为可以运行状态（也就是被唤醒），即使该线程没有被其他线程调用notify()、notifyAll()方法进行通知，
或者被中断，或者等待超时，这就是虚假唤醒.
涉及代码:com.weiliai.chapter1.SpuriousWakeupTest

当一个线程调用共享变量的wait()方法时,当前线程只会释放当前共享变量的锁,当前线程持有其他共享对象的监视器锁不会被释放.
涉及代码:com.weiliai.chapter1.WaitReleaseLock

当一个线程调用共享对象wait()方法被阻塞挂起后,如果其他线程中断了线程,则该线程会抛出InterruptException异常返回.
涉及代码:com.weiliai.chapter1.WaitNotifyInterrupt

wait(long timeout):多了一个超时时间,在等待期间如果没有其他线程唤醒(notify/notifyAll)该线程,该线程仍然会在超过timeout时间后自动返回.

wait(long timeout, int nanos):两个参数,毫秒timeout,纳秒nanos,其内部还是调用的wait(long timeout)方法,只不多对于第二个参数纳秒,
如果其范围是大于0,小于一百万的话(1毫秒=100 0000纳秒),则将timeout加一秒,调用wait(long timeout)

notify()/notifyAll():唤醒在同一个共享变量上等待的线程,区别:一个唤醒一个,一个唤醒所有.
涉及代码:com.weiliai.chapter1.NotifyTest

##等待线程执行终止的join方法
等待线程执行完毕:当调用join方法(该方法属于Thread类)时,就会等待线程执行完毕后,在继续向下运行.
涉及代码:com.weiliai.chapter1.JoinTest

##让线程休眠的sleep方法
sleep方法:Thread中的静态方法sleep(),当一个线程调用了sleep方法后,会短暂的让出cpu执行权(也就是不参与cpu的调度),但是线程拥有的锁等资
源是不释放的,这一点和wait方法不同
涉及代码:com.weiliai.chapter1.SleepTest

##让出CPU执行权的yield方法
yield方法:Thread中的静态方法yield(),当一个线程调用了Thread.yield()时,表名当前线程放弃cpu的使用权,yield之后,当前线程处于就绪状态.
线程调度器会从包括该线程的所有线程中随机选出一个线程分配cpu执行时间片
涉及代码:com.weiliai.chapter1.YieldTest

sleep和yield方法的区别在于,当线程调用sleep方法时调用线程会被阻塞挂起指定时间,在这期间线程调度器不会去调度该线程,而调用yield方法,
线程只是让出剩余的时间片,并没有被阻塞挂起,而是处理就绪状态,线程调度器下一次调度,仍有可能调度到当前线程

##线程中断
线程中断:Java中线程中断是一种线程间的协作模式,通过设置线程的中断标志并不能直接终止该线程的执行,而是被中断的线程根据中断状态自行处理.

void interrupt()方法:中断线程,例:当线程A运行时,线程B可以调用线程A的interrupt()方法来设置中断标志为true并返回,仅仅是设置标识,线程
A实际并没有被中断,仍会继续执行,如果线程A因为调用了wait,join,sleep方法,这时候若线程B调用了线程A的interrupt()方法,线程A会在调用这些
方法的地方抛出InterruptedException
boolean isInterrupted()方法:检测当前线程是否被中断,如果是返回true,否则返回false.
boolean interrupted()方法:检测当前线程是否中断,如果是返回true,否则返回false,如果当前线程被中断,则会清楚中断标识.
涉及代码:com.weiliai.chapter1.InterruptTest

##理解上下文切换
每个时刻每个CPU只能被一个线程使用,CPU资源的分配采用了时间片轮转的策略,也就是给每个线程分配一个时间片,线程在时间片内占用CPU执行任务.
当前线程使用完时间片后,就会处于就绪状态并让出CPU让其他线程占用,这就是上下文切换.

线程上下文切换的时机:当前线程的CPU时间片使用完处于就绪状态时,当前线程被其他线程中断.


##线程死锁
死锁是指在两个或两个以上的线程在执行过程中,因争夺资源而造成的互相等待的现象,在无外力作用的情况下,这些线程会一直相互等待而无法继续运行.

死锁产生的条件:
1.互斥条件:线程对已获取资源进行排他型使用.
2.请求并持有条件:一个线程已经持有了至少一个资源,但又提出了新的资源请求,而新资源被其他线程占有,当前线程进入阻塞但同时不释放已获取资源.
3.不可剥夺条件:线程获取到的资源在自己使用完之前不能被其他线程抢占,只有在自己使用完毕后才释放该资源.
4.环路等待条件:发生锁时,必存在一个线程-资源的环形链.
其中3,4条件可以被破坏,用于避免死锁

涉及代码:com.weiliai.chapter1.DeadLockTest

##守护线程与用户线程
Java中的线程分为两类:1.daemon线程(守护线程) 2.user线程(用户线程)
区别:当最后一个非守护线程关闭时,JVM会正常退出
涉及代码:com.weiliai.chapter1.DaemonTest

##ThreadLocal
ThreadLocal是JDK包提供的,它提供了线程本地变量,也就是如果你创建了一个ThreadLocal变量,那么访问这个变量的每个线程都会有这个变量的副本.
当多个线程操作这个变量时,实际操作的是自己本地内存里的变量,从而避免了线程安全问题.创建一个ThreadLocal变量后,每个线程都复制一个变量到
自己的本地内存.
涉及代码:com.weiliai.chapter1.ThreadLocalTest

ThreadLocal不具备继承性
InheritableThreadLocal:支持继承性,让子线程可以访问父线程中设置的本地变量
涉及代码:com.weiliai.chapter1.TestThreadLocal


##参考资料
https://www.cnblogs.com/nxzblogs/p/11318671.html