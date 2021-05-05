package com.cjl.basic.zone.common.controller;

import com.cjl.basic.zone.framework.config.ZoneConfig;

import java.io.Serializable;

public class ZImage implements Serializable {
    private String id;
    private String src;
    private String title;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = ZoneConfig.getReloadImgUrl() + src;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
