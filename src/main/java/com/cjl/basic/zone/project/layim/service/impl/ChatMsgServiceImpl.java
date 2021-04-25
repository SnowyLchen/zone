package com.cjl.basic.zone.project.layim.service.impl;

import com.cjl.basic.zone.project.layim.mapper.ChatMsgMapper;
import com.cjl.basic.zone.project.layim.entity.ChatMsg;
import com.cjl.basic.zone.project.layim.entity.Mine;
import com.cjl.basic.zone.project.layim.service.ChatMsgService;
import com.cjl.basic.zone.utils.IdGenerat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author chen
 * @Date 2020/4/8 15:14
 * @Version 1.0
 */
@Service
public class ChatMsgServiceImpl implements ChatMsgService {
    @Autowired
    private ChatMsgMapper chatMsgMapper;

    @Override
    public boolean insertChatmsg(ChatMsg chatMsg) {
        chatMsg.setId(IdGenerat.getGeneratID());
//        chatMsg.setCreateTime(new Date());
        return chatMsgMapper.insertChatmsg(chatMsg);
    }

    @Override
    public List<Mine> getChatMsgLog(ChatMsg chatMsg) {
        return chatMsgMapper.getChatMsgLog(chatMsg);
    }
}
