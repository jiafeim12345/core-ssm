package me.cloudcat.develop.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.SimpleTimeZone;


/**
 * @Author: zhenzhong.wang
 * @Time: 2018/1/25 15:23
 */
public class DateUtils {

    /**
     * 获取ISO8601日期
     * @param date
     * @return
     */
    public static String formatISO8601Date(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        df.setTimeZone(new SimpleTimeZone(0, "GMT"));
        return df.format(date);
    }
}
