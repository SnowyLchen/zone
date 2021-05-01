package com.cjl.basic.zone.project.manage.layim.mapper;

import com.cjl.basic.zone.project.manage.layim.entity.ChatMsg;
import com.cjl.basic.zone.project.manage.user.domain.User;

import java.util.List;

/**
 * @Author chen
 * @Date 2021/4/8 14:18
 * @Version 1.0
 */
public interface ChatMsgMapper {
    /**
     * 插入发送的消息记录
     *
     * @param chatMsg
     * @return
     */
    boolean insertChatmsg(ChatMsg chatMsg);

    /**
     * .
     * 查看与好友的聊天记录
     *
     * @param chatMsg
     * @return
     */
    List<User> getChatMsgLog(ChatMsg chatMsg);
}
