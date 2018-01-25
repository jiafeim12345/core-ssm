package me.cloudcat.develop.java;

import me.cloudcat.develop.utils.CommonUtils;
import me.cloudcat.develop.utils.DateUtils;
import me.cloudcat.develop.utils.SignatureUtils;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.SignatureException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @Author: zhenzhong.wang
 * @Time: 2018/1/18 16:16
 */
public class DemoTest {

    @Test
    public void testOne() {
        ArrayList<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("123");
        list.forEach(e -> System.out.println(e));
    }

    @Test
    public void testTwo() throws UnsupportedEncodingException, SignatureException {
        //字典序排序
        Map<String, Object> map = new TreeMap<String, Object>();
        map.put("Action", "DescribeDomainWhoisInfo");
        map.put("DomainName", "cloudcat.me");
        map.put("Format", "json");
        map.put("Version", "2015-01-09");
        UUID uuid = UUID.randomUUID();
        map.put("SignatureNonce", uuid.toString());
        System.out.println(uuid);
        map.put("SignatureVersion", "1.0");
        map.put("SignatureMethod", "HMAC-SHA1");
        map.put("AccessKeyId", "LTAIowH28lBOaWUA");
        String now = DateUtils.formatISO8601Date(new Date());
        System.out.println(now);
        map.put("Timestamp", now);
        String urlParams = CommonUtils.formDataOrderSerialize(map);
        String encodeUrlParam = URLEncoder.encode(urlParams, "utf-8");
        System.out.println(encodeUrlParam);
        String ToSign= "POST" + "&" + "%2F" + "&" + encodeUrlParam;
        System.out.println("Tosign: " + ToSign);
        String hmacSHA1 = SignatureUtils.hmacSHA1Base64(ToSign, "DxgPChG1Z6D8mVV72Yc2hmhjoKnuoi&").trim();
        System.out.println(hmacSHA1);
        System.out.println(URLEncoder.encode(hmacSHA1, "utf-8"));
    }
}
