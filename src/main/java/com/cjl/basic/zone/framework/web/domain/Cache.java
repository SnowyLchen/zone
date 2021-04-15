package com.cjl.basic.zone.framework.web.domain;

import com.cjl.basic.zone.common.exception.base.CacheSyncException;

import java.util.ArrayList;
import java.util.List;

/**
 * 应用数据缓存
 *
 * @author chen
 */
public abstract class Cache<T> {
    /**
     * 初始化同步到Redis
     *
     * @throws CacheSyncException 操作缓存时出现异常
     */
    protected abstract void initSynchronizeDataToRedis() throws CacheSyncException;

    /**
     * 更新数据
     *
     * @param id 键
     * @throws CacheSyncException 操作缓存时出现异常
     */
    public abstract void update(Integer id) throws CacheSyncException;

    /**
     * 删除数据
     *
     * @param id 键
     * @throws CacheSyncException 操作缓存时出现异常
     */
    public abstract void delete(Integer id) throws CacheSyncException;

    /**
     * 根据key读取数据
     *
     * @param id 键
     * @return {@link T}
     */
    public abstract T get(Integer id);

    /**
     * 更具Keys读取数据
     *
     * @param ids 键数组
     * @return {@link List <T>}
     */
    public List<T> list(Integer... ids) {
        List<T> t = new ArrayList<>();
        for (Integer id : ids) {
            t.add(get(id));
        }
        return t;
    }

    ;
}
