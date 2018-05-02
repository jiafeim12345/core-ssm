package me.cloudcat.develop.utils;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.SimpleTimeZone;

/**
 * 日期处理工具
 *
 * @Author: zhenzhong.wang
 * @Time: 2018/1/25 15:23
 */
public class DateUtils {

  /**
   * 获取ISO8601日期
   *
   * @param date
   * @return
   */
  public static String formatISO8601Date(Date date) {
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    df.setTimeZone(new SimpleTimeZone(0, "GMT"));
    return df.format(date);
  }

  /**
   * 格式化为常用格式：yyyy-MM-dd HH:mm:ss
   *
   * @param date
   * @return
   */
  public static String formatToCommon(TemporalAccessor date) {
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    String result = dtf.format(date);
    return result;
  }
}
