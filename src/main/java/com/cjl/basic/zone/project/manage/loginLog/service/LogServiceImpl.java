/**
 * Created by IntelliJ IDEA.
 * User: Kyrie
 * DateTime: 2018/7/23 16:59
 **/
package com.cjl.basic.zone.project.manage.loginLog.service;

import com.cjl.basic.zone.project.manage.loginLog.domain.LoginLog;
import com.cjl.basic.zone.project.manage.loginLog.mapper.LoginLogMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 日志相关Service接口实现
 *
 * @author chen
 */
@Service
public class LogServiceImpl implements LogService {


    @Resource
    private LoginLogMapper loginLogMapper;


    @Override
    public void addLog(String action, String data, String ip, Integer authorId) {
        LoginLog log = new LoginLog();
        log.setAccountId(authorId);
        log.setIp(ip);
        log.setData(data);
        log.setAction(action);
        loginLogMapper.addLog(log);
    }

    @Override
    public void addLog(LoginLog loginLog) {
        loginLogMapper.addLog(loginLog);
    }

    @Override
    public PageInfo<LoginLog> getLogs(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<LoginLog> logs = loginLogMapper.getLogs();
        return new PageInfo<>(logs);

    }
}
