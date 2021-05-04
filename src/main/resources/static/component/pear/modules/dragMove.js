/**
 @ Name：layui.dragMove 图片查看器
 @ Author：FQSong
 @ License：MIT
 */

layui.define('layer', function (exports) {
    // 引入tablePlug.css
    layui.link('/component/pear/css/pear-module/dragMove.css');
    layui.link('/admin/css/other/icon.css');
    layui.link('/component/pear/font/iconfont.css');
    var $ = layui.$
        , layer = layui.layer
        //字符常量
        , MOD_NAME = 'dragMove', ELEM = '.layui-dragMove'
        //外部接口
        , dragMove = {
            index: layui.dragMove ? (layui.dragMove.index + 10000) : 0

            //设置全局项
            , set: function (options) {
                var that = this;
                that.config = $.extend({}, that.config, options);
                return that;
            }

            //事件监听
            , on: function (events, callback) {
                return layui.onevent.call(this, MOD_NAME, events, callback);
            }
        }

        //构造器
        , Class = function (options) {
            var that = this;
            that.index = ++dragMove.index;
            that.config = $.extend({}, that.config, dragMove.config, options);
            that.render();
        };

    //默认配置
    Class.prototype.config = {
        layerArea: ["960px", "720px"],
        layerShade: 0.6,
        layerMove: 0,
        maxZoom: 1
    };

    //渲染视图
    Class.prototype.render = function () {
        var that = this
            , options = that.config
            , dragMoveView = "<div class='layui-dragMove'>"
            + "<div class='dragMove-img'>"
            + "<span class='layui-icon layui-icon-loading layui-anim layui-anim-rotate layui-anim-loop'></span>"
            + "</div>"
            + "<div class='dragMove-btn'>"
            + "<button type='button' class='layui-btn layui-btn-sm' data-method='default'>默认大小</button>"
            + "<button type='button' class='layui-btn layui-btn-sm' data-method='real'>实际大小</button>"
            + "<button type='button' class='layui-btn layui-btn-sm' data-method='zoomin'>放大</button>"
            + "<button type='button' class='layui-btn layui-btn-sm' data-method='zoomout'>缩小</button>"
            + "</div>"
            + "</div>";

        options.elem = $(options.elem);

        options.elem.on("click", "img", function (e) {
            let imgObj = $(this),
                imgSrc = imgObj.attr("src"),
                imgTitle = imgObj.attr("alt") || imgSrc.replace(/(.*\/)*([^.]+).*/ig, "$2");

            layer.open({
                type: 1,
                resize: 0,
                btn: 0,
                skin: "dragMove-skin",
                move: options.layerMove,
                area: options.layerArea,
                shade: options.layerShade,
                title: imgTitle,
                content: dragMoveView,
                success: function (layero) {
                    var imgbox = layero.find(".dragMove-img");
                    options.imgboxWidth = imgbox.innerWidth();
                    options.imgboxHeight = imgbox.innerHeight();

                    var nImg = new Image();
                    nImg.src = imgSrc;
                    if (nImg.complete) {
                        imgbox.empty().append(nImg);
                        that.init(nImg)
                    } else {
                        nImg.onload = function () {
                            imgbox.empty().append(nImg);
                            that.init(nImg)
                        }
                    }
                }
            });
        });
    }

    //
    Class.prototype.init = function (img) {
        var that = this
            , options = that.config;

        let $img = $(img),
            parent = $img.closest(".layui-dragMove"),
            zoomData = {};

        zoomData.img = img;
        zoomData.imgWidth = img.width;
        zoomData.imgHeight = img.height;

        zoomData.zoomSize = Math.min(Math.min(options.imgboxWidth / zoomData.imgWidth, options.imgboxHeight / zoomData.imgHeight), 1);
        zoomData.left = (options.imgboxWidth - zoomData.imgWidth * zoomData.zoomSize) / 2;
        zoomData.top = (options.imgboxHeight - zoomData.imgHeight * zoomData.zoomSize) / 2;
        zoomData.defaultZoom = zoomData.zoomSize;

        that.zoomData = zoomData;
        $img.css({
            "transform-origin": "0 0",
            "transform": "matrix(" + zoomData.zoomSize + ",0,0," + zoomData.zoomSize + "," + zoomData.left + "," + zoomData.top + ")"
        });

        $img.on("mousedown", function (e) {
            e.preventDefault();
            let currentX = e.clientX,
                currentY = e.clientY;
            $img.removeClass("transitioning").css({"cursor": "grabbing"});

            $(document).on("mousemove", function (even) {
                let moveX = even.clientX - currentX,
                    moveY = even.clientY - currentY;
                $img.css({"transform": "matrix(" + zoomData.zoomSize + ",0,0," + zoomData.zoomSize + "," + (zoomData.left + moveX) + "," + (zoomData.top + moveY) + ")"});
            });
            $(document).on("mouseup", function (even) {
                var matrix = $img.css("transform").slice(7, -1).split(','),
                    center = that.getCenter(parseFloat(matrix[4]), parseFloat(matrix[5]), zoomData);

                zoomData.left = center.left;
                zoomData.top = center.top;

                $img.addClass("transitioning").css({
                    "transform": "matrix(" + zoomData.zoomSize + ",0,0," + zoomData.zoomSize + "," + zoomData.left + "," + zoomData.top + ")",
                    "cursor": "grab"
                });

                $(document).off("mousemove");
                $(document).off("mouseup");
            });
        });

        parent.on("click", "button", function (e) {
            e.preventDefault();
            var method = $(this).attr("data-method"),
                scaleSize = 0;
            switch (method) {
                case "default":
                    scaleSize = zoomData.defaultZoom;
                    break;
                case "real":
                    scaleSize = 1;
                    break;
                case "zoomin":
                    scaleSize = zoomData.zoomSize * 1.2;
                    scaleSize = scaleSize > options.maxZoom ? options.maxZoom : scaleSize;
                    break;
                case "zoomout":
                    scaleSize = zoomData.zoomSize / 1.2;
                    scaleSize = scaleSize < zoomData.defaultZoom ? zoomData.defaultZoom : scaleSize;
                    break;

                default:
                    break;
            }
            scaleSize && that.scaleZoom(scaleSize);
        });

        //鼠标滚轮
        parent.on("mousewheel", function (e) {
            e.preventDefault();
            let scaleSize = 0;
            if (e.originalEvent.wheelDelta > 0) {
                scaleSize = zoomData.zoomSize * 1.2;
            } else {
                scaleSize = zoomData.zoomSize / 1.2;
            }
            scaleSize = Math.min(Math.max(scaleSize, zoomData.defaultZoom), options.maxZoom);
            that.scaleZoom(scaleSize);
        });

        $img.on("transitionend webkitTransitionend", function () {
            $(this).removeClass("transitioning")
        });

    };

    Class.prototype.scaleZoom = function (index) {
        var that = this
            , options = that.config
            , zoomData = that.zoomData;

        zoomData.left = zoomData.left - zoomData.imgWidth * (index - zoomData.zoomSize) / 2;
        zoomData.top = zoomData.top - zoomData.imgHeight * (index - zoomData.zoomSize) / 2;
        zoomData.zoomSize = index;

        var center = that.getCenter(zoomData.left, zoomData.top, zoomData);
        zoomData.left = center.left;
        zoomData.top = center.top;

        $(zoomData.img).addClass("transitioning").css({
            "transform": "matrix(" + zoomData.zoomSize + ",0,0," + zoomData.zoomSize + "," + zoomData.left + "," + zoomData.top + ")"
        });
    };

    Class.prototype.getCenter = function (x, y, zoomData) {
        var that = this
            , options = that.config
            , zoomData = that.zoomData;

        let newleft, newtop;
        newleft = (function () {
            var left;
            if (zoomData.imgWidth * zoomData.zoomSize < options.imgboxWidth) {
                left = (options.imgboxWidth - zoomData.imgWidth * zoomData.zoomSize) / 2;
            } else {
                left = Math.max(Math.min(0, x), options.imgboxWidth - zoomData.imgWidth * zoomData.zoomSize);
            }
            return left;
        })();
        newtop = (function () {
            var top;
            if (zoomData.imgHeight * zoomData.zoomSize < options.imgboxHeight) {
                top = (options.imgboxHeight - zoomData.imgHeight * zoomData.zoomSize) / 2;
            } else {
                top = Math.max(Math.min(0, y), options.imgboxHeight - zoomData.imgHeight * zoomData.zoomSize);
            }
            return top;
        })();
        return {left: newleft, top: newtop}
    };

    Class.prototype.decimal = function (num) {
        var result = parseFloat(num);
        if (isNaN(result)) {
            return false;
        }
        result = Math.round(num * 100) / 100;
        return result;
    };

    //核心入口
    dragMove.render = function (options) {
        var ins = new Class(options);
        return ins;
    };

    //加载组件所需样式
    layui.link('/component/pear/css/pear-module/dragMove.css', function () {
        //样式加载完毕的回调
    }, MOD_NAME);

    exports(MOD_NAME, dragMove);
});