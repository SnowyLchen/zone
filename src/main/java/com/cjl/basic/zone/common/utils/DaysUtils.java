package com.cjl.basic.zone.common.utils;

import com.alibaba.fastjson.JSON;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DaysUtils {
    private static final String[] arr = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};

    public static class Day {
        private int year;
        private int month;
        private int date;
        private String week;

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }

        public int getMonth() {
            return month;
        }

        public void setMonth(int month) {
            this.month = month;
        }

        public int getDate() {
            return date;
        }

        public void setDate(int date) {
            this.date = date;
        }

        public String getWeek() {
            return week;
        }

        public void setWeek(String week) {
            this.week = week;
        }

        @Override
        public String toString() {
            return JSON.toJSONString(this);
        }
    }

    /**
     * 根据开始日期和结束日期获取每一天
     *
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return 每日集合
     */
    public static List<Day> createDays(Date startDate, Date endDate) {
        List<Day> days = new ArrayList<>();
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(startDate);
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(endDate);
        while (startCalendar.compareTo(endCalendar) < 0) {
            Day day = new Day();
            day.setDate(startCalendar.get(Calendar.DATE));
            day.setMonth(startCalendar.get(Calendar.MONTH) + 1);
            day.setYear(startCalendar.get(Calendar.YEAR));
            day.setWeek(arr[startCalendar.get(Calendar.DAY_OF_WEEK) - 1]);
            days.add(day);
            startCalendar.add(Calendar.DATE, 1);
        }
        return days;
    }

    public static List<Map<String, Integer>> getDays(Date startDate, Date endDate) {
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(startDate);
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(endDate);
        List<Map<String, Integer>> days = new ArrayList<>();
        boolean isFirst = true;
        do {
            Map<String, Integer> dayMap = new HashMap<>();
            if (equalsDate(startCalendar, endCalendar)) {
                String dateKey = startCalendar.get(Calendar.YEAR) + String.format("%02d", startCalendar.get(Calendar.MONTH) + 1);
                dayMap.put(dateKey, endCalendar.get(Calendar.DATE) - startCalendar.get(Calendar.DATE) + 1);
                days.add(dayMap);
                break;
            }
            if (isFirst) {
                String dateKey = startCalendar.get(Calendar.YEAR) + String.format("%02d", startCalendar.get(Calendar.MONTH) + 1);
                dayMap.put(dateKey, startCalendar.getActualMaximum(Calendar.DAY_OF_MONTH) - startCalendar.get(Calendar.DATE) + 1);
            } else {
                startCalendar.add(Calendar.MONTH, 1);
                String dateKey = startCalendar.get(Calendar.YEAR) + String.format("%02d", startCalendar.get(Calendar.MONTH) + 1);
                if (endCalendar.compareTo(startCalendar) <= 0) {
                    dayMap.put(dateKey, endCalendar.get(Calendar.DATE));
                } else {
                    dayMap.put(dateKey, startCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                }
            }
            startCalendar.set(Calendar.DATE, startCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            days.add(dayMap);
            isFirst = false;
        } while (startCalendar.compareTo(endCalendar) < 0);
        return days;
    }

    private static boolean equalsDate(Calendar start, Calendar end) {
        boolean isSameYear = start.get(Calendar.YEAR) == end.get(Calendar.YEAR);
        boolean isSameMonth = start.get(Calendar.MONTH) == end.get(Calendar.MONTH);
        return isSameYear && isSameMonth;
    }

    public static void main(String[] args) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            List<Map<String, Integer>> days = getDays(format.parse("2020-7-30"), format.parse("2020-7-30"));
            System.out.println(days);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
