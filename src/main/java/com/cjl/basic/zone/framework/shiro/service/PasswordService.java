package com.cjl.basic.zone.framework.shiro.service;

import com.cjl.basic.zone.common.constant.Constants;
import com.cjl.basic.zone.common.exception.user.UserPasswordNotMatchException;
import com.cjl.basic.zone.common.exception.user.UserPasswordRetryLimitExceedException;
import com.cjl.basic.zone.common.utils.MessageUtils;
import com.cjl.basic.zone.common.utils.SpringRedisUtil;
import com.cjl.basic.zone.framework.manager.AsyncManager;
import com.cjl.basic.zone.framework.manager.factory.AsyncFactory;
import com.cjl.basic.zone.project.user.domain.User;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 登录密码方法
 *
 * @author chen
 */
@Component
public class PasswordService {
    private static final String RECORD = "record";
    private String loginName = null;
    @Value(value = "${user.password.maxRetryCount}")
    private Integer maxRetryCount;
    @Value("${user.password.expire:10}")
    private Integer expire;
    @Value("${application.cache.prefix}")
    private String prefix;
    @Autowired
    SpringRedisUtil springRedisUtil;

    public void validate(User user, String password) {
        loginName = user.getLoginName();
        Integer initRetry = (Integer) springRedisUtil.get(getCacheKey(RECORD));
        AtomicInteger retryCount = new AtomicInteger(initRetry == null ? 0 : initRetry);

        if (maxRetryCount != 0 && retryCount.incrementAndGet() > maxRetryCount) {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(loginName, user.getAccountId(), Constants.LOGIN_FAIL, MessageUtils.message("user.password.retry.limit.exceed", maxRetryCount)));
            throw new UserPasswordRetryLimitExceedException(maxRetryCount);
        }

        if (!matches(user, password)) {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(loginName, user.getAccountId(), Constants.LOGIN_FAIL, MessageUtils.message("user.password.retry.limit.count", retryCount)));
            springRedisUtil.set(getCacheKey(RECORD), retryCount.intValue(), expire * 60);
            throw new UserPasswordNotMatchException();
        } else {
            springRedisUtil.delete(getCacheKey(RECORD));
        }
    }

    public boolean matches(User user, String newPassword) {
        return user.getPassword().equals(encryptPassword(user.getLoginName(), newPassword, user.getSalt()));
    }

    public String encryptPassword(String username, String password, String salt) {
        return new Md5Hash(username + password + salt).toHex();
    }

    private String getCacheKey(String k) {
        return prefix + k + ":" + loginName;
    }
}
