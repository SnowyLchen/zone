package com.cjl.basic.zone.project.space.album.service;

import com.cjl.basic.zone.project.space.album.domain.ZAlbum;
import com.cjl.basic.zone.project.space.album.domain.ZPhoto;

import java.util.List;

/**
 * 相册业务层
 */
public interface IZAlbumPhotoService {
    /**
     * 删除照片
     *
     * @param pId
     * @return
     */
    int deletePhotoById(Integer pId);

    /**
     * 插入照片
     *
     * @param record
     * @return
     */
    int addPhoto(ZPhoto record);

    /**
     * 查询单张照片
     *
     * @param pId
     * @return
     */
    ZPhoto selectPhotoById(Integer pId);

    /**
     * 查询当前用户下所有照片
     *
     * @param accountId
     * @return
     */
    ZPhoto selectPhotoByAccountId(Integer accountId);

    /**
     * 按相册查询照片
     *
     * @param accountId
     * @return
     */
    ZPhoto selectPhotoByAlbum(Integer accountId);

    /**
     * 新增相册
     *
     * @param zAlbum
     * @return
     */
    int addAlbum(ZAlbum zAlbum);

    /**
     * 查询相册集合
     *
     * @param accountId
     * @return
     */
    List<ZAlbum> selectAlbumByAccountId(Integer accountId);


    /**
     * 更新相册信息
     *
     * @param aId
     * @return
     */
    int updateAlbumById(Integer aId);

    /**
     * 删除相册信息
     *
     * @param aId
     * @return
     */
    int deleteAlbumById(Integer aId);
}
