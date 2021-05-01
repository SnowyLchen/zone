package com.cjl.basic.zone.project.manage.layim.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @Author chen
 * @Date 2020/4/13 15:08
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Group {
    private String id;
    private String gid;
    private String uid;
    private Date createTime;
    private String status;
}
