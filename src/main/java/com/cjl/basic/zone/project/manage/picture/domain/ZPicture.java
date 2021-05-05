package com.cjl.basic.zone.project.manage.picture.domain;

import java.io.Serializable;
import lombok.Data;

/**
 * z_picture
 * @author 
 */
@Data
public class ZPicture implements Serializable {
    private Integer pId;

    private String pTitle;

    private String pSrc;

    private static final long serialVersionUID = 1L;
}