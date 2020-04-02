package com.weiliai.chapter6;

import java.util.concurrent.locks.StampedLock;

/**
 * @Author: Doug Li
 * @Date 2020/4/2
 * @Describe: StampedLock测试
 */
public class Point {

    //成员变量
    private double x,y;

    //锁实例
    private final StampedLock s1 = new StampedLock();

    //排它锁-写锁(writeLock)
    public void move(double deltaX,double deltaY){
        long stamp = s1.writeLock();
        try{
            x += deltaX;
            y += deltaY;
        }finally {
            s1.unlockWrite(stamp);
        }
    }

    //乐观锁(tryOptimisticRead)
    public double distanceFromOrigin(){
        //1.尝试获取乐观读锁
        long stamp = s1.tryOptimisticRead();
        //将全部变量复制到方法体栈内
        double currentX = x,currentY = y;
        //检查在1处获取了读锁戳记后,锁有没有被其他写线程排它性抢占
        if(!s1.validate(stamp)){
            //如果被抢占则获取一个共享读锁(悲观获取)
            stamp = s1.readLock();
            try{
                //将全部变量复制到方法体栈
                currentX = x;
                currentY = y;
            }finally {
                //释放读锁
                s1.unlockRead(stamp);
            }
        }
        //返回计算结果
        return Math.sqrt(currentX * currentX + currentY * currentY);
    }

    //使用悲观锁获取读锁,并尝试转换为写锁
    public void moveIfAtOrigin(double newX,double newY){
        //这里可以使用乐观读锁替换
        long stamp = s1.readLock();
        try{
            //如果当前点在原点则移动
            while(x == 0.0 && y == 0.0){
                //尝试将获取的读锁升级为写锁
                long ws = s1.tryConvertToWriteLock(stamp);
                //升级成功,则更新戳记,并设置坐标值,然后退出循环
                if(ws!=0L){
                    stamp = ws;
                    x = newX;
                    y = newY;
                    break;
                }else{
                    //读锁升级写锁失败则释放读锁,显式获取独占写锁,然后循环重试
                    s1.unlockRead(stamp);
                    stamp = s1.writeLock();
                }
            }
        }finally {
            //释放锁
            s1.unlock(stamp);
        }
    }
}