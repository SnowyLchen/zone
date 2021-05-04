package com.cjl.basic.zone.project.space.categories.domain;

import java.io.Serializable;

/**
 * 日志分类表(ZCategories)实体类
 *
 * @author makejava
 * @since 2021-05-04 19:46:42
 */
public class ZCategories implements Serializable {
    private static final long serialVersionUID = 380237134653872601L;
    /**
     * 主键
     */
    private Integer cId;
    /**
     * 分类名称
     */
    private String cateName;
    /**
     * 用户id
     */
    private Integer accountId;


    public Integer getCId() {
        return cId;
    }

    public void setCId(Integer cId) {
        this.cId = cId;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

}
