package com.cjl.basic.zone.framework.web.exception;

import com.cjl.basic.zone.common.exception.DemoModeException;
import com.cjl.basic.zone.common.utils.security.PermissionUtils;
import com.cjl.basic.zone.framework.web.domain.AjaxResult;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * 自定义异常处理器
 *
 * @author chen
 */
@RestControllerAdvice
public class DefaultExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(DefaultExceptionHandler.class);

    /**
     * 权限校验失败
     */
    @ExceptionHandler(AuthorizationException.class)
    public AjaxResult handleAuthorizationException(AuthorizationException e) {
        log.error(e.getMessage(), e);
        return AjaxResult.error(PermissionUtils.getMsg(e.getMessage()));
    }

    /**
     * 请求方式不支持
     */
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public AjaxResult handleException(HttpRequestMethodNotSupportedException e) {
        log.error(e.getMessage(), e);
        return AjaxResult.error("不支持' " + e.getMethod() + "'请求");
    }

    /**
     * 拦截未知的运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    public AjaxResult notFount(HttpServletRequest request, RuntimeException e) {
        log.error("请求接口异常" + request.getRequestURI(), e);
        return AjaxResult.error(e.getMessage());
    }

    /**
     * 系统异常
     */
    @ExceptionHandler(Exception.class)
    public AjaxResult handleException(Exception e) {
        log.error(e.getMessage(), e);
        return AjaxResult.error("服务器错误，请联系管理员");
    }

    /**
     * 权限异常
     */
    @ExceptionHandler(UnauthorizedException.class)
    public AjaxResult UnauthorizedException(HttpServletRequest request, Exception e) {
        log.error("请求接口异常" + request.getRequestURI(), e);
        return AjaxResult.error(403, "您的权限不足,请联系管理员");
    }

    /**
     * 演示模式异常
     */
    @ExceptionHandler(DemoModeException.class)
    public AjaxResult demoModeException(DemoModeException e) {
        return AjaxResult.error("演示模式，不允许操作");
    }

}
