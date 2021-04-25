package com.cjl.basic.zone.project.layim.controller;

import com.cjl.basic.zone.common.constant.SocketConstant;
import com.cjl.basic.zone.common.utils.SpringRedisUtil;
import com.cjl.basic.zone.common.utils.security.ShiroAuthenticateUtils;
import com.cjl.basic.zone.project.layim.entity.*;
import com.cjl.basic.zone.project.layim.service.ChatMsgService;
import com.cjl.basic.zone.project.layim.service.FriendsService;
import com.cjl.basic.zone.project.layim.service.GroupsService;
import com.cjl.basic.zone.project.layim.service.SysMsgService;
import com.cjl.basic.zone.project.user.domain.User;
import com.cjl.basic.zone.project.user.service.IUserService;
import com.cjl.basic.zone.utils.LayimUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author chen
 * @Date 2021/4/8 9:46
 * @Version 1.0
 */
@Controller
@RequestMapping(value = "layim")
public class LayIMController {

    @Autowired
    private SpringRedisUtil redisTemplate;
    @Autowired
    private FriendsService friendsService;
    @Autowired
    private IUserService userService;
    @Autowired
    private GroupsService groupsService;
    @Autowired
    private ChatMsgService chatMsgService;
    @Autowired
    private SysMsgService sysMsgService;
    @Value("${application.cache.prefix}")
    private String cachePrefix;

    /**
     * 通过用户id进入个人界面
     *
     * @return
     */
    @GetMapping("/login")
    public String layim(ModelMap map) {
        map.put("accountId", ShiroAuthenticateUtils.getAccountId());
        return "system/layim/index";
    }

    @GetMapping("/chat/log")
    public String toChatLog() {
        return "system/layim/chatlog";
    }

    @GetMapping("/add/find")
    public String addFind(ModelMap map) {
        map.put("accountId", ShiroAuthenticateUtils.getAccountId());
        return "system/layim/find";
    }

    @GetMapping("/add/ask")
    public String addAsk() {
        return "system/layim/msgbox";
    }

    /**
     * 查询聊天记录
     *
     * @param uid
     * @param session
     * @return
     * @throws InterruptedException
     */
    @GetMapping("/chat/log/{uid}")
    @ResponseBody
    public List<User> chatlog(@PathVariable("uid") String uid, HttpSession session) throws InterruptedException {
        Integer accountId = ShiroAuthenticateUtils.getAccountId();
        //模拟消息查询缓慢，让前台展示loading样式
        Thread.sleep(1000);
        ChatMsg chatmsg = new ChatMsg().setSendUserId(accountId).setReciveUserId(uid);
        return chatMsgService.getChatMsgLog(chatmsg);
    }

    /**
     * 查询群聊天记录
     *
     * @param gid
     * @return
     * @throws InterruptedException
     */
    @GetMapping("/chat/log/group/{gid}")
    @ResponseBody
    public List<User> chatlog(@PathVariable("gid") String gid) throws InterruptedException {
        //模拟消息查询缓慢，让前台展示loading样式
        Thread.sleep(1000);
        return groupsService.getGroupChatLogMsg(new GroupMsg().setGroupId(gid));
    }

    @GetMapping("/add/ask/{uid}")
    @ResponseBody
    public List<Map<Object, Object>> addAskRecord(@PathVariable("uid") String uid, HttpSession session) throws InterruptedException {
        String accountId = String.valueOf(ShiroAuthenticateUtils.getAccountId());
        //模拟消息查询缓慢，让前台展示loading样式
        Thread.sleep(1000);

        List<Map<Object, Object>> layimAsks = new ArrayList<>();
        //从redis中取离线接收的消息
        String prefix = cachePrefix + accountId + "_" + SocketConstant.ADD_ASK;
        // 获取所有的key
        Set<String> keys = redisTemplate.keys(prefix);

        if (keys.size() != 0) {
            //遍历key
            for (String str : keys) {
                // 去除前缀
                str = str.replace(cachePrefix, "");
                //获取消息数据
                Map<Object, Object> addAsk = redisTemplate.hashGetAll(str);
                String read = addAsk.get("read").toString();
                if ("0".equals(read)) {
                    //设为已读
                    redisTemplate.hashSet(str, "read", "1");
                    //添加成功的消息
                    if (addAsk.size() == 1) {
                        redisTemplate.delete(str);
                    }
                }
                //处理返回数据
//                LayimAsk layimAsk=new LayimAsk().setId(addAsk.get("id").toString()).setContent(addAsk.get("content").toString())
                if (addAsk.size() != 1) {
                    layimAsks.add(addAsk);
                }
            }
        }
        //查系统消息
        List<SysMsg> sysMsgs = sysMsgService.getSysMsgByUid(accountId);
        for (SysMsg sysMsg : sysMsgs) {
            LayimAsk layimAsk = new LayimAsk().setId(sysMsg.getId()).setContent(sysMsg.getContent()).setUid(accountId).setType("1")
                    .setTime(String.valueOf(sysMsg.getCreateTime().getTime())).setUser(new Mine() {{
                        setAccountId(null);
                    }});
            Map<Object, Object> map = LayimUtil.beanToMap(layimAsk);
            layimAsks.add(map);
        }

        return layimAsks;
    }

