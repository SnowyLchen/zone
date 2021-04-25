package com.cjl.basic.zone.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author chen
 * @Date 2020/4/8 17:06
 * @Version 1.0
 */
public class IdGenerat {
    public static String getDate(String sformat) {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(sformat);
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    public static String getRandomNum(int num){
        String numStr = "";
        for(int i = 0; i < num; i++){
            numStr += (int)(10*(Math.random()));
        }
        return numStr;
    }

    public static String getGeneratID(){
        long time = System.currentTimeMillis();
        String idStr =String.valueOf(time)+getRandomNum(6);
        return idStr;
    }
}
