package com.increff.employee.util;
import java.util.Date;

public class DateUtil {
    public static Date MillisecondToDate(long longDate){
        Date date = new Date(longDate);
        return date;
    }

    public static long DateToMillisecond(Date date){
        return date.getTime();
    }
}
