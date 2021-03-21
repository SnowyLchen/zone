package com.cjl.basic.zone.project.area.domain;

import com.cjl.basic.zone.framework.web.domain.BaseEntity;

/**
 * 地区表 sys_area
 *
 * @author zx
 * @date 2019-03-20
 */
public class CityCode extends BaseEntity {

    private String areaCode;

    private String distric;

    private String area;

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getDistric() {
        return distric;
    }

    public void setDistric(String distric) {
        this.distric = distric;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    @Override
    public String toString() {
        return "CityCode{" +
                "areaCode='" + areaCode + '\'' +
                ", distric='" + distric + '\'' +
                ", area='" + area + '\'' +
                '}';
    }
}
