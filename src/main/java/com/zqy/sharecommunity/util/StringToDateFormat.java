package com.zqy.sharecommunity.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author zqy
 * @Date 2019/12/24
 */
public class StringToDateFormat {

    public static  Date StringToDate(String time) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date date = sdf.parse(time);

        return date;
    }


    public static  Date StringToDateFomat(String time) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        Date date = sdf.parse(time);

        return date;
    }
}
