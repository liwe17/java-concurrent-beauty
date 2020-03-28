package com.weiliai.chapter2;

/**
 * @Author: Doug Li
 * @Date 2020/3/28
 * @Describe: 测试共享变量,内存可见性
 */
public class VolatileTest {

    class ThreadNotSafeCount{

        private long value;

        public long getValue() {
            return value;
        }

        public void setValue(long value) {
            this.value = value;
        }
    }

    class ThreadSafeCount1{

        private long value;

        public synchronized long getValue() {
            return value;
        }

        public synchronized void setValue(long value) {
            this.value = value;
        }
    }

    class ThreadSafeCount2{

        private volatile long value;

        public long getValue() {
            return value;
        }

        public void setValue(long value) {
            this.value = value;
        }
    }

}
