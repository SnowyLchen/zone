package com.cjl.basic.zone.project.layim.service.impl;

import com.cjl.basic.zone.project.layim.entity.SysMsg;
import com.cjl.basic.zone.project.layim.mapper.SysMsgMapper;
import com.cjl.basic.zone.project.layim.service.SysMsgService;
import com.cjl.basic.zone.project.user.mapper.UserMapper;
import com.cjl.basic.zone.utils.IdGenerat;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Author chen
 * @Date 2020/4/13 10:22
 * @Version 1.0
 */
@Service
public class SysMsgServiceImpl implements SysMsgService {
    @Resource
    private SysMsgMapper sysMsgMapper;
    @Resource
    private UserMapper userMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addSysMsg(String uid, String fid) {
        //添加系统消息
        String username = userMapper.selectUserById(Integer.valueOf(uid)).getUserName();
        SysMsg sysMsg = new SysMsg().setId(IdGenerat.getGeneratID()).setContent(username + "  拒绝了你的好友申请")
                .setUid(fid).setCreateTime(new Date()).setStatus("0");
        boolean addSysMsg = sysMsgMapper.addSysMsg(sysMsg);
        if (addSysMsg) {
            String userName = userMapper.selectUserById(Integer.valueOf(fid)).getUserName();
            sysMsg.setId(IdGenerat.getGeneratID()).setContent("你拒绝了" + userName + "的好友申请").setUid(uid);
            boolean addSysMsg1 = sysMsgMapper.addSysMsg(sysMsg);
            if (addSysMsg1) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<SysMsg> getSysMsgByUid(String uid) {
        return sysMsgMapper.getSysMsgByUid(uid);
    }
}
