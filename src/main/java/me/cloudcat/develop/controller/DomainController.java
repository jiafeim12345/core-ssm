package me.cloudcat.develop.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import me.cloudcat.develop.Constant;
import me.cloudcat.develop.service.DomainService;
import me.cloudcat.develop.utils.ThreadUtils;
import me.cloudcat.develop.websocket.handler.ChatWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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

    public static int total;

    @RequestMapping(value = "/admin/wanwang/home", method = RequestMethod.GET)
    public String wanwangHome(Model model, HttpServletRequest request, @RequestParam(value = "username", defaultValue = "") String username) throws InterruptedException {
        if (!username.equals("caiyun")) {
           return "/domain/wanwang";
        }
        request.getSession().setAttribute(Constant.SESSION_USER, username);
        Thread.sleep(3000);
        // 第一次域名查询
        String oldDomain = domainService.getWanWangDomain();
        Map oldDomainMap = JSONObject.toJavaObject(JSON.parseObject(oldDomain), Map.class);
        model.addAttribute("oldDomainMap", oldDomainMap);
        // 记录域名总数
        total = (int) oldDomainMap.get("Total");
        ThreadUtils.setInterrupt(false);
        Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    while (!(boolean) ThreadUtils.getInterrupt()) {
                        // 睡眠4到6秒
                        ThreadUtils.sleep(4, 6);
                        // 查询域名
                        String domainStr = "";
                        domainStr = domainService.getWanWangDomain();
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
                        if (total == currentTotal) {
                            continue;
                        }
                        // 域名总数增加
                        if (currentTotal > total) {
                            // 更新记录总数
                            total = currentTotal;
                            resultMap.put("Rows", rows.subList(0, currentTotal - total));
                        }
                        resultMap.put("Total", currentTotal.toString());
                        socketHandler.sendMessageToUser("caiyun", resultMap);
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    HashMap<String, Object> errorMap = new HashMap<>();
                    errorMap.put("error", "查询出现异常，请联系管理员！");
                    socketHandler.sendMessageToUser("caiyun", errorMap);
                }
            }});
        t.start();
        return "/domain/wanwang";
    }
}
