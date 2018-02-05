package me.cloudcat.develop.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @Author: zhenzhong.wang
 * @Time: 2018/2/5 16:07
 */
@Component
public class RedisMapFactory {

    private static RedisTemplate redisTemplate;

    @Autowired
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public static RedisMap getRedisMap(String hashKey) {
        RedisMap redisMap = new RedisMap(redisTemplate, hashKey);
        return redisMap;
    }
}
