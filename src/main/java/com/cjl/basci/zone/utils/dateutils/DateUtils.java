package com.cjl.basci.zone.utils.dateutils;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * 时间工具类
 *
 * @author wangsen
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {

    public static String YYYY = "yyyy";

    public static String YYYY_MM = "yyyy-MM";

    public static String YYYY_MM_DD = "yyyy-MM-dd";

    public static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    private static String[] parsePatterns = {
            "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
            "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};

    /**
     * 获取当前Date型日期
     *
     * @return Date() 当前日期
     */
    public static Date getNowDate() {
        return new Date();
    }

    /**
     * 获取当前日期, 默认格式为yyyy-MM-dd
     *
     * @return String
     */
    public static String getDate() {
        return dateTimeNow(YYYY_MM_DD);
    }

    public static final String getTime() {
        return dateTimeNow(YYYY_MM_DD_HH_MM_SS);
    }

    public static final String dateTimeNow() {
        return dateTimeNow(YYYYMMDDHHMMSS);
    }

    public static final String dateTimeNow(final String format) {
        return parseDateToStr(format, new Date());
    }

    public static final String dateTime(final Date date) {
        return parseDateToStr(YYYY_MM_DD, date);
    }

    public static final String parseDateToStr(final String format, final Date date) {
        return new SimpleDateFormat(format).format(date);
    }

    public static final Date dateTime(final String format, final String ts) {
        try {
            return new SimpleDateFormat(format).parse(ts);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 日期路径 即年/月/日 如2018/08/08
     */
    public static final String datePath() {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyyMMdd/");
    }

    /**
     * 日期路径 即年/月/日 如20180808
     */
    public static final String dateTime() {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyyMMdd");
    }

    /**
     * 日期型字符串转化为日期 格式
     */
    public static Date parseDate(Object str) {
        if (str == null) {
            return null;
        }
        try {
            return parseDate(str.toString(), parsePatterns);
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date getDate(String date) {
        Date parse = null;
        if (date.contains("年") && date.contains("月") && date.contains("日")) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
            try {
                parse = format.parse(date);
                return parse;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else if (date.contains("年") && date.contains("月") && date.contains("号")) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd号");
            try {
                parse = format.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else if (date.contains("年") && date.contains("月")) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月");
            try {
                parse = format.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else if (date.contains("-")) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            try {
                parse = format.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return parse;
    }


    /**
     * [获取当前月]
     *
     * @return [返回值]
     * @author xiaojie
     * @date 2021/1/26 18:14
     */
    public static int getCurrentMonth() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MONTH) + 1;
    }
    //TODO 万能接口需要用的东西

    /**
     * 获取当天到指定天数的日期列表
     *
     * @param days 天数
     * @return 正数往后推n天的日期列表，负数为往前推n天的日期列表，0为当天日期
     * @author xiaojie
     */
    public static List<String> dateListCurrentOfDay(int days) {
        //日期格式化
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        //日历对象
        Calendar cale;
        //存储集合
        LinkedList<String> dateList = new LinkedList<String>();
        //判断是往前还是往后
        boolean isAdd = false;
        if (days > 0) {
            isAdd = true;
        } else if (days < 0) {
            isAdd = false;
        } else {
            cale = Calendar.getInstance();
            cale.add(Calendar.DATE, days);
            dateList.add(format.format(cale.getTime()));
        }

        while (days != 0) {
            cale = Calendar.getInstance();
            cale.add(Calendar.DATE, days > 0 ? --days : ++days);
            if (isAdd) {
                dateList.addFirst(format.format(cale.getTime()));
            } else {
                dateList.addLast(format.format(cale.getTime()));
            }
        }
        return dateList;
    }
}
