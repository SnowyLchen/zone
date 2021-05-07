package com.cjl.basic.zone.project.space.board.mapper;

import com.cjl.basic.zone.project.space.board.domain.ZMessageBoard;

import java.util.List;

public interface ZMessageBoardMapper {

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
    int insertMessage(ZMessageBoard record);

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
}