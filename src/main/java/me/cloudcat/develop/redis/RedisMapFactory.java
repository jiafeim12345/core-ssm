package me.cloudcat.develop.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * RedisMap工厂
 *
 * @Author: zhenzhong.wang
 * @Time: 2018/2/5 16:07
 */
public class RedisMapFactory {

    private static RedisTemplate redisTemplate;

    public RedisMapFactory() {}

    @Autowired
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public static RedisMap getRedisMap(String hashKey) {
        RedisMap redisMap = new RedisMap(redisTemplate, hashKey);
        return redisMap;
    }
}
