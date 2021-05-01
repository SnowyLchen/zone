package com.cjl.basic.zone.framework.shiro.web.filter;

import com.cjl.basic.zone.common.constant.Constants;
import com.cjl.basic.zone.common.utils.MessageUtils;
import com.cjl.basic.zone.common.utils.security.ShiroAuthenticateUtils;
import com.cjl.basic.zone.framework.manager.AsyncManager;
import com.cjl.basic.zone.framework.manager.factory.AsyncFactory;
import com.cjl.basic.zone.framework.shiro.jwt.StatelessWebUtils;
import com.cjl.basic.zone.project.manage.user.domain.User;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 退出过滤器
 *
 * @author chen
 */
public class StatelessLogoutFilter extends LogoutFilter {
    private static final Logger logger = LoggerFactory.getLogger(StatelessLogoutFilter.class);

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        try {
            final Subject subject = getSubject(request, response);
            // 获取用户信息
            final PrincipalCollection collection = subject.getPreviousPrincipals();
            // shiro退出登录
            subject.logout();
            if (collection != null) {
                final User user = (User) collection.getPrimaryPrincipal();
                // 写入日志
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(
                        user.getLoginName(), user.getAccountId(), Constants.LOGOUT, MessageUtils.message("user.logout.success")
                ));
            }
        } catch (Exception e) {
            logger.error("清除缓存异常");
        } finally {
            // 清除缓存
            ShiroAuthenticateUtils.clear();
            StatelessWebUtils.clearCookies(request, response);
            issueRedirect(request, response, getRedirectUrl());
        }
        return false;
    }
}
