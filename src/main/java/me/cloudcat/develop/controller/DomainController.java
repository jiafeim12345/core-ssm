package me.cloudcat.develop.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import me.cloudcat.develop.entity.User;
import me.cloudcat.develop.redis.RedisMap;
import me.cloudcat.develop.redis.RedisMapFactory;
import me.cloudcat.develop.security.SessionManager;
import me.cloudcat.develop.service.DomainService;
import me.cloudcat.develop.utils.*;
import me.cloudcat.develop.websocket.handler.ChatWebSocketHandler;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.security.SignatureException;
import java.util.HashMap;
import java.util.Map;

/**
 * 新注册域名
 *
 * @Author: zhenzhong.wang
 * @Time: 2018/1/19 14:30
 */
@Controller
public class DomainController {

    private static Logger logger = LoggerFactory.getLogger("domain");

    @Autowired
    DomainService domainService;

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

    @RequestMapping(value = "/api/admin/domain", method = RequestMethod.GET)
    public String getDomain(Model model, HttpServletRequest request) throws InterruptedException {
        // 第一次域名查询
        String oldDomain = domainService.getWanWangDomain();
        // cookie异常，返回首页
        if (StringUtils.isEmpty(oldDomain)) {
            model.addAttribute("error", "Cookie异常，请先进行系统设置！");
            return "/domain/monitor";
        }

        Map oldDomainMap = JSONObject.toJavaObject(JSON.parseObject(oldDomain), Map.class);
        // 记录域名总数
        int total = (int) oldDomainMap.get("Total");
        if (total == 0) {
            model.addAttribute("error", "查询频繁，请稍后再试！");
            return "/domain/monitor";
        }
        domainMap.put("total", total);
        // 更新域名记录
        domainService.updateDomainRecords(JSONArray.parseArray(oldDomainMap.get("Rows").toString()));

        // 封装页面信息
        model.addAttribute("oldDomainMap", oldDomainMap);
        model.addAttribute("minTime", ThreadUtils.getMinTime());
        model.addAttribute("maxTime", ThreadUtils.getMaxTime());

        // 开启线程
        domainService.startThread(request);

        return "/domain/monitor";
    }


    @RequestMapping(value = "/api/admin/domainConfig", method = RequestMethod.GET)
    public String getConfig(Model model, HttpServletRequest request) {
        model.addAttribute("minTime", ThreadUtils.getMinTime());
        model.addAttribute("maxTime", ThreadUtils.getMaxTime());
        return "/domain/config";
    }

    @RequestMapping(value = "/api/admin/domainConfig", method = RequestMethod.POST)
    public String saveConfig(Model model, @RequestParam("cookie") String cookie,
                             @RequestParam("minTime") Integer minTime, @RequestParam("maxTime") Integer maxTime,
                             RedirectAttributes reAttributes) {
        HashMap<String, Object> messageMap = new HashMap<>();
        if (StringUtils.isNotEmpty(cookie)) {
            configMap.put("cookie", cookie);
            reAttributes.addFlashAttribute("info", "设置成功！");
        }
        if (minTime != null && minTime > 0 && minTime <= maxTime) {
            ThreadUtils.setMinTime(minTime);
            reAttributes.addFlashAttribute("info", "设置成功！");
            messageMap.put("option", "updateMinTime");
            messageMap.put("value", minTime);
            socketHandler.sendMessageToUser(BusinessUtils.getUser().getUsername(), messageMap);
        }
        if (maxTime != null && maxTime > 0 && minTime <= maxTime) {
            ThreadUtils.setMaxTime(maxTime);
            reAttributes.addFlashAttribute("info", "设置成功！");
            messageMap.put("option", "updateMaxTime");
            messageMap.put("value", maxTime);
            socketHandler.sendMessageToUser(BusinessUtils.getUser().getUsername(), messageMap);
        }
        return "redirect:/api/admin/domainConfig";
    }

    /**
     * Whois查询
     * @param domainName
     * @return
     * @throws UnsupportedEncodingException
     * @throws SignatureException
     */
    @RequestMapping(value = "/api/admin/whois", method = RequestMethod.GET)
    public String getWhois(@RequestParam("domainName") String domainName, Model model) throws UnsupportedEncodingException, SignatureException {
        Map<String, Object> whoisInfo = DNSUtils.getWhois(domainName);
        model.addAttribute("whoisInfo", whoisInfo);
        return "/domain/whoisDialog";
    }
}
