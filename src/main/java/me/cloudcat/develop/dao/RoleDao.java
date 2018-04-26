package me.cloudcat.develop.dao;

import me.cloudcat.develop.entity.Role;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 角色Dao
 *
 * @Author: zhenzhong.wang
 * @Time: 2018/2/8 17:57
 */
@Repository
public interface RoleDao extends BaseDao<Role> {

  List<Role> getRolesByUserId(@Param("userId") Long userId);
}
