package com.cjl.basic.zone.project.user.service;

import com.cjl.basic.zone.project.user.domain.LoginInfo;
import com.cjl.basic.zone.project.user.domain.User;

import java.util.List;

/**
 * 注册 业务层
 *
 * @author hd_zhu
 */
public interface IRegisterService {

    /**
     * 注册账号
     *
     * @param user 用户对象
     */
    int registerUser(User user);
}
