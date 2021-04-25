package com.cjl.basic.zone.project.layim.service.impl;

import com.cjl.basic.zone.project.layim.mapper.FriendsMapper;
import com.cjl.basic.zone.project.layim.mapper.MineMapper;
import com.cjl.basic.zone.project.layim.mapper.SysMsgMapper;
import com.cjl.basic.zone.project.layim.entity.Friend;
import com.cjl.basic.zone.project.layim.entity.Mine;
import com.cjl.basic.zone.project.layim.entity.SysMsg;
import com.cjl.basic.zone.project.layim.service.FriendsService;
import com.cjl.basic.zone.utils.IdGenerat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @Author LiuZhao
 * @Date 2020/4/8 9:41
 * @Version 1.0
 */
@Service
public class FriendsServiceImpl implements FriendsService {

    @Autowired
    private FriendsMapper friendsMapper;
    @Autowired
    private SysMsgMapper sysMsgMapper;
    @Autowired
    private MineMapper mineMapper;

    @Override
    @Transactional
    public boolean addFriend(Friend friend) {
        friend.setId(IdGenerat.getGeneratID());
        friend.setCreateTime(new Date());
        String uid = friend.getUid();
        String fid = friend.getFid();
        boolean addFriend = friendsMapper.addFriend(friend);
        if (addFriend) {
            //添加两天好友关系
            boolean addFriend1 = friendsMapper.addFriend(new Friend().setId(IdGenerat.getGeneratID()).setUid(fid).setFid(uid).setCreateTime(friend.getCreateTime()));
            if (addFriend1) {
                //添加系统消息
                String content = mineMapper.getUserInfo(uid).getUsername();
                SysMsg sysMsg = new SysMsg().setId(IdGenerat.getGeneratID()).setContent(content + "  已经同意你的好友申请")
                        .setUid(fid).setCreateTime(new Date()).setStatus("0");
                sysMsgMapper.addSysMsg(sysMsg);
                String userName = mineMapper.getUserInfo(fid).getUsername();
                sysMsg.setId(IdGenerat.getGeneratID()).setContent("你同意了" + userName + "的好友申请").setUid(uid);
                sysMsgMapper.addSysMsg(sysMsg);
                return true;
            }


        }
        return false;
    }

    @Override
    public List<Mine> getUserFriend(String userId) {
        return friendsMapper.getUserFriend(userId);
    }
}
