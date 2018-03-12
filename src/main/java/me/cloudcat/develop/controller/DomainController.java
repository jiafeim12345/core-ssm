package me.cloudcat.develop.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import me.cloudcat.develop.Constant;
import me.cloudcat.develop.entity.User;
import me.cloudcat.develop.entity.message.OutputObject;
import me.cloudcat.develop.entity.type.RefreshStatus;
import me.cloudcat.develop.redis.RedisMap;
import me.cloudcat.develop.redis.RedisMapFactory;
import me.cloudcat.develop.security.SessionManager;
import me.cloudcat.develop.service.DomainService;
import me.cloudcat.develop.utils.*;
import me.cloudcat.develop.websocket.handler.ChatWebSocketHandler;
import net.sf.json.util.JSONUtils;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.security.SignatureException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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

    @RequestMapping(value = "/admin/domain", method = RequestMethod.GET)
    public String getDomain(Model model, HttpServletRequest request) throws InterruptedException {

        // 当没人监听时，清空redis中Domain
        if (ThreadUtils.getObserver() <= 0) {
            domainMap.removeAll();
        }

        // 封装页面信息
        model.addAttribute("minTime", ThreadUtils.getMinTime());
        model.addAttribute("maxTime", ThreadUtils.getMaxTime());

        return "/domain/monitor";
    }

    /**
     * 获取初始化域名数据
     *
     * @param model
     * @param request
     * @return
     * @throws InterruptedException
     */
    @ResponseBody
    @RequestMapping(value = "/admin/domain/init", method = RequestMethod.GET)
    public JSONArray intDomain(Model model, HttpServletRequest request) throws InterruptedException {

        // init
        for (int i = 0; i < 12; i++) {
            if (domainService.getDomainArray() != null) {
                return domainService.getDomainArray();
            } else {
                ThreadUtils.sleep(5);
            }
        }
        return null;
    }

    @RequestMapping(value = "/admin/domainConfig", method = RequestMethod.GET)
    public String getConfig(Model model, HttpServletRequest request) {
        model.addAttribute("minTime", ThreadUtils.getMinTime());
        model.addAttribute("maxTime", ThreadUtils.getMaxTime());
        return "/domain/config";
    }

    @RequestMapping(value = "/admin/domainConfig", method = RequestMethod.POST)
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
        }
        if (maxTime != null && maxTime > 0 && minTime <= maxTime) {
            ThreadUtils.setMaxTime(maxTime);
            reAttributes.addFlashAttribute("info", "设置成功！");
            messageMap.put("option", "updateMaxTime");
            messageMap.put("value", maxTime);
        }
        socketHandler.sendMessageToUser(BusinessUtils.getUser().getUsername(),
                new OutputObject(Constant.RESPONSE_CODE_200, "OK", messageMap, null));
        return "redirect:/api/admin/domainConfig";
    }

    /**
     * Whois查询
     * @param domainName
     * @return
     * @throws UnsupportedEncodingException
     * @throws SignatureException
     */
    @RequestMapping(value = "/admin/whois", method = RequestMethod.GET)
    public String getWhois(@RequestParam("domainName") String domainName, Model model) throws UnsupportedEncodingException, SignatureException {
        Map<String, Object> whoisInfo = DNSUtils.getWhois(domainName);
        model.addAttribute("whoisInfo", whoisInfo);
        return "/domain/whoisDialog";
    }
}
