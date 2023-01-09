package com.increff.employee.util;
import java.util.Date;

public class DateUtil {
    public static Date MillisecondToDate(long longDate){
        Date date = new Date(longDate);
        return date;
    }
}
