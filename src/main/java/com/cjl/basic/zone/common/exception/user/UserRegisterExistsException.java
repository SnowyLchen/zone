package com.cjl.basic.zone.common.exception.user;

/**
 * 用户已注册异常类
 *
 * @author chen
 */
public class UserRegisterExistsException extends UserException
{
    private static final long serialVersionUID = 1L;

    public UserRegisterExistsException()
    {
        super("user.register.exist", null);
    }
}
