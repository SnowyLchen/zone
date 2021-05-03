package com.cjl.basic.zone.project.space.home.service;

import com.cjl.basic.zone.common.utils.DateUtils;
import com.cjl.basic.zone.common.utils.InsertOrUpdateUtils;
import com.cjl.basic.zone.common.utils.security.ShiroAuthenticateUtils;
import com.cjl.basic.zone.project.space.home.domain.ZDynamic;
import com.cjl.basic.zone.project.space.home.domain.ZSignIn;
import com.cjl.basic.zone.project.space.home.mapper.ZSignInMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 签到业务层
 *
 * @author chen
 */
@Service
public class ZSignInServiceImpl implements IZSignInService {
    @Resource
    private ZSignInMapper zSignInMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertSignInfo(ZDynamic zDynamic) {
        // 设置动态title
        if (zDynamic.getContent() != null) {
            zDynamic.setTitle(zDynamic.getContent().substring(0, zDynamic.getContent().length() / 2 + 1) + "...");
        }
        zDynamic.setDate(DateUtils.getTime());
        InsertOrUpdateUtils.addInsertAttr(zDynamic);
        int i = checkSignIn(ShiroAuthenticateUtils.getAccountId());
        if (i==0){
            // 插入签到信息
            zSignInMapper.insertSignInfo(zDynamic);
        }
        // 插入动态
        zSignInMapper.insertDynamic(zDynamic);
        return 1;
    }

    @Override
    public int updateSignInfo(ZSignIn record) {
        return zSignInMapper.updateSignInfo(record);
    }

    @Override
    public ZSignIn selectSignInfoByAccountId(Integer accountId) {
        return zSignInMapper.selectSignInfoByAccountId(accountId);
    }

    @Override
    public List<ZSignIn> selectSignInfoList(Integer accountId) {
        return zSignInMapper.selectSignInfoList(accountId);
    }

    @Override
    public List<ZDynamic> getDynamic(Integer accountId) {
        return zSignInMapper.selectDynamicList(accountId, DateUtils.getTime());
    }

    @Override
    public int checkSignIn(Integer accountId) {
        return zSignInMapper.checkSignIn(accountId, DateUtils.getDate());
    }
}
