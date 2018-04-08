package me.cloudcat.develop.security;

import me.cloudcat.develop.dao.UserDao;
import me.cloudcat.develop.entity.User;
import me.cloudcat.develop.entity.type.Status;
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
        int sum = 10000;
        for (int i = 0; i < sum; i++) {
            User user = new User();
            user.setUsername(UUID.randomUUID().toString());
            String encode = CryptoUtils.encode(UUID.randomUUID().toString());
            user.setPassword(encode);
            user.setEmail(UUID.randomUUID().toString());

            user.setStatus(RandomUtils.nextBoolean() ? Status.ENABLE : Status.DISABLE);
            userDao.save(user);
        }
    }

}
