package me.cloudcat.develop.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import me.cloudcat.develop.redis.RedisMap;
import me.cloudcat.develop.redis.RedisMapFactory;
import me.cloudcat.develop.utils.BusinessUtils;
import me.cloudcat.develop.utils.CommonUtils;
import me.cloudcat.develop.utils.HttpUtils;
import me.cloudcat.develop.utils.ThreadUtils;
import me.cloudcat.develop.websocket.handler.ChatWebSocketHandler;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * @Author: zhenzhong.wang
 * @Time: 2018/1/19 14:39
 */
@Service
public class DomainService {

    private static Logger logger = LoggerFactory.getLogger("domain");

    @Autowired
    ChatWebSocketHandler socketHandler;

    static RedisMapFactory redisFactory;
    static RedisMap domainMap;
    static RedisMap configMap;

    @Autowired
    public void setRedisFactory(RedisMapFactory redisFactory) {
        this.redisFactory = redisFactory;
        domainMap = redisFactory.getRedisMap("domain");
        configMap = redisFactory.getRedisMap("config");
    }

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
        // 查询参数初始化
        int pageSize = 200;
        int pageIndex = 1;
        String registrar = "HICHINA ZHICHENG TECHNOLOGY LTD.";

        params.put("Registrar", registrar);
        params.put("PageSize", pageSize);
        params.put("PageIndex", pageIndex);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String now = formatter.format(LocalDate.now());
        params.put("datestr", now);
        String paramStr = CommonUtils.formDataSerialize(params);
        headers.put("Cookie", getDomainCookie());
        String result = HttpUtils.sendPost("https://newly.faname.com/tools/newly/Newly03.ashx",
                paramStr, headers);
        if (StringUtils.isNotEmpty(result)){
            logger.info("万网域名：Total " + JSONArray.parseObject(result).get("Total")
                    + "  刷新频率：" + ThreadUtils.getMinTime() +" ~ " + ThreadUtils.getMaxTime() + " 秒");
            logger.debug("Cookie：" + getDomainCookie());
        }
        return result;
    }

    /**
     * 更新域名记录
     * @param json
     */
    public void updateDomainRecords(JSONArray json) {
        Set<String> domainRecords = new HashSet<String>();
        for(Object ob : json){
            domainRecords.add(JSON.parseObject(ob.toString()).get("Domain").toString());
        }
        domainMap.put("records", domainRecords);
    }

    public Set<String> getDomainRecords() {
        return (Set<String>) domainMap.get("records");
    }

    /**
     * 清除redis中域名
     */
    public void clearDomain() {
        domainMap.remove("records");
    }

    /**
     * 获取新域名
     * 通过新域名集合和旧域名记录的对比，获取新更新的域名
     *
     * @param newDomainArray
     * @return
     */
    public JSONArray getNewDomain(JSONArray newDomainArray) {
        JSONArray result = new JSONArray();
        for(Object ob : newDomainArray) {
            String domain = JSON.parseObject(ob.toString()).get("Domain").toString();
            // 如果域名在旧域名记录中不存在，则返回
            Set<String> oldDomains = getDomainRecords();
            if (!oldDomains.contains(domain)) {
                result.add(ob);
            }
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
        Object domainCookie = configMap.get("cookie");
        if (domainCookie != null && domainCookie.toString().contains(now)) {
            return domainCookie.toString();
        } else {
            return null;
        }
    }
}
