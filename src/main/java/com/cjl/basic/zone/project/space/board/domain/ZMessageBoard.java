package com.cjl.basic.zone.project.space.board.domain;

import com.cjl.basic.zone.framework.web.domain.BaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * z_message_board
 *
 * @author
 */
@Data
public class ZMessageBoard extends BaseEntity implements Serializable {
    private Integer mbId;

    /**
     * 当前用户id
     */
    private Integer accountId;
    private String userName;
    private String avatar;

    /**
     * 留言人的id
     */
    private Integer comeAccountId;
    private String comeUserName;
    private String comeAvatar;
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
    /**
     * 留言回复
     */
    private List<ZReply> replies;

    private static final long serialVersionUID = 1L;
}