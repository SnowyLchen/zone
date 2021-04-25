package com.cjl.basic.zone.project.layim.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Author LiuZhao
 * @Date 2020/4/7 21:56
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Friends {
    private String groupname;//好友分组名
    private String id;//分组ID
    private List<Mine> list;//分组下的好友列表
}
