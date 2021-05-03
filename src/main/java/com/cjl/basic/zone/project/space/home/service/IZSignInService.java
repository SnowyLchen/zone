package com.cjl.basic.zone.project.space.home.service;

import com.cjl.basic.zone.project.space.home.domain.ZDynamic;
import com.cjl.basic.zone.project.space.home.domain.ZSignIn;

import java.util.List;
import java.util.Map;

public interface IZSignInService {
    /**
     * 签到
     *
     * @param zDynamic
     * @return
     */
    int insertSignInfo(ZDynamic zDynamic);

    /**
     * 更新签到信息
     *
     * @param record
     * @return
     */
    int updateSignInfo(ZSignIn record);

    /**
     * 查询单条签到信息
     *
     * @param accountId
     * @return
     */
    ZSignIn selectSignInfoByAccountId(Integer accountId);

    /**
     * 查询签到信息
     *
     * @param accountId
     * @return
     */
    List<ZSignIn> selectSignInfoList(Integer accountId);

    /**
     * 查询签到信息
     *
     * @param accountId
     * @return
     */
    List<Map<String,String>> selectSignInfoMap(Integer accountId);

    /**
     * 查询动态
     *
     * @param accountId
     * @return
     */
    List<ZDynamic> getDynamic(Integer accountId);

    /**
     * 查询今天是否签到
     *
     * @param accountId
     * @return
     */
    int checkSignIn(Integer accountId);
}
