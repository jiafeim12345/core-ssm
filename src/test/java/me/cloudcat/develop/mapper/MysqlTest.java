package me.cloudcat.develop.mapper;

import me.cloudcat.develop.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Author: jiafeim12345@163.com
 * @Date: 2018/4/8/008 21:40
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:xml/spring-context.xml",
        "classpath:xml/spring-redis.xml", "classpath:xml/spring-rabbitmq.xml", "classpath:xml/spring-security.xml"})
public class MysqlTest {

    @Autowired
    UserService userService;

    @Test
    public void insertUser() {
        long start = System.currentTimeMillis();
        int num = 10;
        for (int i = 0; i < num; i++) {
            userService.insertUser(500);
        }
        long time = System.currentTimeMillis() - start;
        System.out.println("花费时间：" + time);
    }
}
