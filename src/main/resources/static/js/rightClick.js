layui.use('layim',function (layim) {
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
        var mineId = $(this).data('mineid');
        var uid = Date.now().toString(36);
        var space_icon = '  ';
        var space_text = '      ';
        var html = [
            '<ul id="contextmenu_' + uid + '" data-id="' + mineId + '" data-index="' + mineId + '" data-mold="1">',
            '<li data-type="menuChat"><i class="layui-icon" ></i>' + space_icon + '发送即时消息</li>',
            '<li data-type="menuProfile"><i class="layui-icon"></i>' + space_icon + '查看资料</li>',
            '<li data-type="menuHistory"><i class="layui-icon" ></i>' + space_icon + '消息记录</li>',
            '<li data-type="menuDelete">' + space_text + '删除好友</li>',
            '<li data-type="menuMoveto">' + space_text + '移动至</li></ul>'
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
        menuChat: function () {
            /*发送即时消息*/
            var mineId = $(this).parent().data('id');
            var moldId = $(this).parent().data('mold');
            console.log(mineId);
            layim.chat({
                type: moldId == 1 ? "friend" : "group",
                name: '小焕',
                avatar: '好友头像，实际应用动态绑定',
                id: mineId,
                status: '好友当前离线状态'
            });
        },
        menuHistory: function () {
            /*消息记录*/
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
        var html = [
            '<ul id="contextmenu_' + uid + '">',
            '<li data-type="menuReset"><i class="layui-icon" ></i>' + space_icon + '刷新好友列表</li>',
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
});