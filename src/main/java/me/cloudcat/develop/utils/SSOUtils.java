package me.cloudcat.develop.utils;

import me.cloudcat.develop.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 单点登录处理
 *
 * @Author: zhenzhong.wang
 * @Time: 2018/3/12 11:41
 */
@Component
public class SSOUtils {

    private static Logger logger = LoggerFactory.getLogger("security");

    private static UserDetailsService userDetailsService;
    private static SessionRegistry sessionRegistry;

    @Autowired
    public void setUserDetailsService(UserDetailsService userDetailsService) {
        SSOUtils.userDetailsService = userDetailsService;
    }

    @Autowired
    public void setSessionRegistry(SessionRegistry sessionRegistry) {
        SSOUtils.sessionRegistry = sessionRegistry;
    }

    /**
     * 模拟登陆
     *
     * @param arg (username 或 email)
     */
    public static void ssoLogin(String arg) {
        try {
            if (arg != null) {
                // 检查用户名是否和当前登录用户相同，如果相同则跳过避免重复登录
                User currentUser = BusinessUtils.getUser();
                if (currentUser != null &&
                        (arg.equals(currentUser.getUsername()) || arg.equals(currentUser.getEmail()))) {
                    logger.trace("User:" + arg + " has been in system, skip login...");
                    return;
                }

                User user = (User) userDetailsService.loadUserByUsername(arg);
                if (user != null) {
                    Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(auth);
                    // 注册session
                    sessionRegistry.registerNewSession(getSession().getId(), user);
                    logger.info("Login success: {}", arg);

                } else {
                    logger.warn("No such user {}, login failed", arg);
                }
            } else {
                if (logger.isTraceEnabled()) {
                    logger.trace("No login request...skip...");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取当前线程请求
     *
     * @return
     */
    public static HttpServletRequest getRequest() {
        HttpServletRequest request = null;
        if (RequestContextHolder.getRequestAttributes() != null) {
            request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        }
        return request;
    }

    /**
     * 获取当前线程Session
     *
     * @return
     */
    public static HttpSession getSession() {
        HttpSession session = null;
        if (RequestContextHolder.getRequestAttributes() != null) {
            session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
        }
        return session;
    }
}
