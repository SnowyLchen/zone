package com.cjl.basic.zone.project.space.categories.controller;

import com.cjl.basic.zone.framework.web.controller.BaseController;
import com.cjl.basic.zone.framework.web.domain.AjaxResult;
import com.cjl.basic.zone.framework.web.page.TableDataInfo;
import com.cjl.basic.zone.project.space.categories.domain.ZCategories;
import com.cjl.basic.zone.project.space.categories.service.IZCategoriesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 日志分类表(ZCategories)表控制层
 *
 * @author makejava
 * @since 2021-05-04 19:46:49
 */
@Controller
@RequestMapping("zCategories")
public class ZCategoriesController extends BaseController {
    private static final String PREFIX = "/space/categories";
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
    public TableDataInfo list(ZCategories zCategories) throws IllegalAccessException, InstantiationException {
        startPage();
        return getDataTable(zCategoriesService.selectZCategoriesList(zCategories));
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
     * @param zCategories [实例]
     * @return [返回值]
     * @Description [新增保存]
     * @author xj
     * @date 2021/5/4 21:09
     */
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult add(ZCategories zCategories) {
        return AjaxResult.success(zCategoriesService.insertZCategories(zCategories));
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
        map.put("bean", this.zCategoriesService.selectZCategoriesById(cId));
        return PREFIX + "/edit";
    }

    /**
     * @param zCategories [实例]
     * @return [返回值]
     * @Description [新增保存]
     * @author xj
     * @date 2021/5/4 21:09
     */
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult edit(ZCategories zCategories) {
        return AjaxResult.success(zCategoriesService.updateZCategories(zCategories));
    }


    /**
     * [修改跳转页]
     *
     * @param cId [主键ID]
     * @return [返回值]
     * @author xiaojie
     * @date 2021/3/31 14:51
     */
    @PostMapping("/remove/{cId}")
    @ResponseBody
    public AjaxResult remove(@PathVariable("cId") Integer cId) {
        return AjaxResult.success(zCategoriesService.deleteZCategoriesById(cId));
    }


}
