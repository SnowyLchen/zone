package com.cjl.basic.zone.project.manage.layim.mapper;

import com.cjl.basic.zone.project.manage.layim.entity.SysMsg;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface SysMsgMapper {

    /**
     * 添加系统消息
     *
     * @param sysMsg
     */
    boolean addSysMsg(SysMsg sysMsg);

    /**
     * 根据用户id获取系统消息
     *
     * @param uid
     * @return
     */
    List<SysMsg> getSysMsgByUid(@Param("uid") String uid);

}
