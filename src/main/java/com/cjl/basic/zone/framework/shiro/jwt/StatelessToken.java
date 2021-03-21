package com.cjl.basic.zone.framework.shiro.jwt;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * 无状态token
 *
 * @author hd_zhu
 */
public class StatelessToken implements AuthenticationToken {

    /**
     * jwtToken访问
     */
    private String accessToken;

    /**
     * 是否是登陆操作
     */
    private boolean isLoginHandle;

    public StatelessToken(String accessToken) {
        this(accessToken, false);
    }

    public StatelessToken(String accessToken, boolean isLoginHandle) {
        this.accessToken = accessToken;
        this.isLoginHandle = isLoginHandle;
    }

    public boolean isLoginHandle() {
        return isLoginHandle;
    }

    /**
     * jwt续签
     *
     * @return
     */
    @Override
    public Object getPrincipal() {
        return accessToken;
    }

    @Override
    public Object getCredentials() {
        return accessToken;
    }
}
