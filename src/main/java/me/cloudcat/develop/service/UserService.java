package me.cloudcat.develop.service;

import me.cloudcat.develop.dao.UserDao;
import me.cloudcat.develop.entity.User;
import me.cloudcat.develop.entity.type.Status;
import me.cloudcat.develop.utils.CryptoUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * 用户管理业务类
 *
 * @Author: zhenzhong.wang
 * @Time: 2018/4/8 19:14
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

//    @Transactional
    public void InsertUser(int sum) {
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
