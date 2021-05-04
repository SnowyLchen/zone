package com.cjl.basic.zone.project.space.categories.controller;

import com.cjl.basic.zone.framework.web.domain.AjaxResult;
import com.cjl.basic.zone.project.space.categories.domain.ZCategories;
import com.cjl.basic.zone.project.space.categories.service.IZCategoriesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 日志分类表(ZCategories)表控制层
 *
 * @author makejava
 * @since 2021-05-04 19:46:49
 */
@Controller
@RequestMapping("zCategories")
public class ZCategoriesController {
    private static final String PREFIX = "zCategories";
    /**
     * 服务对象
     */
    @Resource
    private IZCategoriesService zCategoriesService;

    /**
     * [跳转页]
     *
     * @param map [模型数据]
     * @return [跳转地址]
     * @author xiaojie
     * @date 2021/3/31 14:42
     */
    @GetMapping()
    public String view(ModelMap map) {
        return PREFIX + "/view";
    }

    /**
     * [查询页]
     *
     * @author xiaojie
     * @date 2021/3/31 14:42
     */
    @GetMapping("list")
    @ResponseBody
    public AjaxResult list(ZCategories zCategories) {
        return AjaxResult.success(zCategoriesService.selectZCategoriesList(zCategories));
    }

    /**
     * [新增跳转页]
     *
     * @param map [模型数据]
     * @return [跳转地址]
     * @author xiaojie
     * @date 2021/3/31 14:42
     */
    @GetMapping("/add")
    public String add(ModelMap map) {
        return PREFIX + "/add";
    }

    /**
     * [修改跳转页]
     *
     * @param cId [主键ID]
     * @return [返回值]
     * @author xiaojie
     * @date 2021/3/31 14:51
     */
    @GetMapping("/edit/{cId}")
    public String edit(@PathVariable("cId") Integer cId, ModelMap map) {
        map.put("zCategories", this.zCategoriesService.selectZCategoriesById(cId));
        return PREFIX + "/edit";
    }


}
