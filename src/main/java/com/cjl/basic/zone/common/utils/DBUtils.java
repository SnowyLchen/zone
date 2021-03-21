package com.cjl.basic.zone.common.utils;

import com.cjl.basic.zone.framework.web.domain.BaseEntity;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class DBUtils<T> {

    public <T extends BaseEntity> void insert(T t) {
        Date date = new Date();
//        t.setUpdateBy(ShiroAuthenticateUtils.getLoginName());
        t.setUpdateTime(date);
//        t.setCreateBy(ShiroAuthenticateUtils.getLoginName());
        t.setCreateTime(date);
    }

    public <T extends BaseEntity> void update(T t) {
        Date date = new Date();
//        t.setUpdateBy(ShiroAuthenticateUtils.getLoginName());
        t.setUpdateTime(date);
    }
}
