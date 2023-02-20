package com.increff.employee.util;

public class NumberUtil {
    public static boolean has2DecimalPlaces(Double d) {
        String[] splitter = d.toString().split("\\.");
        splitter[0].length();   // Before Decimal Count
        int decimalLength = splitter[1].length();  // After Decimal Count

        if (decimalLength <= 2) {
            return true;
        } else {
            return false;
        }
    }
    public static boolean isNegative(Double d){
        return d <= 0;
    }
    public static boolean isWholeNumber(Double d){
        boolean result = ((d - Math.floor(d)) == 0);
        return result;
    }
}
