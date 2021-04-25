package com.cjl.basic.zone.project.layim.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @Author LiuZhao
 * @Date 2020/4/13 10:13
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class SysMsg {
    private String id;
    private String uid;
    private String content;
    private String time;
    private Date createTime;
    private String status;
}
