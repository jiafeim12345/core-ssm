package me.cloudcat.develop.security;

import me.cloudcat.develop.dao.UserDao;
import me.cloudcat.develop.entity.User;
import me.cloudcat.develop.utils.CryptoUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Author: zhenzhong.wang
 * @Time: 2018/3/5 16:08
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring-context.xml",
        "classpath:spring-redis.xml", "classpath:spring-rabbitmq.xml", "classpath:spring-security.xml"})
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

}
