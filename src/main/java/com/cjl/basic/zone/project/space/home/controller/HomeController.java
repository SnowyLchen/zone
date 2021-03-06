package com.cjl.basic.zone.project.space.home.controller;

import com.cjl.basic.zone.common.utils.security.ShiroAuthenticateUtils;
import com.cjl.basic.zone.framework.web.domain.AjaxResult;
import com.cjl.basic.zone.project.manage.user.domain.User;
import com.cjl.basic.zone.project.space.album.service.IZAlbumPhotoService;
import com.cjl.basic.zone.project.space.home.domain.ZDynamic;
import com.cjl.basic.zone.project.space.home.service.IZSignInService;
import com.cjl.basic.zone.project.space.journal.domain.ZJournal;
import com.cjl.basic.zone.project.space.journal.service.IZJournalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/home")
public class HomeController {
    @Autowired
    private IZSignInService izSignInService;
    @Autowired
    private IZAlbumPhotoService albumPhotoService;
    @Autowired
    private IZJournalService journalService;

    /**
     * 空间首页
     */
    @GetMapping()
    public String index(ModelMap mmap) {
        User user = ShiroAuthenticateUtils.getUserByToken();
        mmap.put("user", user);
        mmap.put("signTimes", izSignInService.selectSignInfoList(ShiroAuthenticateUtils.getAccountId()).size());
        mmap.put("photoTimes", albumPhotoService.selectPhotoByAccountId(ShiroAuthenticateUtils.getAccountId()).size());
        mmap.put("msgTimes", journalService.selectJournalListByAccountId(new ZJournal() {{
            setAccountId(ShiroAuthenticateUtils.getAccountId());
            setSecret("0");
        }}).size() + journalService.selectJournalListByAccountId(new ZJournal() {{
            setAccountId(ShiroAuthenticateUtils.getAccountId());
            setSecret("1");
        }}).size());
        mmap.put("dynamicTimes", izSignInService.getDynamic(ShiroAuthenticateUtils.getAccountId()).size());
        return "space/home";
    }

    /**
     * 查询签到信息
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/getSignIn")
    public AjaxResult getSignIn() {
        Integer accountId = ShiroAuthenticateUtils.getAccountId();
        return AjaxResult.success(izSignInService.selectSignInfoList(accountId));
    }

    /**
     * 查询签到信息[2021-5-01]
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/getSignMark")
    public AjaxResult getSignMark() {
        Integer accountId = ShiroAuthenticateUtils.getAccountId();
        return AjaxResult.success(izSignInService.selectSignInfoMap(accountId));
    }

    /**
     * 今天是否签到
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/checkSignIn")
    public AjaxResult checkSignIn() {
        Integer accountId = ShiroAuthenticateUtils.getAccountId();
        return AjaxResult.success(izSignInService.checkSignIn(accountId));
    }

    /**
     * 查询动态信息
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/getDynamic")
    public AjaxResult getDynamic() {
        Integer accountId = ShiroAuthenticateUtils.getAccountId();
        return AjaxResult.success(izSignInService.getDynamic(accountId));
    }

    /**
     * 进行签到/发表动态
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/signIn")
    public AjaxResult signInfo(ZDynamic zDynamic) {
        Integer accountId = ShiroAuthenticateUtils.getAccountId();
        zDynamic.setAccountId(accountId);
        return AjaxResult.success(izSignInService.insertSignInfo(zDynamic));
    }
}
