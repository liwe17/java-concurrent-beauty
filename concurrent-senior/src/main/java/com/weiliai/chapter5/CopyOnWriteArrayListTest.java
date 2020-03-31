package com.weiliai.chapter5;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @Author: Doug Li
 * @Date 2020/3/31
 * @Describe: 测试
 */
public class CopyOnWriteArrayListTest {

    public static void main(String[] args) throws InterruptedException {

        test2();
    }

    public static void test(){
        final CopyOnWriteArrayList<String> arrayList = new CopyOnWriteArrayList<>();
        arrayList.add("hello");
        arrayList.add("alibaba");

        final Iterator<String> itr = arrayList.iterator();
        while(itr.hasNext()){
            System.out.println(itr.next());
        }
    }


    //测试弱一致性
    public static void test2() throws InterruptedException {
        final CopyOnWriteArrayList<String> arrayList = new CopyOnWriteArrayList<>();
        arrayList.add("hello");
        arrayList.add("alibaba");
        arrayList.add("english");
        arrayList.add("math");
        arrayList.add("chinese");

        final Iterator<String> itr = arrayList.iterator();

        Thread thread = new Thread(()->{
            arrayList.set(1,"ma");
            arrayList.remove(2); //english
            arrayList.remove(3); //chinese
            arrayList.forEach(System.err::println);
        });
        thread.start();
        thread.join();

        while (itr.hasNext()){
            System.out.println(itr.next());
        }
    }

}
