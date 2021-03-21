package com.cjl.basic.zone.framework.shiro;

/**
 * @author hd_zhu
 */
public class StatelessConstants {

    /**
     * 认证缓存名称
     */
    public static final String ACCESS_TOKEN = "access_token";

    /**
     * 刷新缓存名称
     */
    public static final String REFRESH_TOKEN = "refresh_token";

    /**
     * 授权缓存名称
     */
    public static final String ACCESS_AUTHORIZED = "access_authorized";

    /**
     * shiro缓存根目录
     */
    public static final String SHIRO_PREFIX = "shiro-cache:";

    /**
     * shiro认证默认缓存
     */
    public static final String STATELESS_CACHE = "stateless_cache";

    /**
     * JWT过期时间15分钟
     */
    public static final Long JWT_EXPIRE = 15L;

    /**
     * JWT作者
     */
    public static final String JWT_IS_UER = "www.hdzhenergy.cn";

    /**
     * JWT默认字段，最初是阶段JWT中只保留该默认字段，不存放敏感信息
     */
    public static final String JWT_DEFAULT_CLAIM = "loginName";

    /**
     * JWT密码
     */
    public static final String JWT_SECRET = "!QAZ2wsx3edc$RFV&()**(&*(&^&*(&X^YHN(";

    /**
     * JWT过期提示
     */
    public static final String JWT_EXPIRE_MSG = "登录信息已过期，请重新登录。";
}
