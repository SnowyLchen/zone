package com.cjl.basic.zone.project.space.board.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * z_message_board
 *
 * @author
 */
@Data
public class ZMessageBoard implements Serializable {
    private Integer mbId;

    /**
     * 当前用户id
     */
    private Integer accountId;

    /**
     * 被留言人的id
     */
    private Integer comeAccountId;

    /**
     * 留言的内容
     */
    private String message;

    /**
     * 是否被删除
     */
    private String delFlag;

    /**
     * 留言时间
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;
}