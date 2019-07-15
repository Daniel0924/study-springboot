package com.ydh.spring.springboot.service.impl;

import com.ydh.spring.springboot.service.CacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CacheSerciveImpl implements CacheService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CacheSerciveImpl.class);


    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    public String addCache(String key, String value) {

        LOGGER.info("接收到key：" + key + ", value：" + value);

        ZSetOperations<String, String> operations = redisTemplate.opsForZSet();
        operations.add(key, value, 100.0);
        return operations.count(key, 1.0, 200.0).toString();
    }
}
