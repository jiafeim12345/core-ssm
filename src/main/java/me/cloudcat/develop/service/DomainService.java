package me.cloudcat.develop.service;

import com.alibaba.fastjson.JSONArray;
import me.cloudcat.develop.Constant;
import me.cloudcat.develop.utils.CommonUtils;
import me.cloudcat.develop.utils.HttpUtils;
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
        headers.put("Cookie", Constant.domainCookie);
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
        headers.put("Cookie", Constant.domainCookie);
        String result = HttpUtils.sendPost("https://newly.faname.com/tools/newly/Newly03.ashx",
                paramStr + "&PageIndex=1", headers);
        System.out.println(result);
        return result;
    }

    /**
     * 获取万网更新的域名
     *
     * @return
     */
    public String getWanWangUpdatedDomain() {
        return null;
    }

}
