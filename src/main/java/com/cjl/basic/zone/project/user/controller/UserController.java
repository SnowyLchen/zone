package com.cjl.basic.zone.project.user.controller;

import com.cjl.basic.zone.framework.web.controller.BaseController;
import com.cjl.basic.zone.framework.web.page.TableDataInfo;
import com.cjl.basic.zone.project.role.domain.ZRole;
import com.cjl.basic.zone.project.user.domain.User;
import com.cjl.basic.zone.project.user.service.IUserService;
import com.cjl.basic.zone.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author chen
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {


    @Autowired
    private IUserService userService;

    @GetMapping("/user")
    public String role() {
        return "system/user/user" ;
    }

    @GetMapping("/operate/{type}")
    public String operate(@PathVariable String type, Integer accountId, ModelMap map) {
        if (StringUtils.isNotNull(accountId)) {
            map.put("user", userService.selectUserById(accountId.longValue()));
        }
        return "system/user/" + type;
    }

    @ResponseBody
    @RequestMapping("/userList")
    public TableDataInfo roleList(User user) {
        startPage();
        return getDataTable(userService.selectUserList(user));
    }
//
//
//    @ResponseBody
//    @RequestMapping("/addRole")
//    public AjaxResult addRole(ZRole role) {
//        return AjaxResult.success(userService.addRole(role));
//    }
//
//    @ResponseBody
//    @RequestMapping("/editRole")
//    public AjaxResult editRole(ZRole role) {
//        return AjaxResult.success(userService.editRole(role));
//    }
//
//    @ResponseBody
//    @RequestMapping("/removeRole/{id}")
//    public AjaxResult removeRole(@PathVariable String id) {
//        return AjaxResult.success(userService.removeRole(id));
//    }
}
