layui.use('layim', function (layim) {
    let $ = layui.jquery;
    // 阻止浏览器默认右键点击事件
    document.oncontextmenu = function () {
        return false;
    }
    // 单击聊天主界面事件
    $('body').on('click', '.layui-layim', function (e) {
        emptyTips();
    });
    // 右击聊天主界面事件
    $('body').on('mousedown', '.layui-layim', function (e) {
        emptyTips();
    });
    /* 监听鼠标滚轮事件 */
    $('body').on('mousewheel DOMMouseScroll', '.layim-tab-content', function (e) {
        emptyTips();
    });
    /* 绑定好友右击事件 */
    $('body').on('mousedown', '.layim-list-friend li ul li', function (e) {
        // 清空所有右击弹框
        emptyTips();
        if (3 != e.which) {
            return;
        }
        // 不再派发事件
        e.stopPropagation();
        var othis = $(this);
        if (othis.hasClass('layim-null')) return;
        // 移除所有选中的样式
        $('.layim-list-friend li ul li').removeAttr("style", "");
        // 标注为选中
        othis.css({'background-color': 'rgba(0,0,0,.05)'});
        var item = $(this).data('item').split('//'),
            mineId = item[0],
            u_name = item[1],
            u_status = item[2],
            u_avatar = item[3],
            sign = item[4];

        var uid = Date.now().toString(36);
        var space_icon = '  ';
        var space_text = '      ';
        var html = [
            '<ul id="contextmenu_' + uid + '" data-id="' + mineId + '" data-index="' + mineId + '" data-username="' + u_name + '" data-sign="' + sign + '" data-status="' + u_status + '" data-avatar="' + u_avatar + '" data-mold="1">',
            '<li data-type="menuChat"><i class="layui-icon" ></i>' + space_icon + '发送即时消息</li>',
            '<li data-type="intoSpace"><i class="layui-icon" >&#xe68e;</i>' + space_icon + '进入空间</li>',
            '<li data-type="menuDelete"><i class="layui-icon" ></i>' + space_text + '删除好友</li>',
            '<li data-type="menuMoveto"><i class="layui-icon" >&#xe66b;</i>' + space_text + '移动至</li></ul>'
        ].join('');
        layer.tips(html, othis, {
            tips: 1
            , time: 0
            , shift: 5
            , fix: true
            , skin: 'ayui-box layui-layim-contextmenu'
            , success: function (layero) {
                var liCount = (html.split('</li>')).length;
                var stopmp = function (e) {
                    stope(e);
                };
                layero.off('mousedowm', stopmp).on('mousedowm', stopmp);
                var layerobj = $('#contextmenu_' + uid).parents('.layui-layim-contextmenu');
                // 移动弹框位置
                var top = layerobj.css('top').toLowerCase().replace('px', '');
                var left = layerobj.css('left').toLowerCase().replace('px', '');
                top = getTipTop(1, top, liCount);
                left = 30 + parseInt(left);
                layerobj.css({'width': '150px', 'left': left + 'px', 'top': top + 'px'});
                $('.layui-layim-contextmenu li').css({'padding-left': '18px'});
            }
        });
    });

    // 清空所有右击弹框
    var emptyTips = function () {
        // 移除所有好友选中的样式
        $('.layim-list-friend li ul li').removeAttr("style", "");
        // 移除所有群组选中的样式
        $('.layim-list-group li').removeAttr("style", "");
        // 关闭右键菜单
        layer.closeAll('tips');
    };

    // 获取窗口的文档显示区的高度
    var currentHeight = getViewSizeWithScrollbar();

    function getViewSizeWithScrollbar() {
        var clientHeight = 0;
        if (window.innerWidth) {
            clientHeight = window.innerHeight;
        } else if (document.documentElement.offsetWidth == document.documentElement.clientWidth) {
            clientHeight = document.documentElement.offsetHeight;
        } else {
            clientHeight = document.documentElement.clientHeight + getScrollWith();
        }
        clientHeight = clientHeight - 180;
        return clientHeight;
    }

    /**
     *计算tip定位的高度
     * @param type 类型(1好友、群组，2分组)
     * @param top 原弹框高度
     * @param liCount 弹框层中li数量
     */
    var getTipTop = function (type, top, liCount) {
        liCount--;
        if (top > (currentHeight - 45 * liCount)) {
            top = parseInt(top) - 45;
        } else {
            if (type == 1) {
                top = parseInt(top) + 30 * liCount - 10;
            } else {
                top = parseInt(top) + 30 * (liCount - 1);
            }
        }
        return top;
    };

    // 绑定右击菜单中选项的点击事件
    var active = {
        //发送即时消息
        menuChat: function () {
            var mineId = $(this).parent().data('id');
            var username = $(this).parent().data('username');
            var status = $(this).parent().data('status');
            var avatar = $(this).parent().data('avatar');
            var moldId = $(this).parent().data('mold');
            layim.chat({
                type: moldId == 1 ? "friend" : "group",
                name: username,
                avatar: avatar,
                id: mineId,
                status: status
            });
        },
        // 查看资料
        menuProfile: function () {
            debugger
        },
        intoSpace: function () {
            top.location.href="http://localhost:8000/index?isVisitor=" + accountId + "_" + $(this).parent().data("id")
        },
        // 移动分组
        menuMoveto: function () {
            var mineId = $(this).parent().data('id');
            var username = $(this).parent().data('username');
            var avatar = $(this).parent().data('avatar');
            var sign = $(this).parent().data('sign');
            var status = $(this).parent().data('status');
            layim.setFriendGroup({
                title: '移动至',
                type: 'friend',
                username: username,
                avatar: avatar,
                group: layim.cache().friend,
                submit: function (group, index) {
                    var friend = {
                        id: mineId,
                        username: username,
                        avatar: avatar,
                        groupId: group,
                        sign: sign,
                        status: status
                    };
                    top.addFriendList(friend);
                    // 同步到数据库
                    $.post('/layim/updateFriendToGroup', {
                        fid: mineId,
                        gId: group
                    }, function (res) {
                        if (res.code == '0') {
                            layer.msg('移动成功', {icon: 1});
                            // active.refreshFriendsGroupList();
                        } else {
                            layer.msg('移动失败', {icon: 2});
                        }
                        parent.layer.close(layer.index - 1)
                    })
                }
            })
        },
        // 添加分组
        menuInsert: function () {
            var li = '<li id="removeLi"><h5 layim-event="spread" lay-type="undefined"><i class="layui-icon"></i><span id="newGroup" contenteditable="">新分组</span></h5></li>'
            $(".layim-list-friend").append(li);
            setTimeout(function () {
                // var t = $("#newGroup").val();
                // $("#newGroup").val("").focus().val(t);
                keepLastIndex("newGroup")
            }, 500);
            if ($('#newGroup').parent().attr('lay-type')) {
                $("#newGroup").css('outline', '0');
            }
            $("#newGroup").on('blur', function () {
                // 获取分组名称
                var gname = $("#newGroup").text();
                // 删除预设分组
                $("#removeLi").remove();
                // 发送添加分组请求到后台
                groupBlur($, layim, {
                    groupname: gname
                }, 'addFriendGroup', '添加分组');
            })
        },
        refreshFriendsGroupList: function () {
            // 获取好友分组
            $.ajax({
                url: '/layim/refreshFriendGroupList',
                dataType: 'json',
                type: 'get',
                success: function (res) {
                    if (res.code == '0') {
                        layim.cache().friend = [];
                        var friendCache = layim.cache().friend;
                        $(".layim-list-friend").empty();
                        var friends = [];
                        $.each(res.data, function (idx, item) {
                            var g = {
                                "id": item.id,
                                "groupname": item.groupname,
                                'accountId': item.accountId,
                                list: []
                            };
                            console.log("aaa=" + item.list)
                            // 往主面板添加分组
                            top.addFriendGroup(g);
                            // 更新缓存
                            friendCache.push(g)
                            friends.push({
                                list: item.list
                            })
                        });
                        layim.cache().friend = friendCache;
                        // 在添加好友
                        if (friends.length > 0) {
                            $.each(friends, function (idx, item) {
                                if (item.list != null) {
                                    item.list.forEach(it => {
                                        top.addFriendList(it);
                                    });
                                }
                            })
                        }
                    } else {
                        layer.msg('刷新失败', {icon: 2});
                    }
                }
            });
        },
        // 创建群聊
        createGroup: function () {
            layer.alert("创建群聊")
        },
        // 删除分组
        menuRemove: function () {
            var id = $(this).parent().data('id');
            console.log(id)
            layer.confirm('确定删除该分组吗?', {icon: 3, title: '提示'}, function (index) {
                $.ajax({
                    url: '/layim/removeFriendGroup/' + id,
                    dataType: 'json',
                    type: 'post',
                    success: function (res) {
                        if (res.code == '0') {
                            layer.msg('删除成功', {icon: 1});
                            active.refreshFriendsGroupList();
                        } else {
                            layer.msg('删除失败', {icon: 2});
                        }
                    }
                });
                layer.close(index);
            });
        },
        // 重命名
        menuRename: function () {
            var id = $(this).parent().data('id');
            $("#group" + id).attr("contenteditable", "");
            $("#group" + id).css('outline', '0');
            keepLastIndex("group" + id);
            $("#group" + id).on('blur', function () {
                // 获取分组名称
                var gname = $("#group" + id).text();
                // 删除预设分组
                $("#removeLi").remove();
                // 发送添加分组请求到后台
                groupBlur($, layim, {
                    groupname: gname,
                    id: id
                }, 'updateFriendGroup', '分组重命名');
            });
            // $.ajax({
            //     url: '/layim/removeFriendGroup/' + id,
            //     dataType: 'json',
            //     type: 'post',
            //     success: function (res) {
            //         if (res.code == '0') {
            //             layer.msg('删除成功', {icon: 1});
            //             active.refreshFriendsGroupList();
            //         } else {
            //             layer.msg('删除失败', {icon: 2});
            //         }
            //     }
            // });
        },
        // 消息记录
        menuHistory: function () {
            var mineId = $(this).parent().data('id');
            var moldId = $(this).parent().data('mold');
            console.log(mineId);
        }
    };
    $('body').on('click', '.layui-layer-tips li', function (e) {
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
        // 清空所有右击弹框
        emptyTips();
    });

    /* 绑定好友列表空白地方右击事件 */
    $('body').on('mousedown', '.layim-list-friend', function (e) {
        // 清空所有右击弹框
        emptyTips();
        if (3 != e.which) {
            return;
        }
        // 不再派发事件
        e.stopPropagation();

        var othis = $(this);
        if (othis.hasClass('layim-null')) return;

        var uid = Date.now().toString(36);
        var space_icon = '  ';
        var space_text = '      ';
        // var html = [
        //     '<ul id="contextmenu_' + uid + '" data-id="' + 1 + '" data-index="' + 1 + '">',
        //     '<li data-type="menuReset"><i class="layui-icon" >&#xe669;</i>'+space_icon+'刷新好友列表</li>',
        //     '<li data-type="menuInsert">'+space_text+'添加分组</li>',
        //     '<li data-type="menuRename">'+space_text+'重命名</li>',
        //     '<li data-type="menuRemove" data-mold="1">'+space_text+'删除分组</li></ul>',
        // ].join('');
        var html = [
            '<ul id="contextmenu_' + uid + '">',
            '<li data-type="refreshFriendsGroupList"><i class="layui-icon" ></i>' + space_icon + '刷新好友列表</li>',
            '<li data-type="menuInsert">' + space_text + '添加分组</li></ul>',
        ].join('');

        layer.tips(html, othis, {
            tips: 1
            , time: 0
            , shift: 5
            , fix: true
            , skin: 'ayui-box layui-layim-contextmenu'
            , success: function (layero) {
                var liCount = (html.split('</li>')).length;
                var stopmp = function (e) {
                    stope(e);
                };
                layero.off('mousedowm', stopmp).on('mousedowm', stopmp);
                var layerobj = $('#contextmenu_' + uid).parents('.layui-layim-contextmenu');
                var top = e.pageY;
                var left = e.pageX;
                var screenWidth = window.screen.width;
                // 根据实体情况调整位置
                if (screenWidth - left > 150) {
                    left = left - 30;
                } else if (screenWidth - left < 110) {
                    left = left - 180;
                } else {
                    left = left - 130;
                }
                if (top > 816) {
                    top = top - 140;
                } else {
                    top = top - 60;
                }
                layerobj.css({'width': '150px', 'left': left + 'px', 'top': top + 'px'});
                $('.layui-layim-contextmenu li').css({'padding-left': '18px'});
            }
        });
    });

    /* 绑定分组右击事件 */
    $('body').on('mousedown', '.layim-list-friend li h5', function (e) {
        // 清空所有右击弹框
        emptyTips();
        if (3 != e.which) {
            return;
        }
        // 不再派发事件
        e.stopPropagation();
        var othis = $(this);
        if (othis.hasClass('layim-null')) return;
        var groupId = othis.attr('groupid');
        var uid = Date.now().toString(36);
        var space_icon = '&nbsp;&nbsp;';
        var space_text = '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
        var html = [
            '<ul id="contextmenu_' + uid + '" data-id="' + groupId + '" data-index="' + groupId + '">',
            '<li data-type="refreshFriendsGroupList"><i class="layui-icon" >&#xe669;</i>' + space_icon + '刷新好友列表</li>',
            // '<li data-type="menuOnline"><i class="layui-icon">&#x1005;</i>'+space_icon+'显示在线好友</li>',
            '<li data-type="menuInsert">' + space_text + '添加分组</li>',
            '<li data-type="menuRename">' + space_text + '重命名</li>',
            '<li data-type="menuRemove" data-mold="1">' + space_text + '删除分组</li></ul>',
        ].join('');
        layer.tips(html, othis, {
            tips: 1
            , time: 0
            , shift: 5
            , fix: true
            , skin: 'ayui-box layui-layim-contextmenu'
            , success: function (layero) {
                var liCount = (html.split('</li>')).length;
                var stopmp = function (e) {
                    stope(e);
                };
                layero.off('mousedowm', stopmp).on('mousedowm', stopmp);
                var layerobj = $('#contextmenu_' + uid).parents('.layui-layim-contextmenu');
                // 移动弹框位置
                var top = layerobj.css('top').toLowerCase().replace('px', '');
                var left = layerobj.css('left').toLowerCase().replace('px', '');
                top = getTipTop(2, top, liCount);
                left = 30 + parseInt(left);
                layerobj.css({'width': '150px', 'left': left + 'px', 'top': top + 'px'});
                $('.layui-layim-contextmenu li').css({'padding-left': '18px'});
            }
        });
    });

    /* 绑定群聊右击事件 */
    $('body').on('mousedown', '.layim-list-group li', function (e) {
// 清空所有右击弹框
        emptyTips();
        if (3 != e.which) {
            return;
        }
// 不再派发事件
        e.stopPropagation();
        var othis = $(this);
        if (othis.hasClass('layim-null')) return;
// 移除所有选中的样式
        $('.layim-list-group li').removeAttr("style", "");
// 标注为选中
        othis.css({'background-color': 'rgba(0,0,0,.05)'});
        var mineId = $(this).data('mineid');
        var uid = Date.now().toString(36);
        var space_icon = '&nbsp;&nbsp;';
        var space_text = '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
        var html = [
            '<ul id="contextmenu_' + uid + '" data-id="' + mineId + '" data-index="' + mineId + '" data-mold="2">',
            '<li data-type="menuChat"><i class="layui-icon" >&#xe611;</i>' + space_icon + '发送群消息</li>',
            '<li data-type="menuProfile"><i class="layui-icon">&#xe60a;</i>' + space_icon + '查看群资料</li>',
            '<li data-type="menuHistory"><i class="layui-icon" >&#xe60e;</i>' + space_icon + '消息记录</li>',
            '<li data-type="menuUpdate">' + space_text + '修改群图标</li>',
            '<li data-type="menuRemove" data-mold="2">' + space_text + '解散该群</li>',
            '<li data-type="menuSecede">' + space_text + '退出该群</li></ul>',
        ].join('');
        layer.tips(html, othis, {
            tips: 1
            , time: 0
            , shift: 5
            , fix: true
            , skin: 'ayui-box layui-layim-contextmenu'
            , success: function (layero) {
                var liCount = (html.split('</li>')).length;
                var stopmp = function (e) {
                    stope(e);
                };
                layero.off('mousedowm', stopmp).on('mousedowm', stopmp);
                var layerobj = $('#contextmenu_' + uid).parents('.layui-layim-contextmenu');
                // 移动弹框位置
                var top = layerobj.css('top').toLowerCase().replace('px', '');
                var left = layerobj.css('left').toLowerCase().replace('px', '');
                top = getTipTop(1, top, liCount);
                left = 30 + parseInt(left);
                layerobj.css({'width': '150px', 'left': left + 'px', 'top': top + 'px'});
                $('.layui-layim-contextmenu li').css({'padding-left': '18px'});
            }
        });
        /* 绑定群聊空白地方右击事件 */
        $('body').on('mousedown', '.layim-list-groups', function (e) {
// 清空所有右击弹框
            emptyTips();
            if (3 != e.which) {
                return;
            }
// 不再派发事件
            e.stopPropagation();

            var othis = $(this);
            if (othis.hasClass('layim-null')) return;
            var uid = Date.now().toString(36);
            var space_icon = '&nbsp;&nbsp;';
            var space_text = '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
            var html = [
                '<ul id="contextmenu_' + uid + '">',
                '<li data-type="menuResetGroup"><i class="layui-icon" >&#xe669;</i>' + space_icon + '刷新群聊列表</li>',
                '<li data-type="createGroup"&gt;' + space_text + '创建群聊</li></ul>',
            ].join('');
            layer.tips(html, othis, {
                tips: 1
                , time: 0
                , shift: 5
                , fix: true
                , skin: 'ayui-box layui-layim-contextmenu'
                , success: function (layero) {
                    var liCount = (html.split('</li>')).length;
                    var stopmp = function (e) {
                        stope(e);
                    };
                    layero.off('mousedowm', stopmp).on('mousedowm', stopmp);
                    var layerobj = $('#contextmenu_' + uid).parents('.layui-layim-contextmenu');
                    var top = e.pageY;
                    var left = e.pageX;
                    var screenWidth = window.screen.width;
                    if (screenWidth - left > 150) {
                        left = left - 30;
                    } else if (screenWidth - left < 110) {
                        left = left - 180;
                    } else {
                        left = left - 130;
                    }
                    if (top > 816) {
                        top = top - 140;
                    } else {
                        top = top - 60;
                    }
                    layerobj.css({'width': '150px', 'left': left + 'px', 'top': top + 'px'});
                    $('.layui-layim-contextmenu li').css({'padding-left': '18px'});
                }
            });
        });
    });

    /* 绑定群聊空白地方右击事件 */
    $('body').on('mousedown', '.layim-list-group', function (e) {
        // 清空所有右击弹框
        emptyTips();
        if (3 != e.which) {
            return;
        }
        // 不再派发事件
        e.stopPropagation();

        var othis = $(this);
        if (othis.hasClass('layim-null')) return;

        var uid = Date.now().toString(36);
        var space_icon = '  ';
        var space_text = '      ';
        // var html = [
        //     '<ul id="contextmenu_' + uid + '" data-id="' + 1 + '" data-index="' + 1 + '">',
        //     '<li data-type="menuReset"><i class="layui-icon" >&#xe669;</i>'+space_icon+'刷新好友列表</li>',
        //     '<li data-type="menuInsert">'+space_text+'添加分组</li>',
        //     '<li data-type="menuRename">'+space_text+'重命名</li>',
        //     '<li data-type="menuRemove" data-mold="1">'+space_text+'删除分组</li></ul>',
        // ].join('');
        var html = [
            '<ul id="contextmenu_' + uid + '">',
            '<li data-type="createGroup">' + space_text + '创建群聊</li></ul>',
        ].join('');

        layer.tips(html, othis, {
            tips: 1
            , time: 0
            , shift: 5
            , fix: true
            , skin: 'ayui-box layui-layim-contextmenu'
            , success: function (layero) {
                var liCount = (html.split('</li>')).length;
                var stopmp = function (e) {
                    stope(e);
                };
                layero.off('mousedowm', stopmp).on('mousedowm', stopmp);
                var layerobj = $('#contextmenu_' + uid).parents('.layui-layim-contextmenu');
                var top = e.pageY;
                var left = e.pageX;
                var screenWidth = window.screen.width;
                // 根据实体情况调整位置
                if (screenWidth - left > 150) {
                    left = left - 30;
                } else if (screenWidth - left < 110) {
                    left = left - 180;
                } else {
                    left = left - 130;
                }
                if (top > 816) {
                    top = top - 140;
                } else {
                    top = top - 60;
                }
                layerobj.css({'width': '150px', 'left': left + 'px', 'top': top + 'px'});
                $('.layui-layim-contextmenu li').css({'padding-left': '18px'});
            }
        });
    });

    /**
     * 分组重命名
     * @param $
     * @param layim
     * @param data
     * @param url
     * @param msg
     */
    function groupBlur($, layim, data, url, msg) {
        $.ajax({
            url: '/layim/' + url,
            data: data,
            dataType: 'json',
            type: 'post',
            success: function (res) {
                if (res.code == '0') {
                    layer.msg(msg + '成功', {icon: 1});
                    active.refreshFriendsGroupList()
                } else {
                    layer.msg(msg + '失败', {icon: 2});
                }
            }
        })
    }
});

function keepLastIndex(obj) {
    var esrc = document.getElementById(obj);
    var range = document.createRange();
    range.selectNodeContents(esrc);
    range.collapse(false);
    var sel = window.getSelection();
    sel.removeAllRanges();
    sel.addRange(range);
}
