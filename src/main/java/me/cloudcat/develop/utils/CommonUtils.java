package me.cloudcat.develop.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.TreeMap;

/**
 * 通用工具类
 *
 * @Author: zhenzhong.wang
 * @Time: 2018/1/22 13:42
 */
public class CommonUtils {

  /**
   * 表单参数序列化
   *
   * @param formMap
   * @return
   */
  public static String formDataSerialize(Map<String, Object> formMap) {
    if (formMap != null && formMap.isEmpty()) {
      return null;
    }
    String formStr = "";
    for (String key: formMap.keySet()) {
      formStr += key + "=" + formMap.get(key) + "&";
    }
    // 去除多余的&符
    if (formStr.lastIndexOf('&') == (formStr.length() - 1)) {
      formStr = formStr.substring(0, formStr.length() - 1);
    }
    return formStr;
  }

  /**
   * 表单参数序列化(依据阿里云的规则)
   *
   * @param formMap
   * @return
   */
  public static String formDataOrderSerialize(Map<String, Object> formMap) throws UnsupportedEncodingException {
    if (formMap != null && formMap.isEmpty()) {
      return null;
    }
    TreeMap<String, Object> orderedMap = new TreeMap<>();
    for (String key: formMap.keySet()) {
      orderedMap.put(key, formMap.get(key));
    }
    String result = formDataSerialize(orderedMap);
    return result;
  }

}
