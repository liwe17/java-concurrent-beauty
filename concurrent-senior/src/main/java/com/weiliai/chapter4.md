#Java并发包中原子操作类原理剖析
##原子变量操作类
AtomicInteger/AtomicLong/AtomicBoolean等原子操作类,这些类都是基于都是基于非阻塞算法CAS实现的.
核心方法:compareAndSet(expect,update)-->预期值expect,更新值update

涉及的代码: