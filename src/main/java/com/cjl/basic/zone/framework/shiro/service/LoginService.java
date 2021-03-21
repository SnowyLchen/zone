//package com.cjl.basci.zone.framework.shiro.service;
//
//import com.uustop.common.constant.Constants;
//import com.uustop.common.constant.UserConstants;
//import com.uustop.common.exception.user.NoRoleException;
//import com.uustop.common.exception.user.UserBlockedException;
//import com.uustop.common.exception.user.UserNotExistsException;
//import com.uustop.common.exception.user.UserPasswordNotMatchException;
//import com.uustop.common.utils.DateUtils;
//import com.uustop.common.utils.MessageUtils;
//import com.uustop.common.utils.StringUtils;
//import com.uustop.common.utils.security.ShiroAuthenticateUtils;
//import com.uustop.framework.manager.AsyncManager;
//import com.uustop.framework.manager.factory.AsyncFactory;
//import com.uustop.framework.shiro.jwt.JwtUtil;
//import com.uustop.framework.shiro.jwt.StatelessToken;
//import com.uustop.framework.shiro.jwt.StatelessWebUtils;
//import com.uustop.project.system.menu.service.IMenuService;
//import com.uustop.project.system.role.service.IRoleService;
//import com.uustop.project.system.user.domain.User;
//import com.uustop.project.system.user.domain.UserDeptRole;
//import com.uustop.project.system.user.domain.UserStatus;
//import com.uustop.project.system.user.service.IUserService;
//import org.apache.shiro.SecurityUtils;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.http.HttpServletResponse;
//import java.util.Set;
//
///**
// * 登录校验方法
// *
// * @author wangsen
// */
//@Component
//public class LoginService {
//    private final PasswordService passwordService;
//    private final IUserService userService;
//    private final IRoleService roleService;
//    private final IMenuService menuService;
//
//    public LoginService(PasswordService passwordService, IUserService userService, IRoleService roleService, IMenuService menuService) {
//        this.passwordService = passwordService;
//        this.userService = userService;
//        this.roleService = roleService;
//        this.menuService = menuService;
//    }
//
//    /**
//     * [登录接口]
//     *
//     * @param username [用户名]
//     * @param password [密码]
//     * @return [用户模型]
//     * @author xiaojie
//     * @date 2020/10/9 16:55
//     */
//    public User login(String username, String password, HttpServletResponse response) {
//
//        //1、判断账号名或密码是否为空 空则抛出异常错误
//        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
//            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, null, Constants.LOGIN_FAIL, MessageUtils.message("not.null")));
//            throw new UserNotExistsException();
//        }
//
//        //2、判断密码长度是否合法（20>=password>=5）,不合法抛出异常
//        if (password.length() < UserConstants.PASSWORD_MIN_LENGTH || password.length() > UserConstants.PASSWORD_MAX_LENGTH) {
//            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, null, Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
//            throw new UserPasswordNotMatchException();
//        }
//
//        //3、判断账户名长度是否合法（20>=username>=2），不合法抛出异常
//        if (username.length() < UserConstants.USERNAME_MIN_LENGTH || username.length() > UserConstants.USERNAME_MAX_LENGTH) {
//            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, null, Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
//            throw new UserPasswordNotMatchException();
//        }
//
//        //4、查询用户
//        UserDeptRole u = userService.loginGetUser(username);
//
//        //5、查询用户是否存在
//        if (u == null) {
//            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, null, Constants.LOGIN_FAIL, MessageUtils.message("user.not.exists")));
//            throw new UserNotExistsException();
//        }
//        try {
//            u.getDept().setParentName(userService.selectcompany(u.getDept()).getDeptName());
//        } catch (Exception e) {
//
//        }
//        String mfrsId = null == u.getMfrsId() ? null : String.valueOf(u.getMfrsId());
//
//        //6、判断用户是否禁用
//        if (UserStatus.DISABLE.getCode().equals(u.getStatus())) {
//            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, mfrsId, Constants.LOGIN_FAIL, MessageUtils.message("user.blocked", u.getRemark())));
//            throw new UserBlockedException(u.getRemark());
//        }
//
//        //7、判断用户是否离职
//        if (UserStatus.QUIT.getCode().equals(u.getStatus())) {
//            throw new UserBlockedException("用户已离职");
//        }
//
//        // 8、校验密码
//        passwordService.validate(u, password);
//
//        Long accountId = u.getAccountId().longValue();
//
//        // 9、查询角色信息和菜单
//        Set<String> roles = roleService.selectRoleKeys(accountId);
//        Set<String> menus = menuService.selectPermsByAccountId(accountId);
//        if (null == roles || null == menus || (menus.size() == 0 && !username.equals("admin"))) {
//            throw new NoRoleException("权限不足，请联系管理员。");
//        }
//
//        // 10.jwt认证，写入缓存
//        final String token = JwtUtil.sign(u.getLoginName());
//        SecurityUtils.getSubject().login(new StatelessToken(token, true));
//
//        // 写入cookie
//        StatelessWebUtils.addCookiesForToken(response, token);
//
//        // 12、记录登录信息
//        recordLoginInfo(u);
//
//        // 13、写入登录日志
//        AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, mfrsId, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success")));
//
//        return u;
//    }
//
//    /**
//     * 记录登录信息
//     */
//    public void recordLoginInfo(User user) {
//        user.setLoginIp(ShiroAuthenticateUtils.getIp());
//        user.setLoginDate(DateUtils.getNowDate());
//        userService.updateUserInfo(user);
//    }
//}
