package com.cjl.basic.zone.project.manage.picture.service;

import com.cjl.basic.zone.project.manage.picture.domain.ZPicture;

public interface IPictureService {
    /**
     * 新增图片
     *
     * @return
     */
    int insertPicture(ZPicture zPicture);
}
