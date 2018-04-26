/*
 * Copyright 2004, 2005, 2006 Acegi Technology Pty Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.cloudcat.develop.security;

import me.cloudcat.develop.dao.RoleDao;
import me.cloudcat.develop.entity.Resource;
import me.cloudcat.develop.entity.Role;
import me.cloudcat.develop.redis.RedisMap;
import me.cloudcat.develop.redis.RedisMapFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 资源数据源，初始化资源-权限之间的关系
 *
 * @Author: zhenzhong.wang
 * @Time: 2018/2/9 15:22
 */
public class SecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

  private Logger logger = LoggerFactory.getLogger("security");

  @Autowired
  RoleDao roleDao;

  static RedisMapFactory redisFactory;
  static RedisMap securityMap;

  @Autowired
  public void setRedisFactory(RedisMapFactory redisFactory) {
    this.redisFactory = redisFactory;
    this.securityMap = redisFactory.getRedisMap("security");
  }

  /**
   * 加载数据库中 权限-资源关系表
   */
  private void init() {

		/**
		 * 应当是资源(格式：method,api)为key， 权限为value。 资源为URL-Method的Map， 权限就是那些以ROLE_为前缀的角色。
		 * 一个资源可以由多个权限来访问。
		 */
    Map<String, Collection<ConfigAttribute>> resourceMap = new HashMap<String, Collection<ConfigAttribute>>();

		/*
		 * 在Web服务器启动时，提取系统中的所有权限。
		 */
    List<Role> authorityList = roleDao.findAll();

		/*
		 * 取得所有权限名
		 */
    for (Role auth : authorityList) {
      ConfigAttribute attr = new SecurityConfig(auth.getName());

			/*
			 * 然后，取得资源名：根据权限名 取得 资源名
			 */
      for (Resource res : auth.getResources()) {
				/*
				 * 判断资源文件和权限的对应关系，如果已经存在相关的资源url，则要限通过该url为key提取出权集合，将权限增加到权限集合中
				 */
				String resKey = res.getMethod().toString() + "," + res.getApi();
        if (resourceMap.containsKey(resKey)) {
          Collection<ConfigAttribute> value = resourceMap.get(resKey);
          value.add(attr);
        } else {
          Collection<ConfigAttribute> attibutes = new ArrayList<ConfigAttribute>();
          attibutes.add(attr);
          resourceMap.put(resKey, attibutes);
        }
      }
    }

		/*
		 * 更新redis缓存
		 */
    securityMap.put("resourceMap", resourceMap);
  }

  // ~ Methods
  // ========================================================================================================

  public Collection<ConfigAttribute> getAllConfigAttributes() {
    // 初始化
    init();

    Set<ConfigAttribute> allAttributes = new HashSet<ConfigAttribute>();

    Object resourceMap = securityMap.get("resourceMap");
    if (resourceMap != null) {
      Map<String, Collection<ConfigAttribute>> map = (Map<String, Collection<ConfigAttribute>>) resourceMap;
      for (Map.Entry<String, Collection<ConfigAttribute>> entry : map.entrySet()) {
        allAttributes.addAll(entry.getValue());
      }
    }

    return allAttributes;
  }

  public Collection<ConfigAttribute> getAttributes(Object object) {

    Object resourceMap = securityMap.get("resourceMap");
    if (resourceMap != null) {
      final HttpServletRequest request = ((FilterInvocation) object).getRequest();
      Map<String, Collection<ConfigAttribute>> map = (Map<String, Collection<ConfigAttribute>>) resourceMap;
      for (Map.Entry<String, Collection<ConfigAttribute>> entry : map.entrySet()) {
        /**
			   * 做URL-Method匹配,并且区分URL中的大小写
         */
        try {
          String[] res = entry.getKey().split(",");
          AntPathRequestMatcher um = new AntPathRequestMatcher(res[1], res[0], false);
          if (um.matches(request)) {
            return entry.getValue();
          }
        } catch (Exception e) {
          logger.error("资源解析异常：" + e.getMessage());
        } finally {

        }

      }
    }
    return null;
  }

  // 暂时不清楚干啥的
  public boolean supports(Class<?> clazz) {
    return FilterInvocation.class.isAssignableFrom(clazz);
  }
}
