package com.cjl.basic.zone.project.layim.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @Author chen
 * @Date 2020/4/8 14:49
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class GroupMsg {
    private String id;
    private String groupId;
    private String sendUserId;
    private String content;
    private Date createTime;
    private String status;
}
