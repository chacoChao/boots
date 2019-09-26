package com.chaco.chao.tools.utils;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Function:
 * <p>
 * User: chenhongliang001@ke.com
 * Date: 2019-05-10 11:21:00
 */
@Slf4j
public class DateUtil {

    public final static String YYYY_MM_DD_HH_MM_SS="yyyy-MM-dd HH:mm:ss";


    public static Date getBeforeMinute(int n){
        Calendar beforeTime = Calendar.getInstance();
        beforeTime.add(Calendar.MINUTE, -n);
        return beforeTime.getTime();
    }

    /**
     * dateToString 将日期转成指定格式的字符串
     *
     * @param date
     * @param formatPattern
     * @return
     */
    public static String dateToString(Date date, String formatPattern){
        SimpleDateFormat format = new SimpleDateFormat(formatPattern);
        String time = format.format(date.getTime());
        return time;
    }

    /**
     * dateToString 将日期转成指定格式的字符串(yyyy-MM-dd HH:mm:ss)
     *
     * @param date
     * @return
     */
    public static String dateToString(Date date){
        if(date==null){
            return null;
        }
        SimpleDateFormat format = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
        String time = format.format(date.getTime());
        return time;
    }


    /**
     * stringToDate 将字符串转成日期
     *
     * @param dateStr
     * @return
     */
    public static Date stringToDate(String dateStr){
        SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
        try {
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            log.error("stringToDate ParseException errorMsg:{}, dateStr:{}", e, dateStr);
            return null;
        }
    }

    /**
     * stringToDate 将字符串转成日期
     *
     * @param dateStr
     * @return
     */
    public static Date stringToDate(String dateStr, String pattern){
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            log.error("stringToDate ParseException errorMsg:{}, dateStr:{}, pattern:{}", e, dateStr, pattern);
            return null;
        }
    }

    public static void main(String[] args) {
        System.out.println(getBeforeMinute(5));
    }

}

