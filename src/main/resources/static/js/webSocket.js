//接入WebSocket
var socket
//避免重复链接
var lockReconnect = false,
    // 时间间隔
    timeInterval;
//创建socket连接
var createWebSocket = function (wsUrl) {
    try {
        //成功
        socket = new WebSocket(wsUrl);
        webSocketInit();//初始化webSocket连接函数
        return socket;
    } catch (e) {
        //失败
        console.log('catch');
        //重连函数
        webSocketReconnect(wsUrl);
        return null;
    }
};
//初始化方法，成功后执行
var webSocketInit = function () {
    //连接关闭函数
    socket.onclose = function () {
        console.log("连接已关闭...");
        webSocketReconnect(wsUrl)//如果连接关闭则重连
    };
    //连接错误函数
    socket.onerror = function () {
        console.log("连接错误...");
        webSocketReconnect(wsUrl)//如果连接错误则重连
    };
    //连接建立,发送信息
    socket.onopen = function () {
        console.log("webSocket 启动" + new Date());
        //心跳检测启动
        heartCheck.start();//业务发送之后启动心跳检测机制
    };
};

var webSocketReconnect = function (url) {
    console.log("webSocket 连接断开，正在尝试重新建立连接");
    if (lockReconnect) {
        return;
    }
    lockReconnect = true;
    //没连接上会一直重连，设置延迟，避免请求过多
    //中清楚setTimeout的定时触发设置，之所以加个timer，是为了方便第二次赋值给timer。
    // 也就是说直接clearTImeout（timer）则timer就不存在了 再次访问就是error了。
    // 而timer&&clearTimeout(timer)则将timer 变成undefined
    timeInterval && clearTimeout(timeInterval);
    timeInterval = setTimeout(function () {
        createWebSocket(url);
    }, 4000)
};

/**
 * 心跳检测
 * 这里选择30秒，倒计时30秒内无操作则进行一次访问，有操作则重置计时器
 封装为键值对的形式，成为js对象，与json很相似
 */
var heartCheck = {
    timeout: 30000,//30秒
    timeoutObj: null,
    reset: function () {//接收成功一次推送，就将心跳检测的倒计时重置为30秒
        clearTimeout(this.timeoutObj);//重置倒计时
        this.start();
    },
    start: function () {//启动心跳检测机制，设置倒计时30秒一次
        this.timeoutObj = setTimeout(function () {
            var message = {
                "msgType": "heartCheck",
                "service": "心跳一次 --->" + new Date()
            };
            // JSON.stringify()的作用是将 JavaScript 对象转换为 JSON 字符串
            //而JSON.parse()可以将JSON字符串转为一个对象。
            console.log("心跳一次");
            socket.send(JSON.stringify(message));//启动心跳
        }, this.timeout)
    }
};