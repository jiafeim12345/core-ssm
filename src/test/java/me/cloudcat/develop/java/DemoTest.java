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
        JSONObject json1 = JSON.parseObject(Constant.json1);
        JSONArray rows = JSON.parseArray(json1.get("Rows").toString());
        JSONObject json2 = JSON.parseObject(Constant.json2);
        JSONArray rows2 = JSON.parseArray(json2.get("Rows").toString());
        for (Object ob : rows2) {
            if (!rows.contains(ob)) {
                System.out.println(ob);
            }
        }

    }
}
