package com.cjl.basic.zone.project.space.board.service;

import com.cjl.basic.zone.common.utils.InsertOrUpdateUtils;
import com.cjl.basic.zone.project.space.board.domain.ZMessageBoard;
import com.cjl.basic.zone.project.space.board.domain.ZReply;
import com.cjl.basic.zone.project.space.board.mapper.ZMessageBoardMapper;
import com.cjl.basic.zone.project.space.board.mapper.ZReplyMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BoardServiceImpl implements IBoardService {
    @Resource
    private ZMessageBoardMapper messageBoardMapper;
    @Resource
    private ZReplyMapper replyMapper;

    @Override
    public int deleteMessageById(Integer mbId) {
        return messageBoardMapper.deleteMessageById(mbId);
    }

    @Override
    public int insertMessage(ZMessageBoard messageBoard) {
        InsertOrUpdateUtils.addInsertAttr(messageBoard);
        return messageBoardMapper.insertMessage(messageBoard);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addOwner(ZMessageBoard messageBoard) {
        InsertOrUpdateUtils.addInsertAttr(messageBoard);
        List<ZMessageBoard> owner = selectOwnerMessageBoard(messageBoard.getAccountId());
        if (owner.size() > 0) {
            deleteMessageById(owner.get(0).getMbId());
        }
        messageBoardMapper.insertMessage(messageBoard);
        return 1;
    }

    @Override
    public int insertReplyMessage(ZReply record) {
        InsertOrUpdateUtils.addInsertAttr(record);
        return replyMapper.insertSelective(record);
    }

    @Override
    public ZMessageBoard selectMessageBoardById(Integer mbId) {
        return messageBoardMapper.selectMessageBoardById(mbId);
    }

    @Override
    public List<ZMessageBoard> selectMessageBoardList(Integer accountId) {
        List<ZMessageBoard> zMessageBoards = messageBoardMapper.selectMessageBoardList(accountId);
        for (ZMessageBoard zMessageBoard : zMessageBoards) {
            Integer mbId = zMessageBoard.getMbId();
            zMessageBoard.setReplies(replyMapper.selectReplyList(mbId));
        }
        return zMessageBoards;
    }

    @Override
    public List<ZMessageBoard> selectOwnerMessageBoard(Integer accountId) {
        return messageBoardMapper.selectOwnerMessageBoard(accountId);
    }

    @Override
    public int removeMessage(Integer id) {
        return messageBoardMapper.removeMessage(id);
    }
}