    @PostMapping("/add/agree")
    @ResponseBody
    public User addAskagree(@RequestParam String id) {
        //从redis中取离线接收的消息
        String prefix = "*" + id + "*";
        // 获取所有的key
        Set<String> keys = redisTemplate.keys(prefix);
        if (keys.size() != 0) {
            //遍历key
            for (String str : keys) {
                // 去除前缀
                str = str.replace(cachePrefix, "");
                //获取消息数据
                Map<Object, Object> addAsk = redisTemplate.hashGetAll(str);
                String uid = addAsk.get("uid").toString();
                String fid = addAsk.get("from").toString();
                String type = addAsk.get("type").toString();
                if ("0".equals(type)) {
                    //添加好友
                    boolean addFriend = friendsService.addFriend(new Friend().setUid(uid).setFid(fid));
                    if (addFriend) {
                        //删除redis
                        redisTemplate.delete(str);
                        //发送socket给发起人一个通知并把好友添加到列表
                        try {
                            Mine userInfo = userService.selectMineById(Integer.valueOf(uid));
                            ChatWebSocket.addFriend(fid, userInfo);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return (User) addAsk.get("user");
                    }
                } else if (type.equals("1")) {
                    //添加群
//                    groupsService.addGroupUser(new Group().setUid(fid).setGid())
                }

            }
        }
        return null;
    }

    @PostMapping("/add/refuse")
    @ResponseBody
    public boolean addAskRefuse(@RequestParam String id) {
        //从redis中取离线接收的消息
        String prefix = "*" + id + "*";
        // 获取所有的key
        Set<String> keys = redisTemplate.keys(prefix);
        if (keys.size() != 0) {
            //遍历key
            for (String str : keys) {
                //获取消息数据
                Map<Object, Object> addAsk = redisTemplate.hashGetAll(str);
                String uid = addAsk.get("uid").toString();
                String fid = addAsk.get("from").toString();
                boolean sysMsg = sysMsgService.addSysMsg(uid, fid);
                if (sysMsg) {
                    //删除redis
                    redisTemplate.delete(str);
                    //发送socket给发起人一个通知并把好友添加到列表
                    try {
                        ChatWebSocket.addFriend(fid, null);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * 初始化layim
     *
     * @return
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    @ResponseBody
    public InitImVo init() {
//        String userId = (String) session.getAttribute("userId");
        Integer accountId = ShiroAuthenticateUtils.getAccountId();
        InitImVo initImVo = new InitImVo();
        //个人信息
        Mine user = userService.selectMineById(accountId);
        //好友列表
        List<Mine> mineList = friendsService.getUserFriend(String.valueOf(accountId));
        Friends friends = new Friends().setId("2").setGroupname("我的好友").setList(mineList);
        List<Friends> friendList = new ArrayList<>();
        friendList.add(friends);
        //群组信息
        List<Groups> groupsList = groupsService.getUserGroups(String.valueOf(accountId));
        //Data数据
        ImData imData = new ImData();
        imData.setMine(user);
        imData.setFriend(friendList);
        imData.setGroup(groupsList);
        initImVo.setCode(0);
        initImVo.setMsg("");
        initImVo.setData(imData);
        return initImVo;
    }

}
