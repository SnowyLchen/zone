package com.cjl.basic.zone.project.layim.service;

import com.cjl.basic.zone.project.layim.entity.Mine;

import java.util.List;

/**
 * @Author LiuZhao
 * @Date 2020/4/7 21:26
 * @Version 1.0
 */
public interface MineService {

    /**
     * 更新用户信息
     *
     * @param mine
     */
    boolean upUserMine(Mine mine);


    /**
     * 查询用户的信息
     *
     * @param userId
     * @return
     */
    Mine getUserInfo(String userId);

    /**
     * 获取全部用户
     *
     * @return
     */
    List<Mine> getMineList();
}
