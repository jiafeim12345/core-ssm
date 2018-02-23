package me.cloudcat.develop.designpattern.decorator;

/**
 * T恤
 *
 * @Author: zhenzhong.wang
 * @Time: 2018/2/23 15:51
 */
public class Shirt extends ClothingDecorator {

    Person person;

    public Shirt(Person person) {
        this.person = person;
    }

    @Override
    public double cost() {
        return person.cost() + 123;
    }

    @Override
    public String getDescription() {
        return "Shirt";
    }
}
