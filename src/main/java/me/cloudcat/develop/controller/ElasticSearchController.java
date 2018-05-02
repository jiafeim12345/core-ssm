package me.cloudcat.develop.controller;

import me.cloudcat.develop.service.es.ElasticSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.net.UnknownHostException;

/**
 * @Author: jiafeim12345@163.com
 * @Date: 2018/5/2/002 21:18
 */
@Controller
public class ElasticSearchController {

  @Autowired
  ElasticSearchService esService;

  /**
   * 测试一
   *
   * @param model
   * @return
   */
  @RequestMapping(value = "/es/testOne", method = RequestMethod.GET)
  public String testOne(Model model) throws IOException {
    esService.getConnection();
    esService.addIndex();
    return null;
  }

}
