package com.cjl.basic.zone.project.user.service;

import com.cjl.basic.zone.common.constant.UserConstants;
import com.cjl.basic.zone.common.utils.InsertOrUpdateUtils;
import com.cjl.basic.zone.common.utils.security.ShiroAuthenticateUtils;
import com.cjl.basic.zone.framework.shiro.service.PasswordService;
import com.cjl.basic.zone.project.user.domain.LoginInfo;
import com.cjl.basic.zone.project.user.domain.User;
import com.cjl.basic.zone.project.user.domain.UserStatus;
import com.cjl.basic.zone.project.user.mapper.LoginInfoMapper;
import com.cjl.basic.zone.utils.dateutils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 注册业务层处理
 *
 * @author hd_zhu
 */
@Service("register")
public class RegisterServiceImpl implements IRegisterService {

    @Autowired
    private PasswordService passwordService;
    @Autowired
    private IUserService userService;
    @Resource
    private LoginInfoMapper loginInfoMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int registerUser(User user) {
        User u = addDefaultUserInfo(user);
        addAccountToLoginInfoTable(user.getLoginName());
//        List<MfrsSettings> mfrsSettingsList = userService.trySettings(user.getAccountId());
        return 1;
    }

    /**
     * 写入默认的用户信息
     *
     * @param user 注册user对象
     */
    User addDefaultUserInfo(User user) {
        // 默认用户类型为管理员
        user.setGroupName(UserConstants.USER_TYPE_ADMIN);
        // 生成随机的加密盐
        user.randomSalt();
        // 根据用户名和盐加密密码并初始化密码
        user.setPassword(passwordService.encryptPassword(
                user.getLoginName(),
                user.getPassword(),
                user.getSalt()
        ));
        user.setCreateTime(DateUtils.getNowDate());
        user.setStatus(UserStatus.OK.getCode());
        // 写入注册时的Ip
        user.setLoginIp(ShiroAuthenticateUtils.getIp());

        // 写入注册信息
        return userService.insertUserForRegister(user);
    }

    /**
     * 写入账号信息表
     *
     * @param loginName 账号名
     */
    public void addAccountToLoginInfoTable(String loginName) {
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setStatus(UserStatus.OK.getCode());
        loginInfo.setLoginName(loginName);
        loginInfo.setUpdateBy(loginName);
        loginInfo.setUpdateTime(DateUtils.getNowDate());
        loginInfo.setCreateBy(loginName);
        loginInfo.setCreateTime(DateUtils.getNowDate());
        InsertOrUpdateUtils.requireGreaterThanI(loginInfoMapper.insertSelective(loginInfo), "新增账号信息失败");
    }
}
