layui.define(['jquery', 'popup'], function (exports) {
    "use strict";

    /**
     * Button component
     * */
    var MOD_NAME = 'button',
        popup = layui.popup,
        $ = layui.jquery;

    var button = function (opt) {
        this.option = opt;
    };

    /**
     * Button start loading
     * */
    button.prototype.load = function (opt) {

        var option = {
            elem: opt.elem,
            time: opt.time ? opt.time : false,
            data: {},
            ajaxGetOption: opt.ajaxGetOption ? opt.ajaxGetOption : false,
            ajaxPostOption: opt.ajaxPostOption ? opt.ajaxPostOption : false,
            done: opt.done ? opt.done : function () {
            }
        }
        var text = $(option.elem).text();

        $(option.elem).html("<i class='layui-anim layui-anim-rotate layui-icon layui-anim-loop layui-icon-loading'/>");

        var buttons = $(option.elem);
        if (!!option.ajaxPostOption) {
            var ajaxOption = option.ajaxPostOption;
            if (!ajaxOption.url) {
                popup.failure("请求格式异常");
                return;
            }
            ajaxOption.type = 'post';
            ajaxOption.dataType = 'json';
            ajaxOption.async = false;
            ajaxOption.success = function (data) {
                option.data = data;
            };
            ajaxOption.error = function (res) {
                popup.failure.error("网络错误：" + res.status);
            };
            $.ajax(ajaxOption);
        }
        if (option.time != "" || option.time != false) {
            setTimeout(function () {
                buttons.html(text);
                option.done(option.data);
            }, option.time);
        }
        option.text = text;
        return new button(option);
    }

    /**
     * Button stop loaded
     * */
    button.prototype.stop = function (success) {
        $(this.option.elem).html(this.option.text);
        success();
    }

    exports(MOD_NAME, new button());
});
