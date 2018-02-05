package me.cloudcat.develop.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.SignatureException;
import java.util.*;

/**
 * DNS工具类
 *
 * @Author zhenzhong.wang
 * @Time 2018/1/28 22:03
 */
public class DNSUtils {

    private static Logger logger =  Logger.getLogger(DNSUtils.class);

    public static Map<String, Object> getWhois(String domainName) throws UnsupportedEncodingException, SignatureException {
        //字典序排序
        Map<String, Object> map = new TreeMap<String, Object>();
        map.put("Action", "DescribeDomainWhoisInfo");
        map.put("DomainName", domainName);
        map.put("Format", "json");
        map.put("Version", "2015-01-09");
        map.put("SignatureNonce", UUID.randomUUID().toString());
        map.put("SignatureVersion", "1.0");
        map.put("SignatureMethod", "HMAC-SHA1");
        map.put("AccessKeyId", "LTAIowH28lBOaWUA");
        String now = DateUtils.formatISO8601Date(new Date());
        map.put("Timestamp", now);
        // url参数序列化
        String urlParams = CommonUtils.formDataOrderSerialize(map);
        String encodeUrlParam = URLEncoder.encode(urlParams, "utf-8");
        // 阿里云算法修正，解决TimeStamp冒号被转义成%253A的问题。
        encodeUrlParam = encodeUrlParam.replaceAll("%3A", "%253A");
        logger.info("Whois解析：" + domainName);
        String tosign= "POST" + "&" + "%2F" + "&" + encodeUrlParam;
        String hmacSHA1 = SignatureUtils.hmacSHA1Base64(tosign, "DxgPChG1Z6D8mVV72Yc2hmhjoKnuoi&").trim();
        String signature = URLEncoder.encode(hmacSHA1, "utf-8");
        map.put("Signature", signature);
        String responseStr = HttpUtils.sendPost("https://alidns.aliyuncs.com/", CommonUtils.formDataSerialize(map), null);
        logger.info("result：" + responseStr);
        HashMap<String, Object> resultMap = new HashMap<>();
        try {
            JSONObject domainInfoJson = JSON.parseObject(responseStr);
            resultMap.put("RegistrantEmail", domainInfoJson.get("RegistrantEmail"));
            resultMap.put("RegistrantName", domainInfoJson.get("RegistrantName"));
            resultMap.put("Registrar", domainInfoJson.get("Registrar"));
            resultMap.put("RegistrationDate", domainInfoJson.get("RegistrationDate").toString());
            resultMap.put("ExpirationDate", domainInfoJson.get("ExpirationDate").toString());
        } catch (Exception e) {
            logger.error("whois解析异常：" + e.getMessage());
        }
        return resultMap;
    }
}
