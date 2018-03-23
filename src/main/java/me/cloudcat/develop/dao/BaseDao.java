package me.cloudcat.develop.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * dao基类
 *
 * @Author: zhenzhong.wang
 * @Time: 2018/2/8 11:49
 */
public interface BaseDao<T> {

    // 查询单个实体
    public <T> T findOne(Long id);

    // 条件查询
    public <T> T findByCondition(@Param("e") T entity);

    // 模糊查询
    public <T> T search(@Param("e") T entity);

    public List<T> findAll();

    // 查询基本信息
    public <T> T getBasic(Long id);

    public void save(@Param("e") T entity);

    public void saveAll(List<T> list);

    public void update(@Param("e") T entity);

    public void delete(Long id);

    public void deleteByCondition(@Param("e") T entity);

}
