package com.cjl.basic.zone.project.space.home.controller;

import com.cjl.basic.zone.common.utils.security.ShiroAuthenticateUtils;
import com.cjl.basic.zone.framework.web.domain.AjaxResult;
import com.cjl.basic.zone.project.manage.user.domain.User;
import com.cjl.basic.zone.project.space.home.domain.ZSignIn;
import com.cjl.basic.zone.project.space.home.service.IZSignInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/home")
public class HomeController {
    @Autowired
    private IZSignInService izSignInService;

    /**
     * 空间首页
     */
    @GetMapping()
    public String index(ModelMap mmap) {
        User user = ShiroAuthenticateUtils.getUserByToken();
        mmap.put("user", user);
        return "space/home";
    }

    /**
     * 查询签到信息
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/getSignIn")
    public AjaxResult getSignIn() {
        Integer accountId = ShiroAuthenticateUtils.getAccountId();
        return AjaxResult.success(izSignInService.selectSignInfoList(accountId));
    }

    /**
     * 进行签到
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/signIn")
    public AjaxResult signInfo(ZSignIn zSignIn) {
        Integer accountId = ShiroAuthenticateUtils.getAccountId();
        zSignIn.setAccountId(accountId);
        return AjaxResult.success(izSignInService.insertSignInfo(zSignIn));
    }
}
