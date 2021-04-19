package com.cjl.basic.zone.project.menu.domain;

import java.io.Serializable;

/**
 * z_menu
 *
 * @author
 */
public class ZMenu implements Serializable {
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

    /**
     * 请求地址
     */
    private String href;

    /**
     * 菜单图标
     */
    private String icon;

    private Integer type;

    /**
     * 类型
     */
    private String openType;

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

    public String getOpenType() {
        return openType;
    }

    public void setOpenType(String openType) {
        this.openType = openType;
    }
}