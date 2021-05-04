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
        this.ajaxMsg = function (res, index) {
            top.layer.close(index);
            if (res.code === 0) {
                layer.msg(res.msg, {icon: 1, time: 1000}, function () {
                    parent.layer.close(parent.layer.getFrameIndex(window.name));//关闭当前页
                });
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
            layui.use(['table'], function () {
                let table = layui.table;
                var opt = {
                    //默认元素
                    elem: '#layui-table',
                    //列字段
                    cols: [
                        [
                            {
                                type: 'checkbox',
                            },
                            {
                                field: 'titleName',
                                align: 'left',
                                // width: 900
                            },
                            {
                                field: 'createTime',
                                align: 'right',
                            },
                            {
                                align: 'right',
                                templet: '#oper'
                            }
                        ]
                    ],
                    url: '../../journal/data/table.json',
                    //分页
                    page: true,
                    //隔行变色
                    even: true,
                };
                Object.assign(opt, options);
                table.render(opt);
            })
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
                        ele += '<div class="layui-col-sm4"><span class="time">' + item.createTime + '</span></div>';
                        //第二行右边部分
                        ele += '<div class="layui-col-sm3 layui-col-sm-offset5">' +
                            '<div class="layui-btn-group">\n' +
                            '        <button type="button" class="layui-btn layui-btn-primary layui-btn-xs">\n' +
                            '            <i class="layui-icon">&#xe642;</i>编辑\n' +
                            '        </button>\n' +
                            '        <button type="button" class="layui-btn layui-btn-primary layui-btn-xs">\n' +
                            '            <i class="layui-icon">&#xe640;</i>删除\n' +
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
    $.ul = {
        /**
         * @Description [列表]
         * @author xj
         * @param options [参数]
         * @return [返回值]
         * @date 2021/5/4 17:10
         */
        ulInit: function (options) {
            var opt = {
                //元素
                elem: '#layui-ul',
                //数据
                data: [{
                    'logName': '日志1'
                }, {
                    'logName': '日志2'
                }, {
                    'logName': '日志3'
                }, {
                    'logName': '日志4'
                }, {
                    'logName': '日志5'
                }],
                templet: function (v) {
                    return '<div class="layui-li">' + v.logName + '</div>';
                },
                //隔行换色
                even: false,
            };

            Object.assign(opt, options);

            /**
             * 初始化
             */
            function init() {
                var $ul = $(opt.elem);
                $ul.css('text-align', 'left');
                opt.data.forEach(function (value, index, array) {
                    $ul.append('<li style="padding: 5px">' + opt.templet(value) + '</li>');
                });
                if (opt.even) {
                    $ul.find('li:odd').css('background', '#f2f2f2');
                }
            }

            init();
        }
    }
    $.modal = {
        /**
         * @Description [弹出层]
         * @author xj
         * @param null []
         * @return [返回值]
         * @date 2021/5/4 17:35
         */
        openIframe: function (title, url, width, height, yes, collBack) {
            layui.use('layer', function () { //独立版的layer无需执行这一句
                var $ = layui.jquery, layer = layui.layer; //独立版的layer无需执行这一句
                var opt = {
                    type: 2 //此处以iframe举例
                    , title: title || ''
                    , area: [width || ($(document).width() * 0.8) + 'px', height || ($(document).height() * 0.8) + 'px']
                    , shade: 0.3
                    , maxmin: true
                    , content: url
                    , btn: ['确认', '关闭'] //只是为了演示
                    , yes: yes ? yes : function () {
                        if (typeof yes == 'function') {
                            yes()
                        }
                    }
                    , btn2: function () {
                        layer.closeAll();
                    }
                    , zIndex: layer.zIndex //重点1
                    , success: function (layero) {
                        if (typeof collBack == 'function') {
                            collBack(layero)
                        }
                    }
                };
                layer.open(opt);
            });
        }
    }
})(jQuery)
