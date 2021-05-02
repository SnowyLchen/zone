package com.cjl.basic.zone.project.space.home.mapper;

import com.cjl.basic.zone.project.space.home.domain.ZSignIn;

import java.util.List;

public interface ZSignInMapper {
    /**
     * 签到
     *
     * @param zSignIn
     * @return
     */
    int insertSignInfo(ZSignIn zSignIn);

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
}
