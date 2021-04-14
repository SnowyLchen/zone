/**
 * Created by IntelliJ IDEA.
 * User: Kyrie
 * DateTime: 2018/7/23 17:07
 **/
package com.cjl.basic.zone.project.loginLog.mapper;


import com.cjl.basic.zone.project.loginLog.domain.LoginLog;

import java.util.List;

/**
 * 登录日志
 */
public interface LoginLogMapper {

    /**
     * 添加日志
     *
     * @param logDomain
     * @return
     */
    int addLog(LoginLog logDomain);

    /**
     * 获取日志
     *
     * @return
     */
    List<LoginLog> getLogs();
}
