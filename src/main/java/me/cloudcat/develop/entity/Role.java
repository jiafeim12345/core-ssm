package me.cloudcat.develop.entity;

/**
 * 角色信息
 *
 * @Author: zhenzhong.wang
 * @Time: 2018/2/7 18:28
 */
public class Role extends BaseEntity<Role> {

    private String name;
    private String description;

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
}
