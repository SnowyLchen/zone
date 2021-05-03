package com.cjl.basic.zone.project.space.album.service;

import com.cjl.basic.zone.project.space.album.domain.ZAlbum;
import com.cjl.basic.zone.project.space.album.domain.ZPhoto;
import com.cjl.basic.zone.project.space.album.mapper.ZAlbumPhotoMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ZAlbumPhotoServiceImpl implements IZAlbumPhotoService {
    @Resource
    private ZAlbumPhotoMapper albumPhotoMapper;

    @Override
    public int deletePhotoById(Integer pId) {
        return albumPhotoMapper.deletePhotoById(pId);
    }

    @Override
    public int addPhoto(ZPhoto zPhoto) {
        return albumPhotoMapper.addPhoto(zPhoto);
    }

    @Override
    public ZPhoto selectPhotoById(Integer pId) {
        return albumPhotoMapper.selectPhotoById(pId);
    }

    @Override
    public ZPhoto selectPhotoByAccountId(Integer accountId) {
        return albumPhotoMapper.selectPhotoByAccountId(accountId);
    }

    @Override
    public ZPhoto selectPhotoByAlbum(Integer accountId) {
        return albumPhotoMapper.selectPhotoByAlbum(accountId);
    }

    @Override
    public int addAlbum(ZAlbum zAlbum) {
        return albumPhotoMapper.addAlbum(zAlbum);
    }

    @Override
    public List<ZAlbum> selectAlbumByAccountId(Integer accountId) {
        return albumPhotoMapper.selectAlbumByAccountId(accountId);
    }

    @Override
    public int updateAlbumById(Integer aId) {
        return albumPhotoMapper.updateAlbumById(aId);
    }

    @Override
    public int deleteAlbumById(Integer aId) {
        return albumPhotoMapper.deleteAlbumById(aId);
    }
}
