package com.cjl.basic.zone.project.space.home.mapper;

import com.cjl.basic.zone.project.space.home.domain.ZDynamic;
import com.cjl.basic.zone.project.space.home.domain.ZSignIn;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ZSignInMapper {
    /**
     * 签到
     *
     * @param zSignIn
     * @return
     */
    int insertSignInfo(ZSignIn zSignIn);

    /**
     * 插入动态
     *
     * @param zDynamic
     * @return
     */
    int insertDynamic(ZDynamic zDynamic);

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
    List<ZDynamic> selectSignInfoMap(Integer accountId);

    /**
     * 查询动态信息
     *
     * @param accountId
     * @param currentTime
     * @return
     */
    List<ZDynamic> selectDynamicList(@Param("accountId") Integer accountId, @Param("currentTime") String currentTime);

    /**
     * 查询今天是否签到
     *
     * @param accountId
     * @param currentDate
     * @return
     */
    int checkSignIn(@Param("accountId") Integer accountId, @Param("currentDate") String currentDate);
}
