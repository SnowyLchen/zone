package com.cjl.basic.zone.project.layim.mapper;

import com.cjl.basic.zone.project.layim.entity.Group;
import com.cjl.basic.zone.project.layim.entity.GroupMsg;
import com.cjl.basic.zone.project.layim.entity.Groups;
import com.cjl.basic.zone.project.layim.entity.Mine;

import java.util.List;

/**
 * @Author chen
 * @Date 2020/4/8 8:53
 * @Version 1.0
 */
public interface GroupsMapper {

    /**
     * 查询用户的群
     *
     * @param userId
     * @return
     */
    List<Groups> getUserGroups(String userId);

    /**
     * 群里添加人
     *
     * @return
     */
    boolean addGroupUser(Group group);

    /**
     * 查询群组的成员
     *
     * @param id
     * @return
     */
    List<Mine> getGroupUserById(String id);

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
