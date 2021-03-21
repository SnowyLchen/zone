package com.cjl.basci.zone.common.utils;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cjl.basci.zone.framework.web.domain.AjaxResult;
import com.cjl.basci.zone.project.area.domain.CityCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class Weather {

    private static RestTemplate restTemplate;

    @Autowired
    public Weather(RestTemplate restTemplate) {
        Weather.restTemplate = restTemplate;
    }

    public static void main(String[] args) {
        Map<String, String> temAndSd1 = getHumAndsd("110102");
        Map<String, String> temAndSd2 = getHumAndsd("130108");
        System.out.println(temAndSd1);
        System.out.println(temAndSd2);
    }

    //获取天气
    public static Map<String, String> getHumAndsd(String areaCode) {
        String host = "https://weather01.market.alicloudapi.com";
        String path = "/area-to-weather";
        String appcode = "4102f2f1c1c3479292e776c76785dcbb";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("areaCode", areaCode);
        querys.put("need3HourForcast", "0");
        querys.put("needAlarm", "0");
        querys.put("needHourData", "0");
        querys.put("needIndex", "0");
        querys.put("needMoreDay", "0");

        try {
            String responseString = JSON.toJSON(restTemplate.getForEntity(host + path, AjaxResult.class).getBody()).toString();
            JSONObject showapiResBody = JSON.parseObject(responseString);
            JSONObject showapiResBody2 = JSON.parseObject(showapiResBody.getString("showapi_res_body"));
            //获取now
            JSONObject now = JSON.parseObject(showapiResBody2.getString("now"));
            Map<String, String> temAndSd = new HashMap<String, String>();
            //当前湿度
            String sd = now.getString("sd");
            temAndSd.put("sd", sd);
            //当前温度
            String temperature = now.getString("temperature");
            temAndSd.put("temperature", temperature);
            //获取f1
            JSONObject f1 = JSON.parseObject(showapiResBody2.getString("f2"));
            //白天天气"阵雨"
            String dayWeather = f1.getString("day_weather");
            temAndSd.put("dayWeather", dayWeather);
            //夜间天气"阵雨"
            String nightWeather = f1.getString("night_weather");
            temAndSd.put("nightWeather", nightWeather);
            //降水概率
            String jiangshui = f1.getString("jiangshui");
            temAndSd.put("jiangshui", jiangshui);
            //晚上气温
            String nightAirTemperature = f1.getString("night_air_temperature");
            temAndSd.put("nightAirTemperature", nightAirTemperature);
            //白天气温
            String dayAirTemperature = f1.getString("day_air_temperature");
            temAndSd.put("dayAirTemperature", dayAirTemperature);
            //大气压
            String airPress = f1.getString("air_press");
            temAndSd.put("airPress", airPress);
            //白天风力
            String dayWindPower = f1.getString("day_wind_power");
            temAndSd.put("dayWindPower", dayWindPower);
            //晚上风力
            String nightWindPower = f1.getString("night_wind_power");
            temAndSd.put("nightWindPower", nightWindPower);
            //紫外线强度
            String ziwaixian = f1.getString("ziwaixian");
            temAndSd.put("ziwaixian", ziwaixian);
            double maxTemperature = Integer.parseInt(dayAirTemperature);
            double minTemperature = Integer.parseInt(nightAirTemperature);
            double averageTemperature = (maxTemperature + minTemperature) / 2;
            if ((int) averageTemperature - averageTemperature == 0) {
                String averageTemperatures = String.valueOf(((int) averageTemperature));
                temAndSd.put("averageTemperature", averageTemperatures);
            } else {
                String averageTemperatures = String.format("%.1f", averageTemperature);
                temAndSd.put("averageTemperature", String.valueOf(averageTemperatures));
            }

            //平均气温
            return temAndSd;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //获取城市code
    public static List<CityCode> getCityId(String areaName) {
        String host = "http://saweather.market.alicloudapi.com";
        String path = "/area-to-id";
        String appcode = "4102f2f1c1c3479292e776c76785dcbb";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("area", areaName);

        try {
            String responseString = JSON.toJSON(restTemplate.getForEntity(host + path, AjaxResult.class).getBody()).toString();
            JSONObject showapiResBody = JSON.parseObject(responseString);
            JSONObject showapi_res_body = JSON.parseObject(showapiResBody.getString("showapi_res_body"));
            List<CityCode> list = JSONArray.parseArray(showapi_res_body.getString("list"), CityCode.class);

            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //    根据区域名称返回温度湿度  只能是区 比如西青区
//    public static WeatherResult getWeatherResult(String areaName){
//        List<CityCode> list=getCityId(areaName);
//        String humidity= getHumidity(list.get(0).getAreaCode());
//        String temperature= getTemperature(list.get(0).getAreaCode());
//        WeatherResult weatherResult=new WeatherResult();
//        weatherResult.setHumidity(humidity);
//        weatherResult.setTemperature(temperature);
//        return  weatherResult;
//    }
    public static List<Map<String, Object>> selectWeatherList(List<Weather> weatherlist) {
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();

//        for (int i = 0; i < weatherlist.size(); i++) {
//            String areaCode = weatherlist.get(i).getAreaCode();
//            Map<String, String> temAndSd = Weather.getHumAndsd(areaCode);
//            Map<String, Object> WeayherResult = new HashMap<String, Object>();
//            WeayherResult.put("areaId", weatherlist.get(i).getCountId());
//            //当前气温
//            WeayherResult.put("temperature", temAndSd.get("temperature"));
//            //湿度
//            WeayherResult.put("humidity", temAndSd.get("sd"));
//            //平均气温
//            WeayherResult.put("averageTemperature", temAndSd.get("averageTemperature"));
//            //降水率
//            WeayherResult.put("jiangshui", temAndSd.get("jiangshui"));
//            //白天气温
//            WeayherResult.put("nightAirTemperature", temAndSd.get("nightAirTemperature"));
//            //夜间气温
//            WeayherResult.put("dayAirTemperature", temAndSd.get("dayAirTemperature"));
//            //夜间天气
//            WeayherResult.put("nightWeather", temAndSd.get("nightWeather"));
//            //白天天气
//            WeayherResult.put("dayWeather", temAndSd.get("dayWeather"));
//            //夜间风力
//            WeayherResult.put("nightWindPower", temAndSd.get("nightWindPower"));
//            //白天风力
//            WeayherResult.put("dayWindPower", temAndSd.get("dayWindPower"));
//            //大气压
//            WeayherResult.put("airPress", temAndSd.get("airPress"));
//            //紫外线强度
//            WeayherResult.put("ziwaixianp", temAndSd.get("ziwaixian"));
//            result.add(WeayherResult);
//        }
        return result;
    }

}
