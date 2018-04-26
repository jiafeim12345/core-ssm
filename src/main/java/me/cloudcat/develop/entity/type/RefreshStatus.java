package me.cloudcat.develop.entity.type;

/**
 * 域名刷新状态
 *
 * @Author: zhenzhong.wang
 * @Time: 2018/2/7 19:01
 */
public enum RefreshStatus implements BaseType {

  NONE(0),        // 未刷新
  REFRESHING(1),  // 刷新中
  REFRESHED(2);   // 已刷新


  private int value;

  RefreshStatus(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }
}
