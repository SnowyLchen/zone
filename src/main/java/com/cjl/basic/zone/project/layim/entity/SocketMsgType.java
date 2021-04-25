package com.cjl.basic.zone.project.layim.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Author chen
 * @Date 2020/4/10 14:05
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class SocketMsgType {
    private Integer code;
    private String msg;
    private String msgType;
    private Object data;
}
