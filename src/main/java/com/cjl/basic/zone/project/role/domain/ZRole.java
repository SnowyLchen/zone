package com.cjl.basic.zone.project.role.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * z_role
 */
@ApiModel(value="generate.ZRole")
public class ZRole implements Serializable {
    private Integer zRoleId;

    private String zRoleName;

    private String zRoleKey;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @ApiModelProperty(value="删除标志（0代表存在 2代表删除）")
    private String delFlag;

    /**
     * 创建者
     */
    @ApiModelProperty(value="创建者")
    private String createBy;

    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    private Date createTime;

    /**
     * 更新者
     */
    @ApiModelProperty(value="更新者")
    private String updateBy;

    /**
     * 更新时间
     */
    @ApiModelProperty(value="更新时间")
    private Date updateTime;

    /**
     * 角色状态（0正常 1停用）
     */
    @ApiModelProperty(value="角色状态（0正常 1停用）")
    private String status;

    private static final long serialVersionUID = 1L;

    public Integer getzRoleId() {
        return zRoleId;
    }

    public void setzRoleId(Integer zRoleId) {
        this.zRoleId = zRoleId;
    }

    public String getzRoleName() {
        return zRoleName;
    }

    public void setzRoleName(String zRoleName) {
        this.zRoleName = zRoleName;
    }

    public String getzRoleKey() {
        return zRoleKey;
    }

    public void setzRoleKey(String zRoleKey) {
        this.zRoleKey = zRoleKey;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}