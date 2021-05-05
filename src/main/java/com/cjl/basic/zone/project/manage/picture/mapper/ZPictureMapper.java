package com.cjl.basic.zone.project.manage.picture.mapper;

import com.cjl.basic.zone.project.manage.picture.domain.ZPicture;

public interface ZPictureMapper {
    int deleteByPrimaryKey(Integer pId);

    int insert(ZPicture record);

    int insertSelective(ZPicture record);

    ZPicture selectByPrimaryKey(Integer pId);

    int updateByPrimaryKeySelective(ZPicture record);

    int updateByPrimaryKey(ZPicture record);
}