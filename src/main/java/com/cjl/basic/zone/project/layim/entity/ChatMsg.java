package com.cjl.basic.zone.project.layim.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @Author chen
 * @Date 2020/4/8 14:03
 * @Version 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
public class ChatMsg {
    private String id;
    private String sendUserId;
    private String reciveUserId;
    private String content;
    private Date createTime;
    private String msgType;

}
