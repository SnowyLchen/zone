package com.cjl.basic.zone.project.space.album.controller;

import com.cjl.basic.zone.common.utils.security.ShiroAuthenticateUtils;
import com.cjl.basic.zone.framework.web.controller.BaseController;
import com.cjl.basic.zone.framework.web.domain.AjaxResult;
import com.cjl.basic.zone.framework.web.page.TableDataInfo;
import com.cjl.basic.zone.project.manage.user.domain.User;
import com.cjl.basic.zone.project.space.album.domain.ZAlbum;
import com.cjl.basic.zone.project.space.album.service.IZAlbumPhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 相册模块
 */
@Controller
@RequestMapping("/album")
public class AlbumPhotoController extends BaseController {

    @Autowired
    private IZAlbumPhotoService albumPhotoService;

    /**
     * 空间相册模块
     */
    @GetMapping()
    public String album(ModelMap mmap) {
        User user = ShiroAuthenticateUtils.getUserByToken();
        mmap.put("user", user);
        return "space/album/album";
    }

    /**
     * 空间相册模块-新增相册/上传照片
     */
    @GetMapping("/operator/{type}")
    public String addAlbum(ModelMap mmap, @PathVariable String type, Integer id) {
        if (type.equals("albumPic")) {
            mmap.put("photos", albumPhotoService.selectPhotoByAlbum(id));
        }
        if (type.equals("albumType")) {
            mmap.put("albums", albumPhotoService.selectAlbumByAccountId(ShiroAuthenticateUtils.getAccountId()));
        }
        return "space/album/" + type;
    }


    /**
     * 新增照片
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/addPhoto")
    public AjaxResult addPhoto(ZAlbum albums) {
        Integer accountId = ShiroAuthenticateUtils.getAccountId();
//        zAlbum.setAccountId(accountId);
//        return AjaxResult.success(albumPhotoService.addPhoto(zAlbum));
        return AjaxResult.success(1);
    }

    /**
     * 新增相册
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/addAlbum")
    public AjaxResult addAlbum(ZAlbum zAlbum) {
        Integer accountId = ShiroAuthenticateUtils.getAccountId();
        zAlbum.setAccountId(accountId);
        return AjaxResult.success(albumPhotoService.addAlbum(zAlbum));
    }

    /**
     * 查询相册
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/getAlbums")
    public TableDataInfo getAlbums() {
        startPage();
        Integer accountId = ShiroAuthenticateUtils.getAccountId();
        return getDataTable(albumPhotoService.selectAlbumByAccountId(accountId));
    }

}
