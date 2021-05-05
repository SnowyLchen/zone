package com.cjl.basic.zone.project.space.album.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 相册类
 *
 * @author
 */
@Data
public class ZAlbum extends ZPhoto implements Serializable {
    private Integer aId;

    /**
     * 相册名称
     */
    private String name;

    /**
     * 用户id
     */
    private Integer accountId;

    /**
     * 相册描述
     */
    private String description;
    /**
     * 相册的相片
     */
    private List<ZAlbum> photos;

    private static final long serialVersionUID = 1L;
}