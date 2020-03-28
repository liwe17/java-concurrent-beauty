package com.weiliai.chapter2;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @Author: Doug Li
 * @Date 2020/3/28
 * @Describe: 测试UnSafe类
 */
public class UnSafeTest {

    //获取unsafe的实例
    static final Unsafe unsafe;

    //记录state在类中TesUnSafe中的偏移量
    static final long stateOffset;

    //变量
    private volatile long state = 0;


    static {
        try {
            //使用反射获取Unsafe的成员变量theUnsafe
            final Field field = Unsafe.class.getDeclaredField("theUnsafe");
            //设置为可取
            field.setAccessible(true);
            //获取该变量的值
            unsafe = (Unsafe) field.get(null);
            stateOffset =unsafe.objectFieldOffset(UnSafeTest.class.getDeclaredField("state"));
        } catch (Exception e) {
            System.err.println(e.getLocalizedMessage());
            throw new Error(e);
        }
    }

    public static void main(String[] args) {
        final UnSafeTest test = new UnSafeTest();
        System.out.println(unsafe.compareAndSwapInt(test, stateOffset, 0, 1));
    }

}
