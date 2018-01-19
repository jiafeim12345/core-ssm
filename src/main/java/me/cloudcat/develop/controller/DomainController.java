package me.cloudcat.develop.controller;

import me.cloudcat.develop.service.DomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

    @RequestMapping(value = "/api/admin/domain", method = RequestMethod.GET)
    public void queryDomain(Model model) throws InterruptedException {
        String totalDomain = domainService.getDomain();

//        System.out.println(totalDomain);
    }

}
