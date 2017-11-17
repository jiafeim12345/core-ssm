package me.cloudcat.develop.controller.configuration;

import me.cloudcat.develop.dao.UserMapper;
import me.cloudcat.develop.entity.configuration.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CommonController {

    @Autowired
    UserMapper userMapper;

    @RequestMapping(value = "/common/testOne", method = RequestMethod.GET)
    public String testOne() {
        User user = userMapper.getUserByType(8);
        System.out.println(user.getUsername());
        return null;
    }
}
