package me.cloudcat.develop.utils;

import me.cloudcat.develop.entity.type.RefreshStatus;

import java.util.Random;

/**
 * 线程工具
 *
 * @Author: zhenzhong.wang
 * @Time: 2018/1/23 10:27
 */
public class ThreadUtils {

    private static Integer observer = 0;    // 观察者数量
    private static RefreshStatus refreshStatus = RefreshStatus.NONE;   // 域名刷新状态

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

    /**
     * 睡眠一定时间
     *
     * @param time
     */
    public static void sleep(int time) {
        try {
            Thread.sleep(time * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {

        }
    }

    public static Integer getObserver() {
        return observer;
    }

    public static void setObserver(Integer observer) {
        synchronized (observer) {
            ThreadUtils.observer = observer;
        }
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

    public static RefreshStatus getRefreshStatus() {
        return refreshStatus;
    }

    public static void setRefreshStatus(RefreshStatus refreshStatus) {
        synchronized (refreshStatus) {
            ThreadUtils.refreshStatus = refreshStatus;
        }
    }
}
