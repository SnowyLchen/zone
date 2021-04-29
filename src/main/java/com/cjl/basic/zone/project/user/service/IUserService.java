package com.cjl.basic.zone.project.user.service;

import com.cjl.basic.zone.project.layim.entity.Mine;
import com.cjl.basic.zone.project.user.domain.User;

import java.util.List;

/**
 * 用户信息
 */
public interface IUserService {
    /**
     * 根据条件分页查询用户对象
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    List<User> selectUserList(User user);

    /**
     * 通过用户ID查询用户
     *
     * @param accountId 用户ID
     * @return 用户对象信息
     */
    User selectUserById(Integer accountId);
    /**
     * 通过用户ID查询用户Socket
     *
     * @param accountId 用户ID
     * @return 用户对象信息
     */
    Mine selectMineById(Integer accountId);

    /**
     * 批量删除用户信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     * @throws Exception 异常
     */
    int deleteUserByIds(String ids);

    /**
     * 用户是否离职
     *
     * @param loginName 登录名
     * @param mfrsId    企业id
     */
    boolean checkIsQuit(String loginName, Long mfrsId);

    /**
     * 企业是否存在该账号
     *
     * @param loginName 登录名
     * @param mfrsId    企业id
     * @return {@link Boolean}
     */
    boolean checkLoginNameUnique(String loginName, Long mfrsId);


    /**
     * 保存用户信息
     *
     * @param u 用户信息
     * @return 结果
     */
    int updateUser(User u);


    /**
     * 修改用户详细信息
     *
     * @param user 用户信息
     * @return 结果
     */
    int updateUserInfo(User user);

    /**
     * 修改用户密码信息
     *
     * @param user 用户信息
     * @return 结果
     */
    int resetUserPwd(User user);

    /**
     * 校验用户姓名是否唯一
     *
     * @param loginName 登录帐号
     * @return 结果
     */
    String checkLoginNameUnique(String loginName);

    /**
     * 校验手机号是否唯一
     *
     * @param user 用户信息
     * @return 结果
     */
    String checkPhoneUnique(User user);

    /**
     * 修改用户密码
     *
     * @param user 用户信息
     * @return 结果
     */
    int updateUserPassword(User user);

    /**
     * 校验要修改的密码与原密码是否相同
     *
     * @param user
     * @return
     */
    String checkResetPwdUnique(User user);

    /**
     * 新增用户
     *
     * @param u 用户对象
     */
    User insertUserForRegister(User u);

    /**
     * 登录
     *
     * @param account 账号
     * @return
     */
    User loginGetUser(String account);


    /**
     * 根据手机号码查询用户
     *
     * @param phoneNumber 用户
     * @return {@link User}
     */
    User selectUserByPhoneNumber(String phoneNumber);

    /**
     * 查询所有用户
     *
     * @param user 用户
     * @return u
     */
    List<User> selectUserList2AllUser(User user);

    /**
     * 根据accountid查询用户
     *
     * @param accountId aid
     * @return
     */
    User selectUserDeptRoleByAccountId(Integer accountId);

    void deleteUser(String toString);

    /**
     * 查询用户是否已经注册
     *
     * @param user
     * @return
     */
    User checkRegisterExist(User user);
}
