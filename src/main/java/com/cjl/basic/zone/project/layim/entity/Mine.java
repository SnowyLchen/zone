package com.cjl.basic.zone.project.layim.entity;

import com.cjl.basic.zone.project.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Mine extends User implements Serializable {
    private static final long serialVersionUID = -5119611695181898456L;
    private Integer id;
    private String username;//我的昵称
    private Integer groupId;
    private String gName;
}
