package com.cjl.basic.zone.project.user.service;

import com.cjl.basic.zone.common.constant.Constants;
import com.cjl.basic.zone.common.constant.UserConstants;
import com.cjl.basic.zone.common.exception.user.UserBlockedException;
import com.cjl.basic.zone.common.exception.user.UserNotExistsException;
import com.cjl.basic.zone.common.exception.user.UserPasswordNotMatchException;
import com.cjl.basic.zone.common.utils.MessageUtils;
import com.cjl.basic.zone.common.utils.StringUtils;
import com.cjl.basic.zone.framework.manager.AsyncManager;
import com.cjl.basic.zone.framework.manager.factory.AsyncFactory;
import com.cjl.basic.zone.framework.shiro.jwt.JwtUtil;
import com.cjl.basic.zone.framework.shiro.jwt.StatelessToken;
import com.cjl.basic.zone.framework.shiro.jwt.StatelessWebUtils;
import com.cjl.basic.zone.framework.shiro.service.PasswordService;
import com.cjl.basic.zone.project.user.domain.User;
import com.cjl.basic.zone.project.user.domain.UserStatus;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * @author chen
 */
@Component
public class LoginService {
    @Resource
    private IUserService userService;
    @Resource
    private PasswordService passwordService;

    /**
     * [登录接口]
     *
     * @param username [用户名]
     * @param password [密码]
     * @return [用户模型]
     * @author xiaojie
     * @date 2020/10/9 16:55
     */
    public User login(String username, String password, HttpServletResponse response) {

        //1、判断账号名或密码是否为空 空则抛出异常错误
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, null, Constants.LOGIN_FAIL, MessageUtils.message("not.null")));
            throw new UserNotExistsException();
        }

        //2、判断账户名长度是否合法（20>=username>=2），不合法抛出异常
        if (username.length() < UserConstants.USERNAME_MIN_LENGTH || username.length() > UserConstants.USERNAME_MAX_LENGTH) {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, null, Constants.LOGIN_FAIL, MessageUtils.message("user.username.length.not.valid")));
            throw new UserPasswordNotMatchException("user.username.length.not.valid", 2, 20);
        }
        //3、判断密码长度是否合法（20>=password>=5）,不合法抛出异常
        if (password.length() < UserConstants.PASSWORD_MIN_LENGTH || password.length() > UserConstants.PASSWORD_MAX_LENGTH) {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, null, Constants.LOGIN_FAIL, MessageUtils.message("user.password.length.not.valid")));
            throw new UserPasswordNotMatchException("user.password.length.not.valid", 5, 20);
        }

        //4、查询用户
        User u = userService.loginGetUser(username);

        //5、查询用户是否存在
        if (u == null) {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, null, Constants.LOGIN_FAIL, MessageUtils.message("user.not.exists")));
            throw new UserNotExistsException();
        }
//        //6、判断用户是否禁用
        if (UserStatus.DISABLE.getCode().equals(u.getActivated())) {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, u.getAccountId(), Constants.LOGIN_FAIL, MessageUtils.message("user.blocked", u.getRemark())));
            throw new UserBlockedException(u.getRemark());
        }

//        // 8、校验密码
        passwordService.validate(u, password);

//        // 10.jwt认证，写入缓存
        final String token = JwtUtil.sign(u.getLoginName());
        SecurityUtils.getSubject().login(new StatelessToken(token, true));
//
//        // 写入cookie
        StatelessWebUtils.addCookiesForToken(response, token);
//        // 11、写入登录日志
        AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, u.getAccountId(), Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success")));

        return u;
    }
}
