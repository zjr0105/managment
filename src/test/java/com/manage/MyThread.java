package com.manage;

import java.util.HashMap;

public class MyThread implements Runnable{

    @Override
    public void run() {

       System.out.println(Thread.currentThread().getName());

    }


    public static void main(String[] args) {

        //创建Runnable类的对象

        MyThread thread = new MyThread();

        Thread t1 = new Thread(thread,"张三");
        Thread t2 = new Thread(thread,"王五");

        t2.setDaemon(true);


        t1.start();
        t2.start();
        System.out.println(Thread.currentThread().getName());

    }
}
