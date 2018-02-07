package me.cloudcat.develop.service.security;

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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

}
