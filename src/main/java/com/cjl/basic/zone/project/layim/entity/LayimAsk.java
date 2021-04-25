package com.cjl.basic.zone.project.layim.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @Author LiuZhao
 * @Date 2020/4/10 9:10
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class LayimAsk {

    private String id;
    private String uid;
    private String from;
    private String fromGroup;
    private String content;
    private String remark;
    private Date createTime;
    private String time;
    private String href;
    private String read;
    private String type;
    private Mine user;
}
