/* Copyright 2004, 2005, 2006 Acegi Technology Pty Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.cloudcat.develop.security;

import me.cloudcat.develop.utils.HttpUtils;
import me.cloudcat.develop.utils.SSOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;


/**
 * Votes if a {@link ConfigAttribute#getAttribute()} of <code>IS_AUTHENTICATED_FULLY</code> or
 * <code>IS_AUTHENTICATED_REMEMBERED</code> or <code>IS_AUTHENTICATED_ANONYMOUSLY</code> is present. This list is in
 * order of most strict checking to least strict checking.
 * <p>
 * The current <code>Authentication</code> will be inspected to determine if the principal has a particular
 * level of authentication. The "FULLY" authenticated option means the user is authenticated fully (i.e. {@link
 * org.springframework.security.authentication.AuthenticationTrustResolver#isAnonymous(Authentication)} is false and {@link
 * org.springframework.security.authentication.AuthenticationTrustResolver#isRememberMe(Authentication)} is false). The "REMEMBERED" will grant
 * access if the principal was either authenticated via remember-me OR is fully authenticated. The "ANONYMOUSLY" will
 * grant access if the principal was authenticated via remember-me, OR anonymously, OR via full authentication.
 * <p>
 * All comparisons and prefixes are case sensitive.
 *
 * @author Ben Alex
 */
public class AuthenticatedVoter implements AccessDecisionVoter<Object> {
  //~ Static fields/initializers =====================================================================================

  private Logger logger = LoggerFactory.getLogger("security");

  public static final String IS_AUTHENTICATED_FULLY = "IS_AUTHENTICATED_FULLY";
  public static final String IS_AUTHENTICATED_REMEMBERED = "IS_AUTHENTICATED_REMEMBERED";
  public static final String IS_AUTHENTICATED_ANONYMOUSLY = "IS_AUTHENTICATED_ANONYMOUSLY";
  //~ Instance fields ================================================================================================

  private AuthenticationTrustResolver authenticationTrustResolver = new AuthenticationTrustResolverImpl();

  //~ Methods ========================================================================================================

  private boolean isFullyAuthenticated(Authentication authentication) {
    return (!authenticationTrustResolver.isAnonymous(authentication) &&
        !authenticationTrustResolver.isRememberMe(authentication));
  }

  public void setAuthenticationTrustResolver(AuthenticationTrustResolver authenticationTrustResolver) {
    Assert.notNull(authenticationTrustResolver, "AuthenticationTrustResolver cannot be set to null");
    this.authenticationTrustResolver = authenticationTrustResolver;
  }

  public boolean supports(ConfigAttribute attribute) {
    if ((attribute.getAttribute() != null)
        && (IS_AUTHENTICATED_FULLY.equals(attribute.getAttribute())
        || IS_AUTHENTICATED_REMEMBERED.equals(attribute.getAttribute())
        || IS_AUTHENTICATED_ANONYMOUSLY.equals(attribute.getAttribute()))) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * This implementation supports any type of class, because it does not query the presented secure object.
   *
   * @param clazz the secure object type
   * @return always {@code true}
   */
  public boolean supports(Class<?> clazz) {
    return true;
  }

  // 自定义vote
  public int vote(Authentication authentication, Object object, Collection<ConfigAttribute> attributes) {
    int result = ACCESS_ABSTAIN;

    // Todo:模拟登陆
    HttpServletRequest request = HttpUtils.getRequest();
    if (request != null) {
      String usernameOrEmail = request.getParameter("sso_user");
      if (usernameOrEmail != null) {
        SSOUtils.ssoLogin(usernameOrEmail);
        return ACCESS_GRANTED;
      }
    }

    for (ConfigAttribute attribute : attributes) {
      if (this.supports(attribute)) {
        result = ACCESS_DENIED;

        if (IS_AUTHENTICATED_FULLY.equals(attribute.getAttribute())) {
          if (isFullyAuthenticated(authentication)) {
            return ACCESS_GRANTED;
          }
        }

        if (IS_AUTHENTICATED_REMEMBERED.equals(attribute.getAttribute())) {
          if (authenticationTrustResolver.isRememberMe(authentication)
              || isFullyAuthenticated(authentication)) {
            return ACCESS_GRANTED;
          }
        }

        if (IS_AUTHENTICATED_ANONYMOUSLY.equals(attribute.getAttribute())) {
          if (authenticationTrustResolver.isAnonymous(authentication) || isFullyAuthenticated(authentication)
              || authenticationTrustResolver.isRememberMe(authentication)) {
            return ACCESS_GRANTED;
          }
        }
      }
    }

    return result;
  }
}
