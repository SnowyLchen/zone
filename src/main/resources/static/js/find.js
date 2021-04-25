//接入WebSocket
var socket = new WebSocket('ws://localhost:8000/websocket/' + accountId + "/addAsk");
//打开事件
socket.onopen = function () {
    console.log("查找好友/群 Socket 已打开");
    //socket.send("这是来自客户端的消息" + location.href + new Date());
};


function addAsk() {
    var fromId = $("#friend").val();
    var content = $("#content").val();
    var type = $("#type").val();
    var object = new Object();
    if (type == "0") {
        object["content"] = "申请添加你为好友";
        object["uid"] = fromId;
    } else {
        object["content"] = "申请添加群";
        object["fromGroup"] = fromId;

    }
    object["from"] = accountId;
    object["remark"] = content;
    object["type"] = type;
    object["msgType"] = "addAsk";
    var jsonData = JSON.stringify(object);
    // 发送给websocket
    socket.send(jsonData);
}

layui.use(['layim', 'laypage'], function () {
    var layim = layui.layim
        , layer = layui.layer
        , laytpl = layui.laytpl
        , $ = layui.jquery
        , laypage = layui.laypage;

    //一些添加好友请求之类的交互参见文档

});