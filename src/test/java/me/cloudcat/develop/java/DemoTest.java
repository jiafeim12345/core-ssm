package me.cloudcat.develop.java;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import me.cloudcat.develop.Constant;
import org.junit.Test;

import java.util.ArrayList;

/**
 * @Author: zhenzhong.wang
 * @Time: 2018/1/18 16:16
 */
public class DemoTest {

    @Test
    public void testOne() {
        ArrayList<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("123");
        list.forEach(e -> System.out.println(e));
    }

    @Test
    public void testTwo() {

    }
}
