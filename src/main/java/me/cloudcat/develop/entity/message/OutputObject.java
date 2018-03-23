package me.cloudcat.develop.entity.message;

import java.util.Map;

/**
 * 输出对象
 *
 * @Author: zhenzhong.wang
 * @Time: 2018/3/12 17:24
 */
public class OutputObject {

  String code;        // 状态码
  String message;     // 消息
  Object result;      // 结果
  String option;      // 操作

  private Map<String, Error> fields;

  public OutputObject(String code, String message, Object result, String option) {
    this.code = code;
    this.message = message;
    this.result = result;
    this.option = option;
  }

  public OutputObject() {
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Object getResult() {
    return result;
  }

  public void setResult(Object result) {
    this.result = result;
  }

  public Map<String, Error> getFields() {
    return fields;
  }

  public void setFields(Map<String, Error> fields) {
    this.fields = fields;
  }

  public String getOption() {
    return option;
  }

  public void setOption(String option) {
    this.option = option;
  }
}
