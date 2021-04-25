//当前选中窗口的用户id
var actuserid = "";
//接入WebSocket
var socket = new WebSocket('ws://localhost:8000/websocket/' + accountId);
//打开事件
socket.onopen = function () {
    console.log("WebSocket 已打开");
    // socket.send("{\"msgType\":\"addAsk\",\"msg\":\"这是来自客户端的消息" + location.href + new Date() + "\"}");
};

layui.use('layim', function (layim) {

    //基础配置
    layim.config({
        init: {
            url: basePath + '/layim/init'
            , type: 'get' //默认get，一般可不填
            , data: {} //额外参数
        }
        // //获取群员接口
        // ,members: {
        //     url: basePath+ '/group/member'
        //     ,type: 'get'
        //     ,data: {} //额外参数
        // }
        // //上传图片接口
        // ,uploadImage: {
        //     url: basePath+'/chat/upimg' //接口地址
        //     ,type: 'post' //默认post
        // }
        // //上传文件接口
        // ,uploadFile: {
        //     url: basePath+'/chat/upfile' //接口地址
        //     ,type: 'post' //默认post
        // }
        //扩展工具栏
        , tool: [{
            alias: 'code' //工具别名
            , title: '代码' //工具名称
            , icon: '&#xe64e;' //工具图标，参考图标文档
        }]
        , isAudio: true
        , isVideo: true
        , title: "我的WebIM" //自定义主面板最小化时的标题
        , voice: "default.wav"  //新消息提醒音频
        , notice: false //是否开启桌面消息提醒，默认false
        // ,msgbox: layui.cache.dir + 'css/modules/layim/html/msgbox.html' //消息盒子页面地址，若不开启，剔除该项即可
        , msgbox: basePath + '/layim/add/ask'
        , find: basePath + '/layim/add/find'  //发现页面地址，若不开启，剔除该项即可
        // ,chatLog: layui.cache.dir + 'css/modules/layim/html/chatlog.html' //聊天记录页面地址，若不开启，剔除该项即可
        , chatLog: basePath + '/layim/chat/log' //聊天记录界面

    });
    // //删除本地聊天数据
    layui.data('layim', {key: accountId, remove: true});
    //监听发送的消息
    layim.on('sendMessage', function (res) {
        console.log(res)
        var mine = res.mine; //包含发送的消息及登录用户信息
        var to = res.to;
        var object = new Object();
        object["username"] = mine.username;
        object["avatar"] = mine.avatar;
        object["id"] = mine.id;
        object["content"] = mine.content;
        object["toid"] = to.id;
        object["type"] = to.type;
        object["msgType"] = "chatMsg";
        var jsonData = JSON.stringify(object);
        // 发送给websocket
        socket.send(jsonData);
    });

    //监听收到的消息
    socket.onmessage = function (res) {
        console.log(res)
        console.log(res.data)
        var jObject = JSON.parse(res.data);// Json字符串转对象
        var jsonObject = jObject.data;
        if (jObject.msgType == "onLineMsg") {
            if (jsonObject.type === "group") {
                //新消息提醒
                layim.getMessage({
                    username: jsonObject.username
                    , avatar: jsonObject.avatar
                    , id: jsonObject.toid
                    , type: jsonObject.type
                    , content: jsonObject.content
                    , timestamp: new Date().getTime()
                });
            } else if (jsonObject.type === "friend") {
                //新消息提醒
                layim.getMessage({
                    username: jsonObject.username
                    , avatar: jsonObject.avatar
                    , id: jsonObject.id
                    , type: jsonObject.type
                    , content: jsonObject.content
                    , timestamp: jsonObject.timeStamp
                });
            }
        } else if (jObject.msgType == "addAsk") {
            msgbox = msgbox + 1;
            layim.msgbox(msgbox);
        } else if (jObject.msgType == "Unread") {
            msgbox = jsonObject;
            if (msgbox > 0) {
                layim.msgbox(jsonObject);
            }
        } else if (jObject.msgType == "addFriend") {
            var user = jObject.data;
            if (user == "0") {
                //同意后，将好友追加到主面板
                layim.addList({
                    type: 'friend'
                    , username: user.username
                    , avatar: user.avatar
                    , groupid: "2" //所在的分组id
                    , id: user.id //好友ID
                    , sign: user.sign //好友签名
                });
            }
            msgbox = msgbox + 1;
            layim.msgbox(msgbox);
        }

    };

    //全局消息盒子
    var msgbox = 5;
    //事件名：ready，用于监听LayIM初始化就绪
    layim.on('ready', function (options) {
        // layim.msgbox(msgbox); //模拟消息盒子有新消息，实际使用时，一般是动态获得
        //console.log(options);
        //do something
        //layim.setFriendStatus(11111, 'online'); //设置指定好友在线，即头像取消置灰
        //layim.setFriendStatus("1571476959767947441", 'offline'); //设置指定好友不在线，即头像置灰
    });

    //监听聊天窗口的切换
    layim.on('chatChange', function (res) {
        layim.msgbox(6);
        var type = res.data.type;
        if (type === 'friend') {
            //layim.setChatStatus('<span style="color:#FF5722;">对方正在输入。。。</span>');
            if (res.data.status === "offline") {
                layim.setChatStatus('<span style="color:#FF5722;">离线</span>'); //模拟标注好友在线状态
            } else if (res.data.status === "online") {
                layim.setChatStatus('<span style="color:#777777;">在线</span>'); //模拟标注好友在线状态
            }
        } else if (type === 'group') {
            //模拟系统消息
            // layim.getMessage({
            //     system: true //系统消息
            //     ,id: 111111111
            //     ,type: "group"
            //     ,content: '贤心加入群聊'
            // });
        }
    });
});