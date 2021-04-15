$(function () {
    loginUtils.init()
})
var loginUtils = {
    init: function () {
        loginUtils.toLogin();
    },
    login: function (data, ts) {
        console.log(data);
        var ajaxOptions = {
            'url': '/login',
            'type': 'post',
            'timeout': 10000,
            'data': data,
            'success': function (data) {
                var $btn = $(ts)
                $btn.button('登录中');
                if (data.code === 0) {
                    $btn.button('reset');
                    setTimeout(function () {
                        window.top.location.href = '/index';
                    }, 300);
                } else if (data.code === 2) {
                    // $(".form-group-code").removeClass('hide');
                    // $("#validateCode").val('');
                    // $refreshCode.click();
                    // $.cookie('form-group-code', 'visible', {expires: 1});
                } else {
                    alert(data.msg);
                }
                // $.cookie("iflogin", "true");
            }
        };
        $.ajax(ajaxOptions);
    },
    toLogin: function () {
        var $login = $('.tpl-login-btn');
        $login.on('click', function () {
            var $loginForm = $('#tpl-login');
            debugger
            loginUtils.login($loginForm.serialize(), this);
        })
    },
    logout: function () {

    }
}