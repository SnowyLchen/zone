package com.cjl.basic.zone.project.user.domain;

import com.cjl.basic.zone.framework.web.domain.BaseEntity;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;

import java.util.Date;

/**
 * mfrs_account
 *
 * @author
 */
public class User extends BaseEntity {
    /**
     * 用户ID
     */
    private Integer accountId;

    /**
     * 部门ID
     */
    private Integer deptId;

    /**
     * 登录账号
     */
    private String loginName;

    /**
     * 真实姓名
     */
    private String userName;

    /**
     * 1:超级管理员 2:普通管理员 3普通用户
     */
    private String userType;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 手机号码
     */
    private String phonenumber;

    /**
     * 用户性别（0男 1女 2未知）
     */
    private String sex;

    /**
     * 头像路径
     */
    private Integer avatar;

    /**
     * 密码
     */
    private String password;

    /**
     * 盐加密
     */
    private String salt;

    /**
     * 帐号状态（0正常 1停用）
     */
    private String status;

    /**
     * 最后登陆IP
     */
    private String loginIp;

    /**
     * 最后登陆时间
     */
    private Date loginDate;

    /**
     * 所在企业ID
     */
    private Integer mfrsId;

    /**
     * 数据范围（1：全部数据权限 2：自定数据权限）
     */
    private Integer dataScope;

    /**
     * 生日
     */
    private Date birthday;

    /**
     * QQ
     */
    private String qq;

    /**
     * 岗位id
     */
    private Integer positionId;

    /**
     * 技术等级id
     */
    private Integer technicalLevelId;

    /**
     * 身份证号
     */
    private String idcarNumber;

    /**
     * 民族
     */
    private String nation;

    /**
     * 籍贯
     */
    private String nativePlace;

    /**
     * 合同时间
     */
    private Date contractTime;

    /**
     * 紧急联系人
     */
    private String emergencyContact;

    /**
     * 紧急联系方式
     */
    private String emergencyContactPhone;

    /**
     * 资质证书
     */
    private String certificate;

    /**
     * 离职时间
     */
    private Date quitTime;

    /**
     * 生成随机盐
     */
    public void randomSalt() {
        // 一个Byte占两个字节，此处生成的3字节，字符串长度为6
        SecureRandomNumberGenerator secureRandom = new SecureRandomNumberGenerator();
        String hex = secureRandom.nextBytes(3).toHex();
        setSalt(hex);
    }

    public boolean isAdmin() {
        return isAdmin(this.accountId);
    }

    public static boolean isAdmin(Integer accountId) {
        return accountId != null && 1L == accountId;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getAvatar() {
        return avatar;
    }

    public void setAvatar(Integer avatar) {
        this.avatar = avatar;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    public Integer getMfrsId() {
        return mfrsId;
    }

    public void setMfrsId(Integer mfrsId) {
        this.mfrsId = mfrsId;
    }

    public Integer getDataScope() {
        return dataScope;
    }

    public void setDataScope(Integer dataScope) {
        this.dataScope = dataScope;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public Integer getPositionId() {
        return positionId;
    }

    public void setPositionId(Integer positionId) {
        this.positionId = positionId;
    }

    public Integer getTechnicalLevelId() {
        return technicalLevelId;
    }

    public void setTechnicalLevelId(Integer technicalLevelId) {
        this.technicalLevelId = technicalLevelId;
    }

    public String getIdcarNumber() {
        return idcarNumber;
    }

    public void setIdcarNumber(String idcarNumber) {
        this.idcarNumber = idcarNumber;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getNativePlace() {
        return nativePlace;
    }

    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace;
    }

    public Date getContractTime() {
        return contractTime;
    }

    public void setContractTime(Date contractTime) {
        this.contractTime = contractTime;
    }

    public String getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }

    public String getEmergencyContactPhone() {
        return emergencyContactPhone;
    }

    public void setEmergencyContactPhone(String emergencyContactPhone) {
        this.emergencyContactPhone = emergencyContactPhone;
    }

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    public Date getQuitTime() {
        return quitTime;
    }

    public void setQuitTime(Date quitTime) {
        this.quitTime = quitTime;
    }
}
