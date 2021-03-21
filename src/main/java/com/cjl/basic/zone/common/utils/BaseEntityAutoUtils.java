package com.cjl.basic.zone.common.utils;

import com.cjl.basic.zone.framework.web.domain.BaseEntity;

import java.util.Date;

/**
 * 基类赋值工具类
 */
public class BaseEntityAutoUtils {

    public static Object autoBaseEntity(BaseEntity baseEntity) {
        Date date = new Date();
        baseEntity.setCreateTime(date);
        baseEntity.setUpdateTime(date);
//        baseEntity.setCreateBy(ShiroAuthenticateUtils.getUserByToken().getLoginName());
//        baseEntity.setUpdateBy(ShiroAuthenticateUtils.getUserByToken().getLoginName());
        return baseEntity;
    }

    public static Object autoUpdateBaseEntity(BaseEntity baseEntity) {
        Date date = new Date();
        baseEntity.setUpdateTime(date);
//        baseEntity.setUpdateBy(ShiroAuthenticateUtils.getUserByToken().getLoginName());
        return baseEntity;
    }
}
