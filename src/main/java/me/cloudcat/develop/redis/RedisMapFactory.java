package me.cloudcat.develop.redis;

import org.springframework.data.redis.core.RedisTemplate;

/**
 * @Author: zhenzhong.wang
 * @Time: 2018/2/5 16:07
 */
public class RedisMapFactory {

    private static RedisTemplate redisTemplate;

    public RedisMapFactory() {}

    public RedisMapFactory(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public static RedisMap getRedisMap(String hashKey) {
        RedisMap redisMap = new RedisMap(redisTemplate, hashKey);
        return redisMap;
    }
}
