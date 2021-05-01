package com.cjl.basic.zone.project.manage.menu.controller;

import com.cjl.basic.zone.framework.web.controller.BaseController;
import com.cjl.basic.zone.framework.web.domain.AjaxResult;
import com.cjl.basic.zone.framework.web.page.TableDataInfo;
import com.cjl.basic.zone.project.manage.menu.domain.ZMenu;
import com.cjl.basic.zone.project.manage.menu.service.IMenuService;
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
@RequestMapping("/menu")
public class MenuController extends BaseController {

    @Autowired
    private IMenuService menuService;

    @GetMapping("/menu")
    public String menu() {
        return "system/menu/menu";
    }

    @GetMapping("/operate/{type}")
    public String operate(@PathVariable String type, Integer menuId, ModelMap map) {
        if (StringUtils.isNotNull(menuId)) {
            map.put("menu", menuService.selectMenuById(menuId));
        }
        return "system/menu/" + type;
    }

    @ResponseBody
    @RequestMapping("/menuList")
    public TableDataInfo menuList(ZMenu menu) {
        startPage();
        return getDataTable(menuService.selectMenuList(menu));
    }


    @ResponseBody
    @RequestMapping("/menuTree")
    public AjaxResult menuTree(ZMenu menu) {
        return AjaxResult.successTree(menuService.selectMenuTree(menu));
    }


    @ResponseBody
    @RequestMapping("/addMenu")
    public AjaxResult addMenu(ZMenu menu) {
        return AjaxResult.success(menuService.addMenu(menu));
    }

    @ResponseBody
    @RequestMapping("/editMenu")
    public AjaxResult editMenu(ZMenu menu) {
        return AjaxResult.success(menuService.editMenu(menu));
    }

    @ResponseBody
    @RequestMapping("/removeMenu/{id}")
    public AjaxResult removeMenu(@PathVariable String id) {
        return AjaxResult.success(menuService.removeMenu(id));
    }
}
