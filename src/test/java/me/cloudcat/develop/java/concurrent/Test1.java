package me.cloudcat.develop.java.concurrent;

import org.springframework.retry.backoff.Sleeper;

/**
 * @Author: zhenzhong.wang
 * @Time: 2018/3/6 17:00
 */
public class Test1 {

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(() -> {
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            thread.setDaemon(true);
            thread.start();
            thread.setName(String.valueOf(i));
            System.out.println(thread.getName() + " " + thread.getPriority() + " "
                    + thread.toString() + " ");
            thread.getThreadGroup().list();
        }
    }
}
