package com.cjl.basic.zone.project.user.domain;

/**
 * 用户状态
 *
 * @author chen
 *
 */
public enum UserStatus
{
    OK("1", "正常"), DISABLE("0", "停用"), QUIT("2", "删除");

    private final String code;
    private final String info;

    UserStatus(String code, String info)
    {
        this.code = code;
        this.info = info;
    }

    public String getCode()
    {
        return code;
    }

    public String getInfo()
    {
        return info;
    }
}
