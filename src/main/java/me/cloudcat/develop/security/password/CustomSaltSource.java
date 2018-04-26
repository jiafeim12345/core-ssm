package me.cloudcat.develop.security.password;

import me.cloudcat.develop.utils.PropertyUtils;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 获取盐值
 *
 * @Author: zhenzhong.wang
 * @Time: 2018/3/5 15:43
 */
public class CustomSaltSource implements SaltSource {

  @Override
  public Object getSalt(UserDetails user) {
    return PropertyUtils.getString("password.salt");
  }
}
