package com.cjl.basci.zone.common.utils;

import com.cjl.basci.zone.common.utils.security.ShiroAuthenticateUtils;
import com.cjl.basci.zone.framework.web.domain.BaseEntity;

import java.util.Date;

/**
 * 新增或修改数据的工具类
 *
 * @author hd_zhu
 */
public class InsertOrUpdateUtils {

    /**
     * 添加新增表时所需要的公共属性
     *
     * @param t   目标对象
     * @param <T> {@link T<T extends BaseEntity>}
     */
    public static <T extends BaseEntity> void addInsertAttr(final T t) {
//        final String loginName = ShiroAuthenticateUtils.getLoginName();
        final Date now = DateUtils.getNowDate();
//        t.setUpdateBy(loginName);
        t.setUpdateTime(now);
//        t.setCreateBy(loginName);
        t.setCreateTime(now);
    }

    /**
     * 添加更新表时所需要的公共属性
     *
     * @param t   目标对象
     * @param <T> {@link T<T extends BaseEntity>}
     */
    public static <T extends BaseEntity> void addUpdateAttr(final T t) {
//        final String loginName = ShiroAuthenticateUtils.getLoginName();
        final Date now = DateUtils.getNowDate();
//        t.setUpdateBy(loginName);
        t.setUpdateTime(now);
    }

    /**
     * 检测sql执行结果
     *
     * @param i sql执行结果
     */
    public static Integer requireGreaterThanI(Integer i) {
        return requireGreaterThanI(i, null);
    }

    /**
     * 检测sql执行结果
     *
     * @param i   sql执行结果
     * @param msg 错误消息
     */
    public static Integer requireGreaterThanI(Integer i, String msg) {
        if (i <= 0) {
            if (msg == null) {
                throw new RuntimeException();
            }
            throw new RuntimeException(msg);
        }
        return i;
    }
}
