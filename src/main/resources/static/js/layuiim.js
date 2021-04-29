var isLogin = localStorage.getItem('isLogin') === '1';
var wsUrl = 'ws://localhost:8000/websocket/' + accountId;
console.log('登录?' + isLogin);
//开始创建webSocket连接
var socket = createWebSocket(wsUrl);

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
        //,title: 'WebIM' //自定义主面板最小化时的标题
        ,right: '10px' //主面板相对浏览器右侧距离
        ,minRight: '90px' //聊天面板最小化时相对浏览器右侧距离
        // ,initSkin: '5.jpg' //1-5 设置初始背景
        //,skin: ['aaa.jpg'] //新增皮肤
        // ,isfriend: true //是否开启好友
        // ,isgroup: true //是否开启群组
        // ,min: true //是否始终最小化主面板，默认false
        //,voice: false //声音提醒，默认开启，声音文件为：default.mp3
        ,morseClick:true
    });
    // //删除本地聊天数据
    layui.data('layim', {key: accountId, remove: true});
    //监听发送的消息
    layim.on('sendMessage', function (res) {
        console.log(res)
        var mine = res.mine; //包含发送的消息及登录用户信息
        var to = res.to;
        var object = {};
        object["username"] = mine.username;
        object["avatar"] = mine.avatar;
        object["id"] = mine.id;
        object["loginName"] = mine.loginName;
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
        heartCheck.reset();
    };

    //全局消息盒子
    var msgbox = 5;
    //事件名：ready，用于监听LayIM初始化就绪
    layim.on('ready', function (options) {
        localStorage.setItem('isLogin', '0')
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
            // layim.setChatStatus('<span style="color:#FF5722;">对方正在输入。。。</span>');
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
    //监听在线、隐身按钮
    layim.on('online', function (res) {
        console.log(res)
        var mine = res.j.mine; //包含发送的消息及登录用户信息
        var object = {};
        object["username"] = mine.username;
        object["id"] = mine.id;
        object["loginName"] = mine.loginName;
        object["status"] = res.status;
        object["msgType"] = "statusChange";
        var jsonData = JSON.stringify(object);
        // 发送给websocket
        socket.send(jsonData);
    });
    layim.on('sign', function(value){
        console.log(value); //获得新的签名

        //此时，你就可以通过Ajax将新的签名同步到数据库中了。
    });
    layim.on('setSkin', function(filename, src){
        console.log(filename); //获得文件名，如：1.jpg
        console.log(src); //获得背景路径，如：http://res.layui.com/layui/src/css/modules/layim/skin/1.jpg
    });
    layim.on('contextmenu', function(res){
        console.log(res)
    });
    $(function () {
        //好友右键菜单
        $('#layui-layim-main').find('.layim-list-friend').on('contextmenu', '.layui-layim-list li', function (e) {
            debugger
            var othis = $(this);
            var id = othis[0].id;
            var html = '<ul id="contextmenu_' + othis[0].id + '" data-id="' + othis[0].id + '" data-index="' + othis.data('index') + '">';
            html += '<li layim-event="menu_chat"><i class="layui-icon" >&#xe611;</i>' + space_icon + '发送即时消息</li>';
            html += '<li layim-event="menu_profile"><i class="layui-icon">&#xe60a;</i>' + space_icon + '查看资料</li>';
            html += '<li layim-event="menu_history"><i class="layui-icon" >&#xe60e;</i>' + space_icon + '消息记录</li>';
            html += '<li layim-event="menu_nomsg">' + space_no_icon + '屏蔽消息</li>';
            html += '<li layim-event="menu_delete">' + space_no_icon + '删除好友</li>';
            html += '<li layim-event="menu_moveto">' + space_no_icon + '移动至</li></ul>';
            if (othis.hasClass('layim-null')) return;
            //注意一下这个方法
            showtips(html, this, id, 0, 0);
            //showtips(html, this, id, 160, 25);

        });
    })
});
