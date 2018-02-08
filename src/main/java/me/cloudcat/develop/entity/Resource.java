package me.cloudcat.develop.entity;

import me.cloudcat.develop.entity.type.Status;

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

    private Status status = Status.ENABLE;   // 停启用状态

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
