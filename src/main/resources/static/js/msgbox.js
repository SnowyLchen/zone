layui.use(['layim', 'flow'], function(){
    var layim = layui.layim
        ,layer = layui.layer
        ,laytpl = layui.laytpl
        ,$ = layui.jquery
        ,flow = layui.flow;

    var cache = {}; //用于临时记录请求到的数据

    //请求消息
    var renderMsg = function(page, callback){

        //实际部署时，请将下述 getmsg.json 改为你的接口地址
        $.ajax({
            type: 'get',
            url: basePath + '/layim/add/ask/' + userid,
            dataType: 'json',
            contentType: "application/json;charset=UTF-8",
            beforeSend: function () {
                layer.load(1, { //icon支持传入0-2
                    content: '查询中...',
                    success: function (layero) {
                        layero.find('.layui-layer-content').css({
                            'padding-top': '39px',
                            'width': '60px'
                        });
                    }
                });
            },
            complete: function () {
                layer.closeAll('loading');
            },
            success: function (res) {

                console.log(res)
                callback && callback(res, 1);
            },
            error: function (err) {
                console.log("err:", err);
            }
        });

        // $.get('getmsg.json', {
        //     page: page || 1
        // }, function(res){
        //     if(res.code != 0){
        //         return layer.msg(res.msg);
        //     }
        //
        //     //记录来源用户信息
        //     layui.each(res.data, function(index, item){
        //         cache[item.from] = item.user;
        //     });
        //
        //     callback && callback(res.data, res.pages);
        // });
    };

    //消息信息流
    flow.load({
        elem: '#LAY_view' //流加载容器
        ,isAuto: false
        ,end: '<li class="layim-msgbox-tips">暂无更多新消息</li>'
        ,done: function(page, next){ //加载下一页
            renderMsg(page, function(data, pages){
                console.log(data)
                console.log(page)
                console.log(pages)
                var html = laytpl(LAY_tpl.value).render({
                    data: data
                    ,page: page
                });
                next(html, page < pages);
            });
        }
    });

    //打开页面即把消息标记为已读
    /*
    $.post('/message/read', {
      type: 1
    });
    */

    //操作
    var active = {
        //同意
        agree: function(othis){
            var li = othis.parents('li')
                ,id = li.data('id');
            layer.confirm('确定拒绝吗？', function(index){
                $.post('/layim/add/agree', {
                    id: id //对方用户ID
                }, function(res){
                    //将好友追加到主面板
                    parent.layui.layim.addList({
                        type: 'friend'
                        ,avatar: res.avatar //好友头像
                        ,username: res.username //好友昵称
                        ,groupid: "2" //所在的分组id
                        ,id: res.id //好友ID
                        ,sign: res.sign //好友签名
                    });
                    layer.closeAll();
                    othis.parent().html('已同意');
                });
            });
        }


        //拒绝
        ,refuse: function(othis){
            var li = othis.parents('li')
                ,id = li.data('id');
            layer.confirm('确定拒绝吗？', function(index){
                $.post('/layim/add/refuse', {
                    id: id //对方用户ID
                }, function(res){
                    if(res){
                        layer.closeAll();
                        othis.parent().html('已拒绝');
                    }
                });
            });
        }
    };

    $('body').on('click', '.layui-btn', function(){
        var othis = $(this), type = othis.data('type');
        active[type] ? active[type].call(this, othis) : '';
    });
});