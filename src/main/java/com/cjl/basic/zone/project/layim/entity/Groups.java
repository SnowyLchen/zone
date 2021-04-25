package com.cjl.basic.zone.project.layim.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @Author LiuZhao
 * @Date 2020/4/7 22:12
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Groups {
    private String id;//群组ID
    private String groupname;//群组名
    private String owner; //群主
    private String avatar;//群组头像
    private Date createTime;//加入时间
}
