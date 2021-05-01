package com.cjl.basic.zone.project.manage.user.controller;

import com.cjl.basic.zone.common.RunEnvironment;
import com.cjl.basic.zone.framework.web.controller.BaseController;
import com.cjl.basic.zone.framework.web.domain.AjaxResult;
import com.cjl.basic.zone.project.manage.user.domain.User;
import com.cjl.basic.zone.project.manage.user.service.IRegisterService;
import com.cjl.basic.zone.project.manage.user.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 注册
 *
 * @author chen
 */
@Controller
@Api(tags = "注册接口")
public class RegisterController extends BaseController {
    @Autowired
    private IRegisterService registerService;
    @Autowired
    private IUserService userService;
    @Autowired
    private RunEnvironment runEnvironment;

    @GetMapping("/goRegister")
    @ApiIgnore
    public String goRegister(ModelMap map) {
        //1. 注册页面URL
//        map.put("smsUrl", AliyunTemplateType.REGISTER.getSendUrl());
        return "register";
    }

    @PostMapping("/register")
    @ResponseBody
    @ApiOperation(value = "注册")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", defaultValue = "admin", required = true),
            @ApiImplicitParam(name = "password", value = "密码", defaultValue = "123456", required = true),
            @ApiImplicitParam(name = "email", value = "邮箱", defaultValue = "123456@163.com", required = true),
            @ApiImplicitParam(name = "phonenumber", value = "手机号", defaultValue = "12345678910", required = true),
            @ApiImplicitParam(name = "sex", value = "性别", defaultValue = "1", required = true),
    })
    public AjaxResult register(User user) {
//        if (!runEnvironment.isDev() && !runEnvironment.isTest() && !aliyunService.checkSmsVerIfication(user.getPhonenumber(), user.getYanzheng())) {
//            return AjaxResult.error("验证码错误");
//        }
        return toAjax(registerService.registerUser(user));
    }

    /**
     * 校验账号名
     */
    @PostMapping("/checkLoginNameUnique")
    @ResponseBody
    @ApiOperation(value = "校验账号名")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "loginName", name = "登录账号"),
    })
    public String checkLoginNameUnique(User user) {
        return userService.checkLoginNameUnique(user.getLoginName());
    }

    /**
     * 校验手机号
     */
    @ApiOperation(value = "校验手机号")
    @ApiImplicitParam(name = "phonenumber", value = "手机号")
    @PostMapping("/checkPhoneUnique")
    @ResponseBody
    public String checkPhoneUnique(User user) {
        return userService.checkPhoneUnique(user);
    }
}
