package com.cjl.basic.zone.framework.shiro.realm;

import com.cjl.basic.zone.common.utils.ServletUtils;
import com.cjl.basic.zone.common.utils.SpringRedisUtil;
import com.cjl.basic.zone.framework.shiro.StatelessConstants;
import com.cjl.basic.zone.framework.shiro.jwt.JwtUtil;
import com.cjl.basic.zone.framework.shiro.jwt.StatelessToken;
import com.cjl.basic.zone.framework.shiro.jwt.StatelessWebUtils;
import com.cjl.basic.zone.project.manage.user.domain.User;
import com.cjl.basic.zone.project.manage.user.service.IUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.TimeUnit;

/**
 * 无状态的Realm
 *
 * @author chen
 */
public class StatelessRealm extends AuthorizingRealm {
    private static final Logger logger = LoggerFactory.getLogger(StatelessRealm.class);
    //    @Autowired
//    private IMenuService menuService;
//    @Autowired
//    private IRoleService roleService;
    @Autowired
    private IUserService userService;
    @Autowired
    private SpringRedisUtil kvSpringRedisUtil;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof StatelessToken;
    }

    /**
     * 写入授权信息
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        final User user = (User) principalCollection.getPrimaryPrincipal();
        final Long accountId = user.getAccountId().longValue();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        if (user.isAdmin()) {
            authorizationInfo.addRole("admin");
            authorizationInfo.addStringPermission("*:*:*");
        } else {
//            Set<String> roles = roleService.selectRoleKeys(accountId);
//            roles.stream().filter(s -> s.contains("zg_role"))
//                    .findAny()
//                    .map(s -> "zg_role")
//                    .ifPresent(roles::add);
//            authorizationInfo.setRoles(roles);
//            authorizationInfo.setStringPermissions(menuService.selectPermsByAccountId(accountId));
        }
//        logger.info("开启授权信息，写入用户权限。");
        return authorizationInfo;
    }

    /**
     * 认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        final StatelessToken statelessToken = (StatelessToken) authenticationToken;
        String token = (String) statelessToken.getCredentials();
        User user;
        if (statelessToken.isLoginHandle()) {
            user = userService.loginGetUser(JwtUtil.getLoginNameByToken(token));
//             写入刷新token,缓存存在30天
            kvSpringRedisUtil.set(getWRefreshKey(token), token, TimeUnit.DAYS.toSeconds(30));
        } else {
            if (token == null) {
                throw new UnknownAccountException(StatelessConstants.JWT_EXPIRE_MSG);
            }

//             如果刷新token失效，则直接踢下线。这个token用于强制废除access_token
            final String refreshToken = (String) kvSpringRedisUtil.get(getRRefreshKey(token));
            if (refreshToken == null) {
                throw new UnknownAccountException(StatelessConstants.JWT_EXPIRE_MSG);
            }

//
            // 如果token已过期，则颁发一个新的token
            // 此处可能存在一个安全漏洞，老的token过期后还可以使用一次
            if (!JwtUtil.verify(token)) {
                final String loginName = JwtUtil.getLoginNameByToken(refreshToken);
                String newToken = JwtUtil.sign(loginName);
                // 更新刷新refreshToken的key
                kvSpringRedisUtil.rename(getRRefreshKey(token), getWRefreshKey(newToken));
                // 重置token
                token = newToken;
                // 续签token
                StatelessWebUtils.updateCookiesForToken(token);
            }

            user = userService.loginGetUser(JwtUtil.getLoginNameByToken(token));
        }

        return new SimpleAuthenticationInfo(user, token, getName());
    }

    /**
     * 认证缓存的key
     *
     * @param token token
     * @return
     */
    @Override
    protected Object getAuthenticationCacheKey(AuthenticationToken token) {
        final String sToken = (String) token.getCredentials();
        final String loginName = JwtUtil.getLoginNameByToken(sToken);
        return loginName + ":" + StatelessConstants.ACCESS_TOKEN + ":" + sToken;
    }

    @Override
    protected Object getAuthorizationCacheKey(PrincipalCollection principals) {
        final User user = (User) principals.getPrimaryPrincipal();
//        final String loginName = user.getLoginName();
        final String loginName = user.getLoginName();
        return loginName + ":" + StatelessConstants.ACCESS_AUTHORIZED;
    }

    protected String getWRefreshKey(String token) {
        return StatelessConstants.SHIRO_PREFIX + JwtUtil.getLoginNameByToken(token) + ":" + StatelessConstants.REFRESH_TOKEN + ":" + token;
    }

    protected String getRRefreshKey(String token) {
        return kvSpringRedisUtil.keys2(StatelessConstants.SHIRO_PREFIX + "*:" + StatelessConstants.REFRESH_TOKEN + ":" + token);
    }

    @Override
    protected void clearCache(PrincipalCollection principals) {
        final String token = StatelessWebUtils.getCookieValueByName(ServletUtils.getRequest(), StatelessConstants.ACCESS_TOKEN);
        kvSpringRedisUtil.delete(getRRefreshKey(token));
        super.clearCache(principals);
    }


    @Override
    protected void clearCachedAuthenticationInfo(PrincipalCollection principals) {
        this.getAuthenticationCache().remove(getAuthenticationCacheKey(new StatelessToken(StatelessWebUtils.getCookieValueByName(StatelessConstants.ACCESS_TOKEN))));
    }
}
