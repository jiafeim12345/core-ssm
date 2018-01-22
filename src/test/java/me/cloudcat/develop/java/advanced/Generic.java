package me.cloudcat.develop.java.advanced;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * 泛型
 *
 * @Author zhenzhong.wang
 * @Time 2018/1/16 21:48
 */
public class Generic {

    @Test
    public void testOne() {
        HashSet<Object> set = new HashSet<>();
        set.add(1);
        ArrayList<Object> li = new ArrayList<Object>(set);
        System.out.println(li.get(0));

    }
}
