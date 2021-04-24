package com.cjl.basic.zone.project.menu.domain;

import java.util.List;

/**
 * @author chen
 */
public class ZMenuTree {

    private Integer id;
    private String title;
    private Integer type;
    private String openType;
    private String href;
    private Integer parentId;
    private String checkArr = "0";
    private List<ZMenuTree> children;

    public String getCheckArr() {
        return checkArr;
    }

    public void setCheckArr(String checkArr) {
        this.checkArr = checkArr;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getOpenType() {
        return openType;
    }

    public void setOpenType(String openType) {
        this.openType = openType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public List<ZMenuTree> getChildren() {
        return children;
    }

    public void setChildren(List<ZMenuTree> children) {
        this.children = children;
    }
}
