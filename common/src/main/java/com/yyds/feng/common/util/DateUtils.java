package com.yyds.feng.common.util;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 日期时间 工具类
 */

public class DateUtils {

    public static int getSid(){
        GregorianCalendar ca = new GregorianCalendar();
        int index = new GregorianCalendar().get(GregorianCalendar.AM_PM);
        if(index==0){
            return 3;
        }else{
            return 4;
        }
    }

    public static Long getSeckillTime(){
        String dateStr = getDate();
        if (getSid() == 3){
            dateStr = dateStr + " 10:30:00";
        } else {
            dateStr = dateStr + " 14:00:00";
        }
        return date2TimeStamp(dateStr);
    }
    public static String getSysTime(){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date date = new Date();
        String dateStr = sdf.format(date);

        return dateStr;

    }

    public static String getSysTimeForUpload(){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

        Date date = new Date();
        String dateStr = sdf.format(date);

        return dateStr;

    }
    // 将时间格式转化为字符串
    public static String getSysTimeForUpload(String date){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

        String dateStr = sdf.format(date);

        return dateStr;

    }

    public static String getDate() {
        return getDate("yyyy-MM-dd");
    }

    public static String getDate(String pattern) {
        return DateFormatUtils.format(new Date(), pattern);
    }

    public static Long date2TimeStamp(String date_str){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.parse(date_str).getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int getHour(){
        String hour = getDate("HH");
        int i = Integer.parseInt(hour);
        return i;
    }
}
