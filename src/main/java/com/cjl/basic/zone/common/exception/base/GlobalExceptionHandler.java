package com.cjl.basic.zone.common.exception.base;

import com.cjl.basic.zone.common.utils.security.PermissionUtils;
import com.cjl.basic.zone.framework.shiro.jwt.StatelessWebUtils;
import com.cjl.basic.zone.framework.web.domain.AjaxResult;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * 全局异常简单处理
 *
 * @author zhuru
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private final static Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(UnauthorizedException.class)
    public String unauthorizedExceptionHandler(HttpServletRequest request, HttpServletResponse response, UnauthorizedException e) {
        log.error("权限不足", e);
        if (StatelessWebUtils.isAjaxRequest(request)) {
            outAjaxResult(response, PermissionUtils.getMsg(e.getMessage()));
            return null;
        }
        return "/error/unauth";
    }

    @ExceptionHandler(UnknownAccountException.class)
    public String unauthorizedExceptionHandler() {
        return "/login";
    }

    @ExceptionHandler(Exception.class)
    public Object exceptionHandle(HttpServletRequest request, HttpServletResponse response, Exception e) {
        log.error("系统异常", e);
        if (StatelessWebUtils.isAjaxRequest(request)) {
            outAjaxResult(response, "服务器错误，请联系管理员");
            return null;
        }
        return "/error/500";
    }

    /**
     * 系统异常
     */
    @ExceptionHandler(BaseException.class)
    @ResponseBody
    public Object handleBaseException(HttpServletRequest request, HttpServletResponse response, BaseException e) {
        if (StatelessWebUtils.isAjaxRequest(request)) {
            outAjaxResult(response, e.getMessage());
            return null;
        }
        return AjaxResult.error(e.getMessage());
    }

    /**
     * 请求方式不支持
     */
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    @ResponseBody
    public Object handleException(HttpRequestMethodNotSupportedException e) {
        log.error(e.getMessage(), e);
        return AjaxResult.error("不支持' " + e.getMethod() + "'请求");
    }

    /**
     * 线程锁异常
     */
    @ExceptionHandler({InterruptedException.class})
    @ResponseBody
    public Object handleInterruptedException(InterruptedException e) {
        log.error(e.getMessage(), e);
        return AjaxResult.error("系统忙，请稍后再试");
    }

    private void outAjaxResult(HttpServletResponse response, String msg) {
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            PrintWriter writer = response.getWriter();
            //具体操作
            writer.write(AjaxResult.error(msg).toJSON());
            //
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
