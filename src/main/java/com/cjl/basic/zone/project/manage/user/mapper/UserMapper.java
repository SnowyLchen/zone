package com.cjl.basic.zone.project.manage.user.mapper;

import com.cjl.basic.zone.project.manage.role.domain.ZUserRole;
import com.cjl.basic.zone.project.manage.user.domain.User;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 用户表 数据层
 *
 * @author chen
 */
public interface UserMapper {

    /**
     * 查询用户信息
     *
     * @param user 查询用户形象
     * @return {@link List<User>}
     */
    List<User> selectUserList(User user);

    /**
     * 查询用户信息根据ID
     *
     * @param accountId 用户ID
     * @return {@link User}
     */
    User selectUserById(Integer accountId);


    /**
     * 新增用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    int insertUser(User user);

    /**
     * 新增用户信息
     *
     * @param zUserRole 用户信息
     * @return 结果
     */
    int insertUserMenu(ZUserRole zUserRole);


    /**
     * 根据登录账号（用户名或密码）查询用户信息
     *
     * @param userName name
     */
    User selectUserDeptRoleByUserName(String userName);

    /**
     * 更具登录账号（用户名或密码）查询用户信息
     *
     * @param accountId id
     */
    User selectUserDeptRoleByAccountId(Integer accountId);


    /**
     * 校验用户姓名是否唯一
     *
     * @param loginName 登录帐号
     * @return 结果
     */
    int checkLoginNameUnique(String loginName);


    /**
     * 校验手机号是否唯一
     *
     * @param phonenumber 手机号
     * @return 结果
     */
    int checkPhoneUnique(String phonenumber);


    /**
     * 修改用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    int updateUser(User user);


    /**
     * 更新登录信息
     *
     * @param user user对象
     * @return
     */
    int updateUserSelf(User user);


    /**
     * 校验初始密码与原密码是否相同
     *
     * @param accountId
     * @return
     */
    User checkResetPwdUnique(@Param("accountId") Long accountId);

    /**
     * 删除用户通过Ids
     *
     * @param ids      ids
     * @param date     修改时间
     * @param updateBy 修改人
     * @return {@link Integer}
     */
    int deleteUserByIds(@Param("ids") String ids, @Param("date") Date date, @Param("updateBy") String updateBy);

    /**
     * 是否有超级管理员
     *
     * @param ids ids
     * @return
     */
    int hasSuperAdmin(String ids);

    /**
     * 校验用户姓名是否唯一
     *
     * @param loginName 登录帐号
     * @param mfrsId    企业ID
     * @return 结果
     */
    int checkLoginNameUniqueInMfrs(@Param("loginName") String loginName, @Param("mfrsId") Integer mfrsId);


    /**
     * 根据手机号码查询用户
     *
     * @param phoneNumber 用户
     * @return {@link User}
     */
    User selectUserByPhoneNumber(String phoneNumber);

    void deleteuser(String toString);

    /**
     * 查询用户是否已经注册
     *
     * @param user
     * @return
     */
    User checkRegisterExist(User user);
}
