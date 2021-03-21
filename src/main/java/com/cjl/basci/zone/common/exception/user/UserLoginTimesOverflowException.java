package com.cjl.basci.zone.common.exception.user;

/**
 * @author 朱睿
 */
public class UserLoginTimesOverflowException extends UserException {

    private static final long serialVersionUID = 1L;

    public UserLoginTimesOverflowException() {
        super("login.times.overflow", null);
    }
}
