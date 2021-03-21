package com.cjl.basci.zone.common.utils;

import com.cjl.basci.zone.common.utils.security.ShiroAuthenticateUtils;
import com.cjl.basci.zone.framework.web.domain.BaseEntity;
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
