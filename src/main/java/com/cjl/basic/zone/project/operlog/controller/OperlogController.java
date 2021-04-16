package com.cjl.basic.zone.project.operlog.controller;

import com.cjl.basic.zone.common.utils.ModelMapUtils;
import com.cjl.basic.zone.framework.aspectj.lang.annotation.Log;
import com.cjl.basic.zone.framework.aspectj.lang.enums.BusinessType;
import com.cjl.basic.zone.framework.web.controller.BaseController;
import com.cjl.basic.zone.framework.web.domain.AjaxResult;
import com.cjl.basic.zone.framework.web.page.TableDataInfo;
import com.cjl.basic.zone.project.operlog.domain.OperLog;
import com.cjl.basic.zone.project.operlog.service.IOperLogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 操作日志记录
 *
 * @author chen
 */
@Controller
@RequestMapping("/monitor/operlog")
public class OperlogController extends BaseController {
    private String prefix = "monitor/operlog";

    @Autowired
    private IOperLogService operLogService;

    @RequiresPermissions("monitor:operlog:view")
    @GetMapping()
    public String operlog() {
        return prefix + "/operlog";
    }

    @RequiresPermissions("monitor:operlog:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(OperLog operLog, HttpServletRequest request) {
        startPage();
//        if (!ShiroAuthenticateUtils.getUserByToken().getUserType().equals("1")) {
//            operLog.setMfrsId(this.getCurrentUser().getMfrsId());
//        }
        List<OperLog> list = operLogService.selectOperLogList(operLog);
        return getDataTable(list);
    }

//    @Log(title = "操作日志", businessType = BusinessType.EXPORT)
//    @RequiresPermissions("monitor:operlog:export")
//    @PostMapping("/export")
//    @ResponseBody
//    public AjaxResult export(OperLog operLog, HttpServletRequest request) {
//        operLog.setMfrsId(this.getCurrentUser().getMfrsId().intValue());
//        List<OperLog> list = operLogService.selectOperLogList(operLog);
//        ExcelUtil<OperLog> util = new ExcelUtil<OperLog>(OperLog.class);
//        return util.exportExcel(list, "operLog");
//    }

    @RequiresPermissions("monitor:operlog:remove")
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(operLogService.deleteOperLogByIds(ids));
    }

    @RequiresPermissions("monitor:operlog:detail")
    @GetMapping("/detail/{operId}")
    public String detail(@PathVariable("operId") Long operId, ModelMap mmap) {
        ModelMapUtils.addAndReplaceEmoji(mmap, "operLog", operLogService.selectOperLogById(operId));
        return prefix + "/detail";
    }

    @Log(title = "操作日志", businessType = BusinessType.CLEAN)
    @RequiresPermissions("monitor:operlog:remove")
    @PostMapping("/clean")
    @ResponseBody
    public AjaxResult clean() {
        operLogService.cleanOperLog();
        return success();
    }
}
