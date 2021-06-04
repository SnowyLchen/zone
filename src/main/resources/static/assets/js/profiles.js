var form, $, areaData;
var avatar = avatar;
layui.use(['form', 'layer', 'upload', 'laydate', "convert"], function () {
    form = layui.form;
    $ = layui.jquery;
    var layer = parent.layer === undefined ? layui.layer : top.layer,
        upload = layui.upload,
        laydate = layui.laydate,
        address = layui.address, windowIndex,
        convert = layui.convert;

    //上传头像
    upload.render({
        elem: '#userFace',
        url: '/common/upload',
        method: "get", //此处是为了演示之用，实际使用中请将此删除，默认用post方式提交
        before: function (obj) {
            //预读本地文件示例，不支持ie8
            obj.preview(function (index, file, result) {
                windowIndex = top.layer.msg('请稍候...', {icon: 16, time: false, shade: 0.8});
                // $('#userFace').attr('src', result);
                // debugger
            });
        },
        done: function (res, index, upload) {
            //上传完毕
            if (res.code == 0) {
                if (res.data) {
                    var photo = res.data[0];
                    $('#userFace').attr('src', photo.src);
                    $('#avatar').val(photo.src);
                }
                layer.msg('上传成功', {icon: 1});
                top.layer.close(windowIndex);
            }
        }
    });


    //提交个人资料
    form.on("submit(changeUser)", function (data) {
        var index = layer.msg('提交中，请稍候', {
            icon: 16,
            time: false,
            shade: 0.8
        });
        console.log($("#form-profiles").serialize())
        $.ajax({
            url: '/user/editUser',
            data: $("#form-profiles").serialize(),
            dataType: 'json',
            type: 'post',
            success: function (result) {
                if (result.code == 0) {
                    layer.msg(result.msg, {icon: 1, time: 1000});
                    $.modal.refreshIframe()
                } else {
                    layer.msg(result.msg, {icon: 2, time: 1000});
                }
            }
        });
        top.layer.close(index)
        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    });


    //判断是否设置过头像，如果设置过则修改顶部、左侧和个人资料中的头像，否则使用默认头像
    debugger
    if (avatar && $(".userAvatar").length > 0) {
        $("#userFace").attr("src", avatar);
        $(".userAvatar").attr("src", avatar);
    } else {
        var image = new Image();
        image.src = "admin/images/avatar.jpg";
        $("#userFace").attr("src", convert.imageToBase64(image));
    }


    //修改密码
    form.on("submit(changePwd)", function (data) {
        var index = layer.msg('提交中，请稍候', {
            icon: 16,
            time: false,
            shade: 0.8
        });
        setTimeout(function () {
            layer.close(index);
            layer.msg("密码修改成功！");
            $(".pwd").val('');
        }, 2000);
        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    })
})
