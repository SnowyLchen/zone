package com.cjl.basic.zone.project.manage.layim.service.impl;

import com.cjl.basic.zone.project.manage.layim.entity.ChatMsg;
import com.cjl.basic.zone.project.manage.layim.mapper.ChatMsgMapper;
import com.cjl.basic.zone.project.manage.layim.service.ChatMsgService;
import com.cjl.basic.zone.project.manage.user.domain.User;
import com.cjl.basic.zone.utils.IdGenerat;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author chen
 * @Date 2021/4/8 15:14
 * @Version 1.0
 */
@Service
public class ChatMsgServiceImpl implements ChatMsgService {
    @Resource
    private ChatMsgMapper chatMsgMapper;

    @Override
    public boolean insertChatmsg(ChatMsg chatMsg) {
        chatMsg.setId(IdGenerat.getGeneratID());
//        chatMsg.setCreateTime(new Date());
        return chatMsgMapper.insertChatmsg(chatMsg);
    }

    @Override
    public List<User> getChatMsgLog(ChatMsg chatMsg) {
        return chatMsgMapper.getChatMsgLog(chatMsg);
    }
}
