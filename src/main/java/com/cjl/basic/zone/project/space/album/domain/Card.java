package com.cjl.basic.zone.project.space.album.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * 相册卡片封装类
 */
@Data
public class Card implements Serializable {
    private int id;
    private String image;
    private String title;
    private String remark;
    private String time;
}
