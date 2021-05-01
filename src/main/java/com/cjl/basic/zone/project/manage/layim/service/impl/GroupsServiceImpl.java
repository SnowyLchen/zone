package com.cjl.basic.zone.project.manage.layim.service.impl;

import com.cjl.basic.zone.project.manage.layim.entity.Group;
import com.cjl.basic.zone.project.manage.layim.entity.GroupMsg;
import com.cjl.basic.zone.project.manage.layim.entity.Groups;
import com.cjl.basic.zone.project.manage.layim.entity.Mine;
import com.cjl.basic.zone.project.manage.layim.mapper.GroupsMapper;
import com.cjl.basic.zone.project.manage.layim.service.GroupsService;
import com.cjl.basic.zone.project.manage.user.domain.User;
import com.cjl.basic.zone.utils.IdGenerat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Author chen
 * @Date 2021/4/8 9:43
 * @Version 1.0
 */
@Service
public class GroupsServiceImpl implements GroupsService {

    @Autowired
    private GroupsMapper groupsMapper;

    @Override
    public boolean addGroupUser(Group group) {
        group.setId(IdGenerat.getGeneratID());
        group.setCreateTime(new Date());
        group.setStatus("0");
        return groupsMapper.addGroupUser(group);
    }

    @Override
    public List<Groups> getUserGroups(String userId) {
        return groupsMapper.getUserGroups(userId);
    }

    @Override
    public List<Mine> getGroupUsre(String id) {
        return groupsMapper.getGroupUserById(id);
    }

    @Override
    public boolean InsertGroupMsg(GroupMsg groupMsg) {
        groupMsg.setId(IdGenerat.getGeneratID());
        groupMsg.setCreateTime(new Date());
        return groupsMapper.InsertGroupMsg(groupMsg);
    }

    @Override
    public List<User> getGroupChatLogMsg(GroupMsg groupMsg) {
        return groupsMapper.getGroupChatLogMsg(groupMsg);
    }

    @Override
    public Groups getById(String id) {
        return groupsMapper.getById(id);
    }
}
