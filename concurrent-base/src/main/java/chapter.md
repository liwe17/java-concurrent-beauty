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


##参考资料
https://www.cnblogs.com/nxzblogs/p/11318671.html