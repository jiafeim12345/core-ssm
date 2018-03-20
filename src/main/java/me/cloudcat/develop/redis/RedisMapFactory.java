package me.cloudcat.develop.redis;

import me.cloudcat.develop.utils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * RedisMap工厂
 *
 * @Author: zhenzhong.wang
 * @Time: 2018/2/5 16:07
 */
@Component
public class RedisMapFactory {

    private static RedisTemplate redisTemplate;

    @Autowired
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        RedisMapFactory.redisTemplate = redisTemplate;
    }

    public static RedisMap getRedisMap(String hashKey) {
        RedisMap redisMap = new RedisMap(redisTemplate, PropertyUtils.getString("redis.key") + ":" + hashKey);
        return redisMap;
    }
}
