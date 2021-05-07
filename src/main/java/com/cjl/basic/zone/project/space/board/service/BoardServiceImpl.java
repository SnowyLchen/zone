package com.cjl.basic.zone.project.space.board.service;

import com.cjl.basic.zone.project.space.board.domain.ZMessageBoard;
import com.cjl.basic.zone.project.space.board.mapper.ZMessageBoardMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BoardServiceImpl implements IBoardService {
    @Resource
    private ZMessageBoardMapper messageBoardMapper;

    @Override
    public int deleteMessageById(Integer mbId) {
        return messageBoardMapper.deleteMessageById(mbId);
    }

    @Override
    public int insertMessage(ZMessageBoard messageBoard) {
        return messageBoardMapper.insertMessage(messageBoard);
    }

    @Override
    public ZMessageBoard selectMessageBoardById(Integer mbId) {
        return messageBoardMapper.selectMessageBoardById(mbId);
    }

    @Override
    public List<ZMessageBoard> selectMessageBoardList(Integer accountId) {
        return messageBoardMapper.selectMessageBoardList(accountId);
    }
}
