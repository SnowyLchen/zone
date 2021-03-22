package com.cjl.basic.zone.framework.shiro.jwt;

import com.cjl.basic.zone.common.utils.ServletUtils;
import com.cjl.basic.zone.framework.shiro.StatelessConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * 工具类
 *
 * @author hd_zhu
 */
@Component
public class StatelessWebUtils {
    private static final String XML_HTTP_REQUEST = "XMLHttpRequest";
    private static final String X_REQUESTED_WITH = "X-Requested-With";
    private static String domain;
    private static String path;
    private static Boolean httpOnly;
    private static Integer maxAge;

    @Value("${shiro.cookie.domain:}")
    public void setDomain(String domain) {
        StatelessWebUtils.domain = domain;
    }

    @Value("${shiro.cookie.path:/}")
    public void setPath(String path) {
        StatelessWebUtils.path = path;
    }

    @Value("${shiro.cookie.httpOnly:true}")
    public void setHttpOnly(Boolean httpOnly) {
        StatelessWebUtils.httpOnly = httpOnly;
    }

    @Value("${shiro.cookie.maxAge:30}")
    public void setMaxAge(Integer maxAge) {
        StatelessWebUtils.maxAge = maxAge;
    }

    /**
     * 获取cookies
     *
     * @param servletRequest request
     * @return Cookie[]
     */
    public static Cookie[] getCookiesV(@NonNull ServletRequest servletRequest) {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String cookie = request.getHeader("cookie");
        if (cookie == null) {
            return null;
        }
        return Arrays.stream(cookie.split("; "))
                .map(n -> {
                    String[] val = n.split("=");
                    Cookie c = new Cookie(val[0], val[1]);
                    return c;
                })
                .toArray(Cookie[]::new);
    }


    public static Cookie[] getCookies(@NonNull ServletRequest servletRequest) {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        return request.getCookies();
    }

    /**
     * 获取token
     *
     * @param servletRequest request
     * @return token字符串
     */
    public static String getToken(@NonNull ServletRequest servletRequest) {
        Cookie cookie = getCookieByName(servletRequest, StatelessConstants.ACCESS_TOKEN);
        if (cookie != null) {
            return cookie.getValue();
        }
        return null;
    }

    /**
     * 写入token
     *
     * @param request  ServletRequest
     * @param response ServletResponse
     * @param token    新的token
     */
    public static void updateCookiesForToken(@NonNull ServletRequest request, @NonNull ServletResponse response, @NonNull String token) {
        addCookiesForToken(response, token);
    }

    /**
     * 写入token
     *
     * @param token 新的token
     */
    public static void updateCookiesForToken(@NonNull String token) {
        updateCookiesForToken(null, ServletUtils.getResponse(), token);
    }

    /**
     * 新增token的cookies
     *
     * @param response ServletResponse
     * @param token    新的token
     */
    public static void addCookiesForToken(@NonNull ServletResponse response, @NonNull String token) {
        Cookie cookie = new Cookie(StatelessConstants.ACCESS_TOKEN, token);
        cookie.setPath(path);
        cookie.setHttpOnly(httpOnly);
        cookie.setMaxAge(Math.toIntExact(TimeUnit.DAYS.toSeconds(30)));
        ((HttpServletResponse) response).addCookie(cookie);
    }

    /**
     * 获取登录名
     *
     * @return 登录名
     */
    public static String getLoginNameByToken(@NonNull ServletRequest request) {
        String token = getToken(request);
        if (token == null) {
            return null;
        }
        return JwtUtil.getLoginNameByToken(token);
    }

    /**
     * 清除cookies
     *
     * @param request  request
     * @param response response
     */
    public static void clearCookies(@NonNull ServletRequest request, @NonNull ServletResponse response) {
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        HttpServletResponse servletResponse = (HttpServletResponse) response;
        Cookie[] cookies = servletRequest.getCookies();
        if (cookies == null) {
            return;
        }
        for (Cookie cookie : cookies) {
            cookie.setMaxAge(0);
            cookie.setPath(path);
            servletResponse.addCookie(cookie);
        }
    }

    /**
     * 判断当前请求是否为ajax请求
     *
     * @param request request
     * @return 如果是则返回true
     */
    public static boolean isAjaxRequest(@NonNull ServletRequest request) {
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        return XML_HTTP_REQUEST.equals(servletRequest.getHeader(X_REQUESTED_WITH));
    }

    /**
     * 获取cookie通过cookie的名字
     *
     * @param request    请求对象
     * @param cookieName cookie名
     * @return cookie的值 {@link Cookie}
     */
    public static Cookie getCookieByName(@NonNull ServletRequest request, @NonNull String cookieName) {
        Cookie[] cookies = ((HttpServletRequest) request).getCookies();
        if (cookies == null) {
            return null;
        }
        return Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals(cookieName))
                .findFirst()
                .orElse(null);
    }

    /**
     * 获取cookie值通过cookie的名字
     *
     * @param cookieName cookie名
     * @return cookie的值 {@link String}
     */
    public static String getCookieValueByName(@NonNull String cookieName) {
        return Optional.ofNullable(getCookieByName(ServletUtils.getRequest(), cookieName))
                .map(Cookie::getValue)
                .orElse(null);
    }

    /**
     * 获取cookie值通过cookie的名字
     *
     * @param request    请求对象
     * @param cookieName cookie名
     * @return cookie的值 {@link String}
     */
    public static String getCookieValueByName(@NonNull ServletRequest request, @NonNull String cookieName) {
        return Optional.ofNullable(getCookieByName(request, cookieName))
                .map(Cookie::getValue)
                .orElse(null);
    }
}
