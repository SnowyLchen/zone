package com.cjl.basic.zone.project.layim.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @Author chen
 * @Date 2020/4/13 8:46
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Friend {
    private String id;
    private String uid;
    private String fid;
    private Date createTime;
    private String gId;
}
