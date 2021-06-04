package com.cjl.basic.zone.project.manage.user.controller;

import com.cjl.basic.zone.common.utils.StringUtils;
import com.cjl.basic.zone.common.utils.security.ShiroAuthenticateUtils;
import com.cjl.basic.zone.framework.config.ZoneConfig;
import com.cjl.basic.zone.framework.web.controller.BaseController;
import com.cjl.basic.zone.framework.web.domain.AjaxResult;
import com.cjl.basic.zone.project.manage.menu.domain.ZMenuTree;
import com.cjl.basic.zone.project.manage.menu.service.IMenuService;
import com.cjl.basic.zone.project.manage.user.domain.User;
import com.cjl.basic.zone.project.manage.user.domain.Visitor;
import com.cjl.basic.zone.project.manage.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

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
    private IMenuService menuService;

    @Autowired
    private IUserService userService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Value("${application.cache.prefix}")
    private String prefix;


    /**
     * 系统首页
     */
    @GetMapping("/index")
    public String index(ModelMap mmap, HttpServletResponse response, String isVisitor) {
        User user = userService.selectUserById(ShiroAuthenticateUtils.getAccountId());
        mmap.put("user", user);
        if (StringUtils.isNotNull(isVisitor)) {
            redisTemplate.opsForValue().set(prefix + "visitor_" + isVisitor, isVisitor);
            mmap.put("isVisitor", new Visitor() {{
                setCurrentAccount(userService.selectUserById(Integer.valueOf(isVisitor.split("_")[0])));
                setToAccount(userService.selectUserById(Integer.valueOf(isVisitor.split("_")[1])));
            }});
        }
        mmap.put("accountId", ShiroAuthenticateUtils.getAccountId());
        mmap.put("copyrightYear", zoneConfig.getCopyrightYear());
        //当前用户是游客，需要获取当前的用户状态
        if (StringUtils.isEmpty(user.getHomeUrl())) {
            return "error/404";
        }
        return user.getHomeUrl();
    }

    /**
     * 系统介绍
     */
    @GetMapping("/homePage")
    public String main(ModelMap mmap) {
        return "console/console1";
    }

    /**
     * 菜单
     */
    @GetMapping("/system/menu")
    @ResponseBody
    public List<ZMenuTree> menu(ModelMap mmap) {
        return menuService.selectUserMenu(ShiroAuthenticateUtils.getAccountId());
    }

    /**
     * 系统首页切换
     */
    @GetMapping("/header")
    public String console(ModelMap mmap) {
        User user = ShiroAuthenticateUtils.getUserByToken();
        mmap.put("user", user);
        return "space/header";
    }

    /**
     * 系统首页切换
     */
    @GetMapping("/adminHeader")
    public String admin(ModelMap mmap) {
        User user = ShiroAuthenticateUtils.getUserByToken();
        mmap.put("user", user);
        return "manage/header";
    }

    /**
     * 基本资料
     */
    @GetMapping("/profiles")
    public String profiles(ModelMap mmap) {
        String loginName = ShiroAuthenticateUtils.getUserByToken().getLoginName();
        if (loginName != null && !loginName.equals("admin")) {
            User user = userService.selectUserById(ShiroAuthenticateUtils.getAccountId());
            mmap.put("user", user);
            return "space/profiles";
        }
        return "system/person";
    }

    /**
     * 空白页
     */
    @GetMapping("/space")
    public String space(ModelMap mmap) {
        return "system/space";
    }

    /**
     * 音乐播放器
     */
    @GetMapping("/music")
    public String music(ModelMap mmap) {
        return "space/music";
    }

    /**
     * 返回我的空间
     */
    @ResponseBody
    @PostMapping("/backToSpace")
    public AjaxResult backToSpace(String key, String url) {
//        // 若是刷新，则不清除
//        if (StringUtils.isNotNull(url) && url.contains("isVisitor")) {
//            return toAjax(1);
//        }
        if (StringUtils.isNotNull(key)) {
            redisTemplate.delete(prefix + key);
        }
        return toAjax(1);
    }
}
