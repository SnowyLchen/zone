package com.cjl.basic.zone.project.user.domain;

import com.cjl.basic.zone.framework.web.domain.BaseEntity;

/**
 * login_info
 *
 * @author
 */
public class LoginInfo extends BaseEntity {
    /**
     * 个人账号关联表主键
     */
    private Integer loginInfoId;

    /**
     * 登录名
     */
    private String loginName;

    /**
     * 账号状态
     */
    private String status;


    private static final long serialVersionUID = 1L;

    public Integer getLoginInfoId() {
        return loginInfoId;
    }

    public void setLoginInfoId(Integer loginInfoId) {
        this.loginInfoId = loginInfoId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
