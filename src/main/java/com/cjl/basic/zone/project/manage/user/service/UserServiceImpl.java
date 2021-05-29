package com.cjl.basic.zone.project.manage.user.service;

import com.cjl.basic.zone.common.constant.DelFlagConstants;
import com.cjl.basic.zone.common.constant.UserConstants;
import com.cjl.basic.zone.common.exception.base.BaseException;
import com.cjl.basic.zone.common.support.Convert;
import com.cjl.basic.zone.common.utils.InsertOrUpdateUtils;
import com.cjl.basic.zone.common.utils.StringUtils;
import com.cjl.basic.zone.common.utils.security.ShiroAuthenticateUtils;
import com.cjl.basic.zone.framework.shiro.service.PasswordService;
import com.cjl.basic.zone.project.manage.layim.entity.Friend;
import com.cjl.basic.zone.project.manage.layim.entity.Friends;
import com.cjl.basic.zone.project.manage.layim.entity.Mine;
import com.cjl.basic.zone.project.manage.layim.service.FriendsService;
import com.cjl.basic.zone.project.manage.role.domain.ZUserRole;
import com.cjl.basic.zone.project.manage.user.domain.User;
import com.cjl.basic.zone.project.manage.user.mapper.UserMapper;
import com.cjl.basic.zone.utils.dateutils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


/**
 * 用户 业务层处理
 *
 * @author chen
 */
@Service
public class UserServiceImpl implements IUserService {
    private static final String DEFAULT_PASSWORD = "admin123";

    @Autowired
    private PasswordService passwordService;

    @Resource
    private UserMapper userMapper;

    @Resource
    private FriendsService friendsService;


    @Override
    public List<User> selectUserList(User user) {
        return userMapper.selectUserList(user);
    }

    @Override
    public List<User> selectUserListByAccountId(User user) {
        return userMapper.selectUserListByAccountId(user);
    }

    @Override
    public User selectUserById(Integer accountId) {
        return userMapper.selectUserById(accountId);
    }

    @Override
    public Mine selectMineById(Integer accountId) {
        User user = userMapper.selectUserById(accountId);
        Mine mine = new Mine();
        mine.setAccountId(user.getAccountId());
        mine.setId(user.getAccountId());
        mine.setStatus(user.getStatus());
        mine.setSign(user.getSign());
        mine.setAvatar(user.getAvatar());
        mine.setUsername(user.getUserName());
        mine.setLoginName(user.getLoginName());
        return mine;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteUserByIds(String ids) {
        if (ids.equals("1")) {
            throw new BaseException("不允许删除超级管理员");
        }
        ShiroAuthenticateUtils.clearByAccountIds(Convert.toIntArray(ids));
        InsertOrUpdateUtils.requireGreaterThanI(userMapper.deleteUserByIds(ids, DateUtils.getNowDate(), ShiroAuthenticateUtils.getLoginName()), "删除用户失败");
        return 1;
    }

    @Override
    public boolean checkIsQuit(String loginName, Long mfrsId) {
        return userMapper.checkLoginNameUniqueInMfrs(loginName, mfrsId.intValue()) <= 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateUser(User u) {
        u.setUpdateTime(new Date());
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
        User nu = userMapper.selectUserById(u.getAccountId());
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
        if (u.getUserType() == null) {
            // 设置账户类型
            u.setUserType(UserConstants.USER_TYPE_ADMIN_NAME_FLAG);
            // 设置主页链接
            u.setHomeUrl(UserConstants.USER_TYPE_ADMIN_URL);
        }
        if (u.getPassword() == null) {
            // 随机生成密码加密盐
            u.randomSalt();
            final String md5Password = passwordService.encryptPassword(u.getLoginName(), UserServiceImpl.DEFAULT_PASSWORD, u.getSalt());
            u.setPassword(md5Password);
        }
        InsertOrUpdateUtils.addInsertAttr(u);
        InsertOrUpdateUtils.requireGreaterThanI(userMapper.insertUser(u), "新增用户失败");
        // 设置账号菜单权限
        Integer accountId = u.getAccountId();
        InsertOrUpdateUtils.requireGreaterThanI(userMapper.insertUserMenu(new ZUserRole() {{
            setRoleId(2);
            setAccountId(accountId);
        }}), "新增用户默认菜单失败");
        // 新增用户默认分组
        Friends f = friendsService.createGroup(new Friends() {{
            setAccountId(accountId);
            setGroupname("我的好友");
        }});
        InsertOrUpdateUtils.requireGreaterThanI(f != null ? 1 : 0, "新增用户默认分组失败");
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
    public Friends addFriendGroup(Friends friends) {
        return friendsService.createGroup(friends);
    }

    @Override
    public int removeFriendGroup(Integer id) {
        return friendsService.removeFriendGroup(id);
    }

    @Override
    public int updateFriendGroup(Friends friends) {
        return friendsService.updateFriendGroup(friends);
    }

    @Override
    public int updateFriendToGroup(Friend friends) {
        return friendsService.updateFriendToGroup(friends);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<User> selectUserList2AllUser(User user) {
        return userMapper.selectUserList(user);
    }


    @Override
    public User selectUserDeptRoleByAccountId(Integer accountId) {
        return userMapper.selectUserDeptRoleByAccountId(accountId);
    }

}
