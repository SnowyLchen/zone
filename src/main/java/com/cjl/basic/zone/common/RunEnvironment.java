package com.cjl.basic.zone.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author admin
 */
@Component
public class RunEnvironment {
    private static final String DEV = "dev";
    private static final String PROD = "prod";
    private static final String TEST = "test";

    @Value("${spring.profiles.active}")
    private String active;

    public String getActive() {
        return active;
    }

    public boolean isDev() {
        return DEV.equals(active);
    }

    public boolean isProd() {
        return PROD.equals(active);
    }

    public boolean isTest() {
        return TEST.equals(active);
    }
}
