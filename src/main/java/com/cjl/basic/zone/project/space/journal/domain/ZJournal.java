package com.cjl.basic.zone.project.space.journal.domain;

import com.cjl.basic.zone.framework.web.domain.BaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 日志表
 *
 * @author chen
 */
@Data
public class ZJournal extends BaseEntity implements Serializable {
    /**
     * 主键
     */
    private Integer jId;

    /**
     * 日志标题
     */
    private String title;

    /**
     * 发表时间
     */
    private String time;

    /**
     * 日志内容
     */
    private String content;

    /**
     * 普通日志还是私密日志（1私密0公开）
     */
    private String secret;

    /**
     * 日志分类
     */
    private Integer cateId;

    /**
     * 用户id
     */
    private Integer accountId;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新者
     */
    private String updateBy;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 删除标记
     */
    private String delFlag;

    private static final long serialVersionUID = 1L;
}