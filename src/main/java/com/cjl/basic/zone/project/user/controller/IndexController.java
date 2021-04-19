package com.cjl.basic.zone.project.user.controller;

import com.cjl.basic.zone.common.utils.StringUtils;
import com.cjl.basic.zone.common.utils.security.ShiroAuthenticateUtils;
import com.cjl.basic.zone.framework.config.ZoneConfig;
import com.cjl.basic.zone.framework.web.controller.BaseController;
import com.cjl.basic.zone.project.user.domain.User;
import com.cjl.basic.zone.project.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * 首页 业务处理
 *
 * @author chen
 */
@Controller
public class IndexController extends BaseController {


    @Autowired
    private ZoneConfig zoneConfig;
    @Autowired
    private IUserService userService;

    /**
     * 系统首页
     */
    @GetMapping("/index")
    public String index(ModelMap mmap, HttpServletResponse response) {
        User user = ShiroAuthenticateUtils.getUserByToken();
        mmap.put("user", user);
        mmap.put("copyrightYear", zoneConfig.getCopyrightYear());
        //当前用户是游客，需要获取当前的用户状态
        final String visitor = "visitor";
//            mmap.put("userstate", userService.loginGetUser(user.getLoginName()));
        if (StringUtils.isEmpty(user.getHomeurl())) {
            return "error/404";
        }
        return user.getHomeurl();
    }

    /**
     * 系统介绍
     */
    @GetMapping("/system/main")
    public String main(ModelMap mmap) {
        mmap.put("version", zoneConfig.getVersion());
        return "main";
    }

    /**
     * 菜单
     */
    @GetMapping("/system/menu")
    @ResponseBody
    public String menu(ModelMap mmap) {

        return "main";
    }

    /**
     * 系统首页切换
     */
    @GetMapping("/header")
    public String console(ModelMap mmap) {
        return "space/header";
    }

    /**
     * 系统首页切换
     */
    @GetMapping("/admin/header")
    public String admin(ModelMap mmap) {
        return "header";
    }

    /**
     * 基本资料
     */
    @GetMapping("/profiles")
    public String profiles(ModelMap mmap) {
        return "system/person";
    }
}
