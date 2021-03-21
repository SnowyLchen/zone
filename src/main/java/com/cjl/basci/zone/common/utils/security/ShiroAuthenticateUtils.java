package com.cjl.basci.zone.common.utils.security;

import com.cjl.basci.zone.common.utils.IpUtils;
import com.cjl.basci.zone.common.utils.ServletUtils;
import com.cjl.basci.zone.framework.shiro.StatelessConstants;
import com.cjl.basci.zone.framework.shiro.cache.ShiroRedisCache;
import com.cjl.basci.zone.framework.shiro.cache.ShiroRedisCacheManager;
import com.cjl.basci.zone.framework.shiro.jwt.JwtUtil;
import com.cjl.basci.zone.framework.shiro.jwt.StatelessWebUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * shiro 工具类
 * 获取基本的用户信息
 * 添加在线踢人，清理权限等Api
 *
 * @author hd_zhu
 */
@Component
public class ShiroAuthenticateUtils {
    private static ShiroRedisCache shiroRedisCache;

    /**
     * 在构造函数中注入参数
     */
    @Autowired
    public void init(ShiroRedisCacheManager shiroRedisCacheManager) {
        ShiroAuthenticateUtils.shiroRedisCache = shiroRedisCacheManager.getCache(null);
    }


    /**
     * 认证的key
     *
     * @return
     */
    protected static String getAuthenticationCacheKey(String token) {
        final String loginName = JwtUtil.getLoginNameByToken(token);
        return loginName + ":" + StatelessConstants.ACCESS_TOKEN;
    }

    /**
     * 权限的key
     *
     * @return
     */
    protected static String getAuthorizationCacheKey(String token) {
        final String loginName = JwtUtil.getLoginNameByToken(token);
        return loginName + ":" + StatelessConstants.ACCESS_AUTHORIZED;
    }


    /**
     * 获取Subject对象
     *
     * @return {@link Subject}
     */
    private static Subject getSubject() {
        return SecurityUtils.getSubject();
    }


    /**
     * 清除认证权限
     */
    public static void clearAuthenticationInfo() {
        final String sToken = StatelessWebUtils.getCookieValueByName(StatelessConstants.ACCESS_TOKEN);
        shiroRedisCache.remove(getAuthenticationCacheKey(sToken));
    }

    /**
     * 清除权限信息
     */
    public static void clearAuthorizationInfo() {
        final String sToken = StatelessWebUtils.getCookieValueByName(StatelessConstants.ACCESS_TOKEN);
        shiroRedisCache.remove(getAuthorizationCacheKey(sToken));
    }

    /**
     * 清除所有缓存
     */
    public static void clear() {
        final String token = StatelessWebUtils.getCookieValueByName(ServletUtils.getRequest(), StatelessConstants.ACCESS_TOKEN);
        shiroRedisCache.remove(JwtUtil.getLoginNameByToken(token) + ":" + StatelessConstants.ACCESS_TOKEN + ":" + token);
        shiroRedisCache.remove(JwtUtil.getLoginNameByToken(token) + ":" + StatelessConstants.REFRESH_TOKEN + ":" + token);
    }

    /**
     * 清除所有缓存
     *
     * @param loginName 用户loginName
     */
    public static void clear(String loginName) {
        shiroRedisCache.remove(loginName + "*");
    }

//    /**
//     * 清除所有缓存
//     *
//     * @param accoutIds 用户ids
//     */
//    public static void clearByAccountIds(Integer... accoutIds) {
//        for (Integer accoutId : accoutIds) {
//            String loginName = SpringUtils.getBean(IUserService.class)
//                    .selectUserByIdDel(accoutId.longValue())
//                    .getLoginName();
//            if (loginName != null) {
//                clear(loginName);
//            }
//        }
//    }

//    /**
//     * 清除所有缓存
//     *
//     * @param roleId 角色id
//     */
//    public static void clearByRoleId(Integer roleId) {
//        SpringUtils.getBean(SpringRedisUtil.class)
//                .deleteLike(SpringUtils.getBean(IUserService.class)
//                        .selectLoginNameByRoleId(roleId)
//                        .stream()
//                        .map(s -> StatelessConstants.SHIRO_PREFIX + s)
//                        .collect(Collectors.toList())
//                );
//    }

//    /**
//     * 清除所有缓存
//     *
//     * @param menuId 菜单id
//     */
//    public static void clearByMenuId(Integer menuId) {
//        SpringUtils.getBean(SpringRedisUtil.class)
//                .deleteLike(SpringUtils.getBean(IUserService.class)
//                        .selectLoginNameByMenuId(menuId)
//                        .stream()
//                        .map(s -> StatelessConstants.SHIRO_PREFIX + s)
//                        .collect(Collectors.toList())
//                );
//    }

    /**
     * 受否授权成功
     */
    public static boolean isAuthenticated() {
        return getSubject().isAuthenticated();
    }


//    /**
//     * 通过Token 获取用户信息
//     *
//     * @return User
//     */
//    public static User getUserByToken() {
//        return (User) getSubject().getPrincipal();
//    }

//    /**
//     * 获取当前账号ID
//     *
//     * @return 账号ID
//     */
//    public static Integer getAccountId() {
//        return Math.toIntExact(getUserByToken().getAccountId());
//    }
//
//    /**
//     * 获取当前登录账号
//     *
//     * @return 账号
//     */
//    public static String getLoginName() {
//        if (getUserByToken() == null) {
//            return "";
//        }
//        return getUserByToken().getLoginName();
//    }
//
//    /**
//     * 获取企业
//     */
//    public static Integer getMfrsId() {
//        try {
//            return Objects.requireNonNull(getUserByToken().getMfrsId());
//        } catch (NullPointerException e) {
//            return null;
//        }
//    }

    /**
     * 获取ip
     */
    public static String getIp() {
        return IpUtils.getIpAddr(ServletUtils.getRequest());
    }
}
