package com.cjl.basic.zone.project.space.board.domain;

import com.cjl.basic.zone.framework.web.domain.BaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * z_reply
 *
 * @author
 */
@Data
public class ZReply extends BaseEntity implements Serializable {
    private Integer rId;

    /**
     * 回复人id
     */
    private Integer accountId;
    private String userName;

    /**
     * 被回复人id
     */
    private Integer repliedAccountId;
    private String repliedUserName;

    /**
     * 回复的内容
     */
    private String message;

    /**
     * 回复时间
     */
    private Date createTime;

    /**
     * 是否删除
     */
    private String delFlag;

    private Integer mbId;

    private static final long serialVersionUID = 1L;
}