package com.cjl.basic.zone.project.manage.layim.controller;

import com.alibaba.fastjson.JSONObject;
import com.cjl.basic.zone.common.constant.SocketConstant;
import com.cjl.basic.zone.common.utils.SpringRedisUtil;
import com.cjl.basic.zone.project.manage.layim.entity.*;
import com.cjl.basic.zone.project.manage.layim.service.ChatMsgService;
import com.cjl.basic.zone.project.manage.layim.service.GroupsService;
import com.cjl.basic.zone.project.manage.user.domain.User;
import com.cjl.basic.zone.project.manage.user.service.IUserService;
import com.cjl.basic.zone.utils.IdGenerat;
import com.cjl.basic.zone.utils.LayimUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author chen
 * @ServerEndpoint 注解是一个类层次的注解，它的功能主要是将目前的类定义成一个websocket服务器端,
 * 注解的值将被用于监听用户连接的终端访问URL地址,客户端可以通过这个URL来连接到WebSocket服务器端
 * @ServerEndpoint 可以把当前类变成websocket服务类
 */
@Controller
@ServerEndpoint(value = "/websocket/{accountId}")
public class ChatWebSocket {

    private static ChatMsgService chatMsgService;
    private static GroupsService groupsService;
    private static IUserService userService;
    private static SpringRedisUtil redisTemplate;
    private static String cachePrefix;

    @Autowired
    public void setChatService(ChatMsgService chatService, GroupsService groupsService, IUserService userService, SpringRedisUtil redisTemplate, @Value("${application.cache.prefix}") String cachePrefix) {
        ChatWebSocket.chatMsgService = chatService;
        ChatWebSocket.groupsService = groupsService;
        ChatWebSocket.userService = userService;
        ChatWebSocket.redisTemplate = redisTemplate;
        ChatWebSocket.cachePrefix = cachePrefix;
    }

    /**
     * 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
     */
    private static int onlineCount = 0;
    /**
     * concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
     */
    private static ConcurrentHashMap<Integer, ChatWebSocket> webSocketSet = new ConcurrentHashMap<>();
    /**
     * 用于存放每个客户端信息
     */
    private static ConcurrentHashMap<String, Mine> webSocketInfo = new ConcurrentHashMap<>();

    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session webSocketSession;
    /**
     * 当前发消息的用户id
     */
    private Integer accountId;


