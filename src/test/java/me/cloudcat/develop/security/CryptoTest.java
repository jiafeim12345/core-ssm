package me.cloudcat.develop.security;

import me.cloudcat.develop.dao.UserDao;
import me.cloudcat.develop.entity.User;
import me.cloudcat.develop.entity.type.Status;
import me.cloudcat.develop.service.UserService;
import me.cloudcat.develop.utils.CryptoUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.UUID;

/**
 * @Author: zhenzhong.wang
 * @Time: 2018/3/5 16:08
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:xml/spring-context.xml",
        "classpath:xml/spring-redis.xml", "classpath:xml/spring-rabbitmq.xml", "classpath:xml/spring-security.xml"})
public class CryptoTest {

    @Autowired
    UserDao userDao;

    @Autowired
    UserService userService;

    @Test
    public void crypto() {
        User user = new User();
        user.setUsername("lvlv");
        String encode = CryptoUtils.encode("Qiuqiu");
        user.setPassword(encode);
        userDao.updatePassword(user);
    }

    @Test
    public void insertUser() {
        long start = System.currentTimeMillis();
        int num = 1;
        for (int i = 0; i < num; i++) {
            userService.InsertUser(500);
        }
        long time = System.currentTimeMillis() - start;
        System.out.println("花费时间：" + time);
    }

}
