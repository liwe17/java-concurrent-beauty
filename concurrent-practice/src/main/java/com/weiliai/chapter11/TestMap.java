package com.weiliai.chapter11;

import com.google.gson.Gson;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @Author: Doug Li
 * @Date 2020/4/14
 * @Describe: 测试ConcurrentHashMap
 */
public class TestMap {

    //创建map,key为topic,value为设备列表
    private static final ConcurrentHashMap<String, List<String>> map = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        testPutIfAbsent();
    }

    //测试put
    public static void testPut(){
        //进入topic1,线程one
        Thread threadOne = new Thread(()->{
            List<String> list1 = new CopyOnWriteArrayList<>();
            list1.add("device1");
            list1.add("device2");

            map.put("topic1",list1);
            System.out.println(new Gson().toJson(map));
        });

        //进入topic1,线程two
        Thread threadTwo = new Thread(()->{
            List<String> list1 = new CopyOnWriteArrayList<>();
            list1.add("device11");
            list1.add("device22");

            map.put("topic1",list1);
            System.out.println(new Gson().toJson(map));
        });

        //进入topic2,线程three
        Thread threadThree = new Thread(()->{
            List<String> list1 = new CopyOnWriteArrayList<>();
            list1.add("device111");
            list1.add("device222");

            map.put("topic2",list1);
            System.out.println(new Gson().toJson(map));
        });

        //启动线程
        threadOne.start();
        threadTwo.start();
        threadThree.start();
    }

    //测试putIfAbsent
    public static void testPutIfAbsent(){
        //进入topic1,线程one
        Thread threadOne = new Thread(()->{
            List<String> list1 = new CopyOnWriteArrayList<>();
            list1.add("device1");
            list1.add("device2");

            List<String> oldList = map.putIfAbsent("topic1", list1);
            if(oldList!=null) oldList.addAll(list1);
            System.out.println(new Gson().toJson(map));
        });

        //进入topic1,线程two
        Thread threadTwo = new Thread(()->{
            List<String> list1 = new CopyOnWriteArrayList<>();
            list1.add("device11");
            list1.add("device22");

            List<String> oldList = map.putIfAbsent("topic1", list1);
            if(oldList!=null) oldList.addAll(list1);
            System.out.println(new Gson().toJson(map));
        });

        //进入topic2,线程three
        Thread threadThree = new Thread(()->{
            List<String> list1 = new CopyOnWriteArrayList<>();
            list1.add("device111");
            list1.add("device222");

            List<String> oldList = map.putIfAbsent("topic2", list1);
            if(oldList!=null) oldList.addAll(list1);
            System.out.println(new Gson().toJson(map));
        });

        //启动线程
        threadOne.start();
        threadTwo.start();
        threadThree.start();

    }

}
