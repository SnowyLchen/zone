package com.cjl.basic.zone.common.controller;

import com.cjl.basic.zone.framework.config.ZoneConfig;

import java.io.Serializable;

public class ZImage implements Serializable {
    private String id;
    private String src;
    private String title;
    private String originalFilename;
    private String suffix;
    private String size;

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getOriginalFilename() {
        return originalFilename;
    }

    public void setOriginalFilename(String originalFilename) {
        this.originalFilename = originalFilename;
    }

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
