package com.weiliai.chapter2;

import sun.misc.Unsafe;

/**
 * @Author: Doug Li
 * @Date 2020/3/28
 * @Describe: 测试UnSafe类
 */
public class TestUnSafe {

    //获取unsafe的实例
    static final Unsafe unsafe = Unsafe.getUnsafe();

    //记录state在类中TesUnSafe中的偏移量
    static final long stateOffset;

    //变量
    private volatile long state = 0;


    static {
        try {
            stateOffset = unsafe.objectFieldOffset(TestUnSafe.class.getField("state"));
        } catch (NoSuchFieldException e) {
            System.err.println(e.getLocalizedMessage());
            throw new Error(e);
        }
    }

    public static void main(String[] args) {
        final TestUnSafe test = new TestUnSafe();
        System.out.println(unsafe.compareAndSwapInt(test, stateOffset, 0, 1));
    }

}
