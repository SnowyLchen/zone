package com.cjl.basic.zone.project.space.home.domain;

import com.cjl.basic.zone.framework.web.domain.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户签到实体类
 *
 * @author chen
 */
@Data
public class ZSignIn extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 主键id
     */
    private Integer siId;

    /**
     * 签到类型（如：写日志，传照片）
     */
    private String type;

    /**
     * 签到时间
     */
    private String date;

    /**
     * 签到人员
     */
    private Integer accountId;

}