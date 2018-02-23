package me.cloudcat.develop.entity.type;

/**
 * 角色类型
 *
 * @Author: zhenzhong.wang
 * @Time: 2018/2/7 18:31
 */
public enum RoleType implements BaseType {

    ROOT(0),   // 超级管理员

    USER(1);   // 普通用户

    private int value;

    private RoleType(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }
}
