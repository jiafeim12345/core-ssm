package me.cloudcat.develop.security;

import me.cloudcat.develop.dao.UserDao;
import me.cloudcat.develop.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


/**
 * 用户登录服务，为spring-security提供用户信息
 *
 * @Author: zhenzhong.wang
 * @Time: 2018/2/7 19:11
 */
@Service("userDetailsService")
public class LoginServiceImpl implements UserDetailsService {

  private Logger logger = LoggerFactory.getLogger("security");

  @Autowired
  UserDao userDao;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


    User user = userDao.findByUsernameOrEmail(username);
    if (user != null) {
      // 更新用户状态
      user.setSecurityStatus(true, true, true);
      userDao.updateLastLoginTime(user.getId());
    } else {
      user = new User();
    }
    return user;
  }

}
