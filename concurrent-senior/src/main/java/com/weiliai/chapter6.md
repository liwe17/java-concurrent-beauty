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

涉及的代码:com.weiliai.chapter6.FIFOMutex

##抽象同步队列AQS概述
###锁底层的支持
AbstractQueuedSynchronize抽象同步队列简称AQS,它是实现同步器的基础组件,并发包中锁的底层就是使用AQS实现的.

对于AQS来说,线程同步的关键是对状态值state进行操作,根据state是否属于一个线程,操作state方式分为独占方式和共享方式:
独占方式获取和释放资源使用的方法:
1.void acquire(int arg),忽略中断
2.void acquireInterruptibly(),对中断的线程抛出异常
3.boolean release()
共享方式获取和释放资源使用方法:
1.void acquireShared(int arg),忽略中断
2.void acquireSharedInterruptibly(int arg),对中断的线程抛出异常
3.boolean releaseShared(int arg)

###AQS-条件变量的支持
条件变量的signal和await方法也是用来配合锁(使用AQS实现的锁)实现线程间同步的基础设施.

一个锁对应一个AQS阻塞队列,对应多个条件变量,每个条件变量有自己的一个条件队列.
注意:
1.当多个线程同时调用lock.lock()方法获取锁,只有一个线程获取到锁,其余线程都会被转换为Node节点插入到AQS阻塞队列中,并作CAS尝试获取锁.
2.如果获取锁的线程调用了对应条件变量的await()方法,那么该线程会释放锁,并转换为Node节点插入到条件变量对应的条件队列中.
3.当另一个线程调用条件变量的signal()或者signalAll()方法时,会把条件队列中的一个或者全部Node节点移动到AQS阻塞队列,等待时机获取锁.

涉及的代码:com.weiliai.chapter6.ConditionTest

###基于AQS实现自定义同步器
基于AQS实现一个不可重入的独占锁,自定义AQS重写相关函数
1.定义原子变量state=0代表锁没有被线程持有,state为1表示锁已经被某个线程持有
2.由于不可重入,因此无需记录持有锁的线程获取锁的次数
3.自定义的锁支持条件变量

自定义锁代码:
com.weiliai.chapter6.NonReentrantLock
com.weiliai.chapter6.NonReentrantLockTest

##独占锁ReentrantLock的原理
ReentrantLock是可重入的独占锁,同时只能有一个线程可以获取该锁,其他获取该锁的线程会被阻塞而放入到该锁的AQS阻塞队列里面.

void lock()方法
void lockInterruptibly()方法
boolean tryLock(long timeout,TimeUnit unit)方法
void unlock()方法

使用ReentrantLock实现一个简单的线程安全的List
涉及代码:com.weiliai.chapter6.ReentrantLockList

##读写锁ReentrantReadWriteLock的原理
ReentrantReadWriteLock采用读写分离的策略,须臾多个线程同时获取读锁,适用于写少读多的场景.
















