package com.cjl.basic.zone.project.role.domain;

import com.cjl.basic.zone.framework.web.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * z_role
 */
@ApiModel(value="generate.ZRole")
public class ZRole extends BaseEntity implements Serializable {
    private Integer roleId;

    private String roleName;

    private String roleKey;
    /**
     * 角色状态（0正常 1停用）
     */
    @ApiModelProperty(value="角色状态（0正常 1停用）")
    private String status;

    private static final long serialVersionUID = 1L;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleKey() {
        return roleKey;
    }

    public void setRoleKey(String roleKey) {
        this.roleKey = roleKey;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}