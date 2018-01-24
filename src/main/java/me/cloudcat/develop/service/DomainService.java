package me.cloudcat.develop.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import me.cloudcat.develop.Constant;
import me.cloudcat.develop.utils.CommonUtils;
import me.cloudcat.develop.utils.HttpUtils;
import me.cloudcat.develop.utils.ThreadUtils;
import me.cloudcat.develop.websocket.handler.ChatWebSocketHandler;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

/**
 * @Author: zhenzhong.wang
 * @Time: 2018/1/19 14:39
 */
@Service
public class DomainService {

    private static Logger logger = Logger.getLogger(DomainService.class);

    private static Integer total = 0;

    @Autowired
    ChatWebSocketHandler socketHandler;

    // header封装
    HashMap<String, String> headers = new HashMap<>();
    HashMap<String, Object> params = new HashMap<>();

    public static JSONArray wanwang = new JSONArray();

    public DomainService() {
        // 初始化header
        headers.put("Host", "newly.faname.com");
        headers.put("Origin", "https://newly.faname.com");
        headers.put("Referer", "https://newly.faname.com/tools/newly/");
        headers.put("X-Requested-With", "XMLHttpRequest");
        // 初始化params
        params.put("act", "newlydaily");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String now = formatter.format(LocalDate.now());
        params.put("datestr", now);
        params.put("Tld", "");
        params.put("Registrar", "");
        params.put("Province", "shanghai");
        params.put("City", "all");
        params.put("Email", false);
        params.put("Address", false);
        params.put("PhontNO", false);
        // 排序
        /*params.put("sortby", "RegDate");
        params.put("sortOrder", "desc");*/
    }

    /**
     * 查询万网域名
     * @return
     */
    public String getWanWangDomain() throws InterruptedException {
        int pageSize = 50;
        int pageIndex = 1;
        // 注册商设定为万网
        String registrar = "HICHINA ZHICHENG TECHNOLOGY LTD.";

        params.put("Registrar", registrar);
        params.put("PageSize", pageSize);
        params.put("PageIndex", pageIndex);
        String paramStr = CommonUtils.formDataSerialize(params);
        headers.put("Cookie", getDomainCookie());
        String result = HttpUtils.sendPost("https://newly.faname.com/tools/newly/Newly03.ashx",
                paramStr + "&PageIndex=1", headers);
        if (StringUtils.isNotEmpty(result)){
            logger.info("万网域名：Total-" + JSONArray.parseObject(result).get("Total") + "  " + result
            + "\n刷新时间：" + ThreadUtils.getMinTime() +" ~ " + ThreadUtils.getMaxTime() + " 秒");
        }
        return result;
    }

    /**
     * 获取最新Cookie
     *
     * @return
     */
    public String getDomainCookie() {
        String now = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDate.now());
        String domainCookie = HttpUtils.getDomainCookie();
        if (domainCookie != null && domainCookie.contains(now)) {
            return domainCookie;
        } else {
            return null;
        }
    }

    /**
     * 设置域名总数（线程安全）
     * @param sum
     */
    public static void setTotal(Integer sum) {
        synchronized (total) {
            total = sum;
        }
    }

    public static Integer getTotal() {
        return total;
    }

    /**
     * 开启查询线程，每min~max秒内进行数据爬取
     */
    public void startThread(){
        // 开启线程循环
        ThreadUtils.setInterrupt(false);
        Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    while (!(boolean) ThreadUtils.getInterrupt()) {
                        // 睡眠4到6秒
                        ThreadUtils.sleep(ThreadUtils.getMinTime(), ThreadUtils.getMaxTime());
                        // 查询域名
                        String domainStr = "";
                        domainStr = getWanWangDomain();
                        // 数据清洗
                        JSONObject domainJson = JSON.parseObject(domainStr);
                        HashMap<String, Object> resultMap = new HashMap<>();
                        // 当前域名总数
                        Integer currentTotal = (Integer) domainJson.get("Total");

                        JSONArray rows = (JSONArray) domainJson.get("Rows");
                        // 查询过于频繁,等待六秒钟后查询
                        if (currentTotal == 0) {
                            Thread.sleep(6000);
                            continue;
                        }
                        Integer domainTotal = DomainService.getTotal();
                        if (domainTotal == currentTotal) {
                            continue;
                        }
                        // 域名总数增加
                        if (currentTotal > domainTotal) {
                            // 更新记录总数
                            DomainService.setTotal(currentTotal);
                            resultMap.put("Rows", rows.subList(0, currentTotal - domainTotal));
                        }
                        resultMap.put("Total", currentTotal.toString());
                        socketHandler.sendMessageToUser(Constant.recieveUsername, resultMap);
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    HashMap<String, Object> errorMap = new HashMap<>();
                    errorMap.put("error", "查询出现异常，请联系管理员！");
                    socketHandler.sendMessageToUser(Constant.recieveUsername, errorMap);
                }
            }});
        t.start();
    }
}
