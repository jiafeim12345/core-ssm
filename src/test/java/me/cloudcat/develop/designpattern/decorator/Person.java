package me.cloudcat.develop.designpattern.decorator;

/**
 * 被装饰者基类，用于计算衣服价格
 *
 * @Author: zhenzhong.wang
 * @Time: 2018/2/23 15:45
 */
public abstract class Person {

    public abstract double cost(); // 子类应该实现的方法
}
