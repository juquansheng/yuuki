package com.uuworlds.yuuki.service.impl;


import com.uuworlds.yuuki.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 *@describe
 *@author  ttbf
 *@date  2018/7/5
 */
@Service
public class RedisServiceImpl implements RedisService {
    private static final Logger logger = LoggerFactory.getLogger(RedisServiceImpl.class);
    private final RedisTemplate<String,Object> redisTemplate;

    @Autowired
    public RedisServiceImpl(RedisTemplate<String,Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
    /**
     * 添加缓存(带时间)
     * @param key 缓存key
     * @param value 缓存value
     * @param time 缓存时长
     * @param timeUnit 缓存时间单位
     * @param desc 缓存说明
     */
    @Override
    public void set(String key, String value, long time, TimeUnit timeUnit, String desc) {
        try {
            redisTemplate.opsForValue().set(key, value, time, timeUnit);
        } catch (Exception e) {
            logger.error("redis写入 " + desc + " 信息失败!");
        }
    }

    /**
     * 添加缓存
     *
     * @param key 缓存key
     * @param value 缓存value
     * @param desc 缓存说明
     */
    @Override
    public void set(String key, String value, String desc) {
        try {
            redisTemplate.opsForValue().set(key, value);
        } catch (Exception e) {
            logger.error("redis写入 " + desc + " 信息失败!");
        }
    }

    /**
     * 获取缓存
     *
     * @param key 缓存key
     * @param desc 缓存说明
     */
    @Override
    public String get(String key, String desc) {
        String value = null;
        try {
            value = (String) redisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            logger.error("redis读取 " + desc + " 信息失败!");
        }
        return value;
    }

    @Override
    public void delete(String key, String desc) {
        try {
            redisTemplate.delete(key);
        } catch (Exception e) {
            logger.error("redis读取 " + desc + " 信息失败!");
        }
    }
}
