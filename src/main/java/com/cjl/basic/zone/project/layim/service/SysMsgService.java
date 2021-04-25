package com.cjl.basic.zone.project.layim.service;


import com.cjl.basic.zone.project.layim.entity.SysMsg;

import java.util.List;

/**
 * @Author chen
 * @Date 2020/4/8 15:13
 * @Version 1.0
 */
public interface SysMsgService {
    /**
     * 添加系统消息
     *
     * @param uid
     * @param fid
     * @return
     */
    boolean addSysMsg(String uid, String fid);

    /**
     * 根据用户id获取系统消息
     *
     * @param uid
     * @return
     */
    List<SysMsg> getSysMsgByUid(String uid);
}
