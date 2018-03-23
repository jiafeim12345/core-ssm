package me.cloudcat.develop.entity;

import me.cloudcat.develop.entity.type.Status;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * 用户信息
 *
 * @Author: zhenzhong.wang
 * @Time: 2018/2/7 18:28
 */
public class User extends BaseEntity<User> implements UserDetails {

  private String username;    // 用户名
  private String password;    // 密码
  private String email;      // 邮箱

  private Person person;      // 人员
  private List<Role> roles;   // 角色

  private Date createTime;     // 创建时间
  private Date updateTime;     // 更新时间
  private Date lastLoginTime;  // 最后登录时间

  private Status status;  // 停启用状态

  private boolean accountNonExpired;      // 账户未过期
  private boolean accountNonLocked;       // 账户未被锁
  private boolean credentialsNonExpired;  // 认证未过期

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return roles;
  }

  @Override
  public String getPassword() {
    return password;
  }

  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return accountNonExpired;
  }

  @Override
  public boolean isAccountNonLocked() {
    return accountNonLocked;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return credentialsNonExpired;
  }

  @Override
  public boolean isEnabled() {
    return status.equals(Status.ENABLE) ? true : false;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Person getPerson() {
    return person;
  }

  public void setPerson(Person person) {
    this.person = person;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public Date getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }

  public Date getLastLoginTime() {
    return lastLoginTime;
  }

  public void setLastLoginTime(Date lastLoginTime) {
    this.lastLoginTime = lastLoginTime;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public List<Role> getRoles() {
    return roles;
  }

  public void setRoles(List<Role> roles) {
    this.roles = roles;
  }

  /**
   * 设定账户安全状态
   *
   * @param accountNonLocked
   * @param accountNonExpired
   * @param credentialsNonExpired
   */
  public void setSecurityStatus(Boolean accountNonLocked, Boolean accountNonExpired, Boolean credentialsNonExpired) {
    if (accountNonLocked != null) {
      this.accountNonLocked = accountNonLocked;
    }
    if (accountNonExpired != null) {
      this.accountNonExpired = accountNonExpired;
    }
    if (credentialsNonExpired != null) {
      this.credentialsNonExpired = credentialsNonExpired;
    }
  }
}
