//接入WebSocket
var wsUrl = 'ws://' + basePath.split('//')[1] + '/websocket/' + accountId;
// var socket = new WebSocket('ws://192.168.1.194:8000/websocket/' + accountId + "/addAsk");
//开始创建webSocket连接
var socket = createWebSocket(wsUrl);
//打开事件
socket.onopen = function () {
    console.log("查找好友/群 Socket 已打开");
    //socket.send("这是来自客户端的消息" + location.href + new Date());
};

/**
 * 添加好友
 */
function addAsk(ts) {
    var myBut = $(ts);
    // 弹出添加好友验证界面
    top.openFriend(myBut);
}
