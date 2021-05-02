// $(function () {
//     loginUtils.init()
// })
// var loginUtils = {
//     init: function () {
//         // 登录
//         loginUtils.toLogin();
//         // 注册
//         loginUtils.toRegister();
//     },
//     login: function (data, $btn) {
//         var ajaxOptions = {
//             'url': '/login',
//             'type': 'post',
//             'timeout': 10000,
//             'data': data,
//             'success': function (data) {
//                 if (data.code === 0) {
//                     $btn.button('reset');
//                     setTimeout(function () {
//                         window.top.location.href = '/index';
//                     }, 300);
//                 } else if (data.code === 2) {
//                     // $(".form-group-code").removeClass('hide');
//                     // $("#validateCode").val('');
//                     // $refreshCode.click();
//                     // $.cookie('form-group-code', 'visible', {expires: 1});
//                 } else {
//                     alert(data.msg);
//                 }
//                 // $.cookie("iflogin", "true");
//             }
//         };
//         $.ajax(ajaxOptions);
//     },
//     toLogin: function () {
//         var $login = $('#login');
//         $login.on('click', function () {
//             var $loginForm = $('#tpl-login');
//             var $btn = $(this)
//             $btn.button('登录中');
//             loginUtils.login($loginForm.serialize(), $btn);
//         })
//     },
//     toRegister: function () {
//         var $register = $('#register');
//         $register.on('click', function () {
//
//             var $registerForm = $('#form-register');
//             var $btn = $(this)
//             $btn.button('请稍后...');
//             loginUtils.register($registerForm.serialize(), $btn);
//         })
//     },
//     register: function (data, $btn) {
//         var rOption = {
//             'url': '/register',
//             'type': 'post',
//             'timeout': 10000,
//             'data': data,
//             'success': function (data) {
//                 if (data.code === 0) {
//                     $btn.button('reset');
//                     setTimeout(function () {
//                         window.top.location.href = '/login';
//                     }, 300);
//                 } else if (data.code === 2) {
//                     // $(".form-group-code").removeClass('hide');
//                     // $("#validateCode").val('');
//                     // $refreshCode.click();
//                     // $.cookie('form-group-code', 'visible', {expires: 1});
//                 } else {
//                     alert(data.msg);
//                 }
//                 // $.cookie("iflogin", "true");
//             }
//         };
//         $.ajax(rOption);
//     }
// }