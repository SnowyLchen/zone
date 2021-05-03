package com.cjl.basic.zone.project.space.home.domain;

import lombok.Data;

@Data
public class ZDynamic extends ZSignIn {

    private Integer id;
    /**
     * 动态主题（日志主题）
     */
    private String title;
    /**
     * 动态内容
     */
    private String content;
    /**
     * 用户名
     */
    private String userName;
}
