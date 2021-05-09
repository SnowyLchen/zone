/**
 * layui_dropdown
 * v2.3.2
 * by Microanswer
 * http://layuidropdown.microanswer.cn/
 **/
layui.define(["jquery", "laytpl"], function (i) {
    var s = layui.jquery || layui.$, r = layui.laytpl, e = "a", d = {}, c = "1", u = "2", m = "3";

    function f(i) {
        if (!i) throw new Error("菜单条目内必须填写内容。");
        if ("hr" === i) return "hr";
        if (0 !== i.indexOf("{")) throw new Error("除了分割线hr，别的菜单条目都必须保证是合格的Javascript对象或json对象。");
        return new Function("return " + i)()
    }

    function a(i) {
        if (i && 0 < i.length) {
            for (var n = 0, t = new Array(i.length), o = 0; o < i.length; o++) for (var e = i[o], d = 0; d < e.length; d++) e[d].header && e[d].fixed && (n++, t[o] = e[d], e.splice(d, 1), d--);
            if (0 < n) return t
        }
        return null
    }

    var l = window.MICROANSWER_DROPDOWAN || "dropdown",
        p = "<div tabindex='0' class='layui-anim layui-anim-upbit dropdown-root' " + l + "-id='{{d.downid}}' style='display: none;z-index: {{d.zIndex}}'>{{# if (d.arrow){ }}<div class='dropdown-pointer'></div>{{# } }}<div class='dropdown-content' style='margin: {{d.gap}}px {{d.gap}}px;background-color: {{d.backgroundColor}};min-width: {{d.minWidth}}px;max-width: {{d.maxWidth}}px;min-height: {{d.minHeight}}px;max-height: {{d.maxHeight}}px;white-space: {{d.nowrap?\"nowrap\":\"normal\"}}'>",
        h = "</div></div>",
        w = p + "<div class='dropdown-content-table' cellpadding='0' cellspacing='0'>{{# if (d.fixHeaders && d.fixHeaders.length > 0){ }}<div class='dropdown-content-thead'><div class='dropdown-content-tr'>{{# layui.each(d.fixHeaders, function(i, fixHeader){ }}{{# if (fixHeader) { }}<div class='dropdown-content-th'><div class='dropdown-menu-fixed-head {{(d.menuSplitor && i < (d.menus.length-1))?\"menu-splitor\":\"\"}}'><div class='menu-fixed-head' style='text-align: {{fixHeader.align||\"center\"}}'>{{fixHeader.header}}</div></div></div>{{# } else { }}<th><div class='dropdown-menu-fixed-head {{(d.menuSplitor && i < (d.menus.length-1))?\"menu-splitor\":\"\"}}'><div class='menu-fixed-head'>&nbsp;</div></div></th>{{# } }}{{# }); }}</div></div>{{# } }}<div class='dropdown-content-tbody'><div class='dropdown-content-tr'>{{# layui.each(d.menus, function(i, menu){ }}<div class='dropdown-content-td' valign='top'><div class='dropdown-menu-wrap {{(d.menuSplitor && i < (d.menus.length-1))?\"menu-splitor\":\"\"}} overflowauto' style='min-height: {{d.minHeight}}px;max-height: {{d.maxHeight - ((d.fixHeaders)?24:0)}}px;'><ul class='dropdown-menu' style=''>{{# layui.each(menu, function(index, item){ }}<li class='menu-item-wrap {{(d.fixHeaders && d.fixHeaders.length) > 0?\"nomargin\":\"\"}}'>{{# if ('hr' === item) { }}<hr>{{# } else if (item.header) { }}{{# if (item.withLine) { }}<fieldset class=\"layui-elem-field layui-field-title menu-header withLine\"><legend>{{item.header}}</legend></fieldset>{{# } else { }}<div class='menu-header' style='text-align: {{item.align||\"left\"}}'>{{item.header}}</div>{{# } }}{{# } else { }}<div class='menu-item'><a href='javascript:;' lay-event='{{item.event}}'>{{# if (item.layIcon){ }}<i class='layui-icon {{item.layIcon}}'></i>&nbsp;{{# } }}<span class='{{item.txtClass||\"\"}}'>{{item.txt}}</span></a></div>{{# } }}</li>{{# }); }}</ul></div></div>{{#});}}</div></div></div>" + h,
        t = {
            menus: [],
            templateMenu: "",
            template: "",
            showBy: "click",
            align: "left",
            minWidth: 80,
            maxWidth: 500,
            minHeight: 10,
            maxHeight: 400,
            zIndex: 891,
            gap: 8,
            onHide: function (i, n) {
            },
            onShow: function (i, n) {
            },
            scrollBehavior: "follow",
            backgroundColor: "#FFF",
            cssLink: "https://cdn.jsdelivr.net/gh/microanswer/layui_dropdown@2.3.2/dist/dropdown.css",
            immed: !1,
            arrow: !0,
            templateMenuSptor: "[]",
            menuSplitor: !0
        };

    function v(i, n) {
        "string" == typeof i && (i = s(i)), this.$dom = i, this.option = s.extend({
            downid: String(Math.random()).split(".")[1],
            filter: i.attr("lay-filter")
        }, t, n), 20 < this.option.gap && (this.option.gap = 20), this.init()
    }

    function o(i, o) {
        s(i || "[lay-" + l + "]").each(function () {
            var i = s(this), n = new Function("return " + (i.attr("lay-" + l) || "{}"))();
            i.removeAttr("lay-" + l);
            var t = i.data(l) || new v(i, s.extend({}, n, o || {}));
            i.data(l, t)
        })
    }

    window[l + "_useOwnCss"] || layui.link(window[l + "_cssLink"] || t.cssLink, function () {
    }, l + "_css"), v.prototype.init = function () {
        var n = this, i = !1;
        if (n.option.menus && 0 < n.option.menus.length) {
            i = !0;
            var t = n.option.menus[0];
            Array.isArray(t) || (n.option.menus = [n.option.menus]), n.option.fixHeaders = a(n.option.menus), n.option.nowrap = !0, r(w).render(n.option, function (i) {
                n.downHtml = i, n.initEvent()
            })
        } else if (n.option.templateMenu) {
            var o;
            i = !0, o = -1 === n.option.templateMenu.indexOf("#") ? "#" + n.option.templateMenu : n.option.templateMenu;
            var e = s.extend(s.extend({}, n.option), n.option.data || {});
            r(s(o).text()).render(e, function (i) {
                n.option.menus = function (i, n) {
                    if (!i) return "";
                    if (!n) throw new Error("请指定菜单模板限定符。");
                    for (var t, o, e = n.charAt(0), d = n.charAt(1), s = i.length, r = 0, a = c, l = !1, p = []; r < s;) {
                        var h = i.charAt(r);
                        a !== c || l ? a !== u || l ? a === m && (l ? (o.srcStr += h, l = !1) : "\\" === h ? l = !0 : h === d ? (o = f(o.srcStr), t.push(o), a = u) : o.srcStr += h) : e === h ? (o = {srcStr: ""}, a = m) : d === h && (a = c) : e === h && (t = [], p.push(t), a = u), r += 1
                    }
                    return p
                }(i, n.option.templateMenuSptor), n.option.fixHeaders = a(n.option.menus), n.option.nowrap = !0, r(w).render(n.option, function (i) {
                    n.downHtml = i, n.initEvent()
                })
            })
        } else if (n.option.template) {
            var d;
            i = !0, d = -1 === n.option.template.indexOf("#") ? "#" + n.option.template : n.option.template, (e = s.extend(s.extend({}, n.option), n.option.data || {})).nowrap = !1, r(p + s(d).html() + h).render(e, function (i) {
                n.downHtml = i, n.initEvent()
            })
        } else layui.hint().error("下拉框目前即没配置菜单项，也没配置下拉模板。[#" + (n.$dom.attr("id") || "") + ",filter=" + n.option.filter + "]");
        i && this.option.immed && this.downHtml && this.show()
    }, v.prototype.initSize = function () {
        if (this.$down && (this.$down.find(".dropdown-pointer").css({
            width: 2 * this.option.gap,
            height: 2 * this.option.gap
        }), !this._sized)) {
            var i = 0;
            this.$down.find(".dropdown-menu-wrap").each(function () {
                i = Math.max(i, s(this).height())
            }), this.$down.find(".dropdown-menu-wrap").css({height: i}), this._sized = !0
        }
    }, v.prototype.initPosition = function () {
        if (this.$down) {
            var i, n, t, o, e = this.$dom.offset(), d = this.$dom.outerHeight(), s = this.$dom.outerWidth(), r = e.left,
                a = e.top - window.pageYOffset, l = this.$down.outerHeight(), p = this.$down.outerWidth();
            n = d + a, (i = "right" === this.option.align ? r + s - p + this.option.gap : "center" === this.option.align ? r + (s - p) / 2 : r - this.option.gap) + p >= window.innerWidth && (i = window.innerWidth - p - 2 * this.option.gap), t = i < r ? s < p ? r - i + s / 2 : p / 2 : s < p ? i + (r + s - i) / 2 : p / 2, t -= this.option.gap;
            var h = this.$arrowDom;
            o = -this.option.gap, h.css("left", t), h.css("right", "unset"), n + l >= window.innerHeight ? (n = a - l, o = l - this.option.gap, h.css("top", o).addClass("bottom")) : h.css("top", o).removeClass("bottom"), this.$down.css("left", i), this.$down.css("top", n)
        }
    }, v.prototype.show = function () {
        var t, i, n = this, o = !1;
        n.$down || (n.$down = s(n.downHtml), n.$dom.after(n.$down), n.$arrowDom = n.$down.find(".dropdown-pointer"), o = !0), n.initPosition(), n.opening = !0, setTimeout(function () {
            n.$down.focus()
        }, 100), n.$down.addClass("layui-show"), n.initSize(), n.opened = !0, o && n.initDropdownEvent(), t = n, i = d[e] || [], s.each(i, function (i, n) {
            n(t)
        }), o && n.onSuccess(), n.option.onShow && n.option.onShow(n.$dom, n.$down)
    }, v.prototype.hide = function () {
        this.opened && (this.fcd = !1, this.$down.removeClass("layui-show"), this.opened = !1, this.option.onHide && this.option.onHide(this.$dom, this.$down))
    }, v.prototype.hideWhenCan = function () {
        this.mic || this.opening || this.fcd || this.hide()
    }, v.prototype.toggle = function () {
        this.opened ? this.hide() : this.show()
    }, v.prototype.onSuccess = function () {
        this.option.success && this.option.success(this.$down)
    }, v.prototype._onScroll = function () {
        var i = this;
        i.opened && ("follow" === this.option.scrollBehavior ? setTimeout(function () {
            i.initPosition()
        }, 1) : this.hide())
    }, v.prototype.initEvent = function () {
        var i, n, t, o = this;
        n = function (i) {
            i !== o && o.hide()
        }, (t = d[i = e] || []).push(n), d[i] = t, s(window).on("scroll", function () {
            o._onScroll()
        }), o.$dom.parents().on("scroll", function () {
            o._onScroll()
        }), s(window).on("resize", function () {
            o.opened && o.initPosition()
        }), o.initDomEvent()
    }, v.prototype.initDomEvent = function () {
        var i = this;
        i.$dom.mouseenter(function () {
            i.mic = !0, "hover" === i.option.showBy && (i.fcd = !0, i.show())
        }), i.$dom.mouseleave(function () {
            i.mic = !1
        }), "click" === i.option.showBy && i.$dom.on("click", function () {
            i.fcd = !0, i.toggle()
        }), i.$dom.on("blur", function () {
            i.fcd = !1, i.hideWhenCan()
        })
    }, v.prototype.initDropdownEvent = function () {
        var o = this;
        o.$down.find(".dropdown-menu-wrap").on("mousewheel", function (i) {
            var n = s(this);
            (i = i || window.event).cancelable = !0, i.cancelBubble = !0, i.preventDefault(), i.stopPropagation(), i.stopImmediatePropagation && i.stopImmediatePropagation(), i.returnValue = !1, o.scrolling && n.finish();
            var t = -i.originalEvent.wheelDelta || i.originalEvent.detail;
            0 < t ? (50 < t && (t = 50), o.scrolling = !0, n.animate({scrollTop: n.scrollTop() + t}, {
                duration: 170,
                complete: function () {
                    o.scrolling = !1
                }
            })) : t < 0 ? (t < -50 && (t = -50), o.scrolling = !0, n.animate({scrollTop: n.scrollTop() + t}, {
                duration: 170,
                complete: function () {
                    o.scrolling = !1
                }
            })) : o.scrolling = !1
        }), o.$down.mouseenter(function () {
            o.mic = !0, o.$down.focus()
        }), o.$down.mouseleave(function () {
            o.mic = !1
        }), o.$down.on("blur", function () {
            o.fcd = !1, o.hideWhenCan()
        }), o.$down.on("focus", function () {
            o.opening = !1
        }), o.option.menus && s("[" + l + "-id='" + o.option.downid + "']").on("click", "a", function () {
            var i = (s(this).attr("lay-event") || "").trim();
            i ? (layui.event.call(this, l, l + "(" + o.option.filter + ")", i), o.hide()) : layui.hint().error("菜单条目[" + this.outerHTML + "]未设置event。")
        })
    }, o(), i(l, {
        suite: o, onFilter: function (i, n) {
            layui.onevent(l, l + "(" + i + ")", function (i) {
                n && n(i)
            })
        }, hide: function (i) {
            s(i).each(function () {
                var i = s(this).data(l);
                i && i.hide()
            })
        }, show: function (n, t) {
            s(n).each(function () {
                var i = s(this).data(l);
                i ? i.show() : (layui.hint().error("警告：尝试在选择器【" + n + "】上进行下拉框show操作，但此选择器对应的dom并没有初始化下拉框。"), (t = t || {}).immed = !0, o(n, t))
            })
        }, version: "2.3.2"
    })
});