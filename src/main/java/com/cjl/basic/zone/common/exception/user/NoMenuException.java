package com.cjl.basic.zone.common.exception.user;

import org.apache.shiro.authc.AuthenticationException;

/**
 * 无菜单异常
 * @author hd_zhu
 */
public class NoMenuException extends AuthenticationException {

    private static final long serialVersionUID = 1L;

    public NoMenuException(String msg) {
        super(msg);
    }
}
