package com.cjl.basic.zone.project.space.board.service;

import com.cjl.basic.zone.project.space.board.domain.ZMessageBoard;
import com.cjl.basic.zone.project.space.board.domain.ZReply;

import java.util.List;

/**
 * 留言业务层
 */
public interface IBoardService {
    /**
     * 删除留言
     *
     * @param mbId
     * @return
     */
    int deleteMessageById(Integer mbId);

    /**
     * 新增留言
     *
     * @param record
     * @return
     */
    ZMessageBoard insertMessage(ZMessageBoard record);

    /**
     * 新增主人寄语
     *
     * @param record
     * @return
     */
    int addOwner(ZMessageBoard record);


    /**
     * 新增回复
     *
     * @param record
     * @return
     */
    int insertReplyMessage(ZReply record);

    /**
     * 查询留言
     *
     * @param mbId
     * @return
     */
    ZMessageBoard selectMessageBoardById(Integer mbId);

    /**
     * 按用户查询全部留言
     *
     * @param accountId
     * @return
     */
    List<ZMessageBoard> selectMessageBoardList(Integer accountId);

    /**
     * 查询主人寄语
     *
     * @param accountId
     * @return
     */
    List<ZMessageBoard> selectOwnerMessageBoard(Integer accountId);

    /**
     * 删除留言
     *
     * @param id
     * @return
     */
    int removeMessage(Integer id);
}
