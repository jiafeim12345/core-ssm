package me.cloudcat.develop.entity.type;

/**
 * Http请求类型
 *
 * @Author: zhenzhong.wang
 * @Time: 2018/2/7 19:01
 */
public enum Method implements BaseType {

    GET(0),

    POST(1),

    PUT(2),

    PATCH(3),

    DELETE(4);

    private int value;

    Method(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }
}
