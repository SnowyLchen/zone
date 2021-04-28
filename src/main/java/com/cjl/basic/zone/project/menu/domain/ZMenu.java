package com.cjl.basic.zone.project.menu.domain;

import com.cjl.basic.zone.framework.web.domain.BaseEntity;

import java.io.Serializable;

/**
 * z_menu
 *
 * @author chen
 */
public class ZMenu extends BaseEntity implements Serializable {
    /**
     * 菜单ID
     */
    private Integer menuId;

    /**
     * 菜单名称
     */
    private String title;

    /**
     * 父菜单ID
     */
    private Integer parentId;
    private String parentName;

    /**
     * 请求地址
     */
    private String href;

    /**
     * 菜单图标
     */
    private String icon;
    /**
     * 类型
     */
    private Integer type;
    /**
     * 打开方式
     */
    private String openType;
    /**
     * 是否隐藏
     */
    private Integer visible;
    /**
     * 排序
     */
    private String orderNum;
    /**
     * 是否被选中
     */
    private String checkArr;
    /**
     * 角色id
     */
    private Integer roleId;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getCheckArr() {
        return checkArr;
    }

    public void setCheckArr(String checkArr) {
        this.checkArr = checkArr;
    }

    public String getOpenType() {
        return openType;
    }

    public void setOpenType(String openType) {
        this.openType = openType;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public Integer getVisible() {
        return visible;
    }

    public void setVisible(Integer visible) {
        this.visible = visible;
    }

    private static final long serialVersionUID = 1L;

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }
}