package me.cloudcat.develop.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import me.cloudcat.develop.Constant;
import me.cloudcat.develop.service.DomainService;
import me.cloudcat.develop.websocket.handler.ChatWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

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

    public static boolean isInterrupt = true;

    @RequestMapping(value = "/admin/wanwang/home", method = RequestMethod.GET)
    public String wanwangHome(Model model, HttpServletRequest request, @RequestParam(value = "username", defaultValue = "") String username) throws InterruptedException {
        request.getSession().setAttribute(Constant.SESSION_USER, username);
        String oldDomain = domainService.getWanWangDomain();
        isInterrupt = false;
        /*Thread t = new Thread(new Runnable(){
            public void run(){
                while (!isInterrupt) {
                    Random random = new Random();
                    // 设定随机时间，单位：秒
                    int maxSleep = 6;
                    int minSleep = 4;
                    int randomSecond = minSleep + random.nextInt(maxSleep - minSleep + 1);
                    try {
                        Thread.sleep(randomSecond * 1000);
                        // 查询域名
                        String wanWangDomain = "";
                        wanWangDomain = domainService.getWanWangDomain();
                        JSONObject jsonOB = JSON.parseObject(wanWangDomain);
                        String result = jsonOB.get("Total").toString();
//                        JSONArray arr = JSON.parseArray(jsonOB.get("Rows").toString());
                        socketHandler.sendMessageToUser("caiyun", result);
                        isInterrupt = false;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }});*/
//        t.start();
        Map oldDomainMap = JSONObject.toJavaObject(JSON.parseObject(oldDomain), Map.class);
        model.addAttribute("oldDomainMap", oldDomainMap);
        return "/domain/wanwang";
    }

    @RequestMapping(value = "/admin/allSite/home", method = RequestMethod.GET)
    public String queryWanWangDomain(Model model, HttpServletRequest request, @RequestParam(value = "username", defaultValue = "") String username) throws InterruptedException {
        HashMap<String, Object> messageMap = new HashMap<>();
        messageMap.put("test", 123);
        socketHandler.sendMessageToUser("caiyun", messageMap);
        isInterrupt = true;
        return "";
    }
}
