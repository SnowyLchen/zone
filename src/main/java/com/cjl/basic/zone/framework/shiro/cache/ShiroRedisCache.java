package com.cjl.basic.zone.framework.shiro.cache;

import com.cjl.basic.zone.framework.shiro.StatelessConstants;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author hd_zhu
 */
public class ShiroRedisCache implements Cache<String, Object> {

    private RedisTemplate<String, Object> soRedisTemplate;
    private String prefix;
    /**
     * 认证token7天过期
     */
    private Long expire = 7L;

    public ShiroRedisCache(RedisTemplate<String, Object> soRedisTemplate, String prefix) {
        this.prefix = prefix;
        this.soRedisTemplate = soRedisTemplate;
    }

    private String getShiroRedisKey(String k) {
        return this.prefix + StatelessConstants.SHIRO_PREFIX + k;
    }

    @Override
    public Object get(String s) throws CacheException {
        return soRedisTemplate.opsForValue().get(getShiroRedisKey(s));
    }

    @Override
    public Object put(String s, Object o) throws CacheException {
        soRedisTemplate.opsForValue().set(getShiroRedisKey(s), o, expire, TimeUnit.DAYS);
        return o;
    }

    @Override
    public Object remove(String s) throws CacheException {
        return soRedisTemplate.delete(soRedisTemplate.keys(getShiroRedisKey(s + "*")));
    }

    @Override
    public void clear() throws CacheException {
        soRedisTemplate.delete(keys());
    }

    @Override
    public int size() {
        return keys().size();
    }

    @Override
    public Set<String> keys() {
        return soRedisTemplate.keys(this.getShiroRedisKey("*"));
    }

    @Override
    public Collection<Object> values() {
        return soRedisTemplate.opsForValue().multiGet(keys());
    }
}
