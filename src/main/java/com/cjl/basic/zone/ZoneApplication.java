package com.cjl.basic.zone;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@MapperScan(value = {"com.cjl.basic.zone.project.*.**.mapper", "com.cjl.basic.zone.common.**.**.mapper"})
public class ZoneApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZoneApplication.class, args);
    }

}
