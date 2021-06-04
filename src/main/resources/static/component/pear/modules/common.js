layui.define(['jquery', 'element', 'table'], function (exports) {
    "use strict";

    /**
     * 常用封装类
     * */
    var MOD_NAME = 'common',
        $ = layui.jquery,
        table = layui.table,
        element = layui.element;

    var common = new function () {

        /**
         * 获取当前表格选中字段
         * @param obj 表格回调参数
         * @param field 要获取的字段
         * */
        this.checkField = function (obj, field) {
            let data = table.checkStatus(obj.config.id).data;
            if (data.length === 0) {
                return "";
            }
            let ids = "";
            for (let i = 0; i < data.length; i++) {
                ids += data[i][field] + ",";
            }
            ids = ids.substr(0, ids.length - 1);
            return ids;
        }

        /**
         * 提交 json 数据
         * @param data 提交数据
         * @param href 提交接口
         * @param table 刷新父级表
         *
         * */
        this.submit = function (data, href, table, callback) {
            $.ajax({
                url: href,
                data: JSON.stringify(data),
                dataType: 'json',
                contentType: 'application/json',
                type: 'post',
                success: callback != null ? callback(result) : function (result) {
                    if (result.code == 0) {
                        layer.msg(result.msg, {icon: 1, time: 1000}, function () {
                            parent.layer.close(parent.layer.getFrameIndex(window.name));//关闭当前页
                            parent.layui.table.reload(table);
                        });
                    } else {
                        layer.msg(result.msg, {icon: 2, time: 1000});
                    }
                }
            })
        }

        /**
         * ajax提交
         * @param option
         *
         * */
        this.ajax = function (option, callback) {
            $.ajax({
                url: option.url ? option.url : '',
                data: option.data ? option.data : [],
                dataType: 'json',
                type: option.type ? option.type : 'get',
                success: callback != null ? callback : function (result) {
                    if (result.code == 0) {
                        layer.msg(result.msg, {icon: 1, time: 1000});
                    } else {
                        layer.msg(result.msg, {icon: 2, time: 1000});
                    }
                }
            })
        }
        this.enableMsg = function (index, data, msg) {
            setTimeout(function () {
                top.layer.close(index);
                if (data.code == 0) {
                    layer.msg(msg + "成功");
                } else {
                    layer.msg(msg + "失败");
                }
            }, 500);
        }
        this.ajaxMsg = function (res, index, refresh) {
            if (index) {
                top.layer.close(index);
            }
            if (res.code === 0) {
                layer.msg(res.msg, {icon: 1, time: 1000}, function () {
                    parent.layer.close(parent.layer.getFrameIndex(window.name));//关闭当前页
                });
                if (refresh) {
                    $.modal.refreshIframe(400)
                }
            } else {
                layer.msg(res.msg, {icon: 2, time: 1000});
            }
        }
        this.initLayIm = function () {
            var config = {
                url: 'layim/login',
                dataType: 'json',
                type: 'GET',
                success: function (result) {

                    if (result.code == 0) {
                        console.log('layIm初始化成功')
                    } else {
                        layer.msg(result.msg, {icon: 2, time: 1000});
                    }
                }
            }
            $.ajax(config)
        }
    }
    exports(MOD_NAME, common);
});
/**
 * JQuery全局扩展方法
 */
