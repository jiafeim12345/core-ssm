package me.cloudcat.develop.controller.cache;

import me.cloudcat.develop.dao.UserDao;
import me.cloudcat.develop.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CommonController {

    @Autowired
    UserDao userDao;

    @RequestMapping(value = "/common/testOne", method = RequestMethod.GET)
    public String testOne() {
        User user = userDao.getUser("admin");
        System.out.println(user.getEmail());
        return null;
    }
}