    /**
     * 连接建立成功调用的方法
     * <p>
     * session 可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void onOpen(@PathParam(value = "accountId") Integer uId, Session webSocketSession, EndpointConfig config) {
        //接收到发送消息的用户id
        accountId = uId;
        this.webSocketSession = webSocketSession;
        //加入map中
        webSocketSet.put(accountId, this);
        webSocketInfo.put(cachePrefix + accountId, userService.selectMineById(accountId));
        //在线数加1
        addOnlineCount();
        System.out.println("有新连接加入！当前在线人数为" + getOnlineCount());
        String prefix = SocketConstant.CHANGE_STATUS + ":" + accountId + "_" + SocketConstant.CHANGE_STATUS;
        String status = "online";
        if (redisTemplate.hasKey(prefix)) {
            status = Objects.toString(redisTemplate.get(prefix));
        }
        Mine mine = new Mine();
        mine.setAccountId(accountId);
        mine.setStatus(status);
        //更新用户的状态
        userService.updateUser(mine);
        //获取离线消息
        getOnLineMsg();
        //获取消息盒子
        getMsgBox();
    }

    /**
     * 获取离线消息
     */
    private void getOnLineMsg() {
        //从redis中取离线接收的消息
        String prefix = cachePrefix + SocketConstant.ON_LINE_MESSAGE + ":" + accountId + "_" + SocketConstant.ON_LINE_MESSAGE + "*";
        // 获取所有的key
        Set<String> keys = redisTemplate.keys(prefix);
        if (keys.size() != 0) {
            //遍历key
            for (String str : keys) {
                str = str.replace(cachePrefix, "");
                //获取消息数据
                Map<Object, Object> chatMsgMap = redisTemplate.hashGetAll(str);
                String sendId = chatMsgMap.get("sendId").toString();
                String content = chatMsgMap.get("content").toString();
                String receiveId = chatMsgMap.get("receiveId").toString();
                Date sendTime = new Date(Long.parseLong(chatMsgMap.get("sendTime").toString()));
                //获取登录人信息 填充对象
                Mine userInfo = userService.selectMineById(Integer.valueOf(sendId));
                userInfo.setToid(receiveId);
                userInfo.setContent(content);
                userInfo.setSendTime(sendTime);
                userInfo.setTimeStamp(sendTime.getTime());
                if (str.contains("friend")) {
                    userInfo.setType("friend");
                } else if (str.contains("group")) {
                    userInfo.setType("group");
                }
                //填充消息对象
                ChatMsg chatMsg = new ChatMsg() {{
                    setMsgType("0");
                    setReciveUserId(receiveId);
                    setSendUserId(Integer.parseInt(sendId));
                    setContent(content);
                    setCreateTime(sendTime);
                }};
                //发消息
                chatMsgService.insertChatmsg(chatMsg);
                try {
                    //转成json形式发送出去
                    SocketMsgType socketMsgType = new SocketMsgType() {{
                        setCode(200);
                        setMsgType("发送成功");
                        setMsgType(SocketConstant.ON_LINE_MESSAGE);
                        setData(userInfo);
                    }};
                    webSocketSet.get(Integer.valueOf(receiveId)).sendMessage(JSONObject.toJSONString(socketMsgType));
                    redisTemplate.delete(str);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 获取未读消息
     */
    public void getMsgBox() {
        //从redis中取离线接收的消息
        String prefix = cachePrefix + SocketConstant.ADD_ASK + ":" + accountId + "_" + SocketConstant.ADD_ASK;
        // 获取所有的key
        Set<String> keys = redisTemplate.keys(prefix);
        //未读消息个数
        int Unread = 0;

        if (keys.size() != 0) {
            //遍历key
            for (String str : keys) {
                str = str.replace(cachePrefix, "");
                //获取消息数据
                Map<Object, Object> addAsk = redisTemplate.hashGetAll(str);
                String read = addAsk.get("read").toString();
                if (read.equals("0")) {
                    Unread++;
                    //设为已读
//                    redisTemplate.opsForHash().put(str,"read","1");
                }
            }
        }

        try {
            //转成json形式发送出去
            int finalUnread = Unread;
            SocketMsgType socketMsgType = new SocketMsgType() {{
                setCode(200);
                setMsgType("发送成功");
                setMsgType(SocketConstant.MSG_BOX_UNREAD);
                setData(finalUnread);
            }};
            webSocketSet.get(accountId).sendMessage(JSONObject.toJSONString(socketMsgType));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        if (accountId != null) {
            //从set中删除
            webSocketSet.remove(accountId);
            //在线数减1
            subOnlineCount();
            System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
            //更新用户的状态为离线
            userService.updateUser(new Mine() {{
                setAccountId(accountId);
                setStatus("offline");
            }});
        }
    }


    /**
     * 监听接受消息
     *
     * @param session 可选的参数
     */
    @SuppressWarnings("unused")
    @OnMessage
    public void onMessage(String mine, Session session) {
        //接收前台发送过来的消息
        JSONObject jsonObject = JSONObject.parseObject(mine);
        String msgtype = jsonObject.getString("msgType");
        if ("chatMsg".equals(msgtype)) {
            Mine message = jsonObject.toJavaObject(Mine.class);
            //查看是单反消息还是和群发消息
            if ("friend".equals(message.getType())) {
                sendToUser(message);
            } else if ("group".equals(message.getType())) {
                sendAll(message);
            }
        } else if ("addAsk".equals(msgtype)) {
            LayimAsk layimAsk = jsonObject.toJavaObject(LayimAsk.class);
            addAsk(layimAsk);
        } else if ("statusChange".equals(msgtype)) {
            Mine m = jsonObject.toJavaObject(Mine.class);
            String prefix = SocketConstant.CHANGE_STATUS + ":" + accountId + "_" + SocketConstant.CHANGE_STATUS;
            // 变换状态
            System.out.println("用户：" + m.getUsername() + "->动作：切换状态" + m.getStatus());
            userService.updateUser(new User() {{
                setAccountId(m.getId());
                setStatus(m.getStatus());
            }});
            // 同时将状态存入redis
            redisTemplate.set(prefix, m.getStatus());
        } else if ("heartCheck".equals(msgtype)) {
            System.out.println(jsonObject.getString("service"));
        }
    }

    /**
     * 添加申请
     */
    public void addAsk(LayimAsk layimAsk) {
        //添加好友申请
        if (layimAsk.getType().equals("0")) {

        } else {//加群申请
            //查群主
            Groups groupInfo = groupsService.getById(layimAsk.getFromGroup());
            layimAsk.setUid(groupInfo.getOwner());
            String content = layimAsk.getContent() + "  " + groupInfo.getGroupname();
            layimAsk.setContent(content);
        }
        String time = String.valueOf(System.currentTimeMillis());
        Mine userInfo = userService.selectMineById(Integer.valueOf(layimAsk.getFrom()));
        String uid = layimAsk.getUid();
        layimAsk.setId(IdGenerat.getGeneratID());
        layimAsk.setTime(time);
        layimAsk.setHref("");
        layimAsk.setUser(userInfo);
        boolean onLine = webSocketSet.get(Integer.valueOf(uid)) != null;
        //接收人在线直接发送
        if (onLine) {
            layimAsk.setRead("1");
        } else {//不在线
            layimAsk.setRead("0");
        }
        Map<Object, Object> map = LayimUtil.beanToMap(layimAsk);
        String key = SocketConstant.ADD_ASK + ":" + uid + "_" + SocketConstant.ADD_ASK + "_" + time + "_" + layimAsk.getId() + "_friend";
        redisTemplate.hashSetAll(key, map);

        try {
            //接收人在线直接发送
            if (onLine) {
                SocketMsgType socketMsgType = new SocketMsgType() {{
                    setCode(200);
                    setMsgType("发送成功");
                    setMsgType(SocketConstant.ADD_ASK);
                    setData(null);
                }};
                //转成json形式发送出去
                webSocketSet.get(Integer.valueOf(uid)).sendMessage(JSONObject.toJSONString(socketMsgType));
            } else {//不在线
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 给指定的人发送消息
     *
     * @param message
     */
    public static void sendToUser(Mine message) {
        System.out.println("单点消息");
        message.setSendTime(new Date());
        Date date = message.getSendTime();
        Integer id = message.getId() == null ? message.getAccountId() : message.getId();
        String content = message.getContent();
        String reviceUserId = message.getToid();
        //填充消息对象
        ChatMsg chatMsg = new ChatMsg() {{
            setMsgType("0");
            setReciveUserId(reviceUserId);
            setSendUserId(id);
            setContent(content);
            setCreateTime(date);
        }};
        //发消息
        chatMsgService.insertChatmsg(chatMsg);
        try {
            //接收人在线直接发送
            if (webSocketSet.get(Integer.valueOf(reviceUserId)) != null) {
                SocketMsgType socketMsgType = new SocketMsgType() {{
                    setCode(200);
                    setMsgType("发送成功");
                    setMsgType(SocketConstant.ON_LINE_MESSAGE);
                    setData(message);
                }};
                //转成json形式发送出去
                webSocketSet.get(Integer.valueOf(reviceUserId)).sendMessage(JSONObject.toJSONString(socketMsgType));
            } else {//不在线
                //放入redis处理不在线
                Map<Object, Object> map = new HashMap<>();
                String time = String.valueOf(date.getTime());
                String key = SocketConstant.ON_LINE_MESSAGE + ":" + reviceUserId + "_" + SocketConstant.ON_LINE_MESSAGE + "_" + time + "_friend";
                map.put("receiveId", reviceUserId);
                map.put("sendId", id);
                map.put("content", content);
                map.put("sendTime", date.getTime());
//                map.put("msgType",message.getContent());
                redisTemplate.hashSetAll(key, map);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 给群组中的所有人发消息
     *
     * @param message
     */
    private void sendAll(Mine message) {
        System.out.println("群消息");
        message.setSendTime(new Date());
        String revicegid = message.getToid();
        Date date = message.getSendTime();
        Integer id = message.getAccountId();
        String content = message.getContent();
        //此群中的用户（包含自己）
        List<Mine> userlist = groupsService.getGroupUsre(revicegid);
        groupsService.InsertGroupMsg(new GroupMsg() {{
            setSendUserId(String.valueOf(message.getAccountId()));
            setGroupId(message.getToid());
            setContent(message.getContent());
        }});
        try {
            for (Mine uid : userlist) {
                Integer userId = uid.getAccountId();
                //（过滤掉自己）
                if (!userId.equals(message.getAccountId())) {
                    if (webSocketSet.get(userId) != null) {
                        SocketMsgType socketMsgType = new SocketMsgType() {{
                            setCode(200);
                            setMsgType("发送成功");
                            setMsgType(SocketConstant.ON_LINE_MESSAGE);
                            setData(message);
                        }};
                        //转成json形式发送出去
                        webSocketSet.get(userId).sendMessage(JSONObject.toJSONString(socketMsgType));
                    } else {//不在线
                        //放入redis处理不在线
                        Map<Object, Object> map = new HashMap<>();
                        String time = String.valueOf(date.getTime());
                        String Key = SocketConstant.ON_LINE_MESSAGE + ":" + userId + "_" + SocketConstant.ON_LINE_MESSAGE + "_" + time + "_group";
                        map.put("receiveId", revicegid);
                        map.put("sendId", id);
                        map.put("content", content);
                        map.put("sendTime", date.getTime());
//                map.put("msgType",message.getContent());
                        redisTemplate.hashSetAll(Key, map);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 自定义消息
     */
    public static void addFriend(String fid, Mine mine) throws IOException {
        Integer friendId = Integer.valueOf(fid);
        //在线
        if (webSocketSet.get(friendId) != null) {
            try {
                SocketMsgType socketMsgType = new SocketMsgType() {{
                    setCode(200);
                    setMsgType("addFriend");
                    setMsg("添加好友成功");
                    setData(mine);
                }};
                //这里可以设定只推送给这个sid的，为null则全部推送
                if (mine != null) {
                    webSocketSet.get(friendId).sendMessage(JSONObject.toJSONString(socketMsgType));
                } else {
                    socketMsgType.setMsg("添加好友失败");
                    socketMsgType.setData("0");
                    webSocketSet.get(friendId).sendMessage(JSONObject.toJSONString(socketMsgType));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            //不在线
            String time = String.valueOf(System.currentTimeMillis());
            String Key = SocketConstant.ADD_ASK + ":" + fid + "_" + SocketConstant.ADD_ASK + "_" + time;
            Map<Object, Object> map = new HashMap<>();
            map.put("read", "0");
            redisTemplate.hashSetAll(Key, map);
        }
    }


    /**
     * 发生错误时调用
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }


    /**
     * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
     *
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException {
        this.webSocketSession.getBasicRemote().sendText(message);
        //this.session.getAsyncRemote().sendText(message);
    }


    public static synchronized int getOnlineCount() {
        return onlineCount;
    }


    public static synchronized void addOnlineCount() {
        ChatWebSocket.onlineCount++;
    }


    public static synchronized void subOnlineCount() {
        ChatWebSocket.onlineCount--;
    }

    public static synchronized String getPrefixName(Integer accountId) {
        return webSocketInfo.get(cachePrefix + accountId).getLoginName() + ":";
    }

}

