package com.cjl.basic.zone.project.space.album.mapper;

import com.cjl.basic.zone.project.space.album.domain.Card;
import com.cjl.basic.zone.project.space.album.domain.ZAlbum;
import com.cjl.basic.zone.project.space.album.domain.ZPhoto;

import java.util.List;

public interface ZAlbumPhotoMapper {
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
     * @param zAlbum
     * @return
     */
    int addPhoto(ZPhoto zAlbum);

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
     * @param id
     * @return
     */
    List<ZPhoto> selectPhotoByAlbum(Integer id);

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
    List<Card> selectAlbumByAccountId(Integer accountId);


    /**
     * 更新相册信息
     *
     * @param album
     * @return
     */
    int updateAlbum(ZAlbum album);

    /**
     * 删除相册信息
     *
     * @param aId
     * @return
     */
    int deleteAlbumById(Integer aId);

    /**
     *
     * @param aId
     * @return
     */
    ZAlbum selectAlbumByaId(Integer aId);
}