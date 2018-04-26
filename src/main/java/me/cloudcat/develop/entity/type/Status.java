package me.cloudcat.develop.entity.type;

/**
 * 停启用状态
 *
 * @Author: zhenzhong.wang
 * @Time: 2018/2/7 18:50
 */
public enum Status implements BaseType {

  DISABLE(0),   // 停用

  ENABLE(1);  // 启用

  private int value;

  private Status(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }
}
