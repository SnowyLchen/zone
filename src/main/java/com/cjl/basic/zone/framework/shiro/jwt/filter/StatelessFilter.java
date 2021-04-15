package com.cjl.basic.zone.framework.shiro.jwt.filter;

import com.cjl.basic.zone.common.utils.ServletUtils;
import com.cjl.basic.zone.framework.shiro.StatelessConstants;
import com.cjl.basic.zone.framework.shiro.jwt.StatelessToken;
import com.cjl.basic.zone.framework.shiro.jwt.StatelessWebUtils;
import com.cjl.basic.zone.framework.web.domain.AjaxResult;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.util.AntPathMatcher;
import org.apache.shiro.util.PatternMatcher;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.MessageFormat;

/**
 * 无状态过滤器，这里由jwt实现
 *
 * @author chen
 */
public class StatelessFilter extends AccessControlFilter {
    private Logger logger = LoggerFactory.getLogger(StatelessFilter.class);

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        // 无状态登录，跳过此步校验
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        try {
            executeLogin(request, response);
        } catch (AuthenticationException e) {
            logger.info("认证失败，退出登录");
            getSubject(request, response).logout();
            redirectToLogin(request, response);
            return false;
        } catch (Exception e) {
            logger.error("登陆时发生异常", e);
            return false;
        }
        return true;
    }

    /**
     * 登录校验
     */
    protected boolean executeLogin(ServletRequest request, ServletResponse response) {
        final String token = StatelessWebUtils.getCookieValueByName(request, StatelessConstants.ACCESS_TOKEN);
        getSubject(request, response).login(new StatelessToken(token));
        return true;
    }


    /**
     * 跳转到登录页
     *
     * @param request
     * @param response
     */
    @Override
    protected void redirectToLogin(ServletRequest request, ServletResponse response) {
        HttpServletRequest httpServletRequest = (ShiroHttpServletRequest) request;
        final String uri = httpServletRequest.getRequestURI();
        PatternMatcher matcher = new AntPathMatcher();
        String systemUri = "/system/**", businessUri = "/business/**";
        if (matcher.matches(systemUri, uri) || matcher.matches(businessUri, uri)) {
            ServletUtils.renderHtml(
                    (HttpServletResponse) response,
                    StatelessWebUtils.isAjaxRequest(request)
                            ? AjaxResult.unknownAccount().toJSON()
                            : MessageFormat.format("<script>top.redirectToLogin(''{0}'');</script>", StatelessConstants.JWT_EXPIRE_MSG)
            );
        } else {
            ServletUtils.renderHtml(
                    (HttpServletResponse) response,
                    StatelessWebUtils.isAjaxRequest(request)
                            ? AjaxResult.unknownAccount().toJSON()
                            : "<script>top.location.href = '/login';</script>"
            );
        }
    }

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }
}
