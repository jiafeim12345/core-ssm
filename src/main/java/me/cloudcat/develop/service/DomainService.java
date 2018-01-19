package me.cloudcat.develop.service;

import com.alibaba.fastjson.JSON;
import me.cloudcat.develop.Constant;
import me.cloudcat.develop.utils.HttpUtils;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

/**
 * @Author: zhenzhong.wang
 * @Time: 2018/1/19 14:39
 */
@Service
public class DomainService {

    /**
     * 查询更新的域名
     * @return
     */
    public String getDomain() throws InterruptedException {
        // header封装
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Host", "newly.faname.com");
        headers.put("Origin", "https://newly.faname.com");
        headers.put("Referer", "https://newly.faname.com/tools/newly/");
        headers.put("X-Requested-With", "XMLHttpRequest");
        headers.put("Cookie", Constant.domainCookie);

        HashMap<String, Object> params = new HashMap<>();
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
        params.put("sortby", "RegDate");
        params.put("sortOrder", "desc");

        String paramStr = "";
        for (String paramKey: params.keySet()) {
            paramStr += paramKey + "=" + params.get(paramKey) + "&";
        }
//        String result = HttpUtils.sendPost("https://newly.faname.com/tools/newly/Newly03.ashx",
//                paramStr + "PageSize=200&PageIndex=1", headers);
//        System.out.println(result);
//        Thread.sleep(12000);
        headers.put("Cookie", Constant.domainCookie);
        String result2 = HttpUtils.sendPost("https://newly.faname.com/tools/newly/Newly03.ashx",
                paramStr + "PageSize=50&PageIndex=1", headers);
        System.out.println(result2);
        return result2;
    }

}
