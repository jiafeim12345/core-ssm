package me.cloudcat.develop.utils;

import com.sun.jmx.snmp.tasks.ThreadService;

import java.util.Random;

/**
 * 线程工具类
 *
 * @Author: zhenzhong.wang
 * @Time: 2018/1/23 10:27
 */
public class ThreadUtils {

    private static Boolean interrupt = true;

    /**
     * [min,max]区间内睡眠随机时间
     * @param max
     * @param min
     */
    public static void sleep(int min, int max) {
        Random random = new Random();
        int randomSecond = min + random.nextInt(max - min + 1);
        try {
            Thread.sleep(randomSecond * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void setInterrupt(boolean b) {
        synchronized(interrupt) {
            interrupt = b;
        }
    }

    public static Object getInterrupt() {
        return interrupt;
    }
}
