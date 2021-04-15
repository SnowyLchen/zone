package com.cjl.basic.zone.framework.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author chen
 */
@Configuration
@ConfigurationProperties(prefix = "application.api")
public class ApiConfig {
    /**
     * 智控接口
     */
    private String zht;
    /**
     * center-basic库接口
     */
    private String centerBasic;

    public String getZht() {
        return zht;
    }

    public void setZht(String zht) {
        this.zht = zht;
    }

    public String getCenterBasic() {
        return centerBasic;
    }

    public void setCenterBasic(String centerBasic) {
        this.centerBasic = centerBasic;
    }
}
