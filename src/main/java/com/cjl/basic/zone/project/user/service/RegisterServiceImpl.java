package com.cjl.basic.zone.project.user.service;

import com.cjl.basic.zone.common.exception.user.UserRegisterExistsException;
import com.cjl.basic.zone.common.utils.InsertOrUpdateUtils;
import com.cjl.basic.zone.common.utils.ServletUtils;
import com.cjl.basic.zone.common.utils.security.ShiroAuthenticateUtils;
import com.cjl.basic.zone.framework.shiro.service.PasswordService;
import com.cjl.basic.zone.project.loginLog.domain.LoginLog;
import com.cjl.basic.zone.project.loginLog.mapper.LoginLogMapper;
import com.cjl.basic.zone.project.user.domain.User;
import com.cjl.basic.zone.project.user.domain.UserStatus;
import com.cjl.basic.zone.utils.StringUtils;
import com.cjl.basic.zone.utils.dateutils.DateUtils;
import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 注册业务层处理
 *
 * @author chen
 */
@Service("register")
public class RegisterServiceImpl implements IRegisterService {

    @Autowired
    private PasswordService passwordService;
    @Autowired
    private IUserService userService;
    @Resource
    private LoginLogMapper loginInfoMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int registerUser(User user) {
        // 查询用户是否已经注册
        User u = userService.checkRegisterExist(user);
        if (!StringUtils.isNull(u)) {
            throw new UserRegisterExistsException();
        }
        addDefaultUserInfo(user);
        addAccountToLoginInfoTable(user);
        return 1;
    }

    /**
     * 写入默认的用户信息
     *
     * @param user 注册user对象
     */
    User addDefaultUserInfo(User user) {
        // 生成随机的加密盐
        user.randomSalt();
        // 根据用户名和盐加密密码并初始化密码
        user.setPassword(passwordService.encryptPassword(
                user.getLoginName(),
                user.getPassword(),
                user.getSalt()
        ));
        user.setCreateTime(DateUtils.getNowDate());
        user.setUserName(user.getLoginName());
        user.setActivated(UserStatus.OK.getCode());
        // 写入注册时的Ip
        user.setLoginIp(ShiroAuthenticateUtils.getIp());

        // 写入注册信息
        return userService.insertUserForRegister(user);
    }

    /**
     * 写入账号信息表
     */
    public void addAccountToLoginInfoTable(User user) {
        final UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
        LoginLog loginInfo = new LoginLog();
        loginInfo.setStatus(UserStatus.OK.getCode());
        loginInfo.setLoginName(user.getLoginName());
        loginInfo.setIp(ShiroAuthenticateUtils.getIp());
        // 获取客户端操作系统
        String os = userAgent.getOperatingSystem().getName();
        // 获取客户端浏览器
        String browser = userAgent.getBrowser().getName();
        loginInfo.setOs(os);
        loginInfo.setBrowser(browser);
        loginInfo.setAction("注册");
        loginInfo.setAccountId(user.getAccountId());
        InsertOrUpdateUtils.requireGreaterThanI(loginInfoMapper.addLog(loginInfo), "新增账号信息失败");
    }
}
