package me.cloudcat.develop.controller;

import me.cloudcat.develop.utils.BusinessUtils;
import me.cloudcat.develop.utils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * 登录控制
 *
 * @Author: zhenzhong.wang
 * @Time: 2018/1/19 14:30
 */
@Controller
public class LoginController {

  private static Logger logger = LoggerFactory.getLogger(LoginController.class);

  /**
   * 登录页面路由
   *
   * @param model
   * @param request
   * @return
   * @throws InterruptedException
   */
  @RequestMapping(value = "/login", method = RequestMethod.GET)
  public String getDomain(Model model, HttpServletRequest request) throws InterruptedException {
    // 用户已登录
    if (BusinessUtils.getUser() != null) {
      return "redirect:" + PropertyUtils.getString("login.success.redirect");
    }
    // 进入登录页面
    return "login";
  }
}
