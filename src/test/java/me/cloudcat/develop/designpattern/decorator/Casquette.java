package me.cloudcat.develop.designpattern.decorator;

/**
 * 帽子
 *
 * @Author: zhenzhong.wang
 * @Time: 2018/2/23 15:55
 */
public class Casquette extends HatDecorator {

    Person person;

    public Casquette(Person person) {
        this.person = person;
    }

    @Override
    public double cost() {
        return person.cost();
    }

    @Override
    public String getDescription() {
        return "帽子";
    }
}