(function ($) {

    $.table = {
        /**
         * @Description [数据表格初始化函数]
         * @author xj
         * @param options [参数]
         * @return [返回值]
         * @date 2021/5/4 16:56
         */
        tableInit: function (options) {
            var tableObj;
            let table;
            layui.use(['table'], function () {
                table = layui.table;
                var opt = {
                    //分页
                    page: true,
                    //隔行变色
                    even: true,
                };
                Object.assign(opt, options);

                tableObj = table.render(opt);
            });
            return {
                //重载
                reload: function () {
                    tableObj.reload();
                },
                getTable: function () {
                    return table;
                },
                reloadOpt: function (opt) {
                    Object.assign(options, opt);
                    var result = {};
                    if (result != null) {
                        result.where = {};
                    }
                    result.where = options.where;
                    tableObj.reload({
                        where: result.where,
                        page: {
                            curr: 1
                        },
                        done: function () {
                            result = this;
                        }
                    });
                    return false;
                }
            }
        },
        tableToolClick: function (table, event, callback) {
            layui.use(['table'], function () {
                table.getTable().on('tool(' + event + ')', function (obj) {
                    if (callback) {
                        callback(obj)
                    }
                })
            });
        },
        tableClickSearch: function (table, event, filter) {
            layui.use(['table'], function () {
                var table1 = layui.table;
                table1.on('tool(' + filter + ')', function (obj) {
                    if (obj.event == event) {
                        table.reloadOpt({
                            where: {
                                cateId: obj.data.cid
                            }
                        });
                    }
                });
            });
        },
        tableInputSearch: function (table, val) {
            table.reloadOpt({
                where: {
                    title: val
                }
            });
        },
        /**
         * 初始化表格（卡片）
         * */
        cardInit: function (options) {
            layui.use(['card'], function () {
                let card = layui.card;
                //具体参数进源码可看
                var opt = {
                    elem: '#layui-table',
                    url: '../../journal/data/card.json',
                    lineSize: 2,
                    done: function () {
                        alert(1);
                    },
                    templet: function (item) {
                        var ele = '<div class="project-list-item-body">';
                        //标题
                        ele += '<h2>' + item.titleName + '</h2>';
                        //第二行
                        ele += '<div class="layui-row project-list-item-desc">';
                        //第二行左边部分
                        ele += '<div class="layui-col-sm11"><span class="time">' + item.createTime + '</span></div>';
                        //第二行右边部分
                        ele += '<div class="layui-col-sm1">' +
                            '<div class="layui-btn-group">\n' +
                            '        <button type="button" class="layui-btn layui-btn-primary layui-btn-xs">\n' +
                            '            <i class="layui-icon">&#xe642;</i>\n' +
                            '        </button>\n' +
                            '        <button type="button" class="layui-btn layui-btn-primary layui-btn-xs">\n' +
                            '            <i class="layui-icon">&#xe640;</i>\n' +
                            '        </button>\n' +
                            '</div>' +
                            '</div>';
                        ele += '</div></div>';

                        return ele;
                    }
                };
                Object.assign(opt, options);
                card.render(opt)
            });
        }
    };
    $.local = {
        //存储,可设置过期时间
        set(key, value, expires) {
            let params = {key, value, expires};
            if (expires) {
                // 记录何时将值存入缓存，毫秒级
                var data = Object.assign(params, {startTime: new Date().getTime()});
                localStorage.setItem(key, JSON.stringify(data));
            } else {
                if (Object.prototype.toString.call(value) == '[object Object]') {
                    value = JSON.stringify(value);
                }
                if (Object.prototype.toString.call(value) == '[object Array]') {
                    value = JSON.stringify(value);
                }
                localStorage.setItem(key, value);
            }
        },
        //取出
        get(key) {
            let item = localStorage.getItem(key);
            // 先将拿到的试着进行json转为对象的形式
            try {
                item = JSON.parse(item);
            } catch (error) {
                // eslint-disable-next-line no-self-assign
                item = item;
            }
            // 如果有startTime的值，说明设置了失效时间
            if (item && item.startTime) {
                let date = new Date().getTime();
                // 如果大于就是过期了，如果小于或等于就还没过期
                if (date - item.startTime > item.expires) {
                    localStorage.removeItem(name);
                    return false;
                } else {
                    return item.value;
                }
            } else {
                return item;
            }
        },
        // 删除
        remove(key) {
            localStorage.removeItem(key);
        },
        // 清除全部
        clear() {
            localStorage.clear();
        }
    }

    /**
     * sessionStorage
     */
    $.session = {
        get: function (key) {
            var data = sessionStorage[key];
            if (!data || data === "null") {
                return null;
            }
            return JSON.parse(data).value;
        },
        set: function (key, value) {
            var data = {
                value: value
            }
            sessionStorage[key] = JSON.stringify(data);
        },
        // 删除
        remove(key) {
            sessionStorage.removeItem(key);
        },
        // 清除全部
        clear() {
            sessionStorage.clear();
        }
    }
    $.modal = {
        /**
         * @Description [弹出层]
         * @author xj
         * @param title [标题]
         * @param url [路由]
         * @param width [宽]
         * @param height [高]
         * @param callbackFn [回调函数]
         * @return [返回值]
         * @date 2021/5/4 17:35
         */
        openIframe: function (title, url, width, height, callbackFn, btnList, full) {
            layui.use('layer', function () { //独立版的layer无需执行这一句
                var $ = layui.jquery, layer = layui.layer, toIndex; //独立版的layer无需执行这一句
                var opt = {
                    type: 2,
                    title: title || '',
                    area: [(width || ($(document).width() * 0.8)) + 'px', (height || ($(document).height() * 0.8)) + 'px'],
                    shade: 0.3,
                    maxmin: true,
                    content: url,
                    btn: btnList ? btnList : btnList === null ? [] : ['确认', '关闭'],
                    yes: function () {
                        //回调iframe里面的submitHandler
                        var iframeWin = $('iframe')[0].contentWindow;
                        if (iframeWin.submitHandler) {
                            var data = iframeWin.submitHandler();
                            //当return false时，不会关闭弹窗
                            if (data == false) {
                                return;
                            }
                            if (callbackFn) {
                                callbackFn(data);
                            }
                        }
                        setTimeout(function () {
                            layer.closeAll();
                        }, 1000)
                    },
                    btn2: function () {
                        layer.closeAll();
                    },
                    zIndex: layer.zIndex //重点1
                };
                toIndex = layer.open(opt);
                if (full) {
                    //弹出层最大化
                    layer.full(toIndex);
                }
            });
        },
        refreshIframe: function (loading) {
            layui.use('tab', function () {
                var tab = layui.tab;
                tab.option = {
                    elem: 'spaceContent'
                };
                $.local.set("isRefresh", true, 2000)
                tab.refreshIframe(400, loading ? loading : 1000)
                // 设置3s过期
            })

            function sleep(numberMillis) {
                var now = new Date();
                var exitTime = now.getTime() + numberMillis;
                while (true) {
                    now = new Date();
                    if (now.getTime() > exitTime)
                        return;
                }
            }
        }
    }
    $.notice = {
        option: function (notice) {
            // 注册notice提示框
            notice.options = {
                closeButton: false,//显示关闭按钮
                positionClass: "toast-top-center",//弹出的位置,
                showDuration: "300",//显示的时间
                hideDuration: "1000",//消失的时间
                timeOut: "2000",//停留的时间
                extendedTimeOut: "1000",//控制时间
                showEasing: "swing",//显示时的动画缓冲方式
                hideEasing: "linear",//消失时的动画缓冲方式
                iconClass: 'toast-info', // 自定义图标，有内置，如不需要则传空 支持layui内置图标/自定义iconfont类名
                onclick: null, // 点击关闭回调
            };
            return notice;
        },
        successNotice: function (msg) {
            layui.use('notice', function () {
                var notice = layui.notice;
                $.notice.option(notice).success(msg);
            })
        },
        errorNotice: function (msg) {
            layui.use('notice', function () {
                var notice = layui.notice;
                $.notice.option(notice).error(msg);
            })
        },
        warningNotice: function (msg) {
            layui.use('notice', function () {
                var notice = layui.notice;
                $.notice.option(notice).warning(msg);
            })
        },
        normalNotice: function (msg) {
            layui.use('notice', function () {
                var notice = layui.notice;
                $.notice.option(notice).info(msg);
            })
        },
        successMsg: function (msg, callback) {
            layui.use('popup', function () {
                var popup = layui.popup;
                popup.success(msg, callback);
            })
        },
        errorMsg: function (msg, callback) {
            layui.use('popup', function () {
                var popup = layui.popup;
                popup.failure(msg, callback);

            })
        },
        warningMsg: function (msg, callback) {
            layui.use('popup', function () {
                var popup = layui.popup;
                popup.warning(msg, callback);
            })
        }
    }
    $.dragMove = {
        init: function (elem, area, shade, move, zoom) {
            layui.use('dragMove', function () {
                var dragMove = layui.dragMove;
                //执行示例
                dragMove.render({
                    elem: "#" + elem, //指向图片的父容器
                    layerArea: area ? area : ["960px", "720px"],
                    layerShade: shade ? shade : 0.6, //遮罩的透明度，同layer的shade，默认0.6
                    layerMove: move ? move : false, //触发拖动的元素，同layer的move，这里默认禁止，可设置为'.layui-layer-title'
                    maxZoom: zoom ? zoom : 3 //图片能放大的最大倍数，默认1倍
                });
            })
        }
    }
})(jQuery)
