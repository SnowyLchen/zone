package com.cjl.basic.zone.project.manage.picture.service;

import com.cjl.basic.zone.project.manage.picture.domain.ZPicture;
import com.cjl.basic.zone.project.manage.picture.mapper.ZPictureMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PictureServiceImpl implements IPictureService {
    @Resource
    private ZPictureMapper zPictureMapper;
    @Override
    public int insertPicture(ZPicture zPicture) {
        return zPictureMapper.insertSelective(zPicture);
    }
}
