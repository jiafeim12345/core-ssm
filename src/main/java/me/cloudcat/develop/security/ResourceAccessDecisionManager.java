package me.cloudcat.develop.security;

import me.cloudcat.develop.entity.User;
import me.cloudcat.develop.utils.BusinessUtils;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Iterator;

/**
 * 访问决策管理器
 *
 * @Author: zhenzhong.wang
 * @Time: 2018/2/9 15:17
 */
public class ResourceAccessDecisionManager implements AccessDecisionManager {

    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        if (configAttributes == null) {
            throw new AccessDeniedException("");
        }

        Iterator<ConfigAttribute> ite = configAttributes.iterator();
		/*
		 * 访问控制，登陆用户有一个相应权限就可以访问
		 */
        User currentUser = BusinessUtils.getUser();
        if (currentUser != null) {
			/*
			 * 每次都去查库，读最新的权限配置
			 */
            Collection<? extends GrantedAuthority> gaList = currentUser.getAuthorities();
            if (gaList != null && !gaList.isEmpty()) {
                while (ite.hasNext()) {

                    ConfigAttribute ca = ite.next();
                    String needRole = ((SecurityConfig) ca).getAttribute();

					/*
					 * ga 为用户所被赋予的权限; needRole为访问相应的资源（URL-Method）应该具有的权限。
					 */
                    for (GrantedAuthority ga : gaList) {
                        if (needRole.equalsIgnoreCase(ga.getAuthority())) {
                            return;
                        }
                    }
                }
            }
        }
        throw new AccessDeniedException("access denied !");
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
