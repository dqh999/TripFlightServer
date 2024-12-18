package graph.railgo.infrastructure.cache.impl;

import graph.railgo.infrastructure.cache.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisCacheServiceImpl implements CacheService {
    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public RedisCacheServiceImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void put(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public void put(String key, Object value, long ttl) {
        redisTemplate.opsForValue().set(key, value, ttl, TimeUnit.SECONDS);
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
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public boolean exists(String key) {
        return redisTemplate.hasKey(key);
    }
}
