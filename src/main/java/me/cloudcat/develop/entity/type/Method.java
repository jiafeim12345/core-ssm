package me.cloudcat.develop.entity.type;

/**
 * Http请求类型
 *
 * @Author: zhenzhong.wang
 * @Time: 2018/2/7 19:01
 */
public enum Method implements BaseType {

    /**
     * GET
     */
    GET(0),
    /**
     * POST
     */
    POST(1),
    /**
     * PUT
     */
    PUT(2),
    /**
     * PATCH
     */
    PATCH(3),

    /**
     * DELETE
     */
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
