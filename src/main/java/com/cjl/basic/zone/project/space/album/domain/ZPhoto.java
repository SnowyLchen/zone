package com.cjl.basic.zone.project.space.album.domain;

import com.cjl.basic.zone.common.controller.ZImage;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 照片表
 *
 * @author chen
 */
@Data
public class ZPhoto implements Serializable {
    private Integer pId;
    /**
     * 相册id
     */
    private Integer aId;
    /**
     * 照片路径
     */
    private String path;

    /**
     * 照片名称
     */
    private String name;

    /**
     * 后缀
     */
    private String suffix;

    /**
     * 上传时间
     */
    private Date createTime;

    /**
     * 是否被删除
     */
    private String delFlag;

    private List<ZImage> images;

    private static final long serialVersionUID = 1L;
}