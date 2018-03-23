package me.cloudcat.develop.security;

import me.cloudcat.develop.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Session管理
 *
 * @Author: zhenzhong.wang
 * @Time: 2018/3/12 11:27
 */
@Service
public class SessionManager {

    private static SessionRegistry sessionRegistry;

    public static SessionRegistry getSessionRegistry() {
        return sessionRegistry;
    }

    @Autowired
    public void setSessionRegistry(SessionRegistry sessionRegistry) {
        SessionManager.sessionRegistry = sessionRegistry;
    }

    /**
     * 强制用户退出登录
     */
    public static void expireUser(String username) {
        // 获取所有认证
        List<Object> list = sessionRegistry.getAllPrincipals();
        for (int i = 0; i < list.size(); i++) {
            User u = (User) list.get(i);
            if (u.getUsername().equals(username)) {
                // 根据认证获取所有session并从session注册器中删除
                List<SessionInformation> infos = sessionRegistry.getAllSessions(list.get(i), true);
                for (SessionInformation info : infos) {
                    sessionRegistry.removeSessionInformation(info.getSessionId());
                }
            }
        }
    }
}
