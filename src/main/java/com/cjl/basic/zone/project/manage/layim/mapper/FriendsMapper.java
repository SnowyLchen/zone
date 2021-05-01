package com.cjl.basic.zone.project.manage.layim.mapper;

import com.cjl.basic.zone.project.manage.layim.entity.Friend;
import com.cjl.basic.zone.project.manage.layim.entity.Friends;
import com.cjl.basic.zone.project.manage.layim.entity.Mine;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author chen
 * @Date 2021/4/8 8:39
 * @Version 1.0
 */
public interface FriendsMapper {

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
    List<Mine> getUserFriend(@Param("userId") String userId);

    /**
     * 创建分组
     *
     * @param friends
     * @return
     */
    int createGroup(Friends friends);

    /**
     * 查询用户分组列表
     *
     * @param accountId
     * @return
     */
    List<Friends> getFriendGroupList(Integer accountId);
}
