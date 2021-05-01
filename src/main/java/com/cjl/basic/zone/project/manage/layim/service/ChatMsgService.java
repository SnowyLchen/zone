package com.cjl.basic.zone.project.manage.layim.service;

import com.cjl.basic.zone.project.manage.layim.entity.ChatMsg;
import com.cjl.basic.zone.project.manage.user.domain.User;

import java.util.List;

/**
 * @Author chen
 * @Date 2021/4/8 15:13
 * @Version 1.0
 */
public interface ChatMsgService {
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
