package me.cloudcat.develop.utils;

import java.util.Random;

/**
 * 线程工具类
 *
 * @Author: zhenzhong.wang
 * @Time: 2018/1/23 10:27
 */
public class ThreadUtils {

    private static Boolean interrupt = true;

    private static Integer minTime = 20;
    private static Integer maxTime = 30;

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
        } finally {

        }
    }

    public static void setInterrupt(Boolean b) {
        synchronized(interrupt) {
            interrupt = b;
        }
    }

    public static Boolean getInterrupt() {
        return interrupt;
    }

    public static void setMinTime(Integer min) {
        synchronized(minTime) {
            minTime = min;
        }
    }

    public static Integer getMinTime() {
        return minTime;
    }

    public static void setMaxTime(Integer max) {
        synchronized(maxTime) {
            maxTime = max;
        }
    }

    public static Integer getMaxTime() {
        return maxTime;
    }
}
