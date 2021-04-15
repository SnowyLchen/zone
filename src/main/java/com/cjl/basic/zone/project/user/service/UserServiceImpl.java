package com.cjl.basic.zone.project.user.service;

import com.cjl.basic.zone.common.constant.DelFlagConstants;
import com.cjl.basic.zone.common.constant.UserConstants;
import com.cjl.basic.zone.common.support.Convert;
import com.cjl.basic.zone.common.utils.InsertOrUpdateUtils;
import com.cjl.basic.zone.common.utils.StringUtils;
import com.cjl.basic.zone.common.utils.security.ShiroAuthenticateUtils;
import com.cjl.basic.zone.framework.shiro.service.PasswordService;
import com.cjl.basic.zone.project.user.domain.User;
import com.cjl.basic.zone.project.user.mapper.UserMapper;
import com.cjl.basic.zone.utils.dateutils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


/**
 * 用户 业务层处理
 *
 * @author wangsen
 */
@Service
public class UserServiceImpl implements IUserService {
    private static final String DEFAULT_PASSWORD = "admin123";

    @Autowired
    private PasswordService passwordService;

    @Resource
    private UserMapper userMapper;


    @Override
    public List<User> selectUserList(User user) {
        user.setLoginName(user.getLoginName());
        user.setUserName(user.getUserName());
        user.setPhonenumber(user.getPhonenumber());
        user.setAccountId(ShiroAuthenticateUtils.getAccountId());
        return new ArrayList<>();
    }

    @Override
    public User selectUserById(Long accountId) {
        return null;
    }


    @Override
    public int deleteUserByIds(String ids, String loginName) throws Exception {
        if (userMapper.hasSuperAdmin(ids) > 0) {
            throw new Exception("不允许删除管理员用户");
        }
        InsertOrUpdateUtils.requireGreaterThanI(userMapper.deleteUserByIds(ids, DateUtils.getNowDate(), loginName), "删除用户失败");
        ShiroAuthenticateUtils.clearByAccountIds(Convert.toIntArray(ids));
        return 1;
    }

    @Override
    public boolean checkIsQuit(String loginName, Long mfrsId) {
        return userMapper.checkLoginNameUniqueInMfrs(loginName, mfrsId.intValue()) <= 0;
    }

