package me.cloudcat.develop.entity;

import me.cloudcat.develop.entity.type.Status;

import java.util.List;

/**
 * 角色信息
 *
 * @Author: zhenzhong.wang
 * @Time: 2018/2/7 18:28
 */
public class Role extends BaseEntity<Role> {

    private String name;
    private String description;

    private List<Resource> resources;  // 资源集合
    private Status status;             // 停启用状态

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

    public List<Resource> getResources() {
        return resources;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
