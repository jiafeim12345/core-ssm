package me.cloudcat.develop.dao;

import me.cloudcat.develop.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao extends BaseDao<User> {

    public void updateLastLoginTime(Long id);

    public User findByUsernameOrEmail(String value);

    public void updatePassword(@Param("e") User user);
}