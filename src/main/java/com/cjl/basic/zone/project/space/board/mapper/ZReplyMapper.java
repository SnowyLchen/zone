package com.cjl.basic.zone.project.space.board.mapper;

import com.cjl.basic.zone.project.space.board.domain.ZReply;

import java.util.List;

public interface ZReplyMapper {
    /**
     * 删除回复
     *
     * @param rId
     * @return
     */
    int deleteByPrimaryKey(Integer rId);

    /**
     * 新增回复
     *
     * @param record
     * @return
     */
    int insertSelective(ZReply record);

    /**
     * 所有回复
     * @param mbId
     * @return
     */
    List<ZReply> selectReplyList(Integer mbId);

}