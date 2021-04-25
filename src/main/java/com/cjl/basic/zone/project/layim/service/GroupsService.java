package com.cjl.basic.zone.project.layim.service;

import com.cjl.basic.zone.project.layim.entity.Group;
import com.cjl.basic.zone.project.layim.entity.GroupMsg;
import com.cjl.basic.zone.project.layim.entity.Groups;
import com.cjl.basic.zone.project.layim.entity.Mine;

import java.util.List;

/**
 * @Author LiuZhao
 * @Date 2020/4/8 9:37
 * @Version 1.0
 */
public interface GroupsService {

    /**
     * 群里添加人
     *
     * @return
     */
    boolean addGroupUser(Group group);

    /**
     * 查询用户的群
     *
     * @param userId
     * @return
     */
    List<Groups> getUserGroups(String userId);

    /**
     * 根据群id获取群成员
     *
     * @param id
     * @return
     */
    List<Mine> getGroupUsre(String id);

    /**
     * 插入群聊消息
     *
     * @param groupMsg
     * @return
     */
    boolean InsertGroupMsg(GroupMsg groupMsg);

    /**
     * 查群聊记录
     *
     * @param groupMsg
     * @return
     */
    List<Mine> getGroupChatLogMsg(GroupMsg groupMsg);

    /**
     * 通过id获取群组信息
     *
     * @return
     */
    Groups getById(String id);
}
