package me.cloudcat.develop.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


/**
 * 加密工具
 *
 * @Author: zhenzhong.wang
 * @Time: 2018/3/5 16:11
 */
public class CryptoUtils {

  public static String encode(String password) {
    password = new BCryptPasswordEncoder().encode(password);
    return password;
  }
}
