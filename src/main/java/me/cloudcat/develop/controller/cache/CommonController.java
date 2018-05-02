package me.cloudcat.develop.controller.cache;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CommonController {

  @RequestMapping(value = "/common/testOne", method = RequestMethod.GET)
  public String testOne() {
    return null;
  }
}
