package com.cjl.basic.zone.framework.manager.factory;

import com.cjl.basic.zone.common.constant.Constants;
import com.cjl.basic.zone.common.utils.AddressUtils;
import com.cjl.basic.zone.common.utils.LogUtils;
import com.cjl.basic.zone.common.utils.ServletUtils;
import com.cjl.basic.zone.common.utils.security.ShiroAuthenticateUtils;
import com.cjl.basic.zone.common.utils.spring.SpringUtils;
import com.cjl.basic.zone.project.loginLog.domain.LoginLog;
import com.cjl.basic.zone.project.loginLog.service.LogServiceImpl;
import com.cjl.basic.zone.project.operlog.domain.OperLog;
import com.cjl.basic.zone.project.operlog.service.IOperLogService;
import com.cjl.basic.zone.project.user.domain.Logininfor;
import com.cjl.basic.zone.utils.dateutils.DateUtils;
import eu.bitwalker.useragentutils.UserAgent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.TimerTask;

/**
 * 异步工厂（产生任务用）
 *
 * @author liuhulu
 */
public class AsyncFactory {
    private static final Logger mfrs_user_logger = LoggerFactory.getLogger("sys-user");

    /**
     * 操作日志记录
     *
     * @param operLog 操作日志信息
     * @return 任务task
     */
    public static TimerTask recordOper(final OperLog operLog) {
        return new TimerTask() {
            @Override
            public void run() {
                // todo 定时器有问题
                // 开始执行操作日志
                mfrs_user_logger.debug("开始记录执行操作日志，时间：【{}】，方法: 【{}】", DateUtils.getDate(), operLog.getMethod());
                operLog.setOperLocation(AddressUtils.getRealAddressByIP(operLog.getOperIp()));
                SpringUtils.getBean(IOperLogService.class).insertOperlog(operLog);
            }
        };
    }

    /**
     * 记录登陆信息
     *
     * @param username 账号名
     * @param status   状态
     * @param message  消息
     * @param args     列表
     * @return 任务task
     */
    public static TimerTask recordLogininfor(final String username,final Integer accountId, final String status, final String message, final Object... args) {
        final UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
        final String ip = ShiroAuthenticateUtils.getIp();
        return new TimerTask() {
            @Override
            public void run() {
                StringBuilder s = new StringBuilder();
                s.append(LogUtils.getBlock(ip));
                s.append(AddressUtils.getRealAddressByIP(ip));
                s.append(LogUtils.getBlock(username));
                s.append(LogUtils.getBlock(status));
                s.append(LogUtils.getBlock(message));
                // 打印信息到日志
                mfrs_user_logger.info(s.toString(), args);
                // 获取客户端操作系统
                String os = userAgent.getOperatingSystem().getName();
                // 获取客户端浏览器
                String browser = userAgent.getBrowser().getName();
                // 封装对象
                LoginLog log=new LoginLog();
                log.setLoginName(username);
                log.setIp(ip);
                log.setAccountId(accountId);
                log.setLoginLocation(AddressUtils.getRealAddressByIP(ip));
                log.setBrowser(browser);
                log.setOs(os);
                log.setData(message);
                log.setAction("login");
                // 日志状态
                if (Constants.LOGIN_SUCCESS.equals(status) || Constants.LOGOUT.equals(status)) {
                    log.setStatus(Constants.SUCCESS);
                } else if (Constants.LOGIN_FAIL.equals(status)) {
                    log.setStatus(Constants.FAIL);
                }
                // 插入数据
                try {
                    SpringUtils.getBean(LogServiceImpl.class).addLog(log);
                } catch (Exception e) {
                    mfrs_user_logger.error("写入一直异常", e);
                }
            }
        };
    }
}
