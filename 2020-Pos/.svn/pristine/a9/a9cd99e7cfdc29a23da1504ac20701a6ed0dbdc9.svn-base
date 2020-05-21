package com.mike.baselib.utils;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@SuppressLint("SimpleDateFormat")
public class DateUtils {

    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT1 = "yyyy/MM/dd HH:mm:ss";
    public static final String yyyyMMdd = "yyyy-MM-dd";
    public static final String DATE_FORMAT3 = "yyyy-MM-dd";
    public static final String DATE_FORMAT4 = "yyyy-MM-dd HH:mm";
    public static final String DATE_FORMAT5 = "yyyy/MM/d HH:mm";

    /**
     * @param timeMillis
     * @return
     */
    public static String formatDate(long timeMillis) {
        return formatDate(timeMillis, DATE_FORMAT);
    }

    public static String formatDate(long timeMillis, String format) {
        return formatDate(timeMillis <= 0 ? new Date() : new Date(timeMillis), format);
    }

    public static String formatDate(Date date, String format) {
        return new SimpleDateFormat(format).format(date);
    }

    public static String formatDate(Date date) {
        return formatDate(date, yyyyMMdd);
    }

    /*
     * 将时间转换为时间戳
     */
    public static long dateToStamp(String s) throws ParseException {
        return dateToStamp(s, yyyyMMdd);
    }
    /*
     * 将时间转换为时间戳
     */

    public static long dateToStamp(String s, String format) throws ParseException {
        return new SimpleDateFormat(format).parse(s).getTime();
    }


    public static Calendar dateToCalendar(String ymd) throws ParseException {
        long timeMills = dateToStamp(ymd, yyyyMMdd);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(timeMills));
        return calendar;
    }

    /**
     * 获取当天
     *
     * @return
     */
    public static String getCurrentDay() {
        return formatDate(0, yyyyMMdd);
    }

    /**
     * 获取昨天
     *
     * @return
     */
    public static String getYesterday() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        return new SimpleDateFormat(yyyyMMdd).format(calendar.getTime());
    }
    /**
     * 获取近三天
     *
     * @return
     */
    public static String getNearlyThreeDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -3);
        return new SimpleDateFormat(yyyyMMdd).format(calendar.getTime());
    }
    /**
     * 获取近7天
     *
     * @return
     */
    public static String getNearlySevenDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -7);
        return new SimpleDateFormat(yyyyMMdd).format(calendar.getTime());
    }
    /**
     * 获取近30天
     *
     * @return
     */
    public static String getNearlyThirtyDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -30);
        return new SimpleDateFormat(yyyyMMdd).format(calendar.getTime());
    }

    /**
     * 获取当前月第一天
     */
    public static String getCurrentMonthFirstDay() {
        return getMonthFistDay(0);
    }

    /**
     * 获取每个月第一天
     *
     * @param amount
     * @return
     */
    public static String getMonthFistDay(int amount) {
        return getMonthFistDay(amount, yyyyMMdd);
    }

    /**
     * 获取每个月最后一天
     *
     * @param amount
     * @return
     */
    public static String getMonthLastDay(int amount) {
        return getMonthLastDay(amount, yyyyMMdd);
    }

    /**
     * 获取当前月最后一天
     *
     * @return
     */
    public static String getCurrentMonthLastDay() {
        return getMonthLastDay(1, yyyyMMdd);
    }


    /**
     * 获取当前月,截止到今天
     *
     * @returnr
     */
    public static String[] getCurrentMonth() {
        String[] days = new String[2];
        days[0] = getMonthFistDay(0);
        days[1] = getCurrentDay();
        return days;
    }

    /**
     * 获取上个月头尾
     *
     * @return
     */
    public static String[] getLastMonth() {
        String[] days = new String[2];
        days[0] = getMonthFistDay(-1);
        days[1] = getMonthLastDay(0);
        return days;
    }

    /**
     * 获取近两个月,截止到今天
     *
     * @return
     */
    public static String[] getNearlyTwoMonth() {
        String[] days = new String[2];
        days[0] = getMonthFistDay(-1);
        days[1] = getCurrentDay();
        return days;
    }

    /**
     * 获取近三个月,截止到今天
     *
     * @return
     */
    public static String[] getNearlyThreeMonth() {
        String[] days = new String[2];
        days[0] = getMonthFistDay(-2);
        days[1] = getCurrentDay();
        return days;
    }


    public static String getMonthFistDay(int amount, String format) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, amount);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return new SimpleDateFormat(format).format(calendar.getTime());
    }


    public static String getMonthLastDay(int amount, String format) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, amount);
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        return new SimpleDateFormat(format).format(calendar.getTime());
    }

    public static Calendar getYearFirstDayCalendar(int amount) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, amount);
        calendar.set(Calendar.DAY_OF_YEAR, 1);
        return calendar;
    }
}
