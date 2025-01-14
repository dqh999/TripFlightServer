package com.airline.booking.infrastructure.service.cache.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.airline.booking.infrastructure.service.cache.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class RedisCacheServiceImpl implements CacheService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;


    @Autowired
    public RedisCacheServiceImpl(RedisTemplate<String, Object> redisTemplate,ObjectMapper objectMapper) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public void put(String key, Object value) {
        try {
            String jsonValue = objectMapper.writeValueAsString(value);
            redisTemplate.opsForValue().set(key, jsonValue);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize object for Redis", e);
        }
    }

    @Override
    public void put(String key, Object value, long ttl) {
        try {
            String jsonValue = objectMapper.writeValueAsString(value);
            redisTemplate.opsForValue().set(key, jsonValue, ttl, TimeUnit.SECONDS);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize object for Redis", e);
        }
    }

    @Override
    public boolean acquireLock(String key, long ttl) {
        Boolean isLocked = redisTemplate.opsForValue().setIfAbsent(key, "LOCKED", ttl, TimeUnit.MILLISECONDS);
        return isLocked != null && isLocked;
    }

    @Override
    public void releaseLock(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public <T> T get(String key,Class<T> clazz) {
        String jsonValue = (String) redisTemplate.opsForValue().get(key);
        if (jsonValue == null) {
            return null;
        }
        try {
            return objectMapper.readValue(jsonValue, clazz);
        } catch (IOException e) {
            throw new RuntimeException("Failed to deserialize object from Redis", e);
        }
    }

    @Override
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public boolean exists(String key) {
        return redisTemplate.hasKey(key);
    }

    @Override
    public Set<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }
}
