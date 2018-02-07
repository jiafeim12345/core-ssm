package me.cloudcat.develop.entity;

import java.lang.reflect.Method;
import java.util.Date;

/**
 * 资源类
 *
 * @Author: zhenzhong.wang
 * @Time: 2018/2/7 18:47
 */
public class Resource extends BaseEntity<Resource> {

    private String name;
    private String description;


    private Method method;   // 资源类型
    private String api;      // 接口信息

    private Date createTime;
    private Date updateTime;

}
