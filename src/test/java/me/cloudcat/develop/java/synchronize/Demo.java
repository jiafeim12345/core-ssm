package me.cloudcat.develop.java.synchronize;

import org.junit.Test;

/**
 * @Author: zhenzhong.wang
 * @Time: 2018/2/5 10:44
 */
public class Demo {

    public static void main(String[] args) throws InterruptedException {
        SynchronizedTest test = new SynchronizedTest();
        Thread thread = new Thread(test);
        thread.start();
    }
}
