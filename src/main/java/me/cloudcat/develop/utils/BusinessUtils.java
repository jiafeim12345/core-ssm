package me.cloudcat.develop.utils;

import me.cloudcat.develop.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 业务工具类
 *
 * @Author: zhenzhong.wang
 * @Time: 2018/3/1 18:35
 */
public class BusinessUtils {

    /*private static HttpServletRequest request;

    @Autowired
    public void setRequest(HttpServletRequest request) {
        BusinessUtils.request = request;
    }*/


    static Logger logger = LoggerFactory.getLogger(BusinessUtils.class);

    /**
     * 获取当前用户
     *
     * @return
     */
    public static User getUser() {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            return null;
        }
//        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal != null && UserDetails.class.isAssignableFrom(principal.getClass())) {
            User user = (User) principal;
            return user;
        } else {
            return null;
        }
    }

    /**
     * 获取当前用户
     *
     * @param request
     * @return
     */
    public static User getUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        SecurityContextImpl securityContextImpl = (SecurityContextImpl) session.getAttribute("SPRING_SECURITY_CONTEXT");
        Authentication authentication = securityContextImpl.getAuthentication();
        if (authentication == null) {
            return null;
        }
        Object principal = authentication.getPrincipal();
        if (principal != null && UserDetails.class.isAssignableFrom(principal.getClass())) {
            User user = (User) principal;
            return user;
        } else {
            return null;
        }
    }
}
