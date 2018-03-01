package me.cloudcat.develop.utils;

import me.cloudcat.develop.entity.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 业务工具类
 *
 * @Author: zhenzhong.wang
 * @Time: 2018/3/1 18:35
 */
public class BusinessUtils {

    static Log log = LogFactory.getLog(BusinessUtils.class);

    /**
     * 获取当前用户
     *
     * @return
     */
    public static User getUser() {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            return null;
        }
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal != null && UserDetails.class.isAssignableFrom(principal.getClass())) {
            User user = (User) principal;
            return user;
        } else {
            return null;
        }
    }
}
