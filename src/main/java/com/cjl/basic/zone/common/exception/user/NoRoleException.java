package com.cjl.basic.zone.common.exception.user;

import org.apache.shiro.authc.AuthenticationException;

/**
 * 无角色异常
 *
 * @author hd_zhu
 */
public class NoRoleException extends AuthenticationException {

    private static final long serialVersionUID = 1L;

    public NoRoleException(String msg) {
        super(msg);
    }
}
