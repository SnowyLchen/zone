package com.cjl.basic.zone.project.space.board.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.cjl.basic.zone.common.utils.security.ShiroAuthenticateUtils;
import com.cjl.basic.zone.framework.web.domain.AjaxResult;
import com.cjl.basic.zone.project.manage.user.domain.User;
import com.cjl.basic.zone.project.space.board.domain.ZMessageBoard;
import com.cjl.basic.zone.project.space.board.domain.ZReply;
import com.cjl.basic.zone.project.space.board.service.IBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 留言板
 */
@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private IBoardService boardService;

    /**
     * 空间留言板模块
     */
    @GetMapping()
    public String board(ModelMap mmap) {
        User user = ShiroAuthenticateUtils.getUserByToken();
        mmap.put("user", user);
        mmap.put("owner", boardService.selectOwnerMessageBoard(user.getAccountId()));
        mmap.put("message", JSONArray.parseArray(JSONObject.toJSONString(boardService.selectMessageBoardList(user.getAccountId()), SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue)));
        return "space/board/board";
    }

    /**
     * 空间留言板模块
     */
    @GetMapping("/operator/{type}")
    public String addAlbum(ModelMap mmap, @PathVariable String type, Integer id) {
        return "space/board/" + type;
    }


    /**
     * 新增留言
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/addMessage")
    public AjaxResult addMessage(ZMessageBoard zMessageBoard) {
        // 访问者
        User account = ShiroAuthenticateUtils.getAccount();
        // 被访问者
        User toAccount = ShiroAuthenticateUtils.getToAccount();
        zMessageBoard.setAccountId(toAccount.getAccountId());
        zMessageBoard.setUserName(toAccount.getUserName());
        zMessageBoard.setAvatar(toAccount.getAvatar());
        zMessageBoard.setComeAccountId(account.getAccountId());
        zMessageBoard.setComeUserName(account.getUserName());
        zMessageBoard.setComeAvatar(account.getAvatar());
        return AjaxResult.success(boardService.insertMessage(zMessageBoard));
    }

    /**
     * 新增留言
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/removeMessage/{id}")
    public AjaxResult removeMessage(@PathVariable Integer id) {
        return AjaxResult.success(boardService.removeMessage(id));
    }

    /**
     * 回复留言
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/replyMessage")
    public AjaxResult replyMessage(ZReply reply) {
        Integer accountId = ShiroAuthenticateUtils.getAccountId();
        reply.setAccountId(accountId);
        return AjaxResult.success(boardService.insertReplyMessage(reply));
    }

    /**
     * 主人寄语
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/addOwner")
    public AjaxResult addOwner(ZMessageBoard zMessageBoard) {
        Integer accountId = ShiroAuthenticateUtils.getAccountId();
        zMessageBoard.setAccountId(accountId);
        zMessageBoard.setComeAccountId(accountId);
        return AjaxResult.success(boardService.addOwner(zMessageBoard));
    }

}
