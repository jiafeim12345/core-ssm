package me.cloudcat.develop.java.synchronize;

/**
 * @Author: zhenzhong.wang
 * @Time: 2018/2/5 10:41
 */
public class SynchronizedTest implements Runnable {

    Integer a = 5;

    public void print() throws InterruptedException {
        synchronized (a) {
            Thread.sleep(3000);
            a++;
            System.out.println(a);
        }
    }

    public void printTwo() {
        a = 2;
        System.out.println(a);
    }

    @Override
    public void run() {
        printTwo();
    }
}
