package com.cjl.basic.zone.project.space.album.service;

import com.cjl.basic.zone.project.space.album.domain.Card;
import com.cjl.basic.zone.project.space.album.domain.ZAlbum;
import com.cjl.basic.zone.project.space.album.domain.ZPhoto;
import com.cjl.basic.zone.project.space.album.mapper.ZAlbumPhotoMapper;
import com.cjl.basic.zone.utils.dateutils.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional(rollbackFor = Exception.class)
    public int addPhoto(ZAlbum zAlbum) {
        ZPhoto zPhoto = new ZPhoto();
        List<ZAlbum> photos = zAlbum.getPhotos();
        for (ZAlbum album : photos) {
            zPhoto.setAId(album.getAId());
            zPhoto.setName(album.getName());
            zPhoto.setCreateTime(DateUtils.getNowDate());
            zPhoto.setDelFlag("0");
            zPhoto.setPath(album.getPath());
            zPhoto.setSuffix(album.getSuffix());
            albumPhotoMapper.addPhoto(zPhoto);
        }
        return 1;
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
    public List<ZPhoto> selectPhotoByAlbum(Integer id) {
        return albumPhotoMapper.selectPhotoByAlbum(id);
    }

    @Override
    public int addAlbum(ZAlbum zAlbum) {
        return albumPhotoMapper.addAlbum(zAlbum);
    }

    @Override
    public List<Card> selectAlbumByAccountId(Integer accountId) {
        List<Card> cards = albumPhotoMapper.selectAlbumByAccountId(accountId);
        for (Card card : cards) {
            if (card.getImage() == null) {
                card.setImage("admin/images/nothing.jpg");
            }
            if (card.getTime() == null) {
                card.setTime(DateUtils.getDate());
            }
        }
        return cards;
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
