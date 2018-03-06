package me.cloudcat.develop.java.concurrent;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: zhenzhong.wang
 * @Time: 2018/3/6 17:00
 */
public class Test2 {

    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(() -> {
                try {
                    Thread.sleep(3000);
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    System.out.println(dtf.format(LocalDateTime.now()));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            thread.setDaemon(true);
            thread.setName(String.valueOf(i));
            pool.execute(thread);
            if (i == 2) {
                pool.shutdown();
                System.out.println("stop concurrent");
            }
//            System.out.println(thread.getName() + " " + thread.getPriority() + " "
//                    + thread.toString() + " ");
        }
    }
}
