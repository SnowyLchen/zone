package com.cjl.basic.zone.project.space.home.service;

import com.cjl.basic.zone.project.space.home.domain.ZSignIn;
import com.cjl.basic.zone.project.space.home.mapper.ZSignInMapper;
import org.springframework.stereotype.Service;

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
    public int insertSignInfo(ZSignIn record) {
        // 插入签到信息
        zSignInMapper.insertSignInfo(record);
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
}
