package me.cloudcat.develop.utils;

import java.util.Map;

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

}
