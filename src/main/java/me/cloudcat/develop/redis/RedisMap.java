package me.cloudcat.develop.redis;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * Redis操作类
 *
 * @Author: zhenzhong.wang
 * @Time: 2018/2/5 12:58
 */
public class RedisMap {

    private RedisTemplate redisTemplate;

    private String hashKey;
    private HashOperations operations;

    public RedisMap(RedisTemplate redisTemplate, String hashKey) {
        this.hashKey = hashKey;
        this.redisTemplate = redisTemplate;
        operations = redisTemplate.opsForHash();
    }

    /**
     * 存储
     * @param key
     * @param value
     */
    public void put(String key, Object value) {
        operations.put(hashKey, key, value);
    }

    /**
     * 取值
     * @param key
     * @return
     */
    public Object get(String key) {
        return operations.get(hashKey, key);
    }

    /**
     * 删除RedisMap中键值
     * @param hashKey
     * @param key
     * @return
     */
    public Long remove(String hashKey, Object... key) {
        return operations.delete(hashKey, key);
    }

    /**
     * 删除RedisMap
     * @param hashKey
     */
    public void removeAll(String hashKey) {
        redisTemplate.delete(hashKey);
    }
}