    @Override
    public boolean checkLoginNameUnique(String loginName, Long mfrsId) {
        return checkIsQuit(loginName, mfrsId);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateUser(User u) {
        InsertOrUpdateUtils.addUpdateAttr(u);
        return InsertOrUpdateUtils.requireGreaterThanI(userMapper.updateUser(u), "修改用户信息失败");
    }

    public int updateUserTry(User u) {
        u.setDelFlag(DelFlagConstants.EXIST);
        InsertOrUpdateUtils.addUpdateAttr(u);
        return InsertOrUpdateUtils.requireGreaterThanI(userMapper.updateUser(u), "修改用户信息失败");
    }


    @Override
    public int updateUserInfo(User user) {
        return userMapper.updateUserSelf(user);
    }

    @Override
    public int resetUserPwd(User u) {
        u.randomSalt();
        User nu = userMapper.selectUserById2(u.getAccountId());
        u.setPassword(passwordService.encryptPassword(nu.getLoginName(), u.getPassword(), u.getSalt()));
        InsertOrUpdateUtils.addUpdateAttr(u);
        return InsertOrUpdateUtils.requireGreaterThanI(userMapper.updateUser(u));
    }

    @Override
    public String checkLoginNameUnique(String loginName) {
        int count = userMapper.checkLoginNameUnique(loginName);
        if (count > 0) {
            return UserConstants.USER_NAME_NOT_UNIQUE;
        }
        return UserConstants.USER_NAME_UNIQUE;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public String checkPhoneUnique(User user) {
        int count = userMapper.checkPhoneUnique(user.getPhonenumber());
        if (count > 0) {
            return UserConstants.USER_PHONE_NOT_UNIQUE;
        }
        return UserConstants.USER_PHONE_UNIQUE;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateUserPassword(User user) {
        return userMapper.updateUser(user);
    }


    @Override
    public String checkResetPwdUnique(User user) {
        Long accountId = StringUtils.isNull(user.getAccountId()) ? -1L : user.getAccountId();
        User info = userMapper.checkResetPwdUnique(accountId);
        if (StringUtils.isNotNull(info)) {
            boolean resetpwd = passwordService.matches(info, user.getPassword());
            if (resetpwd) {
                return UserConstants.USER_RESETAWD_NOT_UNIQUE;
            }
            return UserConstants.USER_RESETAWD_UNIQUE;
        }
        return UserConstants.USER_RESETAWD_UNIQUE;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public int register(User dto) {
//        verificationUser(dto);
        // 写入注册信息
        User user = insertUserForRegister(dto);
        Integer accountId = dto.getAccountId();
        // 发送通知
//        NoticeRecord noticeRecord = sendNoticeRecord(accountId);
        return 1;
    }

//
//    /**
//     * 验证用户信息
//     *
//     * @param dto 用户信息
//     */
//    private void verificationUser(UserRolePictureDto dto) {
//        if (Objects.isNull(dto.getRoleIds())) {
//            throw new RuntimeException("请选择所属角色");
//        }
//        if (Objects.isNull(deptService.selectDeptById(dto.getDeptId().longValue()))) {
//            throw new RuntimeException("部门信息被更改,请重新选择所属部门");
//        }
//    }

//    /**
//     * 发生注册通知
//     *
//     * @param accountId 账号ID
//     */
//    private NoticeRecord sendNoticeRecord(Integer accountId) {
//        NoticeRecord noticeRecord = new NoticeRecord();
//        Person person = new Person();
//        person.setEnterpriseId(ShiroAuthenticateUtils.getMfrsId());
//        person.setType(NoticeConstants.MFRS);
//        person.setUserType(Integer.valueOf(UserConstants.USER_TYPE_COMMON));
//        noticeRecord.setNoticeContent("恭喜注册成功！欢迎使用智控管理平台！");
//        noticeRecord.setDelFlag(DelFlagConstants.EXIST);
//        NoticeRecord noticeRecord1 = noticeRecordService.sendNoticeRecord(person, noticeRecord, Math.toIntExact(accountId));
//        return noticeRecord1;
//    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User insertUserForRegister(User u) {
        // 初始化一般用户时为禁用状态，需要企业管理员企业该账号才能正常使用
        if (u.getGroupName() == null) {
            u.setGroupName(UserConstants.USER_TYPE_ADMIN_NAME);
        }
        if (u.getPassword() == null) {
            // 随机生成密码加密盐
            u.randomSalt();
            final String md5Password = passwordService.encryptPassword(u.getLoginName(), UserServiceImpl.DEFAULT_PASSWORD, u.getSalt());
            u.setPassword(md5Password);
        }
        u.setUpdateBy(u.getLoginName());
        u.setUpdateTime(DateUtils.getNowDate());
        u.setCreateBy(u.getLoginName());
        u.setCreateTime(DateUtils.getNowDate());
        InsertOrUpdateUtils.requireGreaterThanI(userMapper.insertUser(u), "新增用户失败");
        return u;
    }

    @Override
    public User loginGetUser(String account) {
        return userMapper.selectUserDeptRoleByUserName(account);
    }


//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public int statusEnableOrDisable(Integer accountId) {
//        User u = selectUserById(accountId.longValue());
//        User iu = new User();
//        iu.setAccountId(accountId);
//        if (u.getStatus().equals(UserStatus.OK.getCode())) {
//            iu.setStatus(UserStatus.DISABLE.getCode());
//            // 停用用户踢下线
//            ShiroAuthenticateUtils.clear(u.getLoginName());
//        } else {
//            iu.setStatus(UserStatus.OK.getCode());
//        }
//        InsertOrUpdateUtils.addUpdateAttr(iu);
//        return InsertOrUpdateUtils.requireGreaterThanI(userMapper.updateUser(iu), "启/停用用户失败");
//    }

    @Override
    public User selectUserByPhoneNumber(String phoneNumber) {
        return userMapper.selectUserByPhoneNumber(phoneNumber);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(String toString) {
        userMapper.deleteuser(toString);
    }

    @Override
    public User checkRegisterExist(User user) {
        return userMapper.checkRegisterExist(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<User> selectUserList2AllUser(User user) {
        return userMapper.selectUserList2(user);
    }


    @Override
    public User selectUserDeptRoleByAccountId(Integer accountId) {
        return userMapper.selectUserDeptRoleByAccountId(accountId);
    }

}
