package com.cjl.basic.zone.project.menu.domain;

import java.util.List;

/**
 * @author chen
 */
public class ZMenuTree {

    private Integer id;
    private String title;
    private Integer parentId;
    private List<ZMenuTree> children;


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
