package me.cloudcat.develop.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import me.cloudcat.develop.Constant;
import me.cloudcat.develop.service.DomainService;
import me.cloudcat.develop.utils.HttpUtils;
import me.cloudcat.develop.utils.ThreadUtils;
import me.cloudcat.develop.websocket.handler.ChatWebSocketHandler;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
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

    @Autowired
    DomainService domainService;

    @Autowired
    ChatWebSocketHandler socketHandler;

    @RequestMapping(value = "/admin/wanwang/home", method = RequestMethod.GET)
    public String wanwangHome(Model model, HttpServletRequest request, @RequestParam(value = "username", defaultValue = "") String username) throws InterruptedException {
        if (!username.equals(Constant.recieveUsername)) {
           return "/domain/wanwang";
        }
        request.getSession().setAttribute(Constant.SESSION_USER, username);
        // 第一次域名查询
        String oldDomain = domainService.getWanWangDomain();
        if (StringUtils.isEmpty(oldDomain)) {
            model.addAttribute("error", "Cookie异常！点击进入Cookie设置！");
            return "/domain/wanwang";
        }

        Map oldDomainMap = JSONObject.toJavaObject(JSON.parseObject(oldDomain), Map.class);
        // 记录域名总数
        int total = (int) oldDomainMap.get("Total");
        if (total == 0) {
            model.addAttribute("error", "查询频繁，请稍后再试！");
            return "/domain/wanwang";
        }
        DomainService.setTotal(total);
        // 更新域名记录
        DomainService.updateDomainRecords(JSONArray.parseArray(oldDomainMap.get("Rows").toString()));

        // 封装页面信息
        model.addAttribute("oldDomainMap", oldDomainMap);
        model.addAttribute("minTime", ThreadUtils.getMinTime());
        model.addAttribute("maxTime", ThreadUtils.getMaxTime());

        // 开启线程
        domainService.startThread();

        return "/domain/wanwang";
    }


    @RequestMapping(value = "/admin/wanwang/config", method = RequestMethod.GET)
    public String getCookie(Model model) {
        model.addAttribute("minTime", ThreadUtils.getMinTime());
        model.addAttribute("maxTime", ThreadUtils.getMaxTime());
        return "/domain/config";
    }

    @RequestMapping(value = "/admin/wanwang/config", method = RequestMethod.POST)
    public String saveCookie(Model model, @RequestParam("cookie") String cookie,
                             @RequestParam("minTime") Integer minTime, @RequestParam("maxTime") Integer maxTime,
                             RedirectAttributes reAttributes) {
        HashMap<String, Object> resultMap = new HashMap<>();
        if (StringUtils.isNotEmpty(cookie)) {
            HttpUtils.setDomainCookie(cookie);
            reAttributes.addFlashAttribute("info", "设置成功！");
        }
        if (minTime != null && minTime > 0 && minTime <= maxTime) {
            ThreadUtils.setMinTime(minTime);
            reAttributes.addFlashAttribute("info", "设置成功！");
            resultMap.put("option", "updateMinTime");
            resultMap.put("value", minTime);
            socketHandler.sendMessageToUser(Constant.recieveUsername, resultMap);
        }
        if (maxTime != null && maxTime > 0 && minTime <= maxTime) {
            ThreadUtils.setMaxTime(maxTime);
            reAttributes.addFlashAttribute("info", "设置成功！");
            resultMap.put("option", "updateMaxTime");
            resultMap.put("value", maxTime);
            socketHandler.sendMessageToUser(Constant.recieveUsername, resultMap);
        }
        return "redirect:/admin/wanwang/config";
    }
}
