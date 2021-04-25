package com.cjl.basic.zone.project.layim.service;

import com.cjl.basic.zone.project.layim.entity.Friend;
import com.cjl.basic.zone.project.layim.entity.Mine;

import java.util.List;

/**
 * @Author LiuZhao
 * @Date 2020/4/8 9:37
 * @Version 1.0
 */
public interface FriendsService {


    /**
     * 添加好友
     *
     * @param friend
     * @return
     */
    boolean addFriend(Friend friend);

    /**
     * 查询用户的好友列表
     *
     * @param userId
     * @return
     */
    List<Mine> getUserFriend(String userId);
}
