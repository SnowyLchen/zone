package com.cjl.basic.zone.framework.web.domain;

import com.alibaba.fastjson.JSON;
import com.cjl.basic.zone.framework.shiro.StatelessConstants;

import java.util.HashMap;

/**
 * 操作消息提醒
 *
 * @author chen
 */
public class AjaxResult extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    /**
     * 初始化一个新创建的 Message 对象
     */
    public AjaxResult() {
    }

    public String toJSON() {
        return JSON.toJSONString(this);
    }

    /**
     * 返回错误消息
     *
     * @return 错误消息
     */
    public static AjaxResult error() {
        return error(1, "操作失败");
    }

    /**
     * 返回错误消息
     *
     * @param msg 内容
     * @return 错误消息
     */
    public static AjaxResult error(String msg) {
        return error(500, msg);
    }

    /**
     * 返回错误消息
     *
     * @param code 错误码
     * @param msg  内容
     * @return 错误消息
     */
    public static AjaxResult error(int code, String msg) {
        AjaxResult json = new AjaxResult();
        json.put("code", code);
        json.put("msg", msg);
        return json;
    }

    /**
     * 返回成功消息
     *
     * @param msg 内容
     * @return 成功消息
     */
    public static AjaxResult success(String msg) {
        AjaxResult json = new AjaxResult();
        json.put("msg", msg);
        json.put("code", 0);
        return json;
    }

    /**
     * 返回成功消息+数据
     *
     * @param data 内容
     * @return 成功消息
     */
    public static AjaxResult success(Object data) {
        AjaxResult json = new AjaxResult();
        json.put("data", data);
        json.put("code", 0);
        json.put("msg", "成功");
        return json;
    }

    /**
     * 返回成功消息
     *
     * @return 成功消息
     */
    public static AjaxResult success() {
        return AjaxResult.success("操作成功");
    }

    /**
     * 返回成功消息
     *
     * @param key   键值
     * @param value 内容
     * @return 成功消息
     */
    @Override
    public AjaxResult put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    public static AjaxResult unknownAccount() {
        return AjaxResult.error(401, StatelessConstants.JWT_EXPIRE_MSG);
    }

    public static AjaxResult unknownAccount(String msg) {
        return AjaxResult.error(401, msg);
    }
}
