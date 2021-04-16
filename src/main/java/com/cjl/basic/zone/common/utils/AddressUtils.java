package com.cjl.basic.zone.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cjl.basic.zone.framework.config.ZoneConfig;
import com.cjl.basic.zone.framework.web.domain.AjaxResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * 获取地址类
 *
 * @author chen
 */
@Component
public class AddressUtils {

    private static final Logger log = LoggerFactory.getLogger(AddressUtils.class);

    // TODO: 去掉ip
    private static final String IP_URL = "http://api.map.baidu.com/location/ip";

    private static RestTemplate restTemplate;

    @Autowired
    public AddressUtils(RestTemplate restTemplate) {
        AddressUtils.restTemplate = restTemplate;
    }

    public static String getRealAddressByIP(String ip) {
        String address = "XX XX";
//        内网不查询
        if (IpUtils.internalIp(ip)) {
            return "内网IP";
        }

        if (ZoneConfig.isAddressEnabled()) {
            String rspStr = JSON.toJSON(restTemplate.getForEntity(IP_URL + "?ip=" + ip + "&ak=apkmsZdl2Kz84PqaB0q0NpyBqB5xoBGk", AjaxResult.class).getBody()).toString();
            if (StringUtils.isEmpty(rspStr)) {
                log.error("获取地理位置异常 {}", ip);
                return address;
            }
            JSONObject obj = JSONObject.parseObject(rspStr);
            JSONObject content = obj.getObject("content", JSONObject.class);
            content = content.getObject("address_detail", JSONObject.class);
            String region = content.getString("province");
            String city = content.getString("city");
            address = region + " " + city;
        }
        return address;
    }

    public static void main(String[] args) {
        System.out.println(getRealAddressByIP("219.230.92.255"));
    }
}
