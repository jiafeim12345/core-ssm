package me.cloudcat.develop.java;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
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
    public void testTwo() throws UnsupportedEncodingException, SignatureException, InvalidKeyException, NoSuchAlgorithmException {

    }

    @Test
    public void testThree() {
        try {
            int a = 1/0;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
