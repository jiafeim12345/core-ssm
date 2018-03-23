package me.cloudcat.develop.dao;

import me.cloudcat.develop.entity.Resource;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 资源dao
 *
 * @Author: zhenzhong.wang
 * @Time: 2018/2/8 17:56
 */
@Repository
public interface ResourceDao extends BaseDao<Resource> {

    public List<Resource> getResourcesByRole(@Param("roleId") Long roleId);
}
