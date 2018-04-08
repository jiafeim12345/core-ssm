package me.cloudcat.develop.controller;

import me.cloudcat.develop.Constant;
import me.cloudcat.develop.entity.message.OutputObject;
import me.cloudcat.develop.entity.vo.ConfigVO;
import me.cloudcat.develop.entity.vo.DomainOutputVO;
import me.cloudcat.develop.service.DomainService;
import me.cloudcat.develop.utils.BusinessUtils;
import me.cloudcat.develop.utils.DNSUtils;
import me.cloudcat.develop.utils.ThreadUtils;
import me.cloudcat.develop.websocket.handler.ChatWebSocketHandler;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.SignatureException;
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

    @RequestMapping(value = "/admin/domain", method = RequestMethod.GET)
    public String getDomain(Model model, HttpServletRequest request) throws InterruptedException {
        request.getSession();
        // 当没人监听时，清空redis中Domain
        if (ThreadUtils.getObserver() <= 0) {
            domainService.clearDomain();
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
    public OutputObject intDomain(Model model, HttpServletRequest request) throws InterruptedException {

        // 暂停两秒，避免短时间内多次刷新导致Redis中的旧域名数据没来得及删除
        ThreadUtils.sleep(2);
        // init
        OutputObject opo = new OutputObject();
        for (int i = 0; i < 12; i++) {
            if (domainService.getDomainArray() != null) {
                opo.setCode(Constant.RESPONSE_CODE_200);
                opo.setResult(new DomainOutputVO(domainService.getTotal(), domainService.getDomainArray()));
                return opo;
            } else {
                ThreadUtils.sleep(5);
            }
        }
        opo.setCode(Constant.RESPONSE_CODE_500);
        opo.setMessage("初始化失败，请刷新页面或检查Cookie！");
        return opo;
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
        OutputObject opo = new OutputObject();

        // 校验参数正确性
        if (StringUtils.isNotEmpty(cookie)) {
            domainService.setConfig("cookie", cookie);
        } else {
            cookie = null;
        }
        if (minTime != null && minTime > 0 && minTime <= maxTime) {
            ThreadUtils.setMinTime(minTime);
        } else {
            minTime = null;
        }
        if (maxTime != null && maxTime > 0 && minTime <= maxTime) {
            ThreadUtils.setMaxTime(maxTime);
        } else {
            maxTime = null;
        }
        socketHandler.sendMessageToUser(BusinessUtils.getUser().getUsername(),
                new OutputObject(Constant.RESPONSE_CODE_200, "OK", new ConfigVO(minTime, maxTime, cookie), null));
        reAttributes.addFlashAttribute("message", "设置成功！");
        return "redirect:/admin/domainConfig";
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
