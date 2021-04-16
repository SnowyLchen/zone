package com.cjl.basic.zone.common.exception.user;

/**
 * 用户密码不正确或不符合规范异常类
 *
 * @author chen
 */
public class UserPasswordNotMatchException extends UserException {
    private static final long serialVersionUID = 1L;

    public UserPasswordNotMatchException() {
        super("user.password.not.match", null);
    }

    public UserPasswordNotMatchException(String i18n, int min, int max) {
        super(i18n, new Object[]{
                min, max
        });
    }
}
