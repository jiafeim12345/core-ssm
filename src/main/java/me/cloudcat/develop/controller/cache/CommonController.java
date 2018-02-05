package me.cloudcat.develop.controller.cache;

import me.cloudcat.develop.redis.RedisMap;
import me.cloudcat.develop.redis.RedisMapFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CommonController {

    @Autowired
    RedisMapFactory redisFactory;

    @RequestMapping(value = "/common/testOne", method = RequestMethod.GET)
    public String testOne() {
        RedisMap redisMap = redisFactory.getRedisMap("test");
//        redisMap.put("name", "123");
        System.out.println(redisMap.get("name"));
        return null;

    }
}
