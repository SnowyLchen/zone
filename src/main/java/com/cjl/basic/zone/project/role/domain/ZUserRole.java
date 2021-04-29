package com.cjl.basic.zone.project.role.domain;

import java.io.Serializable;

/**
 * z_user_role
 * @author 
 */
public class ZUserRole implements Serializable {
    private Integer accountId;

    private Integer roleId;

    private static final long serialVersionUID = 1L;

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}