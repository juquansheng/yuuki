package com.malaxiaoyugan.yuukicore.service;

import java.util.concurrent.TimeUnit;

/**
 *@describe redis
 *@author  ttbf
 *@date  2018/7/9
 */
public interface RedisService {
    void set(String key, String value, long time, TimeUnit timeUnit, String desc);

    void set(String key, String value, String desc);

    String get(String key, String desc);

    void delete(String key, String desc);

}
