package com.increff.employee.util;

public class StringUtil {

	public static boolean isEmpty(String s) {
		return s == null || s.trim().length() == 0;
	}
	public static boolean isLongString(String s){
		return s.length() >= 20;
	}

	public static String toLowerCase(String s) {
		return s == null ? null : s.trim().toLowerCase();
	}
	public static Boolean hasSpecialCharacter(String s){
		for (int i = 0; i < s.length(); i++) {
			if (!Character.isDigit(s.charAt(i))
					&& !Character.isLetter(s.charAt(i))
					&& !Character.isWhitespace(s.charAt(i))) {
				return true;
			}
		}
		return false;
	}
}
