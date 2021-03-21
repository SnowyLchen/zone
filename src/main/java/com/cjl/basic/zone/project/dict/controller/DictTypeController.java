package com.cjl.basic.zone.project.dict.controller;

import com.cjl.basic.zone.common.utils.ModelMapUtils;
import com.cjl.basic.zone.framework.aspectj.lang.annotation.Log;
import com.cjl.basic.zone.framework.aspectj.lang.enums.BusinessType;
import com.cjl.basic.zone.framework.web.controller.BaseController;
import com.cjl.basic.zone.framework.web.domain.AjaxResult;
import com.cjl.basic.zone.framework.web.page.TableDataInfo;
import com.cjl.basic.zone.project.dict.domain.DictType;
import com.cjl.basic.zone.project.dict.service.IDictTypeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 数据字典信息
 *
 * @author wangsen
 */
@Controller
@RequestMapping("/system/dict")
public class DictTypeController extends BaseController {
    private String prefix = "system/dict/type";

    @Autowired
    private IDictTypeService dictTypeService;

    @RequiresPermissions("system:dict:view")
    @GetMapping()
    public String dictType() {
        return prefix + "/type";
    }

    @PostMapping("/list")
    @RequiresPermissions("system:dict:list")
    @ResponseBody
    public TableDataInfo list(DictType dictType) {
        startPage();
        List<DictType> list = dictTypeService.selectDictTypeList(dictType);
        return getDataTable(list);
    }
//
//    @Log(title = "导出字典类型", businessType = BusinessType.EXPORT)
//    @RequiresPermissions("system:dict:export")
//    @PostMapping("/export")
//    @ResponseBody
//    public AjaxResult export(DictType dictType) {
//
//        List<DictType> list = dictTypeService.selectDictTypeList(dictType);
//        ExcelUtil<DictType> util = new ExcelUtil<DictType>(DictType.class);
//        return util.exportExcel(list, "dictType");
//    }

    /**
     * 新增字典类型
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存字典类型
     */
    @Log(title = "字典类型", businessType = BusinessType.INSERT)
    @RequiresPermissions("system:dict:add")
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(DictType dict) {
        return toAjax(dictTypeService.insertDictType(dict));
    }

    /**
     * 修改字典类型
     */
    @GetMapping("/edit/{dictId}")
    public String edit(@PathVariable("dictId") Long dictId, ModelMap mmap) {
        ModelMapUtils.addAndReplaceEmoji(mmap, "dict", dictTypeService.selectDictTypeById(dictId));
        return prefix + "/edit";
    }

    /**
     * 修改保存字典类型
     */
    @Log(title = "字典类型", businessType = BusinessType.UPDATE)
    @RequiresPermissions("system:dict:edit")
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(DictType dict) {
        return toAjax(dictTypeService.updateDictType(dict));
    }


    @Log(title = "字典类型", businessType = BusinessType.STATUS)
    @RequiresPermissions("system:dict:status")
    @PostMapping("/status/{dictId}")
    @ResponseBody
    public AjaxResult status(@PathVariable("dictId") Long dictId) {
        try {
            return toAjax(dictTypeService.statusdictById(dictId));
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }


    @Log(title = "字典类型", businessType = BusinessType.DELETE)
    @RequiresPermissions("system:dict:remove")
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        try {
            return toAjax(dictTypeService.deleteDictTypeByIds(ids));
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    /**
     * 查询字典详细
     */
    @RequiresPermissions("system:dict:list")
    @GetMapping("/detail/{dictId}")
    public String detail(@PathVariable("dictId") Long dictId, ModelMap mmap) {
        ModelMapUtils.addAndReplaceEmoji(mmap, "dict", dictTypeService.selectDictTypeById(dictId));
        ModelMapUtils.addAndReplaceEmoji(mmap, "dictList", dictTypeService.selectDictTypeAll());
        return "system/dict/data/data";
    }

    /**
     * 校验字典类型
     */
    @PostMapping("/checkDictTypeUnique")
    @ResponseBody
    public String checkDictTypeUnique(DictType dictType) {
        return dictTypeService.checkDictTypeUnique(dictType);
    }
}
