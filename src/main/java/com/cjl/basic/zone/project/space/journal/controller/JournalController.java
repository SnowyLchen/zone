package com.cjl.basic.zone.project.space.journal.controller;

import com.cjl.basic.zone.common.utils.security.ShiroAuthenticateUtils;
import com.cjl.basic.zone.framework.web.controller.BaseController;
import com.cjl.basic.zone.framework.web.domain.AjaxResult;
import com.cjl.basic.zone.framework.web.domain.AjaxResult;
import com.cjl.basic.zone.project.manage.user.domain.User;
import com.cjl.basic.zone.project.space.journal.domain.ZJournal;
import com.cjl.basic.zone.project.space.journal.service.IZJournalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/journal")
public class JournalController extends BaseController {
    private static final String PREFIX = "/space";
    private static final String PREFIX_JOURNAL = "/space/journal";
    @Autowired
    private IZJournalService journalService;

    /**
     * 日志
     */
    @GetMapping()
    public String index(ModelMap mmap, HttpServletResponse response) {
        User user = ShiroAuthenticateUtils.getUserByToken();
        mmap.put("user", user);
        return PREFIX + "/journal";
    }

    /**
     * @param url [地址]
     * @return [返回值]
     * @Description [链接跳转]
     * @author xj
     * @date 2021/5/3 19:40
     */
    @RequestMapping("template")
    public String template(String url) {
        return PREFIX_JOURNAL + url;
    }

    /**
     * @return [返回值]
     * @Description [新增跳转页]
     * @author xj
     * @date 2021/5/3 19:45
     */
    @GetMapping("/add")
    public String add() {
        return PREFIX_JOURNAL + "/add";
    }


    /**
     * 写日志
     *
     * @param zJournal
     * @return
     */
    @ResponseBody
    @RequestMapping("/addJournal")
    public AjaxResult addJournal(ZJournal zJournal) {
        Integer accountId = ShiroAuthenticateUtils.getAccountId();
        zJournal.setAccountId(accountId);
        return AjaxResult.success(journalService.addJournal(zJournal));
    }

    /**
     * 更新日志
     *
     * @param zJournal
     * @return
     */
    @ResponseBody
    @RequestMapping("/updateJournal")
    public AjaxResult updateJournalById(ZJournal zJournal) {
        Integer accountId = ShiroAuthenticateUtils.getAccountId();
        zJournal.setAccountId(accountId);
        return AjaxResult.success(journalService.updateJournalById(zJournal));
    }

    /**
     * 查询单篇日志
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("/selectJournalById/{id}")
    public AjaxResult selectJournalById(@PathVariable Integer id) {
        return AjaxResult.success(journalService.selectJournalById(id));
    }

    /**
     * 查询用户下全部日志
     */
    @ResponseBody
    @RequestMapping("/getJournals")
    public AjaxResult selectJournalList() {
        Integer accountId = ShiroAuthenticateUtils.getAccountId();
        return AjaxResult.success(journalService.selectJournalListByAccountId(accountId));
    }

    /**
     * 删除单篇日志
     */
    @ResponseBody
    @RequestMapping("/deleteJournalById/{id}")
    public AjaxResult selectJournalList(@PathVariable Integer id) {
        return AjaxResult.success(journalService.deleteJournalById(id));
    }

    /**
     * 删除多篇日志
     */
    @ResponseBody
    @RequestMapping("/deleteJournalByIds/{ids}")
    public AjaxResult selectJournalList(@PathVariable String ids) {
        return AjaxResult.success(journalService.deleteJournalByIds(ids));
    }

    /**
     * 清空日志
     */
    @ResponseBody
    @RequestMapping("/deleteAllJournal")
    public AjaxResult deleteAllJournal() {
        Integer accountId = ShiroAuthenticateUtils.getAccountId();
        return AjaxResult.success(journalService.deleteJournalByAccountId(accountId));
    }
}
