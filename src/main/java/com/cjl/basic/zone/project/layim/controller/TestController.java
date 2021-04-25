package com.cjl.basic.zone.project.layim.controller;

import com.cjl.basic.zone.project.layim.entity.Mine;
import com.cjl.basic.zone.project.layim.service.GroupsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

@Controller
public class TestController {

    @Autowired
    private GroupsService groupsService;

    @RequestMapping(value = "/test/{id}")
    @ResponseBody
    public List<Mine> test(@PathVariable String id) {
        return groupsService.getGroupUsre(id);
    }

    @RequestMapping(value = "/message/send")
    @ResponseBody
    public void sendToUser() {
        Mine mine = new Mine().setId("1571476959767947448").setUsername("小E同学").setAvatar("/pic/xd.png").setToid("1571476959767947441")
                .setContent("我是小E同学").setType("0").setSendtime(new Date());
        ChatWebSocket.sendToUser(mine);
    }


}
