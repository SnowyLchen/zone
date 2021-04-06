package com.cjl.basic.zone.project.user.controller;

import com.cjl.basic.zone.common.exception.user.*;
import com.cjl.basic.zone.common.utils.ModelMapUtils;
import com.cjl.basic.zone.common.utils.security.PermissionUtils;
import com.cjl.basic.zone.common.utils.security.ShiroAuthenticateUtils;
import com.cjl.basic.zone.framework.web.controller.BaseController;
import com.cjl.basic.zone.framework.web.domain.AjaxResult;
import com.cjl.basic.zone.project.user.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录验证
 *
 * @author wangsen
 */
@Controller
public class LoginController extends BaseController {
    @Value("${spring.profiles.active}")
    private String profile;

    @Autowired
    private LoginService loginService;

    @GetMapping("/login")
    public String login(HttpServletRequest request, ModelMap modelMap) {
        ModelMapUtils.addAndReplaceEmoji(modelMap, "isPrivate", !PermissionUtils.isPrivate(profile));
        // 判断token是否过期，如果没有过期，则直接跳转到index，否则跳转到login
        return ShiroAuthenticateUtils.isAuthenticated() ? "redirect:/index" : "login";
    }

    @ResponseBody
    @GetMapping("/loginzg")
    public AjaxResult loginzg(HttpServletRequest request, ModelMap modelMap) {
        // 判断token是否过期，如果没有过期，则直接跳转到index，否则跳转到login
        if (!ShiroAuthenticateUtils.isAuthenticated()) {
            return AjaxResult.error(401, "登录过期");
        }
        return AjaxResult.success("登录成功");
    }

    @PostMapping("/login")
    @ResponseBody
    public AjaxResult ajaxLogin(HttpServletResponse response, String username, String password, String validateCode) {
        try {
            return AjaxResult.success(loginService.login(username, password, response));
        } catch (UserPasswordNotMatchException | UserNotExistsException | UserBlockedException | NoRoleException | UserPasswordRetryLimitExceedException e) {
            return error(e.getMessage());
        }
    }
//
//    @PostMapping("/toForm")
//    @ResponseBody
//    public AjaxResult toForm() {
//        User sysUser = ShiroAuthenticateUtils.getUserByToken();
//        if (Objects.nonNull(sysUser.getMfrsId())) {
//            success();
//        }
//        return error();
//    }
//
//    /**
//     * 跳转找回密码
//     */
//    @GetMapping("/skipResetPwd/{skip}")
//    @ApiIgnore
//    public String resetPwd(@PathVariable String skip, ModelMap map) {
//        if (FIRST.equals(skip)) {
//            map.put("smsUrl", AliyunTemplateType.RESETPWD.getSendUrl());
//            return "system/resetPwd/resetPwd";
//        } else if (SECOND.equals(skip)) {
//            return "system/resetPwd/resetPwd1";
//        } else if (THIRD.equals(skip)) {
//            return "system/resetPwd/resetPwd2";
//        } else {
//            return "404";
//        }
//    }
//
//    /**
//     * 修改密码
//     *
//     * @param mobile 手机号
//     */
//    @PostMapping("/resetPaw")
//    @ResponseBody
//    public AjaxResult resetPaw(String mobile, String password, String skip, String code, String reset) throws NoHttpResponseBodyException {
//        if (StringUtils.isEmpty(mobile)) {
//            return AjaxResult.error("手机号不能为空");
//        }
//        User user = userService.selectUserByPhoneNumber(mobile);
//        if (FIRST.equals(skip)) {
//            if (user == null) {
//                return AjaxResult.error("您的手机号不存在，请先注册");
//            }
//            if ("1".equals(user.getStatus())) {
//                return AjaxResult.error("您的账号已经停用，请联系管理员");
//            }
//            if (!runEnvironment.isDev()&&!aliyunService.checkSmsVerIfication(mobile, code)) {
//                return AjaxResult.error("验证码错误");
//            }
//        } else {
//            if (user == null) {
//                return AjaxResult.error("您的手机号不存在，请先注册");
//            }
//            User par = new User();
//            par.randomSalt();
//            par.setAccountId(user.getAccountId());
//            //修改密码前判断是否要校验和原密码相同，不相同则允许修改 1：校验
//            if (StringUtils.isNotNull(reset) && reset.equals("1")) {
//                par.setPassword(password);
//                String temp = userService.checkResetPwdUnique(par);
//                //返回 1：不唯一（密码重复） 0 ：唯一（密码未重复）
//                if ("1".equals(temp)) {
//                    return AjaxResult.error("密码不能与原密码相同");
//                } else {
//                    return AjaxResult.success();
//                }
//            }
//            par.setPassword(passwordService.encryptPassword(
//                    user.getLoginName(), password, par.getSalt()));
//            return toAjax(userService.updateUserPassword(par));
//        }
//        return AjaxResult.success();
//    }
//
//    @GetMapping("/unauth")
//    public String unauth() {
//        return "/error/unauth";
//    }
//
//    /**
//     * 校验手机号是否存在，若存在返回
//     */
//    @PostMapping("/checkMobileUnique")
//    @ResponseBody
//    public boolean checkMobileUnique(String phonenumber) {
//        User user = userService.selectUserByPhoneNumber(phonenumber);
//        if (user != null) {
//            return true;
//        }
//        return false;
//    }
//

}
