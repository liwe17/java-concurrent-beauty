package com.weiliai.chapter2;

/**
 * @Author: Doug Li
 * @Date 2020/3/28
 * @Describe: 测试伪共享
 */
public class FalseSharingTest {

    public static void main(String[] args) throws InterruptedException {
        testPointer(new Pointer());
    }

    private static void testPointer(Pointer pointer) throws InterruptedException {
        long start = System.currentTimeMillis();
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 100000000; i++) {
                pointer.x++;
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 100000000; i++) {
                pointer.y++;
            }
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println(System.currentTimeMillis() - start);
        System.out.println(pointer);
    }
}

//方法1
class Pointer {
    volatile long x;
    //long p1, p2, p3, p4, p5, p6, p7; // 添加这7个变量的原因就是让x和y不在一个缓存行中，这样修改x的时候，就不会影响到对y的操作
    volatile long y;
}

//方法2: 同时pointer.x++; 修改为 pointer.x.value++;，把 pointer.y++; 修改为 pointer.y.value++
class Pointer1 {
    MyLong x = new MyLong();
    MyLong y = new MyLong();
}

class MyLong {
    volatile long value;
    long p1, p2, p3, p4, p5, p6, p7; // 同样是占用缓冲行的位置
}

//方法3:默认使用这个注解是无效的，需要在JVM启动参数加上-XX:-RestrictContended才会生效
@sun.misc.Contended
class MyLong1 {
    volatile long value;
}
