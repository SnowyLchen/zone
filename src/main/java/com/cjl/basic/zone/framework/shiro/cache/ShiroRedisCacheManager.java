package com.cjl.basic.zone.framework.shiro.cache;

import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author chen
 */
public class ShiroRedisCacheManager implements CacheManager {

    private final RedisTemplate kvRedisTemplate;
    private final String prefix;

    public ShiroRedisCacheManager(RedisTemplate kvRedisTemplate, String prefix) {
        this.kvRedisTemplate = kvRedisTemplate;
        this.prefix = prefix;
    }

    @Override
    public ShiroRedisCache getCache(String s) throws CacheException {
        return new ShiroRedisCache(kvRedisTemplate, prefix);
    }
}
