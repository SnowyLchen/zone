package com.cjl.basic.zone.project.role.controller;

import com.cjl.basic.zone.framework.web.controller.BaseController;
import com.cjl.basic.zone.framework.web.domain.AjaxResult;
import com.cjl.basic.zone.framework.web.page.TableDataInfo;
import com.cjl.basic.zone.project.role.domain.ZRole;
import com.cjl.basic.zone.project.role.service.IRoleService;
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
@RequestMapping("/role")
public class RoleController extends BaseController {

    @Autowired
    private IRoleService roleService;

    @GetMapping("/role")
    public String role() {
        return "system/role" ;
    }

    @GetMapping("/operate/{type}")
    public String operate(@PathVariable String type, Integer roleId, ModelMap map) {
        if (StringUtils.isNotNull(roleId)) {
            map.put("role", roleService.selectRoleById(roleId));
        }
        return "system/operate/" + type;
    }

    @ResponseBody
    @RequestMapping("/roleList")
    public TableDataInfo roleList(ZRole role) {
        startPage();
        return getDataTable(roleService.selectRoleList(role));
    }


    @ResponseBody
    @RequestMapping("/addRole")
    public AjaxResult addRole(ZRole role) {
        return AjaxResult.success(roleService.addRole(role));
    }

    @ResponseBody
    @RequestMapping("/editRole")
    public AjaxResult editRole(ZRole role) {
        return AjaxResult.success(roleService.editRole(role));
    }

    @ResponseBody
    @RequestMapping("/removeRole/{id}")
    public AjaxResult removeRole(@PathVariable String id) {
        return AjaxResult.success(roleService.removeRole(id));
    }

}
