package com.cjl.basic.zone.project.layim.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Mine {
    private String id; //我的ID
    private String username;//我的昵称
    private String sign; //我的签名
    private String avatar;//我的头像
    private String status;//在线状态 online：在线、hide：隐身

    //补充的属性
    private String content;   //聊天内容
    private String type; //消息类型
    private String toid; //聊天窗口的选中的用户或者群组的id
    private Date sendtime;  //消息发送时间

    private Long timeStamp;
}
