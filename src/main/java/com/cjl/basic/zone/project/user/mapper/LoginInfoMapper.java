package com.cjl.basic.zone.project.user.mapper;

import com.cjl.basic.zone.project.user.domain.LoginInfo;

public interface LoginInfoMapper {

    int insertSelective(LoginInfo record);

    LoginInfo selectByPrimaryKey(Integer loginInfoId);

    int updateByPrimaryKeySelective(LoginInfo record);

    int updateByLoginName(LoginInfo record);

    int deleteMfrsLoginInfoMapper(LoginInfo record);
}
